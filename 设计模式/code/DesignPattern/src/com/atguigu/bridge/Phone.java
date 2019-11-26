package com.atguigu.bridge;

public abstract class Phone {
	//组合品牌
	private Brand brand;
	//构造器
	public Phone(Brand brand) {
		this.brand = brand;
	}
	protected void open() { brand.open(); }
	protected void close() { brand.close(); }
	protected void call() { brand.call(); }
}
