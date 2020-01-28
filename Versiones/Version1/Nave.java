package Arkanoid.Version1;

import java.awt.Canvas;
import java.awt.Graphics;


public class Nave {

	private static Nave instance = null;
	
	//Monstamos la imagen
	public void paint(Graphics g) {
		g.drawImage(SpritesRepository.getInstance().getSprite(SpritesRepository.IMAGEN_NAVE), 200, 530,null);
	}

	public static Nave getInstance() {
		if (instance == null) {
			instance = new Nave();
		}
		return instance;
	}
	
}
