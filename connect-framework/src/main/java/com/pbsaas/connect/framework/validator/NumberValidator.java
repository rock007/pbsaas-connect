package com.pbsaas.connect.framework.validator;

import java.util.regex.Pattern;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * 数字格式校验
 */
public class NumberValidator extends ValidatorHandler<String> implements Validator<String> {

	String regex = "\\d$"; 
	
    private String fieldName;

    public NumberValidator(String fieldName) {
        
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
        if (!(null == s || s.equals(""))) {
        	
        	if(!Pattern.matches(regex, s.trim())) {
        		
       		 context.addError(ValidationError.create(String.format("%s数字验证失败！", fieldName))
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
