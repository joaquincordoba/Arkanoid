package Arkanoid.Version2_2;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Clase que representa a la nave de nuestro juego
 * 
 * @author R
 *
 */
public class Nave extends Actor implements KeyListener, MouseMotionListener {

	private static Nave instancia = null;
	
	protected int vx; // Cantidad de píxeles que aumentará la posición del jugador en cada iteración
						// del bucle principal del juego
	protected int vy; // igual a la anterior en el eje vertical
	private boolean left, right; // Booleanas que determinan si el player se está moviendo actualmente
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
	 * Método necesario para extender de Actor, incorpora el movimiento que el actor
	 * realizará en cada iteración del programa
	 */
	public void act() {
		super.act(); // Necesario para habilitar cambios de sprites en el actor

		// Movimiento en el eje horizontal
		this.x += this.vx; // En cada iteración del bucle principal, el monstruo cambiará su posición en el
							// eje X, sumándole a esta el valor de vx
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
	 * Cuando una tecla se libera se desactiva la bandera booleana que se había
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
	 * Este método no se utiliza pero es necesario implementarlo por el KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * En función de las banderas booleanas de movimiento, actualizamos las
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

	// Métodos Getters y Setters
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
