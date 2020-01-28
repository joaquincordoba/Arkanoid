package Arkanoid.Version2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pelota extends Actor {

	
	public Pelota(int xventana,int yventana,int alto, int ancho) {
		super (xventana,yventana,alto,ancho);
	}
	
	//pintamos la pelota
	public void paint(Graphics g){
		g.setColor(Color.white);
		// Se pinta la bola como un círculo
		g.fillOval(this.xventana,this.yventana,this.alto,this.ancho);
		
	}
	
	
}
