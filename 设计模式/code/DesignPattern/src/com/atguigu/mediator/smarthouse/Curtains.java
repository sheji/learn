package com.atguigu.mediator.smarthouse;

public class Curtains extends Colleague {

	public Curtains(Mediator mediator, String name) {
		super(mediator, name);
		mediator.register(name, this);
	}

	@Override
	public void sendMessage(int stateChange) {
		this.getMediator().getMessage(stateChange, this.name);
	}

	public void fallCurtains() {
		System.out.println("Curtains falls automatically!");
	}

}
