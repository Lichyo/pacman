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
	

		
		//Timer timer = new Timer();
		
		private final Font fnt = new Font("Arial",Font.BOLD,20);	//字形
		static JLabel lab=new JLabel(new ImageIcon("img/left.png"));	//建立標籤
		static JLabel scorebar=new JLabel("Score:");	//建立標籤
		static JPanel pan=new JPanel();
		
		static Image pac_offscreen=null;		//雙緩存用	
				
		static	ImageIcon iup=new ImageIcon("img/up.png");			//獲取相應路徑下的圖片
		static	ImageIcon idown=new ImageIcon("img/down.png");
		static	ImageIcon ileft=new ImageIcon("img/left.png");
		static	ImageIcon iright=new ImageIcon("img/right.png");
		static  ImageIcon heart=new ImageIcon("img/right.png");
		
		static  Image redghost=new ImageIcon("img/redghost.png").getImage();//鬼的圖片
		static  Image pinkghost=new ImageIcon("img/pinkghost.png").getImage();//鬼的圖片
		static  Image blueghost=new ImageIcon("img/blueghost.png").getImage();//鬼的圖片
		static  Image orangeghost=new ImageIcon("img/orangeghost.png").getImage();//鬼的圖片
		static  Image orangemode3=new ImageIcon("img/orangemode3.png").getImage();//鬼的圖片
		static  Image fright=new ImageIcon("img/fright.png").getImage();//鬼的圖片
		
		final static int blocksize =30; 		//方塊大小			//原18
		private static final int n_block =20;		//場地的邊的方塊數量
		
		static int reservex=40,reservey=100;						//邊框保留位置							
		final static int offsetx=13;			//角色偏移位置
		final static int offsety=72;
	
		static int randomx=10;
		static int randomy=12;
	
		static int ghost_speed=6;		//鬼的速度	
		static int speed=10;			//pacman速度
