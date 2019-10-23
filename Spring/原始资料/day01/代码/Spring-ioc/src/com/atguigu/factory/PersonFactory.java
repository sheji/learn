package com.atguigu.factory;

import com.atguigu.pojo.Person;

public class PersonFactory {

	public static Person createPerson() {
		return new Person(15, "静态工厂方法创建的bean对象", null);
	}
	
	public Person createPerson2() {
		return new Person(16, "工厂实例方法创建的bean对象", null);
	}
	
}
