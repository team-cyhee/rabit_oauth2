package com.cyhee.rabit.oauth.sns.naver.model;

import com.cyhee.rabit.oauth.sns.model.SnsProfile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NaverProfile extends SnsProfile {
	private String nickname;
	private String profileImage;
	private String name;
}