//-----------------以下為需要重置的變數	
		static int score=0;				//分數
		static int lives=5;				//lives
	
		static boolean gamestart=false;
		private boolean dead=false; //玩家是否死亡
		static boolean p_up=false;		//現在按下哪個按件
		static boolean p_down=false;
		static boolean p_left=false;
		static boolean p_right=false;
		
		static int orangeleft=6;		//剩下多少大力丸
		static boolean trollleft=true;
		

		static int x=offsetx+blocksize*10,y=offsety+blocksize*12;				//pacman位置
		static int positionx=10,positiony=12;				//pacman位置(格子)
		static int pacmanface=3;		//pacman面對的方向 上1 下2 左3 右4

		

		
		static int redghost_x=offsetx+blocksize*9,redghost_y=offsety+blocksize*2;//紅鬼的位置
		static int redghost_positionx=10,redghost_positiony=10;//紅鬼的格子
		static int redface=1;		//紅鬼面對的方向 上1 下2 左3 右4
		
		static int pinkghost_x=offsetx+blocksize*12,pinkghost_y=offsety+blocksize*2;//粉紅鬼的位置
		static int pinkghost_positionx=10,pinkghost_positiony=10;//粉紅鬼的格子
		static int pinkface=1;		//粉紅鬼面對的方向 上1 下2 左3 右4
		static int pinkghosttargetx=positionx,pinkghosttargety=positiony;	//粉紅鬼的目標
		
		static int blueghost_x=offsetx+blocksize*6,blueghost_y=offsety+blocksize*17;//藍鬼的位置
		static int blueghost_positionx=10,blueghost_positiony=10;//藍鬼的格子
		static int blueface=1;		//藍鬼面對的方向 上1 下2 左3 右4
		static int blueghosttargetx,blueghosttargety;	//藍鬼的目標

		
		static int orangeghost_x=offsetx+blocksize*15,orangeghost_y=offsety+blocksize*17;//橘鬼的位置
		static int orangeghost_positionx=10,orangeghost_positiony=10;//橘鬼的格子
		static int orangeface=1;		//橘鬼面對的方向 上1 下2 左3 右4
		static int orangeghosttargetx,orangeghosttargety;
		static int orangemode=1;		//橘鬼模式
								
								
		private int[] leveldata={
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0,37,19,19,17,19,19,19,25, 0, 0,21,19,19,19,17,19,19,41, 0,
			0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0,
			0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0,
			0,20,19,19,18,19,19,19,24, 0, 0,20,19,19,19,18,19,19,24, 0,
			0,28, 0, 0, 0, 0, 0, 0,28, 0, 0,28, 0, 0, 0, 0, 0, 0,28, 0,
			0,20,19,19,25, 0, 5, 3, 2, 3, 3, 2, 3, 9, 0,21,19,19,24, 0,
			0,28, 0, 0,28, 0,12, 0, 0, 0, 0, 0, 0,12, 0,28, 0, 0,28, 0,
			0,28, 0, 0,28, 0,12, 0, 5, 1, 1, 9, 0,12, 0,28, 0, 0,28, 0,
			0,20,19,19,16,19, 8, 0, 6, 2, 2,10, 0, 4,19,16,19,19,24, 0,			//0 障礙物 1上邊界 2下邊界 4左邊界 8右邊界 16白點 32大力丸 64紀錄本來有白點(防止十字路口出現障礙物)
			0,28, 0, 0,28, 0,12, 0, 0, 0, 0, 0, 0,12, 0,28, 0, 0,28, 0,			
			0,28, 0,37,26, 0, 4, 3, 3, 3, 3, 3, 3, 8, 0,22,41, 0,28, 0,			
			0,28, 0,28, 0, 0,28, 0, 0, 0, 0, 0, 0,28, 0, 0,28, 0,28, 0,
			0,28, 0,28, 0,21,18,17,19,19,19,19,17,18,25, 0,28, 0,28, 0,		
			0,20,19,18,19,24, 0,28, 0, 0, 0, 0,28, 0,20,19,18,19,24, 0,
			0,28, 0, 0, 0,28, 0,28, 0, 0, 0, 0,28, 0,28, 0, 0, 0,28, 0,
			0,20,19,19,19,26, 0,20,19,19,19,19,24, 0,22,19,19,19,24, 0,
			0,28, 0, 0, 0, 0, 0,28, 0, 0, 0, 0,28, 0, 0, 0, 0, 0,28, 0,
			0,38,19,19,19,19,19,18,19,19,19,19,18,19,19,19,19,19,42, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0


		};
		
	
//---------------------------鬼	
		public void heat(int gx,int gy){	//被鬼碰撞
			if(Math.abs(gx-x)<(blocksize)&&Math.abs(gy-y)<(blocksize)){
				System.out.println("heat");
				//death();
			}
		}

		
