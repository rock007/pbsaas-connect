package com.pbsaas.connect.core.model;

import java.io.Serializable;

/**
 * @author sam
 *
 * 包长#版本号#业务#操作#序号#data长度#data
 */
public class MsgHeader implements Serializable{

    public static final int FIXED_HEADER_SKIP = 4 + 4 + 4 + 4 + 4;

    public static final int CMDID_NOOPING = 6;
    public static final int CMDID_NOOPING_RESP = 6;

    public static final int CLIENTVERSION = 100;

    //private String serviceId;

    //包头长度
    private int length;

    //客户端
    private int clientVersion;

    //业务
    private int cmdId;

    //操作
    private int actId;

    //消息序号
    private int seq;

    //消息体
    private byte[] body;

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

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }
}
