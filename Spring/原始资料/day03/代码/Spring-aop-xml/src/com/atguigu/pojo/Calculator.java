package com.atguigu.pojo;

public class Calculator implements Calculate {

	public int add(int num1, int num2) {
		int result = 0;
		result = num1 + num2;
		System.out.println("add(int num1, int num2)");
		return result;
	}

	public int add(int num1, int num2, int num3) {
		int result = 0;
		System.out.println(" add(int num1, int num2, int num3)");
		result = num1 + num2 + num3;
		return result;
	}

	public int div(int num1, int num2) {
		int result = 0;
		System.out.println("div(int num1, int num2)");
		result = num1 / num2;
		return result;
	}

}
