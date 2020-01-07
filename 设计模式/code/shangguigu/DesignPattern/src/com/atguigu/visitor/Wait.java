package com.atguigu.visitor;

//系统扩展新增的评价状态
public class Wait extends Action {
	@Override
	public void getManResult(Man man) {
		System.out.println(" 男人给的评价是该歌手待定 ..");
	}

	@Override
	public void getWomanResult(Woman woman) {
		System.out.println(" 女人给的评价是该歌手待定 ..");
	}

}
