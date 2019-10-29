package com.pbsaas.connect.server.mars.cluster;

import java.io.IOException;
import java.io.Serializable;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;

/**
 * 用于转发的消息
 */
public class MyClusterMessage extends IMHeader implements Serializable {

    public MyClusterMessage() {

    }
    
    public MyClusterMessage(IMHeader header, MessageLite message) {
        this.setLength(header.getLength());
        this.setServiceId(header.getServiceId());
        this.setCommandId(header.getCommandId());
        this.setFlag(header.getFlag());
        this.setSeqnum(header.getSeqnum());
        this.setVersion(header.getVersion());
        this.setReserved(header.getReserved());
        this.body = message.toByteString();
    }
    
    /**
     * 
     */
    private static final long serialVersionUID = -4338840768343533177L;
    
    private ByteString body;

    /**
     * @return the body
     */
    public ByteString getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ByteString body) {
        this.body = body;
    }

    public IMHeader getHeader() {
        IMHeader header = new IMHeader();
        header.setLength(this.getLength());
        header.setServiceId(this.getServiceId());
        header.setCommandId(this.getCommandId());
        header.setFlag(this.getFlag());
        header.setSeqnum(this.getSeqnum());
        header.setVersion(this.getVersion());
        header.setReserved(this.getReserved());
        return header;
    }

    public MessageLite getMessage() throws IOException {
        byte[] content = body.toByteArray();
        MessageLite msg = ProtobufParseMap.getMessage(this.getServiceId(), this.getCommandId(), content);
        return msg;
    }
}
