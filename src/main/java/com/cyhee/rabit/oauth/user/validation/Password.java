package com.cyhee.rabit.oauth.user.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 비밀번호에 대한 validation을 진행하기 위한 annotation
 * @{link #PasswordValidator}
 * @author chy
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Invalid Password!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    /**
     * 특수문자가 포함되어야 하는지 설정
     */
    boolean needSpecial() default false;
    
    
    /**
     * 대문자가 포함되어야 하는지 설정
     */
    boolean needUpper() default false;
        
    /**
     * 최소 길이 설정
     */
    int min() default 8;
    
    /**
     * 최대 길이 설정
     */
    int max() default 20;
}