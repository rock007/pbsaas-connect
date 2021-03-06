/**
 * sam@here 2019/9/6
 **/
package com.pbsaas.connect.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mars.message")
public class MarsConfig {

    /** IP地址 */
    private String ip;
    /** 端口号 */
    private int port;
    /** 文件上传后访问地址 */
    private String fileServer;

    private final Hazelcast hazelcast = new Hazelcast();

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the fileServer
     */
    public String getFileServer() {
        return fileServer;
    }

    /**
     * @param fileServer the fileServer to set
     */
    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    public Hazelcast getHazelcast() {
        return hazelcast;
    }

    public static class Hazelcast {

        private int timeToLiveSeconds = 3600;

        private int backupCount = 1;

        public int getTimeToLiveSeconds() {
            return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(int timeToLiveSeconds) {
            this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public int getBackupCount() {
            return backupCount;
        }

        public void setBackupCount(int backupCount) {
            this.backupCount = backupCount;
        }
    }
}
