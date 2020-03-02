package com.pbsaas.connect.server.mars.proxy;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sam
 *
 */
public class NetMsgHeader {

	  private static final Logger logger = LoggerFactory.getLogger(NetMsgHeader.class);
		
	    private static final int FIXED_HEADER_SKIP = 4 + 4 + 4 + 4 + 4;

	    public static final int CMDID_NOOPING = 6;
	    public static final int CMDID_NOOPING_RESP = 6;

	    public static final int CLIENTVERSION = 200;

	    public int headLength;
	    public int clientVersion;
	    public int cmdId;
	    public int seq;

	    public byte[] options;
	    public byte[] body;



	    public static class InvalidHeaderException extends Exception {

	        public InvalidHeaderException(String message) {
	            super(message);
	        }
	    }

	    /**
	     * Decode NetMsgHeader from InputStream
	     *
	     * @param is close input stream yourself
	     * @return
	     * @throws IOException
	     */
	    public boolean decode(final InputStream is) throws InvalidHeaderException {
	        final DataInputStream dis = new DataInputStream(is);

	        try {
	            headLength = dis.readInt();
	            clientVersion = dis.readInt();
	            cmdId = dis.readInt();
	            seq = dis.readInt();
	            int bodyLen = dis.readInt();

	            if (clientVersion != CLIENTVERSION) {
	                throw new InvalidHeaderException("invalid client version in header, clientVersion: " + clientVersion + " packlen: " + (headLength + bodyLen));
	            }

	            //logger.debug(LogUtils.format("dump clientVersion=%d, cmdid=%d, seq=%d, packlen=%d", clientVersion, cmdId, seq, (headLength + bodyLen)));

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
	                        throw new InvalidHeaderException("invalid header body, cmdid:" + cmdId);
	                }
	            }

	            return true;

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return false;
	    }

	    public byte[] encode() throws InvalidHeaderException {
	        if (body == null && cmdId != CMDID_NOOPING && cmdId != CMDID_NOOPING_RESP) {
	            throw new InvalidHeaderException("invalid header body");
	        }

	        final int headerLength = FIXED_HEADER_SKIP + (options == null ? 0 : options.length);
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

	            if (options != null&&options.length>0) {
	                dos.write(options);
	            }

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
