package com.cyhee.rabit.oauth.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @{link #Password}
 * @author chy
 *
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {
	private boolean needSpecial;
    private boolean needUpper;
    private int min;
    private int max;
    
    private String regexGenerator() {
    	String regex;
		// at least one letter, one number
    	if(!needSpecial && !needUpper)
    		regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]";
		// at least one letter, one number, one upper case
    	else if(!needSpecial && needUpper)
    		regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]";
		// at least one letter, one number, one special
    	else if(needSpecial && !needUpper)
    		regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]";
		// at least one letter, one number, one upper case, one special
    	else
    		regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]";
    	String postfix = String.format("{%d,%d}$", min, max);
    	return regex + postfix;
    }

	@Override
	public void initialize(Password constraintAnnotation) {
		this.needSpecial = constraintAnnotation.needSpecial();
		this.needUpper = constraintAnnotation.needUpper();
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null)
			return false;
		if(value.matches(regexGenerator()))
			return true;
		return false;
	}	
}
