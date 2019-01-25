package com.gupaoedu.vip.jdbc.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_member")
public class Member implements Serializable {
	@Id private Long id;
	private String name;
	private String addr;
	private Long createTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//		create table t_member (
//			id BIGINT NOT NULL auto_increment primary key,
//			name varchar(64),
//			addr varchar(128),
//			create_time bigint
//		) COMMENT '员工信息表';

}
