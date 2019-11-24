package com.atguigu.builder.improve;

//ConcreteBuilder(具体建造者)
public class HighBuilding extends HouseBuilder {

	@Override
	public void buildBasic() { System.out.println(" 高楼的打地基100米 "); }
	@Override
	public void buildWalls() { System.out.println(" 高楼的砌墙20cm "); }
	@Override
	public void roofed() { System.out.println(" 高楼的透明屋顶 "); }

}
