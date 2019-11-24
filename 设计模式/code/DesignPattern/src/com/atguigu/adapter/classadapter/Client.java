package com.atguigu.adapter.classadapter;

public class Client {
	public static void main(String[] args) {
		System.out.println(" === ÀàÊÊÅäÆ÷Ä£Ê½ ====");
		Phone phone = new Phone();
		phone.charging(new VoltageAdapter());
	}
}