//-------------紅鬼(追)

      	public void redghost(Graphics g){			//計算紅鬼的位置		紅鬼目標->玩家位置
			
			if(gamestart){
				redghost_positionx=(redghost_x-offsetx)/blocksize;		//判斷鬼的位置
				redghost_positiony=(redghost_y-offsety)/blocksize;
				
				if(((redghost_y-offsety)%blocksize)==0&&((redghost_x-offsetx)%blocksize)==0){		//確保鬼會走完一個格子才轉身
				
					switch(leveldata[redghost_positionx+20*(redghost_positiony-1)-1]&15){		//判斷鬼該面朝哪
						case 1:					//上有牆壁
							if(redface==1){				//鬼從下上來
								if((redghost_positionx-positionx)>=0){		//鬼在人右邊
									redface=3;
								}else{
									redface=4;
								}
							}else if(redface==4||redface==3){			//鬼從左或右過來
								if((redghost_positiony-positiony)<0){
									redface=2;
								}
							}
	
							
							break;
						case 2:					//下有牆壁
							if(redface==2){				//鬼從上下來
								if((redghost_positionx-positionx)>=0){		//鬼在人右邊
									redface=3;
								}else{
									redface=4;
								}
							}else if(redface==4||redface==3){			//鬼從左或右過來
								if((redghost_positiony-positiony)>0){
									redface=1;
								}
							}						
							break;	
						case 4:					//左有牆壁
							if(redface==3){				//鬼從右過來
								if((redghost_positiony-positiony)<=0){		//鬼在人上面
									redface=2;
								}else{
									redface=1;
								}
							}else if(redface==1||redface==2){			//鬼從上或下過來
								if((redghost_positionx-positionx)<0){
									redface=4;
								}
							}
							break;
						case 5:					//上左有牆壁
							if(redface==3){
								redface=2;
							}else if(redface==1){
								redface=4;
							}
							break;	
						case 6:					//下左有牆壁
							if(redface==3){
								redface=1;
							}else if(redface==2){
								redface=4;
							}
							break;	
						case 8:					//右有牆壁
							if(redface==4){				//鬼從左過來
								if((redghost_positiony-positiony)<=0){		//鬼在人上面
									redface=2;
								}else{
									redface=1;
								}
							}else if(redface==1||redface==2){			//鬼從上或下過來
								if((redghost_positionx-positionx)>0){
									redface=3;
								}
							}					
							break;	
						case 9:					//上右有牆壁
							if(redface==4){
								redface=2;
							}else if(redface==1){
								redface=3;
							}					
							break;	
						case 10:				//下右有牆壁
							if(redface==4){
								redface=1;
							}else if(redface==2){
								redface=3;
							}					
							break;	
						case 0:					//十字路口				
							if(redface==3||redface==4){				//面對左或右
								if((redghost_positiony-positiony)<=0){		//鬼在人上面
									redface=2;
								}else if((redghost_positiony-positiony)>=0){	//鬼在人下面
									redface=1;
								}
							}else{									//面對上或下
								if((redghost_positionx-positionx)<=0){		//鬼在人左邊
									redface=4;
								}else if((redghost_positionx-positionx)>=0){	//鬼在人右邊
									redface=3;
								}
							}
							
							
							break;	
					}
				}
			
				
			//	System.out.println(redface);
				
					switch(redface){
						case 1:		//向上
							redghost_y=redghost_y-ghost_speed;
							break;	
						case 2:		//向下
							redghost_y=redghost_y+ghost_speed;
							break;	
						case 3:		//向左
							redghost_x=redghost_x-ghost_speed;
							break;	
						case 4:		//向右
							redghost_x=redghost_x+ghost_speed;
							break;	
					}
				g.drawImage(redghost,redghost_x,redghost_y,this);
				
				heat(redghost_x,redghost_y);
			}
		}
//-------------紅鬼
		
