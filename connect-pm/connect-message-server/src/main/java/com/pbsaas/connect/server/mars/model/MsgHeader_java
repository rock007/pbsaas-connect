package com.pbsaas.connect.server.mars.model;

import com.pbsaas.connect.server.mars.coder.DataBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author sam
 */
public class MsgHeader implements Serializable{


    private static final Logger logger = LoggerFactory.getLogger(MsgHeader.class);

    public static final int FIXED_HEADER_SKIP = 4 + 4 + 4 + 4 + 4;

    public static final int CMDID_NOOPING = 6;
    public static final int CMDID_NOOPING_RESP = 6;

    public static final int CLIENTVERSION = 200;

    private String serviceId;

    //包头长度
    private int length;

    //客户端
    private int clientVersion;

    //操作类别
    private int cmdId;

    private int actId;

    //消息序号
    private int seq;

    //消息体
    private byte[] body;

    public boolean decode(final DataBuffer buffer) {

        if (null == buffer)
            return false;

        length = buffer.readInt();
        clientVersion = buffer.readInt();
        cmdId = buffer.readInt();
        seq = buffer.readInt();
        int bodyLen = buffer.readInt();

        // read body?
        if (bodyLen > 0) {
            body = buffer.readBytes(bodyLen);
        }

        return true;
    }

    public DataBuffer encode() {

        final int headerLength = FIXED_HEADER_SKIP;
        final int bodyLength = (body == null ? 0 : body.length);
        final int packLength = headerLength + bodyLength;

        DataBuffer db = new DataBuffer(packLength);
        db.writeInt(headerLength);
        db.writeInt(CLIENTVERSION);
        db.writeInt(cmdId);
        db.writeInt(seq);
        db.writeInt(bodyLength);

        if (body != null) {
            db.writeBytes(body);
        }

        return db;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
    }

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }
}
