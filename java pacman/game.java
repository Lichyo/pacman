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
		static int score=0;				//����
		static int lives=3;				//lives
		
		private final Font fnt = new Font("Arial",Font.BOLD,20);	//�r��
		static JLabel lab=new JLabel(new ImageIcon("img/.png"));	//�إ߼���
		static JLabel scorebar=new JLabel("Score:");	//�إ߼���
		static JPanel pan=new JPanel();
		
		static Image pac_offscreen=null;		//���w�s��
		
		
		
/*		static	Image iup=new ImageIcon("img/up.png").getImage();			//����������|�U���Ϥ�
		static	Image idown=new ImageIcon("img/down.png").getImage();
		static	Image ileft=new ImageIcon("img/left.png").getImage();
		static	Image iright=new ImageIcon("img/right.png").getImage();
		static  Image heart=new ImageIcon("img/left.png").getImage();*/
		
				
		static	ImageIcon iup=new ImageIcon("img/up.png");			//����������|�U���Ϥ�
		static	ImageIcon idown=new ImageIcon("img/down.png");
		static	ImageIcon ileft=new ImageIcon("img/left.png");
		static	ImageIcon iright=new ImageIcon("img/right.png");
		static  ImageIcon heart=new ImageIcon("img/right.png");
		
		
		static boolean gamestart=false;
		private boolean dead=false; //���a�O�_���`
		static boolean p_up=false;		//�{�b���U���ӫ���
		static boolean p_down=false;
		static boolean p_left=false;
		static boolean p_right=false;
		

		
		final static int blocksize =30; 		//����j�p			//��18
		private static final int n_block =20;		//���a���䪺����ƶq
		
		static int reservex=40;						//��ثO�d��m
		static int reservey=100;							
		final static int offsetx=13;			//���ⰾ����m
		final static int offsety=72;
		static int x=offsetx+blocksize*10,y=offsety+blocksize*12;				//pacman��m
		static int positionx=10,positiony=12;				//pacman��m(��l)
		static int dx,dy;				//x�By�ܤƶq
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
		private boolean dead=false; //���a�O�_���`
		

	
		private final int screensize=blocksize*n_block;		//���a�j�p
		private final int pacman_speed=6;		//���a�t��
		
		
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
		
		
//-----------------------�غc��

		
		
		public game(){					
			setBackground(Color.black);	
		    addKeyListener(new kadapter());
			drawscorebar();
			
		}
		

		
		
	
		
/*		public void update(Graphics g){					//�ѦҸ��https://blog.csdn.net/weixin_50679163/article/details/119490947		�Q�����w�s�޳N�ѨM����{�{
			
			if(pac_offscreen==null){
				pac_offscreen=this.createImage(16,16);		
			}	
			
			Graphics gOff=pac_offscreen.getGraphics();	
			paint(gOff);
			g.drawImage(pac_offscreen,0,0,null);
		
			
		}	*/

		public void drawscorebar(){			//�e������			
	
			scorebar.setText("Score:"+String.valueOf(score));
			scorebar.setLocation(0,0);
			scorebar.setForeground(Color.white);
			scorebar.setFont(fnt);
			this.add(scorebar);
		}
		
		
        		
        		