//-------------粉紅鬼(圍堵)
		
      	public void pinkghost(Graphics g){			//計算粉紅鬼的位置		
			
			if(gamestart){
				pinkghost_positionx=(pinkghost_x-offsetx)/blocksize;		//判斷鬼的位置
				pinkghost_positiony=(pinkghost_y-offsety)/blocksize;
				
			/*	pinkghosttargetx=positionx;
				pinkghosttargety=positiony;*/
				switch(pacmanface){									//粉紅鬼的目標->玩家面前2格
					case 1:
						pinkghosttargety=positiony-2;
						break;
					case 2:
						pinkghosttargety=positiony+2;
						break;
					case 3:
						pinkghosttargetx=positionx-2;
						break;
					case 4:
						pinkghosttargetx=positionx+2;
						break;		
				}
				
				if(((pinkghost_y-offsety)%blocksize)==0&&((pinkghost_x-offsetx)%blocksize)==0){		//確保鬼會走完一個格子才轉身
					switch(leveldata[pinkghost_positionx+20*(pinkghost_positiony-1)-1]&15){		//判斷鬼該面朝哪
						case 1:					//上有牆壁
							if(pinkface==1){				//鬼從下上來
								if((pinkghost_positionx-pinkghosttargetx)>=0){		//鬼在目標右邊
									pinkface=3;
								}else{
									pinkface=4;
								}
							}else if(pinkface==4||pinkface==3){			//鬼從左或右過來
								if((pinkghost_positiony-pinkghosttargety)<0){
									pinkface=2;
								}
							}
	
							
							break;
						case 2:					//下有牆壁
							if(pinkface==2){				//鬼從上下來
								if((pinkghost_positionx-pinkghosttargetx)>=0){		//鬼在目標右邊
									pinkface=3;
								}else{
									pinkface=4;
								}
							}else if(pinkface==4||pinkface==3){			//鬼從左或右過來
								if((pinkghost_positiony-pinkghosttargety)>0){
									pinkface=1;
								}
							}						
							break;	
						case 4:					//左有牆壁
							if(pinkface==3){				//鬼從右過來
								if((pinkghost_positiony-pinkghosttargety)<=0){		//鬼在目標上面
									pinkface=2;
								}else{
									pinkface=1;
								}
							}else if(pinkface==1||pinkface==2){			//鬼從上或下過來
								if((pinkghost_positionx-pinkghosttargetx)<0){
									pinkface=4;
								}
							}
							break;
						case 5:					//上左有牆壁
							if(pinkface==3){
								pinkface=2;
							}else if(pinkface==1){
								pinkface=4;
							}
							break;	
						case 6:					//下左有牆壁
							if(pinkface==3){
								pinkface=1;
							}else if(pinkface==2){
								pinkface=4;
							}
							break;	
						case 8:					//右有牆壁
							if(pinkface==4){				//鬼從左過來
								if((pinkghost_positiony-pinkghosttargety)<=0){		//鬼在目標上面
									pinkface=2;
								}else{
									pinkface=1;
								}
							}else if(pinkface==1||pinkface==2){			//鬼從上或下過來
								if((pinkghost_positionx-pinkghosttargetx)>0){
									pinkface=3;
								}
							}					
							break;	
						case 9:					//上右有牆壁
							if(pinkface==4){
								pinkface=2;
							}else if(pinkface==1){
								pinkface=3;
							}					
							break;	
						case 10:				//下右有牆壁
							if(pinkface==4){
								pinkface=1;
							}else if(pinkface==2){
								pinkface=3;
							}					
							break;	
						case 0:					//十字路口				
							if(pinkface==3||pinkface==4){				//面對左或右
								if((pinkghost_positiony-pinkghosttargety)<=0){		//鬼在目標上面
									pinkface=2;
								}else if((pinkghost_positiony-pinkghosttargety)>=0){	//鬼在目標下面
									pinkface=1;
								}
							}else{									//面對上或下
								if((pinkghost_positionx-pinkghosttargetx)<=0){		//鬼在人左邊
									pinkface=4;
								}else if((pinkghost_positionx-pinkghosttargetx)>=0){	//鬼在目標右邊
									pinkface=3;
								}
							}
							
							
							break;	
					}
				}
			
				
					switch(pinkface){
						case 1:		//向上
							pinkghost_y=pinkghost_y-ghost_speed;
							break;	
						case 2:		//向下
							pinkghost_y=pinkghost_y+ghost_speed;
							break;	
						case 3:		//向左
							pinkghost_x=pinkghost_x-ghost_speed;
							break;	
						case 4:		//向右
							pinkghost_x=pinkghost_x+ghost_speed;
							break;	
					}
				g.drawImage(pinkghost,pinkghost_x,pinkghost_y,this);
				
				heat(pinkghost_x,pinkghost_y);
			}
		}		
