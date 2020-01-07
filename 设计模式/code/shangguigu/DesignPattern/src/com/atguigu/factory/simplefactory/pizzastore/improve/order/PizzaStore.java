package com.atguigu.factory.simplefactory.pizzastore.improve.order;

//相当于一个客户端，发出订购
public class PizzaStore {
	public static void main(String[] args) {
		//使用简单工厂模式
//		new OrderPizza(new SimpleFactory());
		//使用静态工厂模式 
		new OrderPizza2();
	}
}
