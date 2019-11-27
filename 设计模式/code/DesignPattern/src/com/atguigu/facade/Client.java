package com.atguigu.facade;

public class Client {

	public static void main(String[] args) {
		//这里直接调用。。 很麻烦, 所以用外观模式调用
		HomeTheaterFacade homeTheaterFacade = new HomeTheaterFacade();
		homeTheaterFacade.ready();
		System.out.println("-----------");
		homeTheaterFacade.play();
		System.out.println("-----------");
		homeTheaterFacade.end();
	}
}