//------------------粉紅鬼
//-------------藍色鬼(紅與角色的相對位置)
		
      	public void blueghost(Graphics g){			//計算藍鬼的位置		
			
			if(gamestart){
				blueghost_positionx=(blueghost_x-offsetx)/blocksize;		//判斷鬼的位置
				blueghost_positiony=(blueghost_y-offsety)/blocksize;


				switch(pacmanface){							
					case 1:
						blueghosttargetx=positionx-2;
						break;
					case 2:
						blueghosttargety=positiony+2;
						break;
					case 3:
						blueghosttargetx=positionx-2;
						break;
					case 4:
						blueghosttargetx=positionx+2;
						break;		
				}
				
				blueghosttargetx=2*blueghosttargetx-redghost_positionx;		//藍鬼的目標->紅鬼到玩家面前2格的兩倍距離
				blueghosttargety=2*blueghosttargety-redghost_positiony;
				
				
				if(((blueghost_y-offsety)%blocksize)==0&&((blueghost_x-offsetx)%blocksize)==0){		//確保鬼會走完一個格子才轉身
					switch(leveldata[blueghost_positionx+20*(blueghost_positiony-1)-1]&15){		//判斷鬼該面朝哪
						case 1:					//上有牆壁
							if(blueface==1){				//鬼從下上來
								if((blueghost_positionx-blueghosttargetx)>=0){		//鬼在目標右邊
									blueface=3;
								}else{
									blueface=4;
								}
							}else if(blueface==4||blueface==3){			//鬼從左或右過來
								if((blueghost_positiony-blueghosttargety)<0){
									blueface=2;
								}
							}
	
							
							break;
						case 2:					//下有牆壁
							if(blueface==2){				//鬼從上下來
								if((blueghost_positionx-blueghosttargetx)>=0){		//鬼在目標右邊
									blueface=3;
								}else{
									blueface=4;
								}
							}else if(blueface==4||blueface==3){			//鬼從左或右過來
								if((blueghost_positiony-blueghosttargety)>0){
									blueface=1;
								}
							}						
							break;	
						case 4:					//左有牆壁
							if(blueface==3){				//鬼從右過來
								if((blueghost_positiony-blueghosttargety)<=0){		//鬼在目標上面
									blueface=2;
								}else{
									blueface=1;
								}
							}else if(blueface==1||blueface==2){			//鬼從上或下過來
								if((blueghost_positionx-blueghosttargetx)<0){
									blueface=4;
								}
							}
							break;
						case 5:					//上左有牆壁
							if(blueface==3){
								blueface=2;
							}else if(blueface==1){
								blueface=4;
							}
							break;	
						case 6:					//下左有牆壁
							if(blueface==3){
								blueface=1;
							}else if(blueface==2){
								blueface=4;
							}
							break;	
						case 8:					//右有牆壁
							if(blueface==4){				//鬼從左過來
								if((blueghost_positiony-blueghosttargety)<=0){		//鬼在目標上面
									blueface=2;
								}else{
									blueface=1;
								}
							}else if(blueface==1||blueface==2){			//鬼從上或下過來
								if((blueghost_positionx-blueghosttargetx)>0){
									blueface=3;
								}
							}					
							break;	
						case 9:					//上右有牆壁
							if(blueface==4){
								blueface=2;
							}else if(blueface==1){
								blueface=3;
							}					
							break;	
						case 10:				//下右有牆壁
							if(blueface==4){
								blueface=1;
							}else if(blueface==2){
								blueface=3;
							}					
							break;	
						case 0:					//十字路口				
							if(blueface==3||blueface==4){				//面對左或右
								if((blueghost_positiony-blueghosttargety)<=0){		//鬼在目標上面
									blueface=2;
								}else if((blueghost_positiony-blueghosttargety)>=0){	//鬼在目標下面
									blueface=1;
								}
							}else{									//面對上或下
								if((blueghost_positionx-blueghosttargetx)<=0){		//鬼在人左邊
									blueface=4;
								}else if((blueghost_positionx-blueghosttargetx)>=0){	//鬼在目標右邊
									blueface=3;
								}
							}
							break;	
					}
				}
			
				
					switch(blueface){
						case 1:		//向上
							blueghost_y=blueghost_y-ghost_speed;
							break;	
						case 2:		//向下
							blueghost_y=blueghost_y+ghost_speed;
							break;	
						case 3:		//向左
							blueghost_x=blueghost_x-ghost_speed;
							break;	
						case 4:		//向右
							blueghost_x=blueghost_x+ghost_speed;
							break;	
					}
				g.drawImage(blueghost,blueghost_x,blueghost_y,this);

				heat(blueghost_x,blueghost_y);
			}
		}		
