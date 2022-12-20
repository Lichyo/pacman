package pacman;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Object;

public class game extends JPanel implements ActionListener{

		//static game frm=new game();
		static int score=0;				//分數
		static int lives=3;				//lives
		
		private final Font fnt = new Font("Arial",Font.BOLD,20);	//字形
		static JLabel lab=new JLabel(new ImageIcon("img/.png"));	//建立標籤
		static JLabel scorebar=new JLabel("Score:");	//建立標籤
		static JPanel pan=new JPanel();
		
		static Image pac_offscreen=null;		//雙緩存用
		
		
		
/*		static	Image iup=new ImageIcon("img/up.png").getImage();			//獲取相應路徑下的圖片
		static	Image idown=new ImageIcon("img/down.png").getImage();
		static	Image ileft=new ImageIcon("img/left.png").getImage();
		static	Image iright=new ImageIcon("img/right.png").getImage();
		static  Image heart=new ImageIcon("img/left.png").getImage();*/
		
				
		static	ImageIcon iup=new ImageIcon("img/up.png");			//獲取相應路徑下的圖片
		static	ImageIcon idown=new ImageIcon("img/down.png");
		static	ImageIcon ileft=new ImageIcon("img/left.png");
		static	ImageIcon iright=new ImageIcon("img/right.png");
		static  ImageIcon heart=new ImageIcon("img/right.png");
		
		
		static boolean gamestart=false;
		private boolean dead=false; //玩家是否死亡
		static boolean p_up=false;		//現在按下哪個按件
		static boolean p_down=false;
		static boolean p_left=false;
		static boolean p_right=false;
		

		
		final static int blocksize =30; 		//方塊大小			//原18
		private static final int n_block =20;		//場地的邊的方塊數量
		
		static int reservex=40;						//邊框保留位置
		static int reservey=100;							
		final static int offsetx=13;			//角色偏移位置
		final static int offsety=72;
		static int x=offsetx+blocksize*10,y=offsety+blocksize*12;				//pacman位置
		static int positionx=10,positiony=12;				//pacman位置(格子)
		static int dx,dy;				//x、y變化量
		static int speed=10;
		static int hearts=3;	
		
								
		private int[] leveldata={
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0,21,19,19,17,19,19,19,25, 0, 0,21,19,19,19,17,19,19,25, 0,
			0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0,
			0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0,
			0,20,19,19,18,19,19,19,24, 0, 0,20,19,19,19,18,19,19,24, 0,
			0,28, 0, 0, 0, 0, 0, 0,28, 0, 0,28, 0, 0, 0, 0, 0, 0,28, 0,
			0,20,19,19,25, 0, 5, 3, 2, 3, 3, 2, 3, 9, 0,21,19,19,24, 0,
			0,28, 0, 0,28, 0,12, 0, 0, 0, 0, 0, 0,12, 0,28, 0, 0,28, 0,
			0,28, 0, 0,28, 0,12, 0, 5, 1, 1, 9, 0,12, 0,28, 0, 0,28, 0,
			0,20,19,19,16,19, 8, 0, 6, 2, 2,10, 0, 4,19,16,19,19,24, 0,			//10
			0,28, 0, 0,28, 0,12, 0, 0, 0, 0, 0, 0,12, 0,28, 0, 0,28, 0,
			0,28, 0,21,26, 0, 4, 3, 3, 3, 3, 3, 3, 8, 0,22,25, 0,28, 0,			//12
			0,28, 0,28, 0, 0,28, 0, 0, 0, 0, 0, 0,28, 0, 0,28, 0,28, 0,
			0,28, 0,28, 0,21,18,17,19,19,19,19,17,18,25, 0,28, 0,28, 0,		
			0,20,19,18,19,24, 0,28, 0, 0, 0, 0,28, 0,20,19,18,19,24, 0,
			0,28, 0, 0, 0,28, 0,28, 0, 0, 0, 0,28, 0,28, 0, 0, 0,28, 0,
			0,20,19,19,19,26, 0,20,19,19,19,19,24, 0,22,19,19,19,24, 0,
			0,28, 0, 0, 0, 0, 0,28, 0, 0, 0, 0,28, 0, 0, 0, 0, 0,28, 0,
			0,22,19,19,19,19,19,18,19,19,19,19,18,19,19,19,19,19,26, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0


		};
		
		
		
//---------------		
/*		private Dimension d;
		
		private boolean ingame =false;
		private boolean dead=false; //玩家是否死亡
		

	
		private final int screensize=blocksize*n_block;		//場地大小
		private final int pacman_speed=6;		//玩家速度
		
		
		private int n_ghost=6;

		private int max_ghost;
		private int [] dx,dy;
		private int [] ghost_x,ghost_y,ghostspeed,ghost_dx,ghost_dy;
		
		private int pacman_x,pacman_y,pacman_dx,pacman_dy;
		private int req_dx,req_dy;
		
		private final int validspeed[]={1,2,4,6,8};
		private final int maxSpeed =6;
		private int currentspeed =3;
		private short [] screendata;
*/		
		
		
//-----------------------建構元

		
		
