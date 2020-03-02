package com.pbsaas.connect.server.mars.connect;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 管理所有与服务器建立连接的Client
 */
public class SessionPool {

    private static final Logger logger = LoggerFactory.getLogger(SessionPool.class);

    private SessionPool() {

    }

    // 用于存放在线用户的username和channel
    private static Map<Channel,ConnInfo> connsMap =
            new HashMap<>();

    public synchronized static void  save(Channel channel,Long refreshTime,int status){

        ConnInfo conn = connsMap.get(channel);

        if(conn!=null){

            conn.setChannel(channel);
            conn.setRefreshTime(refreshTime);
            conn.setStatus(status);
        }

        save(channel,conn);
    }

    public synchronized static void  save(Channel channel,Integer cid, String uid,String deviceUid,Long refreshTime,int status,String host){

        ConnInfo conn=new ConnInfo();
        conn.setChannel(channel);
        conn.setCid(cid);
        conn.setDeviceUid(deviceUid);
        conn.setHost(host);
        conn.setRefreshTime(refreshTime);
        conn.setStatus(status);

        save(channel,conn);
    }

    /**
     * 保护连接信息
     * @param channel
     * @param conn
     */
    public synchronized static void  save(Channel channel,ConnInfo conn) {

        ConnInfo result = connsMap.get(channel);

        if (result == null) {

            connsMap.put(channel,conn);

        } else {

            connsMap.replace(channel,result,conn);
        }

    }

    public synchronized static ConnInfo  get(Channel channel) {

        return connsMap.get(channel);
    }

    public synchronized static List<ConnInfo>  get(String  uid) {

        List<ConnInfo>  conns=new ArrayList<>();
        Set<Map.Entry<Channel, ConnInfo>> entries = connsMap.entrySet();
        Iterator<Map.Entry<Channel, ConnInfo>> ite = entries.iterator();
        while (ite.hasNext()) {
            Map.Entry<Channel, ConnInfo> entry = ite.next();
            if (uid.equals(entry.getValue().getUid())) {

                conns.add(entry.getValue());
            }
        }

        return conns;
    }

    public synchronized static List<ConnInfo>  get(String  uid,String deviceId) {

        List<ConnInfo>  conns=new ArrayList<>();
        Set<Map.Entry<Channel, ConnInfo>> entries = connsMap.entrySet();
        Iterator<Map.Entry<Channel, ConnInfo>> ite = entries.iterator();
        while (ite.hasNext()) {
            Map.Entry<Channel, ConnInfo> entry = ite.next();
            if (uid.equals(entry.getValue().getUid())&&deviceId.equals(entry.getValue().getDeviceUid())) {

                conns.add(entry.getValue());
            }
        }

        return conns;
    }

    public synchronized static boolean remove(Channel channel) {

        ConnInfo result = connsMap.remove(channel);

        try{

            channel.close();

        }catch (Exception e){

        }
        if (result != null) {
            logger.info("Conn池 移除成功(" + result.toString() + ")");
            return true;
        } else {
            logger.warn("Conn池 移除失败("  + ")");
            return false;
        }
    }

    /**
     * 删除连接
     *
     * @param uid
     * @return
     */
    public synchronized static void remove(String uid) {

        Set<Map.Entry<Channel, ConnInfo>> entries = connsMap.entrySet();
        Iterator<Map.Entry<Channel, ConnInfo>> ite = entries.iterator();
        while (ite.hasNext()) {
            Map.Entry<Channel, ConnInfo> entry = ite.next();
            if (uid.equals(entry.getValue().getUid())) {

                try{

                    entry.getKey().close();

                }catch (Exception e){

                }
                connsMap.remove(entry.getKey());

            }
        }

    }

    public synchronized static void remove(String uid,String deviceId) {

        Set<Map.Entry<Channel, ConnInfo>> entries = connsMap.entrySet();
        Iterator<Map.Entry<Channel, ConnInfo>> ite = entries.iterator();
        while (ite.hasNext()) {
            Map.Entry<Channel, ConnInfo> entry = ite.next();
            if (uid.equals(entry.getValue().getUid())&&deviceId.equals(entry.getValue().getDeviceUid())) {

                try{

                    entry.getKey().close();

                }catch (Exception e){

                }

                connsMap.remove(entry.getKey());

            }
        }

    }

    public synchronized static int size(){

        return  connsMap.size();
    }

    public synchronized static Map<Channel,ConnInfo> getAll(){

        return  connsMap;
    }
}
