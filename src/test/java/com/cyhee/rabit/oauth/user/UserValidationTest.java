package com.cyhee.rabit.oauth.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.oauth.user.validation.Password;

import lombok.Getter;
import lombok.Setter;

@RunWith(SpringRunner.class)
public class UserValidationTest {
	private Validator validator;
	
	@Getter @Setter
	private static class UserSample {
		@Password
		String password;
	}
	
	
	@Before
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void PasswordValid() {
		UserSample user = new UserSample();
		Set<ConstraintViolation<UserSample>> violations;

		// null
		violations = validator.validate(user);
		assertThat(violations.isEmpty()).isFalse();
		
		// need special
		user.setPassword("nospecialchar1");
		violations = validator.validate(user);
		assertThat(violations.isEmpty()).isTrue();
		
		// less than 8
		user.setPassword("!1short");
		violations = validator.validate(user);
		assertThat(violations.isEmpty()).isFalse();
		
		// over 20
		user.setPassword("@@toolongpasswordfail22");
		violations = validator.validate(user);
		assertThat(violations.isEmpty()).isFalse();
		
		user.setPassword("itwillpass2@");
		violations = validator.validate(user);
		assertThat(violations.isEmpty()).isTrue();
	}
}