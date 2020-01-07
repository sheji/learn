package com.atguigu.strategy.improve;

public class ToyDuck extends Duck{
	public ToyDuck() {
		flyBehavior = new NoFlyBehavior();
	}
	
	@Override
	public void display() {
		System.out.println("Íæ¾ßÑ¼");
	}
}
