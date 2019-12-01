package com.atguigu.mediator.smarthouse;

public abstract class Mediator {
	//将给中介者对象，加入到集合中
	public abstract void register(String colleagueName, Colleague colleague);
	//接收消息, 具体的同事对象发出
	public abstract void getMessage(int stateChange, String colleagueName);
	public abstract void sendMessage();
}
