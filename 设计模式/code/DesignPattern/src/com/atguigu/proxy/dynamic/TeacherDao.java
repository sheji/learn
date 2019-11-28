package com.atguigu.proxy.dynamic;

public class TeacherDao implements ITeacherDao {
	@Override
	public void teach() {
		System.out.println(" ÀÏÊ¦ÊÚ¿ÎÖĞ.... ");
	}

	@Override
	public void sayHello(String name) {
		System.out.println("hello " + name);
	}
}
