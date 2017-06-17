package com.tangkuo.cn.worm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <p>Title: </p>
 * <p>Description: 贪吃蛇的舞台，扩展了面板，添加了蛇和食物</p>
 * <p>Company: www.tk.com</p>   
 * @author   tangkuo
 * @date    2017年3月25日 上午11:47:50
 */
public class WormStage extends JPanel{
	
	private static final long serialVersionUID = 4542116496164291641L;
	private Worm worm;
	private Cell food;
	/**舞台列数*/
	public static final int COLS=35;
	/**舞台行数*/
	public static final int ROWS=35;
	/**舞台格子大小*/
	public static final int CELL_SIZE=10;
	/**
	 * 构造器
	 * */
	public WormStage() {
//		int w = COLS * Cell.SIZE;//35格每格10个像素
//		int h = ROWS * Cell.SIZE;
//		setSize(w, h);
		worm=new Worm();
		food=newFood();		
	}
	

	
	/**
	 * 1,随机食物避开蛇的身体
	 *2,随机生成x,y
	 *3,检查是否包含（x,y）
	 *4,包含返回1
	 *5,否则创建节点
	 * 
	 * */
	private Cell newFood(){
		Random random=new Random();
		int x;
		int y;
		do{			
			x=random.nextInt(COLS);
			y=random.nextInt(ROWS);
		}while(worm.contains(x,y));//调用worm下的contains方法体	
		return new Cell(x,y,Color.yellow);
	}
	 
	public static void test(){	
		WormStage stage=new WormStage();
		System.out.println(stage.worm);
		System.out.println(stage.food);
	}
	//重绘图像
	public void paint(Graphics g){
		g.fillRect(0, 0, getWidth(),getHeight());
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, this.getWidth(), this.getHeight());
		g.fill3DRect(food.getX()*CELL_SIZE,food.getY()*CELL_SIZE,CELL_SIZE, CELL_SIZE, true);
		worm.paint(g);
	}
	private Timer timer;
	private void go() {
		if(timer==null){
			timer=new Timer();
			timer.schedule(new TimerTask(){
				public void run() {
					if(worm.hit()){
						worm=new Worm();
						food=newFood();
						}else{
							boolean eat=worm.creep(food);
							if(eat){
								food=newFood();
							}
						}					
					repaint();
					}
				}, 0,1000);
				this.requestFocus();
				this.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e){
					int key=e.getKeyCode();
					switch(key){
					case KeyEvent.VK_W:creepForFood(Worm.UP);
					break;
					case KeyEvent.VK_S:creepForFood(Worm.DOWN);
					break;
					case KeyEvent.VK_A:creepForFood(Worm.LEFT);
					break;
					case KeyEvent.VK_D:creepForFood(Worm.RIGHT);
					break;
					}
				}
			});
		}
	}
	private void creepForFood(int direction) {
		if(worm.hit(direction)){
			worm=new Worm();
			food=newFood();
			}else{
				boolean eat=worm.creep(direction,food);
				if(eat){
					food=newFood();
				}
			}					
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("贪吃虫");
		frame.setSize(450, 480);
		frame.setBackground(Color.BLACK);
		frame.setLocationRelativeTo(null);//相对于某参照居中，null表示无参照
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		WormStage stage = new WormStage();		
		stage.setSize(CELL_SIZE*COLS,CELL_SIZE*ROWS);
		frame.setLocationRelativeTo(null);
		stage.setLocation(50, 50);
		frame.add(stage);
		stage.setVisible(true);
		
		stage.go();
	  }
}



