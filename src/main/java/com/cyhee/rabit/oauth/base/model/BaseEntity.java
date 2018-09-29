package com.cyhee.rabit.oauth.base.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * 기본 ID 생성 기능을 가진 BaseEntity, 해당 클래스를 상속하여 사용
 * @author chy
 */
@MappedSuperclass
@Data
public class BaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
}
