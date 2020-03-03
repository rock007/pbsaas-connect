package com.pbsaas.connect.framework.validator;

import java.util.regex.Pattern;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * 手机号码校验
 */
public class IdCardValidator extends ValidatorHandler<String> implements Validator<String> {

	//String regex = "\\d{18}$"; 
	String regex= "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|"
            + "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
    
    private String fieldName;

    public IdCardValidator(String fieldName) {
      
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
    
    	if (!(null == s || s.equals(""))) {
        	
        	if(!Pattern.matches(regex, s.trim())) {
        		
       		 context.addError(ValidationError.create(String.format("身份证号码验证失败！"))
                        .setErrorCode(-1)
                        .setField(fieldName)
                        .setInvalidValue(s));
                return false;
        	}
            
            return true;
        }
        return true;
    }

}
