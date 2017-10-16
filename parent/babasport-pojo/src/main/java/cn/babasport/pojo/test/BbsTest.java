package cn.babasport.pojo.test;

import java.io.Serializable;
import java.util.Date;

public class BbsTest implements Serializable {
	private Integer id;
	private String name;
	private Date birthday;

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


 }
