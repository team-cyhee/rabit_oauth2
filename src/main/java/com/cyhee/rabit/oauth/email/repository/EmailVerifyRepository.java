package com.cyhee.rabit.oauth.email.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyhee.rabit.oauth.email.model.EmailVerify;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
	Optional<EmailVerify> findByCode(String code);
}
