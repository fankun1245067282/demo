package com.spring.test.jdbc.framework;

import java.io.Serializable;

import javax.sql.DataSource;
/**
 * 这个类是给人继承的，一般不会自己new出来的
 * 这个类里面所有方法应该设置为protected
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseDaoSupport<T extends Serializable,PK extends Serializable> {

	/**
	 * 设置主键列名
	 */
//	protected abstract void getPKColumn();
	/**
	 * 设置数据源（动态设置）
	 * @param dataSource
	 */
	protected abstract void setDataSource(DataSource dataSource);
}
