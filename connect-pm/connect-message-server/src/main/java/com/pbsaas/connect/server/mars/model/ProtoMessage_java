/**
 * sam@here 2019/9/29
 **/
package com.pbsaas.connect.server.mars.model;

import com.google.protobuf.MessageLite;

public class ProtoMessage<T extends MessageLite> {

    private MsgHeader header;

    private T body;

    public ProtoMessage() {

    }

    public ProtoMessage(MsgHeader header, T body) {
        this.header = header;
        this.body = body;
    }

    public MsgHeader getHeader() {
        return header;
    }

    public void setHeader(MsgHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
