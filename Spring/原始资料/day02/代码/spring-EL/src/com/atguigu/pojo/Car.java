package com.atguigu.pojo;

import com.sun.tracing.dtrace.ProviderAttributes;

public class Car {

	private String name;
	private String carNo;

	public String fun() {
		return "国哥最帅！";
	}

	public static String staticFun() {
		return "静态方法也改变不了帅的事实！";
	}

	public Car() {
		super();
	}

	public Car(String name, String carNo) {
		super();
		this.name = name;
		this.carNo = carNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	@Override
	public String toString() {
		return "Car [name=" + name + ", carNo=" + carNo + "]";
	}

}
