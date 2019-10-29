/**
 * sam@here 2019/9/30
 **/
package com.pbsaas.connect.server.mars.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogicManager {

    private static final Logger logger = LoggerFactory.getLogger(LogicManager.class);

    @Autowired
    private MessageServerCluster messageServerCluster;

}
