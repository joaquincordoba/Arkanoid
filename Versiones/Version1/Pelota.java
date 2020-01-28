package Arkanoid.Version1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pelota extends Actor {

	
	public Pelota(int xventana,int yventana, int ancho,int alto) {
		super (xventana,yventana,ancho,alto);
	}
	
	//pintamos la pelota
	public void paint(Graphics g){
		g.setColor(Color.white);
		// Se pinta la bola como un círculo
		g.fillOval(this.xventana,this.yventana,this.ancho,this.alto);
		
	}
	
	
}
