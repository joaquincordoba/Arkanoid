package Arkanoid.Version1;

import java.awt.Color;
import java.awt.Graphics;

import JuegoFormula.SoundsRepository;

public class Ladrillo extends Actor{
	
	// Damos un ancho y un alto específico al bloque.
		public static final int WIDTH = 30;
		public static final int HEIGHT = 20;
		public static final int ESPACIO = 2;
		
		
		
		
		
		
		//propiedades de cada color
		private Color color = null;
	
	public Ladrillo(int xventana,int yventana, int ancho,int alto) {
		super (xventana,yventana,ancho,alto);
		this.color = Color.white;;
	}
	
	public void paint(Graphics g) {
		int contadorx = 12;
		int contadory = 5;
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				
				g.setColor(this.color);
				g.fillRect(contadorx,contadory, this.ancho,this.alto);
				
				
				contadorx +=30;
				
				
			}
			contadory += 5;
			contadorx = 12;
			
			
		}
			
		
	}
	
	
	
	
	
}
