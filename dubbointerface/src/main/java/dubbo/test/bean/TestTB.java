package dubbo.test.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String name;
	private Date birthday;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "TestB [name=" + name + ", birthday=" + (birthday==null?null:sdf.format(birthday)) + "]";
	}
	
}