//--------------藍鬼
		
//--------------橘鬼(亂數且有隨機模式)		
      	public void orangeghost(Graphics g){			//計算粉紅鬼的位置		
			
			if(gamestart){
				orangeghost_positionx=(orangeghost_x-offsetx)/blocksize;		//判斷鬼的位置
				orangeghost_positiony=(orangeghost_y-offsety)/blocksize;

				if(orangemode==1){					//亂走模式
					if(((orangeghost_y-offsety)%blocksize)==0&&((orangeghost_x-offsetx)%blocksize)==0){		//確保鬼會走完一個格子才轉身
						switch(leveldata[orangeghost_positionx+20*(orangeghost_positiony-1)-1]&15){		//判斷鬼該面朝哪
							case 1:					//上有牆壁
								if(orangeface==1){				//鬼從下上來
									if(Math.random()*2>1){					//左或右
										orangeface=3;
									}else{
										orangeface=4;
									}
								}else if(orangeface==4||orangeface==3){			//鬼從左或右過來
									if(Math.random()*2>1){					//1/2換邊
										orangeface=2;
									}
								}
		
								
								break;
							case 2:					//下有牆壁
								if(orangeface==2){				//鬼從上下來
									if(Math.random()*2>1){		
										orangeface=3;
									}else{
										orangeface=4;
									}
								}else if(orangeface==4||orangeface==3){			//鬼從左或右過來
									if(Math.random()*2>1){
										orangeface=1;
									}
								}						
								break;	
							case 4:					//左有牆壁
								if(orangeface==3){				//鬼從右過來
									if(Math.random()*2>1){		//鬼在目標上面
										orangeface=2;
									}else{
										orangeface=1;
									}
								}else if(orangeface==1||orangeface==2){			//鬼從上或下過來
									if(Math.random()*2>1){
										orangeface=4;
									}
								}
								break;
							case 5:					//上左有牆壁
								if(orangeface==3){
									orangeface=2;
								}else if(orangeface==1){
									orangeface=4;
								}
								break;	
							case 6:					//下左有牆壁
								if(orangeface==3){
									orangeface=1;
								}else if(orangeface==2){
									orangeface=4;
								}
								break;	
							case 8:					//右有牆壁
								if(orangeface==4){				//鬼從左過來
									if(Math.random()*2>1){		//鬼在目標上面
										orangeface=2;
									}else{
										orangeface=1;
									}
								}else if(orangeface==1||orangeface==2){			//鬼從上或下過來
									if(Math.random()*2>1){
										orangeface=3;
									}
								}					
								break;	
							case 9:					//上右有牆壁
								if(orangeface==4){
									orangeface=2;
								}else if(orangeface==1){
									orangeface=3;
								}					
								break;	
							case 10:				//下右有牆壁
								if(orangeface==4){
									orangeface=1;
								}else if(orangeface==2){
									orangeface=3;
								}					
								break;	
							case 0:					//十字路口				
								orangeface=orangeface+(int)(Math.random()*3+1);
								if(orangeface>4){
									orangeface=orangeface-4;
								}
								break;	
						}
					}
		
				}else if(orangemode==3){
					if(((orangeghost_y-offsety)%blocksize)==0&&((orangeghost_x-offsetx)%blocksize)==0){		//確保鬼會走完一個格子才轉身
						if((int)(Math.random()*2)==0){				//讓鬼的移動較好看
							if(orangeghost_positionx-positionx>0){	
								orangeface=3;				
							}else{
								orangeface=4;
							}
      					}else{
      						if(orangeghost_positiony-positiony>0){
								orangeface=1;
							}else{
								orangeface=2;
							}
      					}
						
					}
				}
				
				switch(orangeface){
						case 1:		//向上
							orangeghost_y=orangeghost_y-ghost_speed;
							break;	
						case 2:		//向下
							orangeghost_y=orangeghost_y+ghost_speed;
							break;	
						case 3:		//向左
							orangeghost_x=orangeghost_x-ghost_speed;
							break;	
						case 4:		//向右
							orangeghost_x=orangeghost_x+ghost_speed;
							break;	
				}
				
				switch(orangemode){
					case 1:			//一般
						g.drawImage(orangeghost,orangeghost_x,orangeghost_y,this);
						break;
					case 2:			//驚嚇
						g.drawImage(fright,orangeghost_x,orangeghost_y,this);
						break;
					case 3:			//mode3
						g.drawImage(orangemode3,orangeghost_x,orangeghost_y,this);
						break;
					
				}
			//	g.drawImage(orangeghost,orangeghost_x,orangeghost_y,this);
				
				heat(orangeghost_x,orangeghost_y);
			}
		}
		
