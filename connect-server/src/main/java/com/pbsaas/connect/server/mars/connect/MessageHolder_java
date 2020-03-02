package com.pbsaas.connect.server.mars.connect;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import com.pbsaas.connect.server.mars.proxy.NetMsgHeader;
import io.netty.channel.Channel;

/**
 * 消息载体.
 *
 * 传输模块与服务模块之间双向数据传输载体:
 *
 *                   MessageHolder
 * Service Module <----------------> Transport Module
 */
public class MessageHolder {


    private static final int FIXED_HEADER_SKIP = 4 + 4 + 4 + 4 + 4;

    public static final int CMDID_NOOPING = 6;
    public static final int CMDID_NOOPING_RESP = 6;

    public static final int CLIENTVERSION = 200;

    public static AtomicInteger seq_atomic = new AtomicInteger(10000);

	/**
	 * 包长度
	 */
	private int headLength;
	
	/**
	 * 协议标识码，固定值
	 */
	private int clientVersion=200;
	
	/**
	 * 命令类型
	 */
	public int cmdId;
	
	/**
	 * 任务号
	 */
	private int seq;

	/**
	 * 内容bytes
	 */
	public byte[] body;
   
    // 接收到消息的通道
    private Channel channel;

	public int getCmdId() {
		return cmdId;
	}

	public void setCmdId(int cmdId) {
		this.cmdId = cmdId;
	}

	public int getSeq() {
		return seq;
	}

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "MessageHolder{" +
                "cmd=" + cmdId +
                ", seq=" + seq +
                ", bodyLen=" + body.length +
                ", channel=" + channel +
                '}';
    }
    
    public boolean decode(final InputStream is) throws NetMsgHeader.InvalidHeaderException {
        final DataInputStream dis = new DataInputStream(is);

        try {
            headLength = dis.readInt();
            clientVersion = dis.readInt();
            cmdId = dis.readInt();
            seq = dis.readInt();
            int bodyLen = dis.readInt();

            if (clientVersion != CLIENTVERSION) {
                throw new NetMsgHeader.InvalidHeaderException("invalid client version in header, clientVersion: " + clientVersion + " packlen: " + (headLength + bodyLen));
            }
            // read body?
            if (bodyLen > 0) {
                body = new byte[bodyLen];
                dis.readFully(body);

            } else {
                // no body?!
                switch (cmdId) {
                    case CMDID_NOOPING:
                        break;

                    default:
                        throw new NetMsgHeader.InvalidHeaderException("invalid header body, cmdid:" + cmdId);
                }
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public byte[] encode() throws NetMsgHeader.InvalidHeaderException {
    	
        if (body == null && cmdId != CMDID_NOOPING && cmdId != CMDID_NOOPING_RESP) {
            throw new NetMsgHeader.InvalidHeaderException("invalid header body");
        }

        final int headerLength = FIXED_HEADER_SKIP ;
        final int bodyLength = (body == null ? 0 : body.length);
        final int packLength = headerLength + bodyLength;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(packLength);

        try {
            final DataOutputStream dos = new DataOutputStream(baos);

            dos.writeInt(headerLength);
            dos.writeInt(CLIENTVERSION);
            dos.writeInt(cmdId);
            dos.writeInt(seq);
            dos.writeInt(bodyLength);

            if (body != null) {
                dos.write(body);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                baos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return baos.toByteArray();
    }

}
