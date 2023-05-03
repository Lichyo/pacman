package pacman;
import pacman.game;
import java.awt.*;
import javax.swing.*;


class pacman extends JFrame{
	static int blocksize=30;
	static int reservex=40;
	static int reservey=100;
	
	public pacman(){
		
		setTitle("pacman");  
		setSize(blocksize*20+reservex*2,blocksize*20+ reservey*2);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		game a=new game();
		
		a.setFocusable(true);
		add(a);
		setVisible(true);
	}
	
	public static void main(String args[]){    	
		pacman b=new pacman();
		
    }
	
	
	
}

