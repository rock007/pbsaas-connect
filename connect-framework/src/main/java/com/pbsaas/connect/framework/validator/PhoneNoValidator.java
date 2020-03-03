package com.pbsaas.connect.framework.validator;

import java.util.regex.Pattern;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * 手机号码校验
 */
public class PhoneNoValidator extends ValidatorHandler<String> implements Validator<String> {

	String regex = "\\d{11}$"; 
	
    private String fieldName;

    public PhoneNoValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
        if (!(null == s || s.trim().equals(""))) {
        	
        	if(!Pattern.matches(regex, s.trim())) {
        		
        		 context.addError(ValidationError.create(String.format("%s必须是数字并且11位！", fieldName))
                         .setErrorCode(-1)
                         .setField(fieldName)
                         .setInvalidValue(s));
                 return false;
        	}
        	
        	if(!s.trim().startsWith("1")) {
        	
        		 context.addError(ValidationError.create(String.format("%s必须以1开头", fieldName))
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
