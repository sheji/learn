package com.atguigu.pojo;

public class Person {

	private String name;
	private Car car;

	public void init() {
		System.out.println("初始化方法");
	}
	
	public void destroy() {
		System.out.println("销毁的方法");
	}
	
	public Person() {
		super();
		System.out.println("Person被创建了");
	}

	public Person(Car car) {
		super();
		System.out.println("有参构造器 --->>> " + car);
		this.car = car;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", car=" + car + "]";
	}

}
