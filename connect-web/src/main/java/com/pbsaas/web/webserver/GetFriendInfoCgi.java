package com.pbsaas.web.webserver;

import com.paintfriend.backend.utils.LogUtils;
import com.paintfriend.chat.proto.FriendListRspOuterClass.ChatFriendBuff;
import com.paintfriend.chat.proto.FriendListRspOuterClass.FriendListRsp;
import com.paintfriend.chat.proto.PaintFriend;
import com.google.protobuf.Any;
import com.pbsaas.connect.db.entity.Account;
import com.pbsaas.connect.db.entity.ChatFriend;
import com.pbsaas.connect.db.entity.ChatFriendIds;
import com.pbsaas.connect.db.service.AccountService;
import com.pbsaas.connect.db.service.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/***
 * 
 * 获得用户信息
 * @author sam
 *
 */
@Path("/")
@Component
public class GetFriendInfoCgi  {

	private static final Logger logger = LoggerFactory.getLogger(GetFriendInfoCgi.class);

	@Autowired
	private FriendService friendService;
	
	@Autowired
	private AccountService accountService;
	
	@Path("/get-friend-info")
    @POST()
    @Consumes("application/octet-stream")
    @Produces("application/octet-stream")
    public Response get_my_friends(InputStream is) {
        
    	int result=1;
    	String msg="获取数据成功";
    	
    	FriendListRsp friendList=FriendListRsp.newBuilder().build();
    	
		try {
            final PaintFriend.ReqBody request = PaintFriend.ReqBody.parseFrom(is);

            logger.info(LogUtils.format("get_my_friends request from user=%s, text=%s", request.getUid(), request.getText()));

            
            String text= request.getText();
            
            Account oneAccount= accountService.findById(text);
            
			ChatFriend m=new ChatFriend();
			m.setIds(new ChatFriendIds(request.getUid(),""));
			
			Page<ChatFriend> page= friendService.searchFriends(m, 0, 256);
			
			List<ChatFriendBuff> friends = new LinkedList<ChatFriendBuff>();
			
			for(ChatFriend friend:page.getContent()){
				
				ChatFriendBuff one=ChatFriendBuff.newBuilder()
					.setNickName(friend.getNick_name())
					.setAcceptDate(friend.getAccept_date().toLocaleString())
					.setSendFrom(friend.getIds().getFriend_user())
					.setToUser(friend.getIds().getTo_user())
					.setReqStatus(friend.getReq_status())
					.setCreateDate(friend.getCreate_date().toLocaleString())
					.build();
				
				friends.add(one);
			}
			
			friendList= FriendListRsp.newBuilder()
										.addAllFriends(friends)
										.build();
			
			//data = Any.newBuilder().mergeFrom(friendList)
		    //           .build();
			
        } catch (Exception e) {
            logger.info(LogUtils.format("%s", e));
            
            result=-100;
        	msg="获取数据失败，系统出现异常";
        }

        final PaintFriend.RespBody response = PaintFriend.RespBody.newBuilder()
        		.setActid(PaintFriend.CmdID.CMD_ID_MY_FRIENDS_VALUE)
        		.setResult(result)
        		.setMsg(msg)
        		.setData(Any.pack(friendList))
        		.build();
        
        final StreamingOutput stream = new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                response.writeTo(os);
            }
        };
        return Response.ok(stream).build();
        
    }
}