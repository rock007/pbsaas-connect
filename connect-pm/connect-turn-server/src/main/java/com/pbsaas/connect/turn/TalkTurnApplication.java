package com.pbsaas.connect.turn;

import org.ice4j.Transport;
import org.ice4j.TransportAddress;
import org.jitsi.turnserver.stack.TurnServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.net.InetAddress;

@SpringBootApplication
public class TalkTurnApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(TalkTurnApplication.class);

    private TurnServer server;

    public static void main(String[] args) {
        SpringApplication.run(TalkTurnApplication.class, args);
    }

    @PreDestroy
    public void destroy() {
        if (server != null && server.isStarted()) {
            server.shutDown();
        }
    }

    @Override
    public void run(String... args) throws Exception {

        TransportAddress localAddress = null;
        if (args.length == 2) {
            localAddress = new TransportAddress(args[0], Integer.valueOf(args[1]), Transport.UDP);
        } else {
            InetAddress inad = InetAddress.getLocalHost();

            logger.info("turn server start at address {}", inad);
            localAddress = new TransportAddress(inad, 3478, Transport.UDP);
        }
        server = new TurnServer(localAddress);
        server.start();
        // Thread.sleep(600 * 1000);
        // if (server.isStarted())
        // {
        // server.shutDown();
        // }

    }
}
