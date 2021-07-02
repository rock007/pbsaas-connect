package com.pbsaas.connect.server.mars.coder;

import com.pbsaas.connect.core.model.MsgHeader;

public class DataBufferCoder {

    public static MsgHeader decode(final DataBuffer buffer) {

        MsgHeader header=new MsgHeader();

        if (null == buffer)
            return null;

        header.setLength(buffer.readInt());

        header.setClientVersion(buffer.readInt());
        header.setCmdId(buffer.readInt());
        header.setActId(buffer.readInt());
        header.setSeq(buffer.readInt());
        int bodyLen = buffer.readInt();

        // read body?
        if (bodyLen > 0) {
            header.setBody(buffer.readBytes(bodyLen));
        }

        return header;
    }

    public static DataBuffer encode(MsgHeader header) {

        final int headerLength = MsgHeader.FIXED_HEADER_SKIP;
        final int bodyLength = (header.getBody() == null ? 0 : header.getBody().length);
        final int packLength = headerLength + bodyLength;

        DataBuffer db = new DataBuffer(packLength);

        //包长
        db.writeInt(headerLength);
        //版本号
        db.writeInt(header.getClientVersion());
        //业务
        db.writeInt(header.getCmdId());
        //操作
        db.writeInt(header.getActId());
        //序号
        db.writeInt(header.getSeq());

        //data长度
        db.writeInt(bodyLength);

        //data
        if (header.getBody() != null) {
            db.writeBytes(header.getBody());
        }
        return db;
    }
}
