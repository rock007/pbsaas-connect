package com.pbsaas.connect.web.app.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.context.annotation.Configuration;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sam
 *
 */
@Configuration
@ApplicationPath("/stream")
public class JerseyConfig extends ResourceConfig{  
	
	private static final Logger logger = LoggerFactory.getLogger(JerseyConfig.class);
	
	public JerseyConfig() {  
    	
           register(RequestContextFilter.class);  
           //配置restful package. ！！过期了 
           //packages("com.paintfriend.mars.webserver");  
           
           scan("com.pbsaas.web.webserver");
	}

    public void scan(String... packages) {
        for (String pack : packages) {
            Reflections reflections = new Reflections(pack);
            reflections.getTypesAnnotatedWith(Provider.class)
                    .parallelStream()
                    .forEach((clazz) -> {
                        logger.debug("New provider registered: " + clazz.getName());
                        register(clazz);
                    });
            reflections.getTypesAnnotatedWith(Path.class)
                    .parallelStream()
                    .forEach((clazz) -> {
                        logger.debug("New resource registered: " + clazz.getName());
                        register(clazz);
                    });
        }
    }
}
