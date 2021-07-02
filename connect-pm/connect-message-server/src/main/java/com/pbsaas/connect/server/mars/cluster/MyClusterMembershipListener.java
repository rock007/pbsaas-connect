package com.pbsaas.connect.server.mars.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

@Component
public class MyClusterMembershipListener implements MembershipListener {

    @Autowired
    private MessageServerManager messageServerManager;
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        String uuid = membershipEvent.getMember().getUuid();
        logger.info("建立连接: {}",  uuid);
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        String uuid = membershipEvent.getMember().getUuid();
        logger.info("关闭连接: {}", uuid);
        messageServerManager.unload(uuid);
        // userClientInfoManager.unloadServer(uuid);
    }

    @Override
    public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
        // TODO Auto-generated method stub

    }

}
