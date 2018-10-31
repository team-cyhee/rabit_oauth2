package com.cyhee.rabit.oauth.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 생성, 수정 일시를 관리하는 entity 객체
 * 상속하여 사용
 * @author chy
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=false)
public class TimestampEntity extends BaseEntity{
	@CreationTimestamp
	@Column(nullable=false, updatable=false)
	private Date createDate;
	
	@UpdateTimestamp
	private Date lastUpdated;
}
