/**
* sam@here 2019年10月20日
**/
package com.pbsaas.connect.gateway.config.security;

import java.io.Serializable;
import java.util.List;

import io.jsonwebtoken.Claims;

public class TokenInfo  implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1128090353341382802L;

	private String userName;
	 
	private String userId;
	  
	private Claims _claims;
	
	private List<String> authorities;
	
	public TokenInfo() {
		
	}
	
	@SuppressWarnings("unchecked")
	public TokenInfo(Claims claims) {
		  
		_claims=claims;
		
		this.userId=claims.get("user_id", String.class);
		this.userName=claims.get("user_name", String.class);
		this.authorities=(List<String>) claims.get("authorities");
	}

	public Claims get_claims() {
		return _claims;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserId() {
		return userId;
	}

}


