package com.cyhee.rabit.oauth.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.cyhee.rabit.oauth.base.model.TimestampEntity;
import com.cyhee.rabit.oauth.user.validation.NotSnsGroup;
import com.cyhee.rabit.oauth.user.validation.Password;
import com.cyhee.rabit.oauth.user.validation.SetPasswordGroup;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="rabit_user")
@Getter @Setter
public class User extends TimestampEntity {
	
    @Column(nullable=false, unique=true, length=50, updatable=false)
	@Email(groups=NotSnsGroup.class)
	@NotNull(groups=NotSnsGroup.class)
	private String email;
	
	@Column(nullable=false, length=255)
	@Password(groups=SetPasswordGroup.class)
	private String password;
	
	@Column(nullable=false, unique=true, length=20)
	@NotNull
	private String username;
	
	@Column(length=30)
	private String name;
		
	@Column(length=20)
	private String phone;
	
	@Temporal(TemporalType.DATE) 
	private Date birth;
	
	@Column(nullable=false)
	private UserStatus status = UserStatus.PENDING;
}