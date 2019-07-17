package com.cyhee.rabit.oauth.email.service;

import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.oauth.email.model.EmailVerify;
import com.cyhee.rabit.oauth.email.repository.EmailVerifyRepository;
import com.cyhee.rabit.oauth.user.dao.UserRepository;
import com.cyhee.rabit.oauth.user.model.User;
import com.cyhee.rabit.oauth.user.model.UserStatus;
import com.cyhee.rabit.oauth.user.service.UserService;

@Service
public class EmailService {
  
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private EmailVerifyRepository verifyRepostiory;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    private static final String verifyUrl = "/v1/email/codes/";
 
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        mailSender.send(message);
    }
    
    public void registerEmail(User user, String email) {
    	logger.debug("registerEmail with " + user.getUsername() + " and email " + email);
    	EmailVerify emailVerify = new EmailVerify();
    	emailVerify.setUser(user);
    	String code = email + getRandomCode(20);
    	emailVerify.setCode(code);
    	verifyRepostiory.save(emailVerify);
    	
    	user.setStatus(UserStatus.PENDING);
    	userRepository.save(user);
    	
    	MimeMessage mail = mailSender.createMimeMessage();
    	MimeMessageHelper message;
		try {
			message = new MimeMessageHelper(mail, true);
	        message.setSubject("Rabit email registeration code");
	        message.setTo(email);
			mail.setText(this.template(code));
		} catch (MessagingException e) {
			throw new RuntimeException("Error during sending message!");
		}
        mailSender.send(mail);
    }
    
    public void verifyEmail(String code) {
    	logger.debug("verifyEmail with " + code);
    	Optional<EmailVerify> emailVerifyOpt = verifyRepostiory.findByCode(code);
    	if(!emailVerifyOpt.isPresent())
    		throw new NoSuchContentException("No such content with current code");
    	
    	EmailVerify emailVerify = emailVerifyOpt.get();
    	emailVerify.setDone(true);
    	verifyRepostiory.save(emailVerify);
    	
    	User user = emailVerify.getUser();
    	user.setStatus(UserStatus.ACTIVE);
    	userRepository.save(user);
    }
    
    public void findPassword(User user) {
    	logger.debug("find password with user " + user.getUsername());
    	String newPassword = getRandomCode(10);
    	userService.changePassword(user, newPassword);
    	
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(user.getEmail());
        message.setSubject("Rabit notifies new password"); 
        message.setText("변경된 비밀번호 " + newPassword);
        mailSender.send(message);
    }
    
    private String template(String code) {
    	String link = getBaseUrl() + verifyUrl + code;
    	return link;
    }
    
    private String getRandomCode(int len) {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        while (code.length() < len) { // length of the random string.
            int index = (int) (random.nextFloat() * saltChars.length());
            code.append(saltChars.charAt(index));
        }
        return code.toString();
    }
    
    private String getBaseUrl() {
    	RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	if(null != requestAttributes && requestAttributes instanceof ServletRequestAttributes) {
    	  HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
    	  return String.format("%s://%s:%d",request.getScheme(), request.getServerName(), request.getServerPort());
    	}
    	else {
    	  return null;
    	}
    }
}
