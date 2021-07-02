/**
* sam@here 2019年10月21日
**/
package com.pbsaas.connect.gateway.filter;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.pbsaas.connect.core.AppConstants;
import com.pbsaas.connect.core.model.JsonBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import com.pbsaas.connect.gateway.config.FilterIgnorePropertiesConfig;
import com.pbsaas.connect.gateway.config.security.TokenInfo;
import com.pbsaas.connect.gateway.filter.decorator.AccessFilterHttpRequestDecorator;
import com.pbsaas.connect.gateway.utils.GatewayLogUtil;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccessGatewayFilter implements GlobalFilter , Ordered{

	private static final Logger log = LoggerFactory.getLogger(AccessGatewayFilter.class);
	
	private  static  final String prefix="token_";
	
	@Autowired
	public RedisTemplate<String, Object> redisTemplate;
	  
    @Resource
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;
    
    //@Reference(version = "1.6.0")
    //SysAccountService sysAccountService;
    
    @Override
    public int getOrder() {
        return -100;
    }
    
	@Override
	    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain filterChain) {
	        log.info("check token and user permission....");
	        
	        ServerHttpRequest request = exchange.getRequest();
	        ServerHttpResponse response = exchange.getResponse();
		    
	        URI originalRequestUrl = request.getURI();
	        
	        String scheme = originalRequestUrl.getScheme();
	        if ((!"http".equals(scheme) && !"https".equals(scheme))) {
	            return filterChain.filter(exchange);
	        }

	        String upgrade = request.getHeaders().getUpgrade();
	        if ("websocket".equalsIgnoreCase(upgrade)) {
	            return filterChain.filter(exchange);
	        }
	        
	        TokenInfo tokenInfo=null;
	        
	        //reform
	        final String method = request.getMethod().toString();
	        String requestUri = request.getPath().pathWithinApplication().value().toLowerCase();
	        String clientIp = GatewayLogUtil.getIpAddress(request);
	        
	        MultiValueMap<String, String> requestQueryParams = request.getQueryParams();

            log.info("GET 参数："+requestQueryParams);
            
            //资源文件忽略
            if(requestUri.endsWith(".css")||requestUri.endsWith(".js")||requestUri.endsWith(".ico")
            		||requestUri.endsWith(".jpg")||requestUri.endsWith(".png")||requestUri.endsWith(".gif")
            		||requestUri.endsWith(".eot")||requestUri.endsWith(".svg")||requestUri.endsWith(".ttf")
            		||requestUri.endsWith(".woff")||requestUri.endsWith(".otf")) {
            	
            	return filterChain.filter(exchange);
            }
            
            // 不进行拦截的地址
	        if(filterIgnorePropertiesConfig.getUrls().stream().anyMatch(url->requestUri.startsWith(url.toLowerCase()))) {
	        	
	        	return filterChain.filter(exchange);
	        }
	        
	        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
	        
	        if (authHeader == null) {
	        	
	        	log.warn("not find AUTHORIZATION or get Token, url:"+requestUri+", code:"+authHeader);
	        	
	        	return respErrMsg(response, new JsonBody<>(-401,"token为空，限制访问"));
	        	
	        }else {
	        	
	        	 String token = authHeader.replace("Bearer", "").trim();

	 	        try {
					Object val = redisTemplate.boundValueOps(prefix + token).get();

					if (val == null) {

						Claims claims = Jwts.parser().setSigningKey("test_key".getBytes()).parseClaimsJws(token)
								.getBody();

						tokenInfo = new TokenInfo(claims);

						redisTemplate.opsForValue().set(prefix + token, tokenInfo, 5, TimeUnit.MINUTES);

					} else {
						tokenInfo = (TokenInfo) val;
						
						redisTemplate.opsForValue().set(prefix + token, tokenInfo, 5, TimeUnit.MINUTES);
					}

	 	            log.info("requestUri:{}, token:uid:{},name:{}  ",requestUri, tokenInfo.getUserId(),tokenInfo.getUserName());
	/** 	            
	 		        ResultVO<Integer> checkPermission=sysAccountService.checkUrlPermissionByUid(requestUri, method.toLowerCase(), tokenInfo.getUserId());
	 		        
	 		        if(checkPermission.getResult()!=1) {
	 		        	
	 		        	return respErrMsg(response, new JsonBody<>(-100,"url权限校验失败"));
	 		        }
	 		        
	 		        if(checkPermission.getData()<=0) {
	 		        	
	 		        	return respErrMsg(response, new JsonBody<>(-403,"抱歉，没有权限访问"));
	 		        }
**/
	 	         } catch (SignatureException e) {
	 	             // 验证错误
	 	        	 log.warn("jwt token parse error: {}", e.getMessage());
	 	        	 
	 	        	return respErrMsg(response, new JsonBody<>(-401,"token验证失败"));
	 	        	
	 	         } catch (ExpiredJwtException e) {
	 	             // token 超时
	 	        	 log.warn("jwt token is expired:{}",e.getMessage());
	 	        	 
	 	        	return respErrMsg(response, new JsonBody<>(-401,"token已过期，请重新获取"));
	 	        	 
	 	         } catch (MalformedJwtException e) {
	 	             // token Malformed
	 	        	 log.warn("jwt token is malformed,err:"+e.getMessage());
	 	        	 
	 	        	return respErrMsg(response, new JsonBody<>(-401,"token格式不正确"));
	 	         }
	        }
	        
	        keepLog(requestUri,method,tokenInfo.getUserId(),clientIp,new Gson().toJson(requestQueryParams.toSingleValueMap()),"","");
	       
	        if ("POST".equals(method)&&tokenInfo!=null) {
	 	        	
		        //封装request，传给下一级
	            AccessFilterHttpRequestDecorator requestDecorator = new AccessFilterHttpRequestDecorator(exchange.getRequest(),tokenInfo.getUserId());

		        return filterChain.filter(exchange.mutate().request(requestDecorator).build());
	            
	        } else if ("GET".equals(method)) {
	            
	        	String newUrl=request.getURI().toString()+(requestQueryParams.size()==0?"?uid="+tokenInfo.getUserId():"&uid="+tokenInfo.getUserId());
	        	
				try {
					ServerHttpRequest build = request.mutate().uri(new URI(newUrl))
					       .build();
					
			        return filterChain.filter(exchange.mutate().request(build).build());
			        
				} catch (URISyntaxException e) {

					log.error("uri ",e);
				}
	        }
	   	        
	        return filterChain.filter(exchange.mutate().request(request).build())
	        		.then( );
	    }
	 
	   /**
	     * 网关抛异常
	     *
	     * @param body
	    */
	    private Mono<Void> respErrMsg(ServerHttpResponse response, JsonBody<Object> body) {
	        
	    	response.setStatusCode(HttpStatus.OK);
	    	
	        byte[] bytes = (new Gson()).toJson(body).getBytes(StandardCharsets.UTF_8);
	        
	        DataBuffer buffer = response.bufferFactory().wrap(bytes);
	        
	        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            
	        //!!!
			HttpHeaders  header= response.getHeaders();
			
			header.setAccessControlAllowCredentials(true);
			
			header.set("Access-Control-Allow-Origin", "*");
			header.set("Access-Control-Allow-Credentials", "true");
			header.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			header.set("Access-Control-Max-Age", "3600");
			header.set("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization,x-requested-with");
	   	
	        return response.writeWith(Flux.just(buffer));
	        
	    }
	    
	    private void keepLog(String requestUri,String method,String uid,String ip,String reqString,String reqbody,String resp) {
	    	
	    	String prefix=requestUri.substring(1,requestUri.indexOf("/", 1));
	    	String url=requestUri.substring(requestUri.indexOf("/", 1));
	    	
			Map<String,Object> extra=new HashMap<>();
			extra.put("prefix", prefix);
			extra.put("url", url);
			extra.put("method", method);
			extra.put("uid", uid);
			extra.put("ip", ip);
			
			extra.put("reqString", reqString);
			extra.put("reqbody", reqbody);
			extra.put("resp", resp);
				
			redisTemplate.convertAndSend(AppConstants.SUB_GATEWAY_LOG_TOPIC, extra);
	    }
	    
}


