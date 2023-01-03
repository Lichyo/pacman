package pacman;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class game extends JPanel implements ActionListener{

		private final Font fnt = new Font("Arial",Font.BOLD,20);	//�r��
		private final Font pacfnt = new Font("Arial",Font.BOLD,15);	//�j�O�Y�˼Ʀr��
		private final Font startfnt = new Font("Arial",Font.BOLD,40);	//�r��
		static JLabel count=new JLabel("");	//�إ߼���(pacman�˼�)
		static JLabel lab=new JLabel(new ImageIcon("img/left.png"));	//�إ߼���(pacman)
		static JLabel scorebar=new JLabel("Score:");	//�إ߼���(����)
		static JLabel highest=new JLabel("Highest Score: 0");	//�إ߼���(�̰���)
		static JLabel last=new JLabel("Last Score: 0");	//�إ߼���(�W���o��)
		
		static JLabel lstart=new JLabel("Press Space to Start");	//�إ߼���(�}�l���ܤ�r)
		static JPanel pan=new JPanel();
		long starttime, currenttime;
		static Image pac_offscreen=null;		//���w�s��	
				
		static	ImageIcon iup=new ImageIcon("img/up.png");			//����������|�U���Ϥ�
		static	ImageIcon idown=new ImageIcon("img/down.png");
		static	ImageIcon ileft=new ImageIcon("img/left.png");
		static	ImageIcon iright=new ImageIcon("img/right.png");
		static  ImageIcon heart=new ImageIcon("img/right.png");
		static  ImageIcon cherry=new ImageIcon("img/cherry.png");
		
		static  Image redghost=new ImageIcon("img/redghost.png").getImage();//�����Ϥ�
		static  Image pinkghost=new ImageIcon("img/pinkghost.png").getImage();//�����Ϥ�
		static  Image blueghost=new ImageIcon("img/blueghost.png").getImage();//�����Ϥ�
		static  Image orangeghost=new ImageIcon("img/orangeghost.png").getImage();//�����Ϥ�
		static  Image orangemode3=new ImageIcon("img/orangemode3.png").getImage();//�����Ϥ�
		static  Image fright=new ImageIcon("img/fright.png").getImage();//�����Ϥ�
		
		final static int blocksize =30; 		//����j�p			//��18
		private static final int n_block =20;		//���a���䪺����ƶq
		
		static int reservex=40,reservey=100;						//��ثO�d��m							
		final static int offsetx=13;			//���ⰾ����m
		final static int offsety=72;
	
		static int randomx=10;
		static int randomy=12;

		static int speed=10;			//pacman�t��
//-----------------�H�U���ݭn���m���ܼ�	
		static int ghost_speed=6;		//�����t��	
		static int clearlevel=0;
		static int score=0;				//����
		static int lives=3;				//lives
		static int heat=0;
		static int highestscore=0;
		static int lastscore=0;	//�إ߼���(�W���o��)
		static boolean gamestart=false;
		private boolean dead=false; //���a�O�_���`
		static boolean power=false;	//�O�_�Y��j�O�Y
		static boolean p_up=false;		//�{�b���U���ӫ���
		static boolean p_down=false;
		static boolean p_left=false;
		static boolean p_right=false;
		
		static int orangeleft=6;		//�ѤU�h�֤j�O�Y
		static int dotleft=160;		//�ѤU�h���I�I(�]�t�j�O�Y)
		static boolean trollleft=true;
		

		static int x=offsetx+blocksize*10,y=offsety+blocksize*12;				//pacman��m
		static int positionx=10,positiony=12;				//pacman��m(��l)
		static int pacmanface=3;		//pacman���諸��V �W1 �U2 ��3 �k4
	
		static int redghost_x=offsetx+blocksize*9,redghost_y=offsety+blocksize*10;//��������m
		static int redghost_positionx=10,redghost_positiony=10;//��������l
		static int redface=1;		//�������諸��V �W1 �U2 ��3 �k4
		static int redmode=0;		//���Ҧ�
		
		static int pinkghost_x=offsetx+blocksize*10,pinkghost_y=offsety+blocksize*10;//����������m
		static int pinkghost_positionx=10,pinkghost_positiony=10;//����������l
		static int pinkface=1;		//���������諸��V �W1 �U2 ��3 �k4
		static int pinkghosttargetx=positionx,pinkghosttargety=positiony;	//���������ؼ�
		static int pinkmode=0;		//���Ҧ�
	
		static int blueghost_x=offsetx+blocksize*11,blueghost_y=offsety+blocksize*10;//�Ű�����m
		static int blueghost_positionx=10,blueghost_positiony=10;//�Ű�����l
		static int blueface=1;		//�Ű����諸��V �W1 �U2 ��3 �k4
		static int blueghosttargetx,blueghosttargety;	//�Ű����ؼ�
		static int bluemode=0;		//���Ҧ�
		
		static int orangeghost_x=offsetx+blocksize*12,orangeghost_y=offsety+blocksize*10;//�ﰭ����m
		static int orangeghost_positionx=10,orangeghost_positiony=10;//�ﰭ����l
		static int orangeface=1;		//�ﰭ���諸��V �W1 �U2 ��3 �k4
		static int orangeghosttargetx,orangeghosttargety;
		static int orangemode=0;		//�ﰭ�Ҧ�
		static boolean orangeevolution=false;
								
								
		static int[] leveldata={					//���d
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0,37,19,19,17,19,19,19,25, 0, 0,21,19,19,19,17,19,19,41, 0,
			0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0,
			0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0,
			0,20,19,19,18,19,19,19,24, 0, 0,20,19,19,19,18,19,19,24, 0,
			0,28, 0, 0, 0, 0, 0, 0,28, 0, 0,28, 0, 0, 0, 0, 0, 0,28, 0,
			0,20,19,19,25, 0, 5, 3, 2, 3, 3, 2, 3, 9, 0,21,19,19,24, 0,
			0,28, 0, 0,28, 0,12, 0, 0, 0, 0, 0, 0,12, 0,28, 0, 0,28, 0,
			0,28, 0, 0,28, 0,12, 0, 5, 1, 1, 9, 0,12, 0,28, 0, 0,28, 0,
			0,20,19,19,16,19, 8, 0, 6, 2, 2,10, 0, 4,19,16,19,19,24, 0,			//0 ��ê�� 1�W��� 2�U��� 4����� 8�k��� 16���I 32�j�O�Y 64�������Ӧ����I(����Q�r���f�X�{��ê��)
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
		static int[] innitleveldata={					//���d
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0,37,19,19,17,19,19,19,25, 0, 0,21,19,19,19,17,19,19,41, 0,
			0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0,
			0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0, 0, 0,28, 0, 0,28, 0,
			0,20,19,19,18,19,19,19,24, 0, 0,20,19,19,19,18,19,19,24, 0,
			0,28, 0, 0, 0, 0, 0, 0,28, 0, 0,28, 0, 0, 0, 0, 0, 0,28, 0,
			0,20,19,19,25, 0, 5, 3, 2, 3, 3, 2, 3, 9, 0,21,19,19,24, 0,
			0,28, 0, 0,28, 0,12, 0, 0, 0, 0, 0, 0,12, 0,28, 0, 0,28, 0,
			0,28, 0, 0,28, 0,12, 0, 5, 1, 1, 9, 0,12, 0,28, 0, 0,28, 0,
			0,20,19,19,16,19, 8, 0, 6, 2, 2,10, 0, 4,19,16,19,19,24, 0,			//0 ��ê�� 1�W��� 2�U��� 4����� 8�k��� 16���I 32�j�O�Y 64�������Ӧ����I(����Q�r���f�X�{��ê��)
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
		};;				//���d����l���
			
		
	
//---------------------------��	
		public void heat(int gx,int gy){	//�Q���I��
			if(Math.abs(gx-x)<(blocksize/2)&&Math.abs(gy-y)<(blocksize/2)){
				death();
			}
		}
		
		
		public int escape(int gx,int gy,int face){			
			switch(leveldata[gx+20*(gy-1)-1]&15){		//�P�_���ӭ��­�
				case 1:					//�W�����
					if(face==1){				//���q�U�W��
						if((gx-positionx)>=0){		//���b�H�k��
							face=4;
						}else{
							face=3;
						}
					}else if(face==4||face==3){			//���q���Υk�L��
						if((gy-positiony)>0){			//�H��
							face=2;
						}
					}
								
					break;
				case 2:					//�U�����
					if(face==2){				//���q�W�U��
						if((gx-positionx)>=0){		//���b�H�k��
							face=4;
						}else{
							face=3;
						}
					}else if(face==4||face==3){			//���q���Υk�L��
						if((gy-positiony)<0){
							face=1;
						}
					}						
					break;	
				case 4:					//�������
					if(face==3){				//���q�k�L��
						if((gy-positiony)>=0){		//���b�H�W��
							face=2;
						}else{
							face=1;
						}
					}else if(face==1||face==2){			//���q�W�ΤU�L��
						if((gx-positionx)>0){
							face=4;
						}
					}
					break;
				case 5:					//�W�������
					if(face==3){
						face=2;
					}else if(face==1){
						face=4;
					}
					break;	
				case 6:					//�U�������
					if(face==3){
						face=1;
					}else if(face==2){
						face=4;
						}
						break;
				case 8:					//�k�����
					if(face==4){				//���q���L��
						if((gy-positiony)>=0){		//���b�H�W��
							face=2;
						}else{
							face=1;
						}
					}else if(face==1||face==2){			//���q�W�ΤU�L��
						if((gx-positionx)<0){
							face=3;
						}
					}					
					break;	
				case 9:					//�W�k�����
					if(face==4){
						face=2;
					}else if(face==1){
						face=3;
					}					
					break;	
				case 10:				//�U�k�����
					if(face==4){
						face=1;
					}else if(face==2){
						face=3;
					}					
					break;	
				case 0:					//�Q�r���f				
					if(face==3||face==4){				//���索�Υk
						if((gy-positiony)<=0){		//���b�H�W��
							face=1;
						}else if((gy-positiony)>=0){	//���b�H�U��
							face=2;
						}
					}else{									//����W�ΤU
						if((gx-positionx)<=0){		//���b�H����
							face=3;
						}else if((gx-positionx)>=0){	//���b�H�k��
							face=4;
						}
					}
					
					
					break;	
			}			
			return face;
		}
		
		

		
//-------------����(�l)

      	public void redghost(Graphics g){			//�p���������m		�����ؼ�->���a��m
			
			if(gamestart){
				redghost_positionx=(redghost_x-offsetx)/blocksize;		//�P�_������m
				redghost_positiony=(redghost_y-offsety)/blocksize;
				
				if(redmode==1){
					if(((redghost_y-offsety)%blocksize)==0&&((redghost_x-offsetx)%blocksize)==0){		//�T�O���|�����@�Ӯ�l�~�ਭ
					
						switch(leveldata[redghost_positionx+20*(redghost_positiony-1)-1]&15){		//�P�_���ӭ��­�
							case 1:					//�W�����
								if(redface==1){				//���q�U�W��
									if((redghost_positionx-positionx)>=0){		//���b�H�k��
										redface=3;
									}else{
										redface=4;
									}
								}else if(redface==4||redface==3){			//���q���Υk�L��
									if((redghost_positiony-positiony)<0){
										redface=2;
									}
								}
		
								
								break;
							case 2:					//�U�����
								if(redface==2){				//���q�W�U��
									if((redghost_positionx-positionx)>=0){		//���b�H�k��
										redface=3;
									}else{
										redface=4;
									}
								}else if(redface==4||redface==3){			//���q���Υk�L��
									if((redghost_positiony-positiony)>0){
										redface=1;
									}
								}						
								break;	
							case 4:					//�������
								if(redface==3){				//���q�k�L��
									if((redghost_positiony-positiony)<=0){		//���b�H�W��
										redface=2;
									}else{
										redface=1;
									}
								}else if(redface==1||redface==2){			//���q�W�ΤU�L��
									if((redghost_positionx-positionx)<0){
										redface=4;
									}
								}
								break;
							case 5:					//�W�������
								if(redface==3){
									redface=2;
								}else if(redface==1){
									redface=4;
								}
								break;	
							case 6:					//�U�������
								if(redface==3){
									redface=1;
								}else if(redface==2){
									redface=4;
								}
								break;	
							case 8:					//�k�����
								if(redface==4){				//���q���L��
									if((redghost_positiony-positiony)<=0){		//���b�H�W��
										redface=2;
									}else{
										redface=1;
									}
								}else if(redface==1||redface==2){			//���q�W�ΤU�L��
									if((redghost_positionx-positionx)>0){
										redface=3;
									}
								}					
								break;	
							case 9:					//�W�k�����
								if(redface==4){
									redface=2;
								}else if(redface==1){
									redface=3;
								}					
								break;	
							case 10:				//�U�k�����
								if(redface==4){
									redface=1;
								}else if(redface==2){
									redface=3;
								}					
								break;	
							case 0:					//�Q�r���f				
								if(redface==3||redface==4){				//���索�Υk
									if((redghost_positiony-positiony)<=0){		//���b�H�W��
										redface=2;
									}else if((redghost_positiony-positiony)>=0){	//���b�H�U��
										redface=1;
									}
								}else{									//����W�ΤU
									if((redghost_positionx-positionx)<=0){		//���b�H����
										redface=4;
									}else if((redghost_positionx-positionx)>=0){	//���b�H�k��
										redface=3;
									}
								}
								break;	
						}
					}					
					
					
				}else if(redmode==2){
					if(((redghost_y-offsety)%blocksize)==0&&((redghost_x-offsetx)%blocksize)==0){
						redface=escape(redghost_positionx,redghost_positiony,redface);
					}
					
				}else if(redmode==0){
					redface=1;
					if(((redghost_y-offsety)%blocksize)==0&&((redghost_x-offsetx)%blocksize)==0){
						if(redghost_positiony==7){
							redmode=1;
							redface=3;
						}
					}
					
				}			
				switch(redface){
					case 1:		//�V�W
						redghost_y=redghost_y-ghost_speed;
						break;	
					case 2:		//�V�U
						redghost_y=redghost_y+ghost_speed;
						break;	
					case 3:		//�V��
						redghost_x=redghost_x-ghost_speed;
						break;	
					case 4:		//�V�k
						redghost_x=redghost_x+ghost_speed;
						break;	
				}

				
				if(redmode==2){				//�Qpacman�Y��
					g.drawImage(fright,redghost_x,redghost_y,this);
					if(Math.abs(redghost_x-x)<(blocksize/2)&&Math.abs(redghost_y-y)<(blocksize/2)){
						score=score+10;
						redghost_x=offsetx+blocksize*9;
						redghost_y=offsety+blocksize*10;
						redmode=0;
						redface=0;
					}
					
				}else{
					g.drawImage(redghost,redghost_x,redghost_y,this);
					heat(redghost_x,redghost_y);		
				}
				
				if(((redghost_positionx>8&&redghost_positionx<13)&&((redghost_positiony>8&&redghost_positiony<11)))&&redmode!=0){		//����d�b�ж�
					redghost_x=offsetx+blocksize*9;
					redghost_y=offsety+blocksize*10;
					redmode=0;
					redface=0;	
				}
			
				
				
			}
		}
//-------------����
		
//-------------������(���)
		
      	public void pinkghost(Graphics g){			//�p�⯻��������m		
			
			if(gamestart){
				pinkghost_positionx=(pinkghost_x-offsetx)/blocksize;		//�P�_������m
				pinkghost_positiony=(pinkghost_y-offsety)/blocksize;
				
				switch(pacmanface){									//���������ؼ�->���a���e2��
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
				
				
				if(pinkmode==1){
					if(((pinkghost_y-offsety)%blocksize)==0&&((pinkghost_x-offsetx)%blocksize)==0){		//�T�O���|�����@�Ӯ�l�~�ਭ
						switch(leveldata[pinkghost_positionx+20*(pinkghost_positiony-1)-1]&15){		//�P�_���ӭ��­�
							case 1:					//�W�����
								if(pinkface==1){				//���q�U�W��
									if((pinkghost_positionx-pinkghosttargetx)>=0){		//���b�ؼХk��
										pinkface=3;
									}else{
										pinkface=4;
									}
								}else if(pinkface==4||pinkface==3){			//���q���Υk�L��
									if((pinkghost_positiony-pinkghosttargety)<0){
										pinkface=2;
									}
								}
		
								
								break;
							case 2:					//�U�����
								if(pinkface==2){				//���q�W�U��
									if((pinkghost_positionx-pinkghosttargetx)>=0){		//���b�ؼХk��
										pinkface=3;
									}else{
										pinkface=4;
									}
								}else if(pinkface==4||pinkface==3){			//���q���Υk�L��
									if((pinkghost_positiony-pinkghosttargety)>0){
										pinkface=1;
									}
								}						
								break;	
							case 4:					//�������
								if(pinkface==3){				//���q�k�L��
									if((pinkghost_positiony-pinkghosttargety)<=0){		//���b�ؼФW��
										pinkface=2;
									}else{
										pinkface=1;
									}
								}else if(pinkface==1||pinkface==2){			//���q�W�ΤU�L��
									if((pinkghost_positionx-pinkghosttargetx)<0){
										pinkface=4;
									}
								}
								break;
							case 5:					//�W�������
								if(pinkface==3){
									pinkface=2;
								}else if(pinkface==1){
									pinkface=4;
								}
								break;	
							case 6:					//�U�������
								if(pinkface==3){
									pinkface=1;
								}else if(pinkface==2){
									pinkface=4;
								}
								break;	
							case 8:					//�k�����
								if(pinkface==4){				//���q���L��
									if((pinkghost_positiony-pinkghosttargety)<=0){		//���b�ؼФW��
										pinkface=2;
									}else{
										pinkface=1;
									}
								}else if(pinkface==1||pinkface==2){			//���q�W�ΤU�L��
									if((pinkghost_positionx-pinkghosttargetx)>0){
										pinkface=3;
									}
								}					
								break;	
							case 9:					//�W�k�����
								if(pinkface==4){
									pinkface=2;
								}else if(pinkface==1){
									pinkface=3;
								}					
								break;	
							case 10:				//�U�k�����
								if(pinkface==4){
									pinkface=1;
								}else if(pinkface==2){
									pinkface=3;
								}					
								break;	
							case 0:					//�Q�r���f				
								if(pinkface==3||pinkface==4){				//���索�Υk
									if((pinkghost_positiony-pinkghosttargety)<=0){		//���b�ؼФW��
										pinkface=2;
									}else if((pinkghost_positiony-pinkghosttargety)>=0){	//���b�ؼФU��
										pinkface=1;
									}
								}else{									//����W�ΤU
									if((pinkghost_positionx-pinkghosttargetx)<=0){		//���b�H����
										pinkface=4;
									}else if((pinkghost_positionx-pinkghosttargetx)>=0){	//���b�ؼХk��
										pinkface=3;
									}
								}
								
								
								break;	
						}
					}					
				}else if(pinkmode==2){
					if(((pinkghost_y-offsety)%blocksize)==0&&((pinkghost_x-offsetx)%blocksize)==0){
						pinkface=escape(pinkghost_positionx,pinkghost_positiony,pinkface);
					}
					
				}else if(pinkmode==0){
					pinkface=1;
					if(((pinkghost_y-offsety)%blocksize)==0&&((pinkghost_x-offsetx)%blocksize)==0){
						if(pinkghost_positiony==7){
							pinkmode=1;
							pinkface=3;
						}
					}
					
				}
					switch(pinkface){
						case 1:		//�V�W
							pinkghost_y=pinkghost_y-ghost_speed;
							break;	
						case 2:		//�V�U
							pinkghost_y=pinkghost_y+ghost_speed;
							break;	
						case 3:		//�V��
							pinkghost_x=pinkghost_x-ghost_speed;
							break;	
						case 4:		//�V�k
							pinkghost_x=pinkghost_x+ghost_speed;
							break;	
					}
				if(pinkmode==2){
					g.drawImage(fright,pinkghost_x,pinkghost_y,this);
					if(Math.abs(pinkghost_x-x)<(blocksize/2)&&Math.abs(pinkghost_y-y)<(blocksize/2)){
						score=score+10;
						pinkghost_x=offsetx+blocksize*10;
						pinkghost_y=offsety+blocksize*10;
						pinkmode=0;
						pinkface=0;
					}
				}else{
					g.drawImage(pinkghost,pinkghost_x,pinkghost_y,this);
					heat(pinkghost_x,pinkghost_y);
				}	
				
				if(((pinkghost_positionx>8&&pinkghost_positionx<13)&&((pinkghost_positiony>8&&pinkghost_positiony<11)))&&pinkmode!=0){		//����d�b�ж�
					pinkghost_x=offsetx+blocksize*10;
					pinkghost_y=offsety+blocksize*10;
					pinkmode=0;
					pinkface=0;	
				}
				
				
				
			}
		}		
//------------------������
//-------------�ŦⰭ(���P���⪺�۹��m)
		
      	public void blueghost(Graphics g){			//�p���Ű�����m		
			
			if(gamestart){
				blueghost_positionx=(blueghost_x-offsetx)/blocksize;		//�P�_������m
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
				
				blueghosttargetx=2*blueghosttargetx-redghost_positionx;		//�Ű����ؼ�->�����쪱�a���e2�檺�⭿�Z��
				blueghosttargety=2*blueghosttargety-redghost_positiony;
				
				if(bluemode==1){
					if(((blueghost_y-offsety)%blocksize)==0&&((blueghost_x-offsetx)%blocksize)==0){		//�T�O���|�����@�Ӯ�l�~�ਭ
						switch(leveldata[blueghost_positionx+20*(blueghost_positiony-1)-1]&15){		//�P�_���ӭ��­�
							case 1:					//�W�����
								if(blueface==1){				//���q�U�W��
									if((blueghost_positionx-blueghosttargetx)>=0){		//���b�ؼХk��
										blueface=3;
									}else{
										blueface=4;
									}
								}else if(blueface==4||blueface==3){			//���q���Υk�L��
									if((blueghost_positiony-blueghosttargety)<0){
										blueface=2;
									}
								}
		
								
								break;
							case 2:					//�U�����
								if(blueface==2){				//���q�W�U��
									if((blueghost_positionx-blueghosttargetx)>=0){		//���b�ؼХk��
										blueface=3;
									}else{
										blueface=4;
									}
								}else if(blueface==4||blueface==3){			//���q���Υk�L��
									if((blueghost_positiony-blueghosttargety)>0){
										blueface=1;
									}
								}						
								break;	
							case 4:					//�������
								if(blueface==3){				//���q�k�L��
									if((blueghost_positiony-blueghosttargety)<=0){		//���b�ؼФW��
										blueface=2;
									}else{
										blueface=1;
									}
								}else if(blueface==1||blueface==2){			//���q�W�ΤU�L��
									if((blueghost_positionx-blueghosttargetx)<0){
										blueface=4;
									}
								}
								break;
							case 5:					//�W�������
								if(blueface==3){
									blueface=2;
								}else if(blueface==1){
									blueface=4;
								}
								break;	
							case 6:					//�U�������
								if(blueface==3){
									blueface=1;
								}else if(blueface==2){
									blueface=4;
								}
								break;	
							case 8:					//�k�����
								if(blueface==4){				//���q���L��
									if((blueghost_positiony-blueghosttargety)<=0){		//���b�ؼФW��
										blueface=2;
									}else{
										blueface=1;
									}
								}else if(blueface==1||blueface==2){			//���q�W�ΤU�L��
									if((blueghost_positionx-blueghosttargetx)>0){
										blueface=3;
									}
								}					
								break;	
							case 9:					//�W�k�����
								if(blueface==4){
									blueface=2;
								}else if(blueface==1){
									blueface=3;
								}					
								break;	
							case 10:				//�U�k�����
								if(blueface==4){
									blueface=1;
								}else if(blueface==2){
									blueface=3;
								}					
								break;	
							case 0:					//�Q�r���f				
								if(blueface==3||blueface==4){				//���索�Υk
									if((blueghost_positiony-blueghosttargety)<=0){		//���b�ؼФW��
										blueface=2;
									}else if((blueghost_positiony-blueghosttargety)>=0){	//���b�ؼФU��
										blueface=1;
									}
								}else{									//����W�ΤU
									if((blueghost_positionx-blueghosttargetx)<=0){		//���b�H����
										blueface=4;
									}else if((blueghost_positionx-blueghosttargetx)>=0){	//���b�ؼХk��
										blueface=3;
									}
								}
								break;	
						}
					}					
				}else if(bluemode==2){
					if(((blueghost_y-offsety)%blocksize)==0&&((blueghost_x-offsetx)%blocksize)==0){
						blueface=escape(blueghost_positionx,blueghost_positiony,blueface);
					}
					
				}else if(bluemode==0){
					blueface=1;
					if(((blueghost_y-offsety)%blocksize)==0&&((blueghost_x-offsetx)%blocksize)==0){
						if(blueghost_positiony==7){
							bluemode=1;
							blueface=4;
						}
					}
					
				}
				switch(blueface){
					case 1:		//�V�W
						blueghost_y=blueghost_y-ghost_speed;
						break;	
					case 2:		//�V�U
						blueghost_y=blueghost_y+ghost_speed;
						break;	
					case 3:		//�V��
						blueghost_x=blueghost_x-ghost_speed;
						break;	
					case 4:		//�V�k
						blueghost_x=blueghost_x+ghost_speed;
						break;	
				}
					
				if(bluemode==2){
					g.drawImage(fright,blueghost_x,blueghost_y,this);
					if(Math.abs(blueghost_x-x)<(blocksize/2)&&Math.abs(blueghost_y-y)<(blocksize/2)){
						score=score+10;
						blueghost_x=offsetx+blocksize*11;
						blueghost_y=offsety+blocksize*10;
						bluemode=0;
						blueface=0;
					}
				}else{
					g.drawImage(blueghost,blueghost_x,blueghost_y,this);
					heat(blueghost_x,blueghost_y);
				}
				
				if(((blueghost_positionx>8&&blueghost_positionx<13)&&((blueghost_positiony>8&&blueghost_positiony<11)))&&bluemode!=0){		//����d�b�ж�
					blueghost_x=offsetx+blocksize*11;
					blueghost_y=offsety+blocksize*10;
					bluemode=0;
					blueface=0;	
				}

				
			}
		}		
//--------------�Ű�
		
//--------------�ﰭ(�üƥB����L�Ҧ�)		
      	public void orangeghost(Graphics g){			//�p�⯻��������m		
			
			if(gamestart){
				orangeghost_positionx=(orangeghost_x-offsetx)/blocksize;		//�P�_������m
				orangeghost_positiony=(orangeghost_y-offsety)/blocksize;

				if(orangemode==1){					//�è��Ҧ�
					if(((orangeghost_y-offsety)%blocksize)==0&&((orangeghost_x-offsetx)%blocksize)==0){		//�T�O���|�����@�Ӯ�l�~�ਭ
						switch(leveldata[orangeghost_positionx+20*(orangeghost_positiony-1)-1]&15){		//�P�_���ӭ��­�
							case 1:					//�W�����
								if(orangeface==1){				//���q�U�W��
									if(Math.random()*2>1){					//���Υk
										orangeface=3;
									}else{
										orangeface=4;
									}
								}else if(orangeface==4||orangeface==3){			//���q���Υk�L��
									if(Math.random()*2>1){					//1/2����
										orangeface=2;
									}
								}
		
								
								break;
							case 2:					//�U�����
								if(orangeface==2){				//���q�W�U��
									if(Math.random()*2>1){		
										orangeface=3;
									}else{
										orangeface=4;
									}
								}else if(orangeface==4||orangeface==3){			//���q���Υk�L��
									if(Math.random()*2>1){
										orangeface=1;
									}
								}						
								break;	
							case 4:					//�������
								if(orangeface==3){				//���q�k�L��
									if(Math.random()*2>1){		//���b�ؼФW��
										orangeface=2;
									}else{
										orangeface=1;
									}
								}else if(orangeface==1||orangeface==2){			//���q�W�ΤU�L��
									if(Math.random()*2>1){
										orangeface=4;
									}
								}
								break;
							case 5:					//�W�������
								if(orangeface==3){
									orangeface=2;
								}else if(orangeface==1){
									orangeface=4;
								}
								break;	
							case 6:					//�U�������
								if(orangeface==3){
									orangeface=1;
								}else if(orangeface==2){
									orangeface=4;
								}
								break;	
							case 8:					//�k�����
								if(orangeface==4){				//���q���L��
									if(Math.random()*2>1){		//���b�ؼФW��
										orangeface=2;
									}else{
										orangeface=1;
									}
								}else if(orangeface==1||orangeface==2){			//���q�W�ΤU�L��
									if(Math.random()*2>1){
										orangeface=3;
									}
								}					
								break;	
							case 9:					//�W�k�����
								if(orangeface==4){
									orangeface=2;
								}else if(orangeface==1){
									orangeface=3;
								}					
								break;	
							case 10:				//�U�k�����
								if(orangeface==4){
									orangeface=1;
								}else if(orangeface==2){
									orangeface=3;
								}					
								break;	
							case 0:					//�Q�r���f				
								orangeface=orangeface+(int)(Math.random()*3+1);
								if(orangeface>4){
									orangeface=orangeface-4;
								}
								break;	
						}
					}
		
				}else if(orangemode==3){
					if(((orangeghost_y-offsety)%blocksize)==0&&((orangeghost_x-offsetx)%blocksize)==0){		//�T�O���|�����@�Ӯ�l�~�ਭ
						if((int)(Math.random()*2)==0){				//���������ʸ��n��
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
				}else if(orangemode==2){
					if(((orangeghost_y-offsety)%blocksize)==0&&((orangeghost_x-offsetx)%blocksize)==0){
						orangeface=escape(orangeghost_positionx,orangeghost_positiony,orangeface);
					}
					
				}else if(orangemode==0){
					orangeface=1;
					if(((orangeghost_y-offsety)%blocksize)==0&&((orangeghost_x-offsetx)%blocksize)==0){
						if(orangeghost_positiony==7){
							orangemode=1;
							orangeface=4;
						}
					}
					
				}
				
				switch(orangeface){
						case 1:		//�V�W
							orangeghost_y=orangeghost_y-ghost_speed;
							break;	
						case 2:		//�V�U
							orangeghost_y=orangeghost_y+ghost_speed;
							break;	
						case 3:		//�V��
							orangeghost_x=orangeghost_x-ghost_speed;
							break;	
						case 4:		//�V�k
							orangeghost_x=orangeghost_x+ghost_speed;
							break;	
				}
				
				switch(orangemode){
					case 0:
						g.drawImage(orangeghost,orangeghost_x,orangeghost_y,this);
						break;
					case 1:			//�@��
						g.drawImage(orangeghost,orangeghost_x,orangeghost_y,this);
						heat(orangeghost_x,orangeghost_y);
						break;
					case 2:			//���~
						g.drawImage(fright,orangeghost_x,orangeghost_y,this);
						if(Math.abs(orangeghost_x-x)<(blocksize/2)&&Math.abs(orangeghost_y-y)<(blocksize/2)){
							score=score+10;
							orangeghost_x=offsetx+blocksize*12;
							orangeghost_y=offsety+blocksize*10;
							orangemode=0;
							orangeface=0;
						}
						break;
					case 3:			//mode3
						g.drawImage(orangemode3,orangeghost_x,orangeghost_y,this);
						heat(orangeghost_x,orangeghost_y);
						break;
					
				}

				if(((orangeghost_positionx>8&&orangeghost_positionx<13)&&((orangeghost_positiony>8&&orangeghost_positiony<11)))&&orangemode!=0&&!orangeevolution){		//����d�b�ж�
					orangeghost_x=offsetx+blocksize*12;
					orangeghost_y=offsety+blocksize*10;
					orangemode=0;
					orangeface=0;	
				}			
			}
		}
		
//--------------�ﰭ

		
//--------------------------------		
		public game(){					//�غc��
			count.setForeground(Color.red);
			count.setFont(pacfnt);

			drawhighest();
			drawlast();
			setBackground(Color.black);	
		    addKeyListener(new kadapter());
			setlstart();
		    this.add(lstart);
		    this.add(count);
			drawscorebar();
			
		}
		
		
		public void setlstart(){		//�]�w�}�l���ܤ�r
			lstart.setFont(startfnt);
		    lstart.setForeground(Color.yellow);
		    lstart.setLocation(150,420);
			
		}
		

			
		public void levelclear(){		//�ˬd���d�O�_����
			if(dotleft==0){	//�p�G���d����
				reset();
				clearlevel++;
			}
		}
		
		public void drawcherry(Graphics2D g){		//�e�X���
			int cx=blocksize+5,cy=720;
			for(int i=clearlevel;i>0;i--){
				g.drawImage(cherry.getImage(),30+cx*(i-1),cy,this);
			}
		}
		

		
		
		public void update(Graphics g){					//�Q�����w�s�޳N�ѨM����{�{     �ѦҸ��https://blog.csdn.net/weixin_50679163/article/details/119490947		
			
			if(pac_offscreen==null){
				pac_offscreen=this.createImage(16,16);		
			}	
			
			Graphics gOff=pac_offscreen.getGraphics();	
			paint(gOff);
			g.drawImage(pac_offscreen,0,0,null);
		
			
		}	

		public void drawscorebar(){			//�e������			
			scorebar.setText("Score:"+String.valueOf(score));
			scorebar.setLocation(0,0);
			scorebar.setForeground(Color.white);
			scorebar.setFont(fnt);
			this.add(scorebar);
		}
		
		
		public void drawhighest(){			//�e�̰�������			
			highest.setText("Highest Score:"+String.valueOf(highestscore));
			highest.setLocation(450,0);
			highest.setForeground(Color.white);
			highest.setFont(fnt);
			this.add(highest);
		}
		
		public void drawlast(){			//�e�W��������			
			last.setText("Last Score:"+String.valueOf(lastscore));
			last.setLocation(480,25);
			last.setForeground(Color.white);
			last.setFont(fnt);
			this.add(last);
		}
		 
		public void drawlevel(Graphics2D graphics2D,int[] map){			//�e�X�a��
			int x=0,y=0;
			for(int i=0;i<400;i++){
				x=i/20;		//�]�wxy
				y=i%20;
				if(map[y*20+x]==0){
					graphics2D.setColor(Color.blue);				//�e���
					if(y==7&&x>7&&x<12){
						graphics2D.drawRect(reservex+x*blocksize,reservey+y*blocksize,blocksize,blocksize);	
					}else{
						graphics2D.fillRect(reservex+x*blocksize,reservey+y*blocksize,blocksize,blocksize);	
					}
					
				}
				if((map[y*20+x]&16)!=0){				//��l�]�w�����I
					graphics2D.setColor(Color.white);
					graphics2D.fillOval(reservex+10+x*blocksize,reservey+10+y*blocksize,15,15);			//�첾10->�ܤ�
				}else if((map[y*20+x]&32)!=0){				//��l�]�w���j�O�Y
					graphics2D.setColor(Color.orange);
					graphics2D.fillOval(reservex+4+x*blocksize,reservey+4+y*blocksize,23,23);			//�첾10->�ܤ�
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
			for(int i=lives;i>0;i--){
				g.drawImage(heart.getImage(),hx*(i-1),hy,this);
			}
		}		
		
		public void gamestart(){		//�C���}�l
			gamestart=true;
			lab.setIcon(iup);		//�إ�pacman
			lab.setLocation(x,y);
			lab.setSize(blocksize,blocksize);
			this.add(lab);
			
			lstart.setText("");
			
			if(dead){
				death();
			}
			
		}
			
		public void reset(){						//�����d�������m (�Y��or���z)
			count.setText("");
			lstart.setText("Press Space to Start");
			lab.setIcon(null);
			if(clearlevel>20){
				ghost_speed=10;
			}else{
				ghost_speed=6;
			}
			
			lives=3;				//lives
			gamestart=false;
			dead=false; //���a�O�_���`
			p_up=false;		//�{�b���U���ӫ���
			p_down=false;
			p_left=false;
			p_right=false;
			orangeleft=6;		//�ѤU�h�֤j�O�Y
			dotleft=160;		//�ѤU�h���I�I
			trollleft=true;
			x=offsetx+blocksize*10;y=offsety+blocksize*12;				//pacman��m
			positionx=10;positiony=12;				//pacman��m(��l)
			pacmanface=3;		//pacman���諸��V �W1 �U2 ��3 �k4
			redghost_x=offsetx+blocksize*9;redghost_y=offsety+blocksize*10;//��������m

			redface=1;		//�������諸��V �W1 �U2 ��3 �k4
			pinkghost_x=offsetx+blocksize*10;pinkghost_y=offsety+blocksize*10;//����������m

			pinkface=1;		//���������諸��V �W1 �U2 ��3 �k4
			pinkghosttargetx=positionx;pinkghosttargety=positiony;	//���������ؼ�
			blueghost_x=offsetx+blocksize*11;blueghost_y=offsety+blocksize*10;//�Ű�����m		
			blueface=1;		//�Ű����諸��V �W1 �U2 ��3 �k4
			orangeghost_x=offsetx+blocksize*12;orangeghost_y=offsety+blocksize*10;//�ﰭ����m
	
			orangeface=1;		//�ﰭ���諸��V �W1 �U2 ��3 �k4

			orangemode=0;		//�ﰭ�Ҧ�
			pinkmode=0;		//���Ҧ�
			redmode=0;		//���Ҧ�
			bluemode=0;		//���Ҧ�
			orangeevolution=false;			
			for(int i=0;i<400;i++){
				leveldata[i]=innitleveldata[i];
			}
			
			
		}
		public void dying(){				//�u���]��m
			count.setText("");
			lstart.setText("Press Space to Start");
			lab.setIcon(null);
			x=offsetx+blocksize*10;y=offsety+blocksize*12;				//pacman��m
			positionx=10;positiony=12;				//pacman��m(��l)
			p_up=false;		//�{�b���U���ӫ���
			p_down=false;
			p_left=false;
			p_right=false;
			pinkmode=0;		//���Ҧ�
			redmode=0;		//���Ҧ�
			bluemode=0;		//���Ҧ�
			if(!orangeevolution){
				orangemode=0;
			}
			
			redghost_x=offsetx+blocksize*9;redghost_y=offsety+blocksize*10;//��������m
			pinkghost_x=offsetx+blocksize*10;pinkghost_y=offsety+blocksize*10;//����������m
			blueghost_x=offsetx+blocksize*11;blueghost_y=offsety+blocksize*10;//�Ű�����m		
			orangeghost_x=offsetx+blocksize*12;orangeghost_y=offsety+blocksize*10;//�ﰭ����m

			gamestart=false;
		}
		
		
		public void death(){		//�ˬd�O�_���`
			lives=lives-1;
			if(lives==0){
				gamestart=false;
				if(score>highestscore){
					highestscore=score;
				}
				lastscore=score;
				clearlevel=0;
				score=0;
				reset();		
			}else{
				dying();
			}
			
		}
		
		public void recover(){		//����_
			count.setText("");
			power=false;
			pinkmode=1;		//���Ҧ�
			redmode=1;		//���Ҧ�
			bluemode=1;		//���Ҧ�
			if(orangemode==2){
				orangemode=1;
			}
			
		}
		
		
		public void paintComponent(Graphics graphics){			//���gpaint��k�|�ɭP�Ӯe��������L�ե�L�kø�s	
			super.paintComponent(graphics);
			
			Graphics2D graphics2D=(Graphics2D) graphics;

			run();
			
			drawlevel(graphics2D,leveldata);		//�e�X�a��
			
			try{
				Thread.sleep(60);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			
			
			redghost(graphics);
			pinkghost(graphics);
			blueghost(graphics);
			orangeghost(graphics);
			if(power){
				count.setLocation(x+4,y+3);
				count.setText(String.valueOf(8-(currenttime-starttime)/1000));
				currenttime=System.currentTimeMillis();
				if((currenttime-starttime)>=8000){
					recover();
				}
				
			}
			drawscorebar();
			drawhighest();
			drawlast();
			setlstart();
			drawpacman();
			
			drawhearts(graphics2D);
			drawcherry(graphics2D);
			levelclear();
			repaint();						//���ƦA�e�@��	-->�|�Ϧ���Ƥ@������							
			
		}
		

			
			

//--------------�Y�쨧�l	
		public void eatdot(){						
			Graphics g=getGraphics();
			if((leveldata[positionx+20*(positiony-1)-1]&16)!=0){			//�p�G�Y����I
				leveldata[positionx+20*(positiony-1)-1]=leveldata[positionx+20*(positiony-1)-1]-16+64;	//�M�����I�A�ì������B�쥻�����I
				dotleft--;
				score=score+1;
			}else if((leveldata[positionx+20*(positiony-1)-1]&32)!=0){		//�p�G�Y����I
				leveldata[positionx+20*(positiony-1)-1]=leveldata[positionx+20*(positiony-1)-1]-32;	//�M���j�O�Y
				dotleft--;
				score=score+5;
				if(trollleft){
					if(((int)(Math.random()*orangeleft))==0){		//�Y�찲�j�O�Y	
						switch((int)(Math.random()*3)+1){
							case 1:			//�ǰe���H����m
								randomx=((int)(Math.random()*(n_block-2))+2);	//�إ��H����m(��l)
								randomy=((int)(Math.random()*(n_block-2))+2);
				
								while(leveldata[randomx+20*(randomy-1)-1]==0||													//�p�G�ǰe�����
									(Math.abs(randomx-redghost_positionx)<=1&&Math.abs(randomy-redghost_positiony)<=1)||	//�p�G�ǰe�찭����								
									(Math.abs(randomx-blueghost_positionx)<=1&&Math.abs(randomy-blueghost_positiony)<=1)||
									(Math.abs(randomx-pinkghost_positionx)<=1&&Math.abs(randomy-pinkghost_positiony)<=1)||
									(Math.abs(randomx-orangeghost_positionx)<=1&&Math.abs(randomy-orangeghost_positiony)<=1)||
									(Math.abs(randomx-positionx)<=1&&Math.abs(randomy-positiony)<=1)||				//�p�G�ǰe��ۤv����
									((leveldata[randomx+20*(randomy-1)-1]&32)!=0)||								//�p�G�ǰe��m���j�O�Y
									((randomx>8&&randomx<13)&&((randomy>8&&randomy<11)))							//�p�G�ǰe�찭�������I
									){
									randomx=((int)(Math.random()*(n_block-2))+2);	//�إ��H����m
									randomy=((int)(Math.random()*(n_block-2))+2);	
								}
								
								x=offsetx+blocksize*randomx;	//�⪱�a�ǰe���H����m
								y=offsety+blocksize*randomy;
								positionx=randomx;
								positiony=randomy;		
										

								break;
							case 2:		//�����[�t
								redghost_x=redghost_positionx*blocksize+offsetx;redghost_y=redghost_positiony*blocksize+offsety;		//���Ⱝ�]�w�b��l��
								blueghost_x=blueghost_positionx*blocksize+offsetx;blueghost_y=blueghost_positiony*blocksize+offsety;
								pinkghost_x=pinkghost_positionx*blocksize+offsetx;pinkghost_y=pinkghost_positiony*blocksize+offsety;
								orangeghost_x=orangeghost_positionx*blocksize+offsetx;orangeghost_y=orangeghost_positiony*blocksize+offsety;
								
								ghost_speed=10;		
									
		
								break;
							case 3:		//��ﰭ��mode3
								orangemode=3;
								orangeevolution=true;
								break;	
						}
						trollleft=false;
					}else{					//�Y��u���j�O�Y
						if(clearlevel<=10){
							starttime=System.currentTimeMillis();	//�����ɶ�
							power=true;
							redmode=2;
							pinkmode=2;
							bluemode=2;
							if(!orangeevolution){
								orangemode=2;		
							}	
						}
					}
					
					orangeleft--;

				}else{
					starttime=System.currentTimeMillis();	//�����ɶ�
					power=true;
					redmode=2;
					pinkmode=2;
					bluemode=2;
					if(!orangeevolution){
						orangemode=2;		
					}
				}
				
			}

		}
//--------------�Y�쨧�l		
		
		
		
       
        
        public void run(){				//����			
         
        	if(p_up&&!p_down&&!p_left&&!p_right){				//�W			
        		 pacmanface=1;
        		 if(!((leveldata[positionx+20*(positiony-1)-1]&1)>0)||(y-offsety)%blocksize>0){
					
					if((x-offsetx)%blocksize!=0){				
						x=(positionx)*blocksize+offsetx;
					}					
					
					y=y-speed;		
	 				if(((y-offsety)%blocksize)==(blocksize/3)){
	 					positiony=positiony-1;
	 				}
				}
        	}else if(!p_up&&p_down&&!p_left&&!p_right){
        		pacmanface=2;
        		if(!((leveldata[positionx+20*(positiony-1)-1]&2)>0)||(y-offsety)%blocksize>0){		//�p�G��e��l�k��i�q��||�٨S���쩳
	 				if((x-offsetx)%blocksize!=0){								//�p�G���⭱�e��������P�w��m���e�S�����->�B���쥿�T��m
						x=(positionx)*blocksize+offsetx;
					}
	 				
	 				y=y+speed;
	 				if(((y-offsety)%blocksize)==(2*blocksize/3)){			//�P�_�{�b��l
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
	 					positionx=positionx+1;
	 				}
				}
        	}
        	
        	eatdot();
        	lab.setLocation(x,y);	
        }
        

        
        
        
//----------------��L��ť

	class kadapter extends KeyAdapter{		//�������O�B�A�t��
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		@Override
		public void keyPressed(KeyEvent e){					
			
			if(e.getKeyCode()==KeyEvent.VK_SPACE){
				gamestart();
				lab.setIcon(iup);
			}
			if(gamestart){
				if(e.getKeyCode()==KeyEvent.VK_DOWN){			//���U�U��V���	
					p_down=true;
				}else if(e.getKeyCode()==KeyEvent.VK_UP){
					p_up=true;
				}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					p_right=true;
				}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
					p_left=true;
				}
			}
 			
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