		public game(){					
			setBackground(Color.black);	
		    addKeyListener(new kadapter());
			drawscorebar();
			
		}
		

		
		
	
		
/*		public void update(Graphics g){					//參考資料https://blog.csdn.net/weixin_50679163/article/details/119490947		利用雙緩存技術解決角色閃爍
			
			if(pac_offscreen==null){
				pac_offscreen=this.createImage(16,16);		
			}	
			
			Graphics gOff=pac_offscreen.getGraphics();	
			paint(gOff);
			g.drawImage(pac_offscreen,0,0,null);
		
			
		}	*/

		public void drawscorebar(){			//畫分數欄			
	
			scorebar.setText("Score:"+String.valueOf(score));
			scorebar.setLocation(0,0);
			scorebar.setForeground(Color.white);
			scorebar.setFont(fnt);
			this.add(scorebar);
		}
		
		
        		
        		
//---------------繪製地圖        
        
		public void drawlevel(Graphics2D graphics2D,int[] map){			//畫出地圖
			int x=0,y=0;
			for(int i=0;i<400;i++){
				x=i/20;		//設定xy
				y=i%20;
				if(map[y*20+x]==0){
					graphics2D.setColor(Color.blue);				//畫牆壁
					graphics2D.drawRect(reservex+x*blocksize,reservey+y*blocksize,blocksize,blocksize);	
				}
				if((map[y*20+x]&16)!=0){				//初始設定有白點
					graphics2D.setColor(Color.white);
					graphics2D.fillOval(reservex+10+x*blocksize,reservey+10+y*blocksize,15,15);			//位移10->至中
				}
				
			}
			
		}	
		
		public void drawpacman(){			//設定pacman的圖案
			if(p_up&&!p_down&&!p_left&&!p_right){
				lab.setIcon(iup);
			}else if(!p_up&&p_down&&!p_left&&!p_right){	
				lab.setIcon(idown);
			}else if(!p_up&&!p_down&&p_left&&!p_right){
				lab.setIcon(ileft);
			}else if(!p_up&&!p_down&&!p_left&&p_right){
				lab.setIcon(iright);
			}
		}
		
		public void drawhearts(Graphics2D g){			//畫出生命直
			int hx=blocksize+15,hy=30;
			for(int i=hearts;i>0;i--){
				g.drawImage(heart.getImage(),hx*(i-1),hy,this);
			}
		}		
		
		public void gamestart(){		//遊戲開始
			System.out.println("start");
			gamestart=true;
			
			lab.setIcon(iup);		//建立pacman
			lab.setLocation(x,y);
			lab.setSize(blocksize,blocksize);
			this.add(lab);
			
			
			if(dead){
				death();
			}
			
		}
		
		public void death(){		//死亡
			lives=lives-1;
			if(lives==0){
				gamestart=false;		
			}
		}
		
