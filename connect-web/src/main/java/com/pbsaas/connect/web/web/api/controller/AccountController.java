package com.pbsaas.connect.web.web.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pbsaas.connect.core.AppConstants;

import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.framework.controller.JsonBaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import com.pbsaas.connect.web.utils.StringHelper;

/**
 * @author sam
 *
 */
@Controller
@CrossOrigin( maxAge = 3600)
@RequestMapping(value="/api")
public class AccountController extends JsonBaseController {

	//@Autowired
	//private RestTemplate restTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 private Account findAccount(String uid){

			Account curUser= accountService.findById(uid);

			return curUser;
		}
		****/

		@ApiOperation(value="用户注册", notes="新用户注册")
		@RequestMapping(value="/register.json",method = RequestMethod.POST)
		public @ResponseBody
		JsonBody<Map<String, String>> register(
				String validcode,HttpSession session) {
			
			Map<String, String>  resultMap=new HashMap<String,String>();
			//!!!!
			/**
			Account m=new Account();
			if( StringUtils.isEmptyOrWhitespace(m.getMobile())){
				
				return new JsonBody<>(-1,"手机号不能为空");
			}
			if( StringUtils.isEmptyOrWhitespace(m.getPwd())){
				return new JsonBody<>(-1,"密码不能为空");
			}
			
			if( StringUtils.isEmptyOrWhitespace(validcode)){
				return new JsonBody<>(-1,"手机验证码不能为空");
			}
			
			Object valid_obj=session.getAttribute(AppConstants.CON_SESSION_VALID_CODE);
			if(valid_obj!=null&&((String)valid_obj).equals(validcode)){
				
				//ok
				
			}else{
				return new JsonBody<>(-1,"手机验证失败");
			}
			
			Account existOne= accountService.findByMobile(m.getMobile());
			if(existOne!=null){
				
				return new JsonBody<>(-2,"抱歉，该手机已经注册过用户，请用找回密码重置原来密码登录");
			}
			
			m.setCreate_date(new Date());
			m.setName(m.getMobile());
			m.setStatus(0);
			
			m.setPwd(passwordEncoder.encode(m.getPwd()));
			
			m=accountService.save(m);
			
			//roles
			accountService.addRole(m.getId(), RoleType.normal);
			 
			resultMap.put("user_id", m.getId());
			resultMap.put("user_name", m.getName());
			****/
			return new JsonBody<>(1,"注册用户成功",resultMap);
		}
		
		@RequestMapping(value="/get-mobile-code.json",method=RequestMethod.GET)
		public @ResponseBody  JsonBody<String> get_mobile_code(String user_mobile,
				 HttpSession session) throws Exception {
			
			if( StringUtils.isEmptyOrWhitespace(user_mobile)){
				
				return new JsonBody<>(-1,"user_mobile 不能为空");
			}
			
			//发送短信
			String user_valid_code=StringHelper.generateWord();
			
			session.setAttribute(AppConstants.CON_SESSION_VALID_CODE, user_valid_code);
			String resp=sendSns(user_mobile,"你的验证码为："+user_valid_code+",请不要告诉任何人");
			logger.debug("get_mobile_code resp:",resp);
		
			return new JsonBody<String>(1,"操作成功，验证码发送",user_valid_code);
		}

	@ApiOperation(value="用户登录", notes="接口取消，不要使用，要获取token")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "String")
	})
	@RequestMapping(value="/login.json",method = RequestMethod.POST)
		public @ResponseBody  JsonBody<Map<String, Object>> login(String user_name,String pwd) {
			
			Map<String, Object>  resultMap=new HashMap<String,Object>();
			
			if( StringUtils.isEmptyOrWhitespace(user_name)){
				
				return new JsonBody<>(-1,"用户名不能为空");
			}
			if( StringUtils.isEmptyOrWhitespace(pwd)){
				
				return new JsonBody<>(-1,"密码不能为空");
			}
	/**
			Account existOne=new Account();
			
			if(StringHelper.isMobileNO(user_name)){

				//支持手机号登录 20170113
				existOne=accountService.findByMobile(user_name);
				
			}else{

				existOne= accountService.findByName(user_name);	
			}

			 String roleStr="";
			 
			if(existOne!=null&&passwordEncoder.matches(pwd, existOne.getPwd())){
				
				 if (existOne.getRoles() != null) {
					 for (Role r : existOne.getRoles()) {
						 roleStr += r.getAuthority() + ",";
					 }
				}
				 
				//List<WyHouse> houses= wyHouseService.findByCreateUserAndStatus(existOne.getId(),1);
				//List<SubwayShop> shops= subwayShopService.findByCreateUserAndStatus(existOne.getId(),1);
				
				resultMap.put("user_id", existOne.getId());
				resultMap.put("user_name", existOne.getName());
				
				resultMap.put("qq_nickname", existOne.getQq_nickname());
				resultMap.put("qq_open_id", existOne.getQqOpenid());
				resultMap.put("qq_token", existOne.getQq_token());
				resultMap.put("wx_nickname", existOne.getWx_nickname());
				resultMap.put("wx_open_id", existOne.getWxOpenid());
				resultMap.put("wx_token", existOne.getWx_token());
				resultMap.put("role", roleStr);
				
				//resultMap.put("houses", houses);
				//resultMap.put("shops", shops);
				
				return new JsonBody<>(1,"登录成功",resultMap);
				
			}else{
				
				return new JsonBody<>(-1,"登录失败，用户名密码不正确");	
			}
			****/
			return  null;
		}


		@RequestMapping(value="/release-login-4-third.json",method = RequestMethod.POST)
		public @ResponseBody  JsonBody<String> release_login_4_third(HttpServletRequest request, HttpServletResponse response, String user_id,
				String openid, String token, String nickname, String from) {

			// user是否已经登录
			/***
			Account oneUser = null;

			if (StringUtils.isEmptyOrWhitespace(user_id)) {

				 return new JsonBody<String>(-1, "参数错误，user不能为空！");

			}

			oneUser = findAccount(user_id);
			if (oneUser == null) {
				
				return new JsonBody<String>(-1, "用户不存在！");
			}

			// 检查是否已经创建用户
			Account existUser = accountService.find4Third(from, openid);

			if(existUser==null){
				
				return new JsonBody<String>(-2, "openid 没有绑定用户！");

			}
			
			if (existUser.getId().equals(user_id)) {

				// 清除绑定
				oneUser = setThirdLink(from, oneUser, "", "", "");

				oneUser = accountService.save(oneUser);

			}else{
				
				return new JsonBody<String>(-2, "参数错误，非本用户不能操作！");
			}
	***/
			return new JsonBody<String>(1, "第三方账号解除绑定成功！");
			
		}
		
		@RequestMapping(value="/login-4-third.json",method = RequestMethod.POST)
		public @ResponseBody JsonBody<Map<String,Object>> login_4_third(HttpServletRequest request,HttpServletResponse response,
				String user_id,String openid,String token ,String nickname,String from,String headimgurl){

			Map<String,Object> userInfo=new HashMap<String,Object>();
			/****
			//user是否已经登录
			Account oneUser=null;
			
			if(StringUtils.isEmptyOrWhitespace(openid)){
				
				return new JsonBody<>(-1,"openid 不能为空");
			}
			
			if(StringUtils.isEmptyOrWhitespace(token)){
				
				return new JsonBody<>(-1,"token 不能为空");
			}

			if(StringUtils.isEmptyOrWhitespace(nickname)){
		
				return new JsonBody<>(-1,"nickname 不能为空");
			}

			if(StringUtils.isEmptyOrWhitespace(from)){
		
				return new JsonBody<>(-1,"from 不能为空");
			}
			
			if(StringUtils.isEmptyOrWhitespace(headimgurl)){
				
				return new JsonBody<>(-1,"headimgurl 不能为空");
			}
			
			if(!StringUtils.isEmpty(user_id)){
				
				
				//绑定第三方账号
				oneUser = findAccount(user_id);
				if(oneUser==null){
					return new JsonBody<>(-1,"用户不存在！",userInfo);
				}
				
				//检查是否已经创建用户
				Account existUser = accountService.find4Third(from, openid);
				if(existUser!=null&&!existUser.getId().equals(user_id)){
					userInfo.put("user_id", existUser.getId());
					userInfo.put("wx_nickname", existUser.getWx_nickname());
					userInfo.put("qq_nickname", existUser.getQq_nickname());
					userInfo.put("wb_nickname", existUser.getWb_nickname());
					
					return new JsonBody<>(-2,"该账号已经绑定过用户!",userInfo);
					
				}
				//绑定
				oneUser=setThirdLink(from,oneUser,openid,token,nickname);
				oneUser=accountService.save(oneUser);
				
			}else{
				oneUser = accountService.find4Third(from, openid);
				
				if(oneUser==null){
					//add
					
					Account newAccount=new Account();
					newAccount.setName(nickname);
					newAccount.setPwd(passwordEncoder.encode("123321！"));
					newAccount.setCreate_date(new Date());
					newAccount.setUser_level(10);
					newAccount.setStatus(0);
					newAccount.setUser_money(0.0f);
				
					newAccount.setAvatar(headimgurl);
					
					newAccount=setThirdLink(from,newAccount,openid,token,nickname);
					
					//add reg_from by sam@20170512 
					newAccount.setReg_from(from);
					newAccount.setStatus(0);
					oneUser=accountService.save(newAccount);

					//roles
					accountService.addRole(oneUser.getId(),RoleType.normal);
					
				}else{
					//update
					oneUser=setThirdLink(from,oneUser,openid,token,nickname);
					oneUser=accountService.save(oneUser);
				}
			
			}
			
			userInfo.put("user_id", oneUser.getId());
			userInfo.put("user_name", oneUser.getName());
			
			//第三方登录信息
			userInfo.put("qq_nickname", oneUser.getQq_nickname());
			userInfo.put("qq_open_id", oneUser.getQqOpenid());
			userInfo.put("qq_token", oneUser.getQq_token());
			userInfo.put("wx_nickname", oneUser.getWx_nickname());
			userInfo.put("wx_open_id", oneUser.getWxOpenid());
			userInfo.put("wx_token", oneUser.getWx_token());
			userInfo.put("from", from);

			//modify roles by sam@20170512 
			 String roleStr="";
			 for(Role r:oneUser.getRoles()){
				 roleStr+=r.getAuthority()+",";
			 }
			 
			//List<WyHouse> houses= wyHouseService.findByCreateUserAndStatus(oneUser.getId(),1);
			//List<SubwayShop> shops= subwayShopService.findByCreateUserAndStatus(oneUser.getId(),1);
			
			userInfo.put("roles", roleStr);
			//userInfo.put("houses", houses);
			//userInfo.put("shops", shops);
			****/
			return new JsonBody<>(1,"用户第三方账号登录成功！",userInfo);
		}

		/***
		private Account setThirdLink(String from,Account oneUser,String openid,String token,String nickname){
			
			if(from.equals("qq")){
	    		oneUser.setQqOpenid(openid);
	    		oneUser.setQq_token(token);
	    		oneUser.setQq_nickname(nickname);
	    		
	    	}else if(from.equals("wx")){
	    		oneUser.setWxOpenid(openid);
	    		oneUser.setWx_token(token);
	    		oneUser.setWx_nickname(nickname);
	    		
	    	}else if(from.equals("weibo")){
	    		oneUser.setWbOpenid(openid);
	    		oneUser.setWb_token(token);
	    		oneUser.setWb_nickname(nickname);
	    	}
			
			return oneUser;
		}
***/
		@RequestMapping(value="/find-pwd.json",method = RequestMethod.POST)
		public @ResponseBody  JsonBody<String> find_pwd(String user_mobile) throws Exception {

			/***
			if( StringUtils.isEmptyOrWhitespace(user_mobile)){
				
				return new JsonBody<>(-1,"user_mobile 不能为空");
			}
			Account user= accountService.findByMobile(user_mobile);
			if(user==null){
				
				return new JsonBody<>(-1,"手机未注册");
			}
			
			//发送短信
			String user_pwd_new=StringHelper.generateWord();
			
			String resp=sendSns(user_mobile,"你的新密码为："+user_pwd_new+",请登录后及时修改密码");
			logger.debug("find_pwd resp:",resp);
			user.setPwd(passwordEncoder.encode(user_pwd_new));
		 	accountService.save(user);
****/
			return new JsonBody<String>(1,"操作成功，新密码已经发送，请登录后修改密码");
		}
		
		@RequestMapping(value = "/change-pwd.json",method = RequestMethod.POST)
		public @ResponseBody  JsonBody<String> change_pwd(String user_id,String user_pwd,String user_pwd_new) {


			if( StringUtils.isEmptyOrWhitespace(user_id)){
				
				return new JsonBody<>(-1,"user_id不能为空");
			}
			if( StringUtils.isEmptyOrWhitespace(user_pwd)){
				
				return new JsonBody<>(-1,"user_pwd不能为空");
			}
			if( StringUtils.isEmptyOrWhitespace(user_pwd_new)){
				
				return new JsonBody<>(-1,"user_pwd_new不能为空");
			}
			/***
		 	Account curUser= findAccount(user_id);
			
		 	if(curUser==null){
		 		return new JsonBody<String>(-1,"操作失败，用户不存在！");
		 	}
		 	
		 	if(passwordEncoder.matches(curUser.getPwd(), passwordEncoder.encode(user_pwd))){
		 		return new JsonBody<String>(-1,"操作失败，输入的原来密码不正确！");
		 	}
		 	
		 	curUser.setPwd(passwordEncoder.encode(user_pwd_new));
		 	accountService.save(curUser);
			 ****/
			return new JsonBody<String>(1,"操作成功，下次登录请用新密码");
		}
	
		@RequestMapping(value="/update-my-prifile.json",method = RequestMethod.POST)
		public @ResponseBody JsonBody<String> update_my_prifile(
				@RequestParam(value = "user_id", required = true, defaultValue = "") String user_id,
				@RequestParam(value = "user_name", required = true, defaultValue = "") String name, Integer sex,
				String province, String city, String avatar,String birthday,String nickname) {

			if (StringUtils.isEmpty(user_id)) {

				return new JsonBody<>(-1, "user_id 不能为空");
			}

			if (StringUtils.isEmptyOrWhitespace(name)) {

				return new JsonBody<>(-1, "name不能为空");
			}
			if (StringUtils.isEmptyOrWhitespace(avatar)) {

				return new JsonBody<>(-1, "avatar不能为空");
			}
			if (StringUtils.isEmptyOrWhitespace(birthday)) {

				return new JsonBody<>(-1, "birthday不能为空");
			}
			if (StringUtils.isEmptyOrWhitespace(nickname)) {

				return new JsonBody<>(-1, "nickname不能为空");
			}
/***
			Account oneAccount = findAccount(user_id);

			if (oneAccount == null) {
				return new JsonBody<>(-2, "参数不正确，用户不存在");
			}
			
			Account existAccount= accountService.findByName(name);
			
			if(existAccount!=null&&!existAccount.getId().equals(oneAccount.getId())){
				return new JsonBody<>(-2, "抱歉，该用户名已经被使用");
			}
			
			oneAccount.setName(name);
			oneAccount.setAvatar(avatar);
			oneAccount.setSex(sex);
			oneAccount.setProvince(province);
			oneAccount.setCity(city);

			oneAccount.setBirthday(birthday);
			oneAccount.setNickname(nickname);
			
			accountService.save(oneAccount);
*****/
			return new JsonBody<>(1, "更新用户信息成功");
		}
		

		
		/***
		 * 字典表查询
		 * @param user_id
		 * @param mtype
		 * @return
		 */
		@RequestMapping(value="/get-dict-by.json",method = RequestMethod.GET)
		public @ResponseBody JsonBody<List<Object>> get_dict_by(String user_id,
                                                                 Long mtype){
			
			if(StringUtils.isEmpty(user_id) ){
				
				return new JsonBody<>(-1,"user_id 不能为空");
			}
			
			if(mtype==null){
				return new JsonBody<>(-1,"mtype 不能为空");
			}
			/***
			Account curUser=this.findAccount(user_id);
			
			if(curUser==null){
				
				return new JsonBody<>(-2,"参数错误，用户不存在");
			}
			
			List<SysDict> list= sysDictService.findByMtype(mtype);
			***/
			return new JsonBody<>(1,"获取数据成功",null);//,list
		}
		
		@RequestMapping(value="/read-msg.json",method = RequestMethod.GET)
		public @ResponseBody JsonBody<String> get_my_msg(String user_id,Long msg_id){
			
			if(StringUtils.isEmpty(user_id) ){
				
				return new JsonBody<>(-1,"user_id 不能为空");
			}
			/***
			Account curUser=this.findAccount(user_id);
			
			if(curUser==null){
				
				return new JsonBody<>(-2,"参数错误，用户不存在");
			}
			
			Message msg= messageService.findById(msg_id);
			
			if(msg!=null&&msg.getSend_to().equals(user_id)){
				
				msg.setStatus(1);
				messageService.save(msg);
			}
			****/
			return new JsonBody<>(1,"更新数据成功");
		}

    /***
     * 查询用户（添加好友）
      * @param key
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @PostMapping("/search-friends.json")
    public @ResponseBody  JsonBody<Page<Object>> search_friends(String  key
            , @RequestParam(value="page",required=false,defaultValue="0")  int pageIndex
            , @RequestParam(value="pageSize",required=false,defaultValue="10")  int pageSize) {
/***
        Account m=new Account();
        m.setName(key);
        Page<Account> page= accountService.search(m, pageIndex, pageSize);

        return new JsonBody<Page<Account>>(1,"获取数据成功",page);
 ***/
return  null;

    }


		private String  sendSns(String sendNumber,String msgContent){
			   
			String url = "http://58.51.146.98:8080/MsgPlatService/sms";
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		    map.add("sendNumber", sendNumber);
		    map.add("msgContent", msgContent);
		    map.add("serialNumber", String.valueOf(new Date().getTime()));
		    map.add("platCode", "9990000011043");

		    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		    ResponseEntity<String> response =null;// restTemplate.postForEntity( url, request , String.class );
		    
		    return response.getBody();
		}
}
