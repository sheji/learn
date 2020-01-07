package com.atguigu.command;

public class LightOnCommand implements Command {
	//聚合LightReceiver
	LightReceiver light;
	//构造器
	public LightOnCommand(LightReceiver light) {
		this.light = light;
	}
	
	@Override
	public void execute() {
		//调用接收者的方法
		light.on();
	}

	@Override
	public void undo() {
		//调用接收者的方法
		light.off();
	}

}
