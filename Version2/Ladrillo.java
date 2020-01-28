package Arkanoid.Version2;

import java.awt.Color;
import java.awt.Graphics;

import JuegoFormula.SoundsRepository;

public class Ladrillo extends Actor{
	/**
	 * 
	 */
	
	public Ladrillo(int xventana,int yventana,int alto, int ancho) {
		super (xventana,yventana,alto,ancho);
	}
	
	public void paint(Graphics g) {
		 
			g.setColor(Color.CYAN);
			g.fillRect(this.xventana,this.yventana, this.alto, this.ancho);
			
			
		
	}
	
	
	
	
	
}
