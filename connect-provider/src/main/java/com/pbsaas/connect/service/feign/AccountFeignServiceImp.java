package com.pbsaas.connect.service.feign;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.db.entity.Account;
import com.pbsaas.connect.db.entity.AccountRoles;
import com.pbsaas.connect.db.entity.AccountRolesIds;
import com.pbsaas.connect.db.repository.AccountRepository;
import com.pbsaas.connect.db.repository.AccountRolesRepository;

import com.pbsaas.connect.db.type.RoleType;
import com.pbsaas.connect.framework.controller.JsonBaseController;
import com.pbsaas.connect.framework.dao.SqlDynamicExec;
import com.pbsaas.connect.framework.validator.NotNullValidator;
import com.pbsaas.connect.framework.validator.PhoneNoValidator;
import com.pbsaas.connect.model.dto.RegistAccountDTO;
import com.pbsaas.connect.model.vo.AccountVO;
import com.pbsaas.connect.service.AccountFeignService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author sam
 *
 */
@RestController
public class AccountFeignServiceImp extends JsonBaseController implements AccountFeignService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountRolesRepository accountRolesRepository;

	@Autowired
	private SqlDynamicExec sqlDynamicExec;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public JsonBody<AccountVO> findById(String id) {

		if (StringUtils.isEmpty(id)) {
			return new JsonBody<>(-1, "id不能为空");
		}
		Account one=accountRepository.findById("112").orElse(null);
		String sqlId="common.test";
		Map<String, Object> params=new HashMap<>();

		PageModel<Map<String, Object>> pag2=  sqlDynamicExec.queryByDynamicName(sqlId,params,1,10);

		AccountVO vo=new AccountVO();
		vo.setUserName("admin");
		vo.setUid(id);
		return  new JsonBody<>(1,"success",vo);
	}

	@Override
	public JsonBody<AccountVO> findByName(String name) {

		if (StringUtils.isEmpty(name)) {
			return new JsonBody<>(-1, "name不能为空");
		}
		Map<String, Object> params=new HashMap<>();
		params.put("userName",name);
		JsonBody<PageModel<AccountVO>> exec=search(params,1,10);

		if(exec.getResult()!=1){
			return  new JsonBody<>(-1,exec.getMsg());
		}

		List<AccountVO> list= exec.getData().getList();

		if(list.size()==0){
			return  new JsonBody<>(-1,"记录不存在");
		}

		return  new JsonBody<>(1,"success",list.get(0));
	}

	@Override
	public JsonBody<String> regist(RegistAccountDTO dto) {

		// 字段校验
		ComplexResult checker = FluentValidator.checkAll()
				.on(dto.getMobile(), new NotNullValidator<String>("手机号"))
				.on(dto.getMobile(), new PhoneNoValidator("手机号"))
				.on(dto.getPassword(), new NotNullValidator<String>("密码"))
				.on(dto.getUserName(), new NotNullValidator<String>("用户名"))
				.doValidate()
				.result(ResultCollectors.toComplex());

		if (!checker.isSuccess()) {

			return new JsonBody<>(-1, checker.getErrors().get(0).getErrorMsg());
		}

		//检查手机号
		int	num= accountRepository.countByMobile(dto.getMobile());
		if(num>0) {

			return new JsonBody<>(-1,"该手机号已创建帐号，不能重复注册");
		}

		//检查是否已经注册过
		num= accountRepository.countByUserName(dto.getUserName());
		if(num>0) {

			return new JsonBody<>(-1,"抱歉，你的资料已经新建了帐号，如忘记密码，可与管理员联系");
		}

		Account acc=new Account();

		acc.setCreate_date(new Date());
		acc.setUserName(dto.getUserName());
		acc.setPassword(dto.getMobile());
		acc.setStatus(0);

		acc.setPassword(passwordEncoder.encode(dto.getPassword()));

		acc=accountRepository.save(acc);

		AccountRolesIds id=new AccountRolesIds();
		id.setUser_id(acc.getId());
		id.setRole_id(RoleType.normal);

		AccountRoles accountRoles=new AccountRoles();
		accountRoles.setAccountRolesIds(id);
		accountRolesRepository.save(accountRoles);

		return new JsonBody<>(1,"注册帐号成功");
	}

	@Override
	public JsonBody<String> update(Map<String, Object> params) {
		return null;
	}

	@Override
	public JsonBody<String> resetPwd(Map<String, Object> params) {
		return null;
	}

	@Override
	public JsonBody<PageModel<AccountVO>> search(Map<String, Object> params, int pageIndex, int pageSize) {

		String queryName = "user.search";

		PageModel<AccountVO> pageBean = sqlDynamicExec.queryByDynamicName(queryName, params,AccountVO.class, pageIndex,
				pageSize);

		return new JsonBody<>(1, "success", pageBean);
	}
}
