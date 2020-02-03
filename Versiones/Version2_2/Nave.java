package Arkanoid.Version2_2;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Clase que representa a la nave de nuestro juego
 * 
 * @author R
 *
 */
public class Nave extends Actor implements KeyListener, MouseMotionListener {

	private static Nave instancia = null;
	
	protected int vx; // Cantidad de p�xeles que aumentar� la posici�n del jugador en cada iteraci�n
						// del bucle principal del juego
	protected int vy; // igual a la anterior en el eje vertical
	private boolean left, right; // Booleanas que determinan si el player se est� moviendo actualmente
	protected static final int PLAYER_SPEED = 4; // velocidad del movimiento de la nave en los dos ejes

	/**
	 * 
	 */
	public Nave() {
		super(new String[] { "nave-25x7.png" });

	}

	public void paint(Graphics2D g) {
		g.drawImage(SpritesRepository.getInstance().getSprite("nave-25x7.png"), this.x, this.y, null);
	}

	/**
	 * M�todo necesario para extender de Actor, incorpora el movimiento que el actor
	 * realizar� en cada iteraci�n del programa
	 */
	public void act() {
		super.act(); // Necesario para habilitar cambios de sprites en el actor

		// Movimiento en el eje horizontal
		this.x += this.vx; // En cada iteraci�n del bucle principal, el monstruo cambiar� su posici�n en el
							// eje X, sum�ndole a esta el valor de vx
		// si la nave intenta salir por la derecha no se lo permitimos
		if (this.x < 0) {
			this.x = 0;
		}
		// si la nave intenta salir por la izquierda no se lo permitimos
		if (this.x > (Arkanoid.getInstancia().getWidth() - this.width)) {
			this.x = (Arkanoid.getInstancia().getWidth() - this.width);
		}
	}

	/**
	 * Cuando pulsen una tecla activamos la bandera booleana concreta que indica que
	 * existe un movimiento
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_SPACE:
			Bola.getInstancia().setSpace(true);
			break;
			

		}
		updateSpeed();
	}

	/**
	 * Cuando una tecla se libera se desactiva la bandera booleana que se hab�a
	 * activado al pulsarla
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		}
		updateSpeed();
	}

	/**
	 * Este m�todo no se utiliza pero es necesario implementarlo por el KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * En funci�n de las banderas booleanas de movimiento, actualizamos las
	 * velocidades en los dos ejes
	 */
	protected void updateSpeed() {
		vx = 0;
		vy = 0;
		if (left)
			vx = -PLAYER_SPEED;
		if (right)
			vx = PLAYER_SPEED;

	}

	// M�todos Getters y Setters
	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	// Para ticlear la nave
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// Pasar el cursor
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Point punto = MouseInfo.getPointerInfo().getLocation();
		this.x = punto.x;
	}
	public static Nave getInstancia() {
		if (instancia == null) {
			instancia = new Nave();
		}
		return instancia;
	}

}
