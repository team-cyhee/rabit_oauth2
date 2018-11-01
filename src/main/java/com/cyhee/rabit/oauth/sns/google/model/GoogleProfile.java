package com.cyhee.rabit.oauth.sns.google.model;

import java.util.ArrayList;
import java.util.List;

import com.cyhee.rabit.oauth.sns.model.SnsProfile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GoogleProfile extends SnsProfile {
	private String id;
	private String displayName;
	private List<Email> emails = new ArrayList<>();
	
	@Override
	public String getEmail() {
		if(emails.isEmpty())
			throw new IllegalStateException("GoogleProfile must have email");
		return emails.get(0).getValue();
	};
	
	@Data
	private static class Email {
		private String value;
		private String type;
	}
}
