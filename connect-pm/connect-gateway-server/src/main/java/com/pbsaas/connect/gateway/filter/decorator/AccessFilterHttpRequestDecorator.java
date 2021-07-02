/**
* sam@here 2019年11月12日
**/
package com.pbsaas.connect.gateway.filter.decorator;

import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.pbsaas.connect.gateway.utils.DataBufferUtilFix;
import com.pbsaas.connect.gateway.utils.DataBufferWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AccessFilterHttpRequestDecorator extends ServerHttpRequestDecorator {
	
	  private static final Logger log = LoggerFactory.getLogger(AccessFilterHttpRequestDecorator.class);
	
	  private DataBufferWrapper data=null ;
 
	  private Integer bodyLength;
	   
	  private String uid;
	  
	  public AccessFilterHttpRequestDecorator(ServerHttpRequest delegate,String uid) {
	        super(delegate);
	        
	        this.uid=uid;
	  } 
	 
	  @Override
	  public Flux<DataBuffer> getBody() {

		  Gson gson= new Gson();

	        synchronized (this) {
	            Mono<DataBuffer> mono = null;
	            if (data == null) {
	                mono = DataBufferUtilFix.join(super.getBody())
	                        .doOnNext(d -> {
	                        		this.data = d;
	                        	
	                        		byte[] data1= d.getData();
	                        		byte[] outBytes=data1;
	                        		
	                        		String bodyJson = new String(data1, Charset.forName("UTF-8"));//
	                        		log.debug("data length:"+data1.length+",bodyJson:"+bodyJson);
	                        		
	                        		try {
	                        			if(bodyJson.startsWith("{")&&bodyJson.endsWith("}")) {

											//JSONObject jsonObject = JSON.parseObject(bodyJson);
		 		                            
		 		                            //jsonObject.put("uid", uid);
											//outBytes=jsonObject.toJSONString().getBytes("UTF-8");

											JsonElement jsonObject= gson.toJsonTree(bodyJson);
											jsonObject.getAsJsonObject().addProperty("uid",uid);

											outBytes=jsonObject.toString().getBytes("UTF-8");
				                            
		                        		}else if(bodyJson.startsWith("[")&&bodyJson.endsWith("]")) {
/**
				                            JSONArray jsonArray =  JSONObject.parseArray(bodyJson);
				                            
				                            for(int i=0;i<jsonArray.size();i++){

				                            	JSONObject one = jsonArray.getJSONObject(i); 
				                            	one.put("uid", uid);
				                            }
				                            outBytes=jsonArray.toJSONString().getBytes("UTF-8");
***/
											JsonElement jsonObject= gson.toJsonTree(bodyJson);
											JsonArray jsonArray= jsonObject.getAsJsonArray();
											for(int i=0;i<jsonArray.size();i++){

												JsonElement one = jsonArray.get(i);
												one.getAsJsonObject().addProperty("uid",uid);
											}
											outBytes=jsonArray.toString().getBytes("UTF-8");

										}else {
		                        			
		                        		}
	                        			
	                        		}catch(Exception ex) {
	                        			
	                        			log.error("AccessFilterHttpRequestDecorator Exception:"+ex.getMessage());
	                        		}
		                            
		                            bodyLength=outBytes.length;
		                            
		                            this.data.setData(outBytes);
	                        	
	                        	})
	                        .filter(d -> d.getFactory() != null)
	                        .map(DataBufferWrapper::newDataBuffer);
	            } else {
	                mono = Mono.justOrEmpty(data.newDataBuffer());
	            }

	            return Flux.from(mono);
	        }
	    }
	    

		@Override
		public HttpHeaders getHeaders() {

			HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.putAll(super.getHeaders());
            Integer oldLength=Integer.parseInt(httpHeaders.getFirst(HttpHeaders.CONTENT_LENGTH));
            httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(bodyLength!=null?bodyLength:oldLength));
            
			return httpHeaders;
		}    
}