package com.pbsaas.web.app.config;

import javax.sql.DataSource;

import com.pbsaas.web.app.config.security.AccountAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountAuthService userDetailsService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new StandardPasswordEncoder("dd");

        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        //encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        //encoders.put("sha256", new StandardPasswordEncoder());

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);

        DelegatingPasswordEncoder delegatingPasswordEncoder =
                (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(passwordEncoder);
        return  delegatingPasswordEncoder;
    }

    @Autowired
    private DataSource dataSource;
    
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

	 @Autowired
	 public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService)
	                .passwordEncoder(passwordEncoder());
	 }

	  @Override
	  @Bean
	  public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	  }
	  
	
    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
   
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring()
			.antMatchers("/data/**")
			.antMatchers("/plugins/**")
			.antMatchers("/vendors/**")
			.antMatchers("/js/**")
			.antMatchers("/css/**")
			.antMatchers("/images/**")
			.antMatchers("/fonts/**")
			.antMatchers("/home/**")
			.antMatchers("/**.html")
			.antMatchers("/","**.json") 
			.antMatchers("/**/articles/**")
			.antMatchers("/app/**","/test/**")
            .antMatchers("/api/send-msg.json","/api/connect-list.json")
			//.antMatchers("/oauth/**")锛侊紒锛乶o 
			.antMatchers("/mars/**")
			.antMatchers("/login.html")
            .antMatchers("/v2/api-docs", "/configuration/ui",
                    "/swagger-resources", "/configuration/security",
                    "/swagger-ui.html", "/webjars/**",
                    "/swagger-resources/configuration/ui","/swagge鈥屸�媟-ui.html")

       ;
		
	}

	/**
	@Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
	*****/
   
}