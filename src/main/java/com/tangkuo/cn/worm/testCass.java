package com.tangkuo.cn.worm;
import org.junit.Test;
public class testCass {
//	@Test
	public void stageTest(){
		WormStage.test();
	}
	
//	@Test
	public void creepTest(){
		
		Worm worm=new Worm();
		worm.creep();
		System.out.println(worm);
	}
	
//	@Test
	public void creep(){
		
		Worm worm=new Worm();
		Cell food=new Cell(0,1);
		System.out.println(worm);
		System.out.println(worm.creep(food));
		System.out.println(worm);
	}
	
	@Test
	public void creep1(){
		Worm worm=new Worm();
		Cell food=new Cell(0,2);
//		System.out.println(worm);
//		System.out.println(worm.creep(food));
//		System.out.println(worm);
//		System.out.println(worm.creep(food));
//		System.out.println(worm);
//		System.out.println(worm.hit(Worm.DOWN));
//		System.out.println(worm.creep(Worm.DOWN, food));
//		System.out.println(worm);
//		System.out.println(worm.hit(Worm.DOWN));
//		System.out.println(worm.creep(Worm.DOWN, food));
//		System.out.println(worm);
//		System.out.println(worm.hit(Worm.DOWN));
//		System.out.println(worm.creep(Worm.DOWN, food));
//		System.out.println(worm);
		System.out.println(worm.creep(Worm.DOWN, food));
//		System.out.println(worm.hit(Worm.UP));
//		System.out.println(worm);
		System.out.println(worm.creep(Worm.DOWN, food));
//		System.out.println(worm.hit(Worm.LEFT));
//		System.out.println(worm);		
	}
	
}