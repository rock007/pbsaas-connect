package com.pbsaas.connect.server.mars.connect;

import io.netty.channel.Channel;

import java.util.Date;

/***
 * 连接信息
 */
public class ConnInfo {

    /***
     * 连接id（唯一）
     */
    private  int cid;

    /***
     * 帐号id
     */
    private String uid;

    /***
     * 设备id
     */
    private String deviceUid;
    /***
     * 最后一次接收到的时间
     */
    private long refreshTime;

    /**
     * 连接
     */
    private Channel channel;

    /***
     * 状态（0：正常,-1:待删除）
     */
    private int status;

    /***
     * channel所在的服务器（为群集准备）
     */
    private String host;


    private String clientIp;

    private String platform;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {

        return "ConnInfo {" +
                "cid=" + cid +
                ", uid=" + uid +
                ", deviceUid=" + deviceUid +
                ", refreshTime=" + new Date(refreshTime)  +
                ", status=" + status +
                ", host=" + host +
                ", channel=" + channel +
                '}';
    }
}