//---------------ø�s�a��        
        
		public void drawlevel(Graphics2D graphics2D,int[] map){			//�e�X�a��
			int x=0,y=0;
			for(int i=0;i<400;i++){
				x=i/20;		//�]�wxy
				y=i%20;
				if(map[y*20+x]==0){
					graphics2D.setColor(Color.blue);				//�e���
					graphics2D.drawRect(reservex+x*blocksize,reservey+y*blocksize,blocksize,blocksize);	
				}
				if((map[y*20+x]&16)!=0){				//��l�]�w�����I
					graphics2D.setColor(Color.white);
					graphics2D.fillOval(reservex+10+x*blocksize,reservey+10+y*blocksize,15,15);			//�첾10->�ܤ�
				}
				
			}
			
		}	
		
		public void drawpacman(){			//�]�wpacman���Ϯ�
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
		
		public void drawhearts(Graphics2D g){			//�e�X�ͩR��
			int hx=blocksize+15,hy=30;
			for(int i=hearts;i>0;i--){
				g.drawImage(heart.getImage(),hx*(i-1),hy,this);
			}
		}		
		
		public void gamestart(){		//�C���}�l
			System.out.println("start");
			gamestart=true;
			
			lab.setIcon(iup);		//�إ�pacman
			lab.setLocation(x,y);
			lab.setSize(blocksize,blocksize);
			this.add(lab);
			
			
			if(dead){
				death();
			}
			
		}
		
		public void death(){		//���`
			lives=lives-1;
			if(lives==0){
				gamestart=false;		
			}
		}
		
		public void paintComponent(Graphics graphics){			//���gpaint��k�|�ɭP�Ӯe��������L�ե�L�kø�s		-->�|�@������	

			super.paintComponent(graphics);
			
			Graphics2D graphics2D=(Graphics2D) graphics;
			
			try{
				Thread.sleep(80);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			run();
			
			drawlevel(graphics2D,leveldata);		//�e�X�a��
			drawscorebar();
			
			drawpacman();
			drawhearts(graphics2D);
			repaint();								
			
		}
		

			
			

//--------------�Y�쨧�l	
		public void eatdot(){							//�ݭק�
	
			Graphics g=getGraphics();
			if((leveldata[positionx+20*(positiony-1)-1]&16)!=0){
				leveldata[positionx+20*(positiony-1)-1]=leveldata[positionx+20*(positiony-1)-1]-16+32;	//�M�����I�A�ì������B�쥻�����I
				score=score+1;
			}
			
			
		}
		
		
        //�]�w��l
        /*
         for(int i=0;i<399;i++){
         	if(leveldata[i]>32){
         		leveldata[i]=leveldata[i]-32+16;
       
         	}
         		
         }
         */
        
        public void run(){				//����			//�P�w���~
        	//repaint();					//����{�{
 
        	if(p_up&&!p_down&&!p_left&&!p_right){				//�W			
        		 
        		 if(!((leveldata[positionx+20*(positiony-1)-1]&1)>0)||(y-offsety)%blocksize>0){
					
					if((x-offsetx)%blocksize!=0){				
						x=(positionx)*blocksize+offsetx;
					}					
					
					y=y-speed;		
	 				if(((y-offsety)%blocksize)==(blocksize/3)){
	 					System.out.println("�W");
	 					positiony=positiony-1;
	 				}
				}
        	}else if(!p_up&&p_down&&!p_left&&!p_right){
        		if(!((leveldata[positionx+20*(positiony-1)-1]&2)>0)||(y-offsety)%blocksize>0){		//�p�G��e��l�k��i�q��||�٨S���쩳
	 				if((x-offsetx)%blocksize!=0){								//�p�G���⭱�e��������P�w��m���e�S�����->�B���쥿�T��m
						x=(positionx)*blocksize+offsetx;
					}
	 				
	 				y=y+speed;
	 				if(((y-offsety)%blocksize)==(2*blocksize/3)){			//�P�_�{�b��l
	 					System.out.println("�U");
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
						System.out.println("��");
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
						System.out.println("�k");
	 					positionx=positionx+1;
	 				}
				}
        	}
        	lab.setLocation(x,y);	
        }
        
//----------------��L��ť

	class kadapter extends KeyAdapter{		//�������O�B�A�t��
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		@Override
		public void keyPressed(KeyEvent e){						//��L����ݸѨM -->https://blog.csdn.net/sangjinchao/article/details/60584630
			eatdot();
			
 			if(e.getKeyCode()==KeyEvent.VK_DOWN){			//���U�U��V���	
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
			
			if(e.getKeyCode()==KeyEvent.VK_DOWN){			//���U�U��V���
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