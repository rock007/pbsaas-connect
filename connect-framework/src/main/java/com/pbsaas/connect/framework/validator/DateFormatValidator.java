package com.pbsaas.connect.framework.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * 日期格式校验
 */
public class DateFormatValidator extends ValidatorHandler<String> implements Validator<String> {


    private String formatter;

    private String fieldName;

    public DateFormatValidator(String formatter,String fieldName) {
       
        this.formatter = formatter;
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
    	
        if (!(null == s ||s.equals(""))) {
        	
			try {
				
				DateFormat f = new SimpleDateFormat(formatter);
				
				f.parse(s);
				
				return true;
			} catch (ParseException e) {
				
			    context.addError(ValidationError.create(String.format("%s转换%s格式失败！", fieldName, formatter))
		                    .setErrorCode(-1)
		                    .setField(fieldName)
		                    .setInvalidValue(s));
		        return false;
			}
        }
        return true;
    }

}