		public void paintComponent(Graphics graphics){			//重寫paint方法會導致該容器中的其他組件無法繪製		-->會一直重複	

			super.paintComponent(graphics);
			
			Graphics2D graphics2D=(Graphics2D) graphics;
			
			try{
				Thread.sleep(80);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			run();
			
			drawlevel(graphics2D,leveldata);		//畫出地圖
			drawscorebar();
			
			drawpacman();
			drawhearts(graphics2D);
			repaint();								
			
		}
		

			
			

//--------------吃到豆子	
		public void eatdot(){							//需修改
	
			Graphics g=getGraphics();
			if((leveldata[positionx+20*(positiony-1)-1]&16)!=0){
				leveldata[positionx+20*(positiony-1)-1]=leveldata[positionx+20*(positiony-1)-1]-16+32;	//清除白點，並紀錄此處原本有白點
				score=score+1;
			}
			
			
		}
		
		
        //設定初始
        /*
         for(int i=0;i<399;i++){
         	if(leveldata[i]>32){
         		leveldata[i]=leveldata[i]-32+16;
       
         	}
         		
         }
         */
        
        public void run(){				//移動			//判定有誤
        	//repaint();					//停止閃爍
 
        	if(p_up&&!p_down&&!p_left&&!p_right){				//上			
        		 
        		 if(!((leveldata[positionx+20*(positiony-1)-1]&1)>0)||(y-offsety)%blocksize>0){
					
					if((x-offsetx)%blocksize!=0){				
						x=(positionx)*blocksize+offsetx;
					}					
					
					y=y-speed;		
	 				if(((y-offsety)%blocksize)==(blocksize/3)){
	 					System.out.println("上");
	 					positiony=positiony-1;
	 				}
				}
        	}else if(!p_up&&p_down&&!p_left&&!p_right){
        		if(!((leveldata[positionx+20*(positiony-1)-1]&2)>0)||(y-offsety)%blocksize>0){		//如果當前格子右邊可通行||還沒走到底
	 				if((x-offsetx)%blocksize!=0){								//如果角色面前有牆壁但判定位置面前沒有牆壁->矯正到正確位置
						x=(positionx)*blocksize+offsetx;
					}
	 				
	 				y=y+speed;
	 				if(((y-offsety)%blocksize)==(2*blocksize/3)){			//判斷現在格子
	 					System.out.println("下");
	 					positiony=positiony+1;
	 				}
 					
 			 	}
        	}else if(!p_up&&!p_down&&p_left&&!p_right){
        		if(!((leveldata[positionx+20*(positiony-1)-1]&4)>0)||(x-offsetx)%blocksize>0){
					if((y-offsety)%blocksize!=0){				
						y=(positiony)*blocksize+offsety;
					}
					
					x=x-speed;
					if(((x-offsetx)%blocksize)==(blocksize/3)){
						System.out.println("左");
	 					positionx=positionx-1;
	 				}	
				}
        	}else if(!p_up&&!p_down&&!p_left&&p_right){
        		if(!((leveldata[positionx+20*(positiony-1)-1]&8)>0)||(x-offsetx)%blocksize>0){		
					if((y-offsety)%blocksize!=0){				
						y=(positiony)*blocksize+offsety;
					}
					
					x=x+speed;
					if(((x-offsetx)%blocksize)==(2*blocksize/3)){
						System.out.println("右");
	 					positionx=positionx+1;
	 				}
				}
        	}
        	lab.setLocation(x,y);	
        }
        
//----------------鍵盤監聽

	class kadapter extends KeyAdapter{		//內部類別、適配器
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		@Override
		public void keyPressed(KeyEvent e){						//鍵盤延遲待解決 -->https://blog.csdn.net/sangjinchao/article/details/60584630
			eatdot();
			
 			if(e.getKeyCode()==KeyEvent.VK_DOWN){			//按下下方向鍵時	
				p_down=true;
			}else if(e.getKeyCode()==KeyEvent.VK_UP){
				p_up=true;
			}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				p_right=true;
			}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
				p_left=true;
			}else if(e.getKeyCode()==KeyEvent.VK_SPACE){
				gamestart();
			}
			
			
			
			
 			System.out.println("x"+x+"y"+y);
 			System.out.println("x"+positionx+"y"+positiony+"["+(positionx+20*(positiony-1)-1)+"]");
 			System.out.println("data"+leveldata[positionx+20*(positiony-1)-1]);
 			System.out.println(p_up+""+p_down+" "+p_left+" "+p_right);
 		}
	 	
	 	@Override
		public void keyReleased(KeyEvent e) {
			
			if(e.getKeyCode()==KeyEvent.VK_DOWN){			//按下下方向鍵時
				p_down=false;
 				
			}else if(e.getKeyCode()==KeyEvent.VK_UP){
				p_up=false;
				
			}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				p_right=false;
				
			}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
				p_left=false;
			}
			
		}
		
		
	}
      
	public void actionPerformed(ActionEvent e){
		repaint();
	}
		
		
}