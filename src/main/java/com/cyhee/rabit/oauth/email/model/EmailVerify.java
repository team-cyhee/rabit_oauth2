package com.cyhee.rabit.oauth.email.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cyhee.rabit.oauth.base.model.TimestampEntity;
import com.cyhee.rabit.oauth.user.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
public class EmailVerify extends TimestampEntity {
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(length=50)
	private String code;
	
	@Column(nullable=false)
	private boolean done = false;
}
