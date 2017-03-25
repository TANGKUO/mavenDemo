package com.tangkuo.cn.worm;

import java.awt.Color;
import java.awt.Graphics;

/**
 * <p>Title: </p>
 * <p>Description: 格子:食物或者蛇的节点</p>
 * <p>Company: www.tk.com</p>   
 * @author   tangkuo
 * @date    2017年3月25日 上午11:49:07
 */
public class Cell {
	public static final int SIZE = 10;
	private int x;
	private int y;
	private Color color;
	
	public Cell(){	
	}
	
	public Color setColor(Color color){
		this.color=color;
		return color;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Color getColor(){
		return color;
	}
	
	public Cell(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public Cell(int x,int y,Color color){
		this.x=x;
		this.y=y;
		this.color=color;
	}
	public String toString() {
		return "["+x+","+y+"]";
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fill3DRect(x*SIZE, y*SIZE, SIZE, SIZE, true);	
	}
}
