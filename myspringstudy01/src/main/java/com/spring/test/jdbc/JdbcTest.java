package com.spring.test.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JdbcTest {
	
	public static void main(String[] args) {
		//最源生的jdbc如何操作？
		//1、加载驱动类
		//2、建立连接
		//3、创建语句集
		//4、执行
		//5、获取结果集
/*		
drop table if exists user;
create table user(
		id BIGINT not null auto_increment,
		user_name varchar(60) not null comment '用户姓名',
		sex varchar(3) not null comment '男、女',
		age int not null comment '年龄',
		primary key (id)
) comment '用户表';
insert into user (user_name,sex,age) values ('樊坤','男',18);
insert into user (user_name,sex,age) values ('小星星','女',18);
insert into user (user_name,sex,age) values ('张三','男',28);
insert into user (user_name,sex,age) values ('小月月','女',21);
*/
		//关联查询可以防止数据 更新
		//把数据从数据库中查询，再关联，会出现数据库更新产生的脏数据
		//使用事务是否可以的？测试！！
		try {
			//被封装成了datasource,方法连接池，目的是为了提高相应速度
			//1 
			Class.forName("com.mysql.jdbc.Driver");
			//2
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123456");
			//3
			PreparedStatement ps = conn.prepareStatement("select * from user ");
			//4
			ResultSet rs = ps.executeQuery();
			//5
			//封装了，做成一个orm的过程
			//Object Relation Mapping（ORM） 对象关系映射
			//自动变成一个我们显而易见的普通的自己写的java对象（实体类）
//			testPrimaryJDBC(rs);
			testPrimaryJDBCORM(rs);
			rs.close();
			ps.close();
			conn.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	
	private static void testPrimaryJDBC(ResultSet rs) throws SQLException {
		System.out.println(rs.getMetaData());
		while(rs.next()) {
			System.out.print(rs.getLong("id"));
			System.out.print("\t"+rs.getString("name_en"));
			System.out.println("\t"+rs.getString("name_ch"));
			System.out.println("------------------------------------------------");
		}
	}
	private static void testPrimaryJDBCORM(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		System.out.println(rs.getMetaData());
		int columnNum = rs.getMetaData().getColumnCount();
		List<Object> users = new ArrayList<Object>();
		while(rs.next()) {
			Class<?> clazz = User.class;
			Object obj =  clazz.newInstance();
			for (int i = 1; i <= columnNum; i++) {//从1开始
				String columnName = rs.getMetaData().getColumnName(i);
				System.out.print("columnName"+i+":"+columnName);
				String fieldName = SnakeToHump.covert(columnName);
				System.out.println("\t\tfieldName"+i+":"+fieldName);
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				Class<?> fieldType = field.getType();
				String columnType = rs.getMetaData().getColumnTypeName(i);
				
				if(fieldType==Long.class) {
					field.set(obj, rs.getLong(i));
				}else if(fieldType==Integer.class) {
					field.set(obj, rs.getInt(i));
				}else if(fieldType==String.class) {
					field.set(obj, rs.getString(i));
				}
			}
			users.add(obj);
		}
		System.out.println(users);
	}
} 
