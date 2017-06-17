package com.tangkuo.cn.worm;

import java.awt.Color;
import java.awt.Graphics;

import java.util.Arrays;

/**
 * <p>Title: </p>
 * <p>Description: 代表蛇</p>
 * <p>Company: www.tk.com</p>   
 * @author   tangkuo
 * @date    2017年3月25日 上午11:48:29
 */
public class Worm {
	/**代表蛇包含的格子*/
	private Cell[] cells;
	/**代表蛇的方向*/
	private int currentDirection;
	/**代表蛇的颜色*/
	private Color color;
	
	public static final int UP=1;
	public static final int DOWN=-1;
	public static final int LEFT=2;
	public static final int RIGHT=-2;
	public Worm(){
		color=Color.red;		
		cells=new Cell[12];
		for(int x=0,y=0;x<12;x++){
			cells[x]=new Cell(x,y,color);
		}
		currentDirection=DOWN;
	}	
	//点是否在cells数组图像内
	public boolean contains(int x,int y){
		for(int i=0;i<cells.length;i++){
			Cell cell=cells[i];
			if(cell.getX()==x&&cell.getY()==y){
				return true;
			}
		}
		return false;
	}
//cells输出
	public String toString(){
		String s = "";
		for (int i = 0; i < cells.length; i++) {
			Cell node = cells[i];
			s +=node+"," ;
		}
		return s;
	}
//蛇的爬行的无参方法
	public void creep(){
		for(int i=this.cells.length-1;i>0;i--){
			cells[i]=cells[i-1];
		}
		cells[0]=createHead(currentDirection);
	}
//爬行后需要创建新头
	private Cell createHead(int direction){
		int x=cells[0].getX();
		int y=cells[0].getY();
		switch(direction){
		
		case DOWN:y++;
		break;
		
		case UP:y--;
		break;
		
		case RIGHT:x++;
		break;
		
		case LEFT:x--;
		break;
		}
		return new Cell(x,y);
	}
//爬行后食物被吃到	
	public boolean creep(Cell food){
		Cell head = createHead(currentDirection);
		boolean eat=head.getX()==food.getX()&&head.getY()==food.getY();
		if(eat){
			Cell[] ary=Arrays.copyOf(cells, cells.length+1);
			cells=ary;
		}
		for(int i=cells.length-1;i>=1;i--){
			cells[i]=cells[i-1];
		}
		cells[0]=head;
		return eat;
	}
//判断方向改变和食物是否被吃到的方法	
	public boolean creep(int direction, Cell food){
		if(direction+currentDirection==0){
			direction=currentDirection;
		}
		currentDirection=direction;
		Cell head = createHead(currentDirection);
		boolean eat=head.getX()==food.getX()&&head.getY()==food.getY();
		if(eat){
			Cell[] ary=Arrays.copyOf(cells, cells.length+1);
			cells=ary;
		}
		for(int i=cells.length-1;i>=1;i--){
			cells[i]=cells[i-1];
		}
		cells[0]=head;
		return eat;
	}
	
	public boolean hit(int direction){
		//反方向
		if(currentDirection+direction==0){
			return false;
		}
		//出界
		Cell head=createHead(direction);
		if(head.getX()<0||head.getX()>=WormStage.COLS||head.getY()<0||head.getY()>=WormStage.ROWS){
			return true;
		}
		//吃自己
		for(int i=0;i<cells.length-1;i++){
			if(cells[i].getX()==head.getX()&&
					cells[i].getY()==head.getY()){
				return true;
			}
		}
		return false;
	}
//自动判断
	public boolean hit(){
		return hit(currentDirection);
	}

	public void paint(Graphics g){
		g.setColor(Color.CYAN);
		for(int i=0;i<cells.length;i++){
			Cell cell=cells[i];
			g.fill3DRect(cell.getX()*WormStage.CELL_SIZE, cell.getY()*WormStage.CELL_SIZE, WormStage.CELL_SIZE, WormStage.CELL_SIZE, true);
		}
		
	}
}
