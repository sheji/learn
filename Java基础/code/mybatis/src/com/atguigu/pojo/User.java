package com.atguigu.pojo;

public class User {

	private Integer id;
	private String lastName;
	private Integer sex;

	public User(Integer id, String lastName, Integer sex) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.sex = sex;
	}

	public User() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", lastName=" + lastName + ", sex=" + sex
				+ "]";
	}

}
