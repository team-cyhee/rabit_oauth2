package com.cyhee.rabit.oauth.user.model;

/**
 * 유저의 상태를 나타내기 위한 enum
 * @author chy
 *
 */
public enum UserStatus {
	// 활성
	ACTIVE,
	// 비활성
	INACTIVE,
	// 금지
	FORBIDDEN,
	// 삭제됨
	DELETED,
	// 가입 보류
	PENDING;
}