//--------------橘鬼
		
//--------------------------------		
		public game(){					//建構元
			setBackground(Color.black);	
		    addKeyListener(new kadapter());
			drawscorebar();
			
		}
		

		
		
	
		
		public void update(Graphics g){					//參考資料https://blog.csdn.net/weixin_50679163/article/details/119490947		利用雙緩存技術解決角色閃爍
			
			if(pac_offscreen==null){
				pac_offscreen=this.createImage(16,16);		
			}	
			
			Graphics gOff=pac_offscreen.getGraphics();	
			paint(gOff);
			g.drawImage(pac_offscreen,0,0,null);
		
			
		}	

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
				}else if((map[y*20+x]&32)!=0){				//初始設定有大力丸
					graphics2D.setColor(Color.orange);
					graphics2D.fillOval(reservex+4+x*blocksize,reservey+4+y*blocksize,23,23);			//位移10->至中
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
			for(int i=lives;i>0;i--){
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

			run();
			
			drawlevel(graphics2D,leveldata);		//畫出地圖
			
			try{
				Thread.sleep(60);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			redghost(graphics);
			pinkghost(graphics);
			blueghost(graphics);
			orangeghost(graphics);
			drawscorebar();
			
			drawpacman();
			
			drawhearts(graphics2D);
			
			repaint();						//重複再畫一次								
			
		}
		

			
			

//--------------吃到豆子	
		public void eatdot(){						
			Graphics g=getGraphics();
			if((leveldata[positionx+20*(positiony-1)-1]&16)!=0){
				leveldata[positionx+20*(positiony-1)-1]=leveldata[positionx+20*(positiony-1)-1]-16+64;	//清除白點，並紀錄此處原本有白點
				score=score+1;
			}else if((leveldata[positionx+20*(positiony-1)-1]&32)!=0){
				leveldata[positionx+20*(positiony-1)-1]=leveldata[positionx+20*(positiony-1)-1]-32;	//清除大力丸
				score=score+5;
				if(trollleft){
					if(((int)(Math.random()*orangeleft))==0){		//吃到假大力丸	
					
						switch((int)(Math.random()*3)+1){
							case 1:			//傳送到隨機位置
								randomx=((int)(Math.random()*(n_block-2))+2);	//建立隨機位置
								randomy=((int)(Math.random()*(n_block-2))+2);
				
								while(leveldata[randomx+20*(randomy-1)-1]==0||													//如果傳送到牆壁
									(Math.abs(randomx-redghost_positionx)<=1&&Math.abs(randomy-redghost_positiony)<=1)||	//如果傳送到鬼旁邊								
									(Math.abs(randomx-blueghost_positionx)<=1&&Math.abs(randomy-blueghost_positiony)<=1)||
									(Math.abs(randomx-pinkghost_positionx)<=1&&Math.abs(randomy-pinkghost_positiony)<=1)||
									(Math.abs(randomx-orangeghost_positionx)<=1&&Math.abs(randomy-orangeghost_positiony)<=1)||
									(Math.abs(randomx-positionx)<=1&&Math.abs(randomy-positiony)<=1)||				//如果傳送到自己旁邊
									(leveldata[randomx+20*(randomy-1)-1]&32)!=0									//如果傳送位置有大力丸
									){
									randomx=((int)(Math.random()*(n_block-2))+2);	//建立隨機位置
									randomy=((int)(Math.random()*(n_block-2))+2);	
								}
								
								x=offsetx+blocksize*randomx;	//把玩家傳送到隨機位置
								y=offsety+blocksize*randomy;
								positionx=randomx;
								positiony=randomy;		
										

								break;
							case 2:		//幫鬼加速
								ghost_speed=10;		
									
		
								break;
							case 3:		//把橘鬼變mode3
								orangemode=3;
			
								break;	
						}
						trollleft=false;
					}
					
					orangeleft--;

				}
				
			}

		}
		
		
		
		
       
        
        public void run(){				//移動			
         
        	if(p_up&&!p_down&&!p_left&&!p_right){				//上			
        		 pacmanface=1;
        		 if(!((leveldata[positionx+20*(positiony-1)-1]&1)>0)||(y-offsety)%blocksize>0){
					
					if((x-offsetx)%blocksize!=0){				
						x=(positionx)*blocksize+offsetx;
					}					
					
					y=y-speed;		
	 				if(((y-offsety)%blocksize)==(blocksize/3)){
	 		//			System.out.println("上");
	 					positiony=positiony-1;
	 				}
				}
        	}else if(!p_up&&p_down&&!p_left&&!p_right){
        		pacmanface=2;
        		if(!((leveldata[positionx+20*(positiony-1)-1]&2)>0)||(y-offsety)%blocksize>0){		//如果當前格子右邊可通行||還沒走到底
	 				if((x-offsetx)%blocksize!=0){								//如果角色面前有牆壁但判定位置面前沒有牆壁->矯正到正確位置
						x=(positionx)*blocksize+offsetx;
					}
	 				
	 				y=y+speed;
	 				if(((y-offsety)%blocksize)==(2*blocksize/3)){			//判斷現在格子
	 		//			System.out.println("下");
	 					positiony=positiony+1;
	 				}
 					
 			 	}
        	}else if(!p_up&&!p_down&&p_left&&!p_right){
        		pacmanface=3;
        		if(!((leveldata[positionx+20*(positiony-1)-1]&4)>0)||(x-offsetx)%blocksize>0){
					if((y-offsety)%blocksize!=0){				
						y=(positiony)*blocksize+offsety;
					}
					
					x=x-speed;
					if(((x-offsetx)%blocksize)==(blocksize/3)){
			//			System.out.println("左");
	 					positionx=positionx-1;
	 				}	
				}
        	}else if(!p_up&&!p_down&&!p_left&&p_right){
        		pacmanface=4;
        		if(!((leveldata[positionx+20*(positiony-1)-1]&8)>0)||(x-offsetx)%blocksize>0){		
					if((y-offsety)%blocksize!=0){				
						y=(positiony)*blocksize+offsety;
					}
					
					x=x+speed;
					if(((x-offsetx)%blocksize)==(2*blocksize/3)){
			//			System.out.println("右");
	 					positionx=positionx+1;
	 				}
				}
        	}
        	
        	eatdot();
        	lab.setLocation(x,y);	
        }
        

        
        
        
//----------------鍵盤監聽

	class kadapter extends KeyAdapter{		//內部類別、適配器
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		@Override
		public void keyPressed(KeyEvent e){					
			
			if(e.getKeyCode()==KeyEvent.VK_SPACE){
				gamestart();
			}
			if(gamestart){
				if(e.getKeyCode()==KeyEvent.VK_DOWN){			//按下下方向鍵時	
					p_down=true;
				}else if(e.getKeyCode()==KeyEvent.VK_UP){
					p_up=true;
				}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					p_right=true;
				}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
					p_left=true;
				}
			}
 			
			
				
		/*	
 			System.out.println("x"+x+"y"+y);
 			System.out.println("x"+positionx+"y"+positiony+"["+(positionx+20*(positiony-1)-1)+"]");
 			System.out.println("data"+leveldata[positionx+20*(positiony-1)-1]);
 			System.out.println(p_up+""+p_down+" "+p_left+" "+p_right);*/
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