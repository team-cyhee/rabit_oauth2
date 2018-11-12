package com.cyhee.rabit.oauth.sns.facebook.model;

import com.cyhee.rabit.oauth.sns.model.SnsProfile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FacebookProfile extends SnsProfile {	
	private Long id;
	private String name;
}
