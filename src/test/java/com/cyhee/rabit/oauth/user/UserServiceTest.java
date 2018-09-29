package com.cyhee.rabit.oauth.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyhee.rabit.oauth.user.model.User;
import com.cyhee.rabit.oauth.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Import({UserService.class, BCryptPasswordEncoder.class})
public class UserServiceTest {
	@Autowired
	private TestEntityManager entityManger;	
	@Autowired
	private UserService userService;
	
	@Test
	public void create() {
		User user = new User();
		user.setEmail("email@rabit");
		user.setUsername("username");
		user.setPassword("password@@123");
		
		userService.addUser(user);
		
		entityManger.flush();
		assertThat(user.getId())
			.isNotNull();
	}
}
