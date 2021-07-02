/**
 * sam@here 2020/3/2
 **/
package com.pbsaas.connect.server.mars.connect;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.proto.Connect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProtobufParseMap {

    private static final Logger logger = LoggerFactory.getLogger(ProtobufParseMap.class);

    @FunctionalInterface
    public interface Parsing {
        MessageLite process(byte[] bytes) throws IOException;
    }

    /** Protobuf parses Map */
    public static Map<Integer, Map<Integer, Parsing>> parseServiceMap =
            new HashMap<>();

    static {

        // Initialize the parses map
        Connect.CmdID[] serviceIDs = Connect.CmdID.values();
        for (Connect.CmdID cmdID : serviceIDs) {
            parseServiceMap.put(cmdID.getNumber(),
                    new HashMap<Integer, ProtobufParseMap.Parsing>());
        }
    }

    private ProtobufParseMap() {
    }

    public static void register(final int serviceId, final int commandId, final ProtobufParseMap.Parsing parse) {
        Map<Integer, ProtobufParseMap.Parsing> parserMap = parseServiceMap.get(serviceId);
        if (parserMap == null) {
            logger.error("Protobuf service has not been registered in parseMap, serviceId: {}",
                    serviceId);
            return;
        }

        ProtobufParseMap.Parsing parser = parserMap.get(commandId);
        if (parser == null) {
            parserMap.put(commandId, parse);
        } else {
            logger.warn("Protobuf command has been registered in parseMap锛� serviceId={}, commandId={}",
                    serviceId, commandId);
            return;
        }
    }

    public static void register(final int serviceId, final int commandId, final ProtobufParseMap.Parsing parse, Class<?> claz) {
        register(serviceId, commandId, parse);

        // FIXME
        // do some thing for the reverse parsing
    }

    public static MessageLite getMessage(final int serviceId, final int commandId, final byte[] bytes)
            throws IOException {
        Map<Integer, ProtobufParseMap.Parsing> parserMap = parseServiceMap.get(serviceId);
        if (parserMap == null) {
            throw new IOException("UnKnown Protocol service: " + serviceId);
        }

        ProtobufParseMap.Parsing parser = parserMap.get(commandId);
        if (parser == null) {
            throw new IOException(
                    "UnKnown Protocol commandId: service=" + serviceId + ",command=" + commandId);
        }

        MessageLite msg = parser.process(bytes);
        return msg;
    }
}
