package com.pbsaas.connect.server.mars.proxy;

import com.pbsaas.connect.proto.PaintFriend;
import com.pbsaas.connect.server.app.AppConstants;
import com.pbsaas.connect.server.mars.connect.ConnInfo;
import com.pbsaas.connect.server.mars.connect.MessageHolder;
import com.pbsaas.connect.server.mars.connect.SendMsgQueue;
import com.pbsaas.connect.server.mars.connect.SessionPool;
import io.netty.channel.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.springframework.util.StringUtils;

/**
 * 消息处理逻辑
 * @author sam
 *
 */
public class NetMsgHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NetMsgHandler.class);

    public static AtomicInteger cid_atomic = new AtomicInteger(1000);

    public NetMsgHandler() {
        super();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        logger.info("client connected! " + ctx.toString());

        SessionPool.save(ctx.channel(),cid_atomic.getAndIncrement(),"","",System.currentTimeMillis(),0,"");
    }    
    
    @Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        ConnInfo connInfo = SessionPool.get(ctx.channel());
        if (connInfo != null) {

            logger.info(" 状态： channelInactive,删除："+connInfo.toString());
        }
        SessionPool.remove(ctx.channel());

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (evt instanceof IdleStateEvent) {
			//if (uid == null) {
				//uid = ConnPool.query(ctx.channel());
			//}

		}
	}

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	
    	PaintFriend.RespBody response;
    	
        try {
            // decode request
            final InputStream socketInput = new ByteBufInputStream((ByteBuf) msg);

            MessageHolder holder=new MessageHolder();
            
            boolean ret = holder.decode(socketInput);
            IOUtils.closeQuietly(socketInput);

            if(!ret) {

            	return;
            }

            //logger.info(LogUtils.format("client req, cmdId=%d, seq=%d", holder.cmdId, holder.getSeq()));

            //check
            ConnInfo connInfo=SessionPool.get(ctx.channel());

            if(connInfo==null){

                //不可能为空吧
                logger.warn("不可能为空吧,后面就有问题了！");

            }

            if(holder.cmdId== PaintFriend.CmdID.CMD_ID_NOOPING_VALUE){

                byte[] respBuf = holder.encode();

                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(respBuf));

                //check
                String uid=connInfo.getUid();
                if(uid==null){

                    logger.debug("没有绑定uid，要求重新登录");

                    response = PaintFriend.RespBody.newBuilder()
                            .setActid(PaintFriend.ActID.ACT_ID_NOTICE_RSP_VALUE)
                            .setResult(-100)
                            .setMsg("请先登录")
                            .build();

                    response(ctx, AppConstants.MESSAGE_PUSH,0,response.toByteArray());

                }

                //更新时间
                connInfo.setRefreshTime(System.currentTimeMillis());
                SessionPool.save(ctx.channel(),connInfo);

                return;
            }

            final PaintFriend.ReqBody request = PaintFriend.ReqBody.parseFrom(holder.body);

            //更新uid，更新时间
            connInfo.setUid(request.getUid());
            connInfo.setRefreshTime(System.currentTimeMillis());
            SessionPool.save(ctx.channel(),connInfo);

            holder.setChannel(ctx.channel());

            if(request.getActid()== PaintFriend.ActID.ACT_ID_LOGIN_VALUE){

                PaintFriend.ParamList paramList = request.getData().unpack(PaintFriend.ParamList.class);

                if(paramList!=null){

                    Map<String,String> params=new HashMap<>();
                    for(PaintFriend.Param p:paramList.getParamsList()){

                        params.put(p.getKey(),p.getValue());
                    }
                    //更新连接信息
                    connInfo.setDeviceUid(params.get("deviceId"));
                    connInfo.setClientIp(params.get("clientIp"));
                    connInfo.setPlatform(params.get("platform"));

                    //再次更新
                    if(StringUtils.isEmpty(connInfo.getUid())&&StringUtils.isEmpty(connInfo.getDeviceUid())){

                        List<ConnInfo> existConns= SessionPool.get(connInfo.getUid(),connInfo.getDeviceUid());

                        for(ConnInfo cc:existConns){

                            SessionPool.remove(connInfo.getUid(),connInfo.getDeviceUid());
                        }

                    }

                    SessionPool.save(ctx.channel(),connInfo);
                }

            }

            //直接加入栈
            if(!SendMsgQueue.getQueue().offer(request)){

                logger.warn("add sendMsgQueue fail");
            }
            logger.info("服务器回复：ok");

            response =  PaintFriend.RespBody.newBuilder()
                    .setActid(holder.cmdId)
                    .setResult(1)
                    .setMsg("ok")
                    .build();

            holder.body=response.toByteArray();
            byte[] respBuf = holder.encode();

            ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(respBuf));

        }catch (Exception e) {
        	
            logger.error("channelRead exception:", e);
           
        }finally {

            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        logger.error("exceptionCaught,channel close :", cause);

        SessionPool.remove(ctx.channel());
        ctx.close();
    }

    /**
     * 服务器响应
     *
     * @param channel
     * @param cmdId
     * @param bytes
     */
    private void response(ChannelHandlerContext channel,int cmdId,int seq,byte[] bytes) throws NetMsgHeader.InvalidHeaderException
    {
    	
        MessageHolder messageHolder = new MessageHolder();
      
        messageHolder.setCmdId(cmdId);
        messageHolder.setSeq(seq);
        messageHolder.setBody(bytes);

        byte[] respBuf = messageHolder.encode();

        ChannelFuture recChannel=channel.writeAndFlush(channel.alloc().buffer().writeBytes(respBuf));

        recChannel.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                if (future.isSuccess()) {
                    //关闭连接
                    logger.info("cmdId:"+cmdId+" 回复成功 ");

                } else {

                    logger.info("cmdId:"+cmdId+" 回复失败");
                }

            }
        });
        
    }
   

}

