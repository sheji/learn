package com.atguigu.decorator;

public class Milk extends Decorator {
	public Milk(Drink obj) {
		super(obj);
		setDes(" еёдл ");
		setPrice(2.0f); 
	}
}
