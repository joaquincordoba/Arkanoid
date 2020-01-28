package Arkanoid.Version2_2;

import java.awt.Graphics2D;

public class Explosion extends Actor {

	
	public Explosion() {
		super(new String[] {"sprite-explosion1.png","sprite-explosion2.png","sprite-explosion3.png",
				"sprite-explosion4.png","sprite-explosion5.png",
				"sprite-explosion6.png","sprite-explosion7.png",
				"sprite-explosion8.png","sprite-explosion9.png"},5);
	}
	
	@Override
	public void act () {
		super.act();
		
	}
	
	public void paint(Graphics2D g) {
		super.paint(g);
		
		if (this.spriteActual.equals(this.sprites.get(this.sprites.size()-1))) {
			this.setMarkedForRemoval(true);
		}
	}
}
