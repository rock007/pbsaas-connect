/**
* sam@here 2019年10月23日
**/
package com.pbsaas.connect.gateway.filter;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;


public class ReqTimeCheckGateWayFilterFactory extends AbstractGatewayFilterFactory<ReqTimeCheckConfig>{

	private static final Logger log = LoggerFactory.getLogger(ReqTimeCheckGateWayFilterFactory.class);

	private static final String COUNT_Start_TIME = "countStartTime";
    private static final String KEY = "withParams";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    public ReqTimeCheckGateWayFilterFactory() {
        super(ReqTimeCheckConfig.class);
    }

    @Override
    public GatewayFilter apply(ReqTimeCheckConfig config) {
        return (exchange, chain) -> {
            exchange.getAttributes().put(COUNT_Start_TIME, System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute(COUNT_Start_TIME);
                        if (startTime != null) {
                            StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                                    .append(": ")
                                    .append(System.currentTimeMillis() - startTime)
                                    .append("ms");
                            if (config.isWithParams()) {
                                sb.append(" params:").append(exchange.getRequest().getQueryParams());
                            }
                            log.debug(sb.toString());
                        }
                    })
            );
        };
    }


    
}


