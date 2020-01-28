package Arkanoid.Version2_2;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Clase que representa a cada ladrillo de los que pondremos sobre la pantalla
 * 
 * @author R
 *
 */
public class Ladrillo extends Actor {
	// Damos un ancho y un alto específico al ladrillo. Suponemos que todos los
	// ladrillos serán iguales
	public static final int ANCHO = 30;
	public static final int ALTO = 15;

	private Color colores[] = new Color[] { Color.red, Color.blue, Color.cyan, Color.pink, Color.yellow, Color.ORANGE,
			Color.white };

	// Variable para patrón Singleton
	private static Ladrillo instancia = null;

	// Propiedades específicas de cada ladrillo
	private Color color = null;

	/**
	 * 
	 */
	public Ladrillo() {
		super();
		spriteActual = null; // El ladrillo se pinta vectorialmente, así que no utilizo ningún sprite
		this.height = ALTO;
		this.width = ANCHO;
		this.color = Color.WHITE; // Por defecto pongo el ladrillo en color blanco
	}

	// bucle de para los ladrillos

	public void ladrillos() {
		int contadorx = 20;
		int contadory = 5;
		for (int i = 0; i < colores.length; i++) {
			for (int j = 0; j < 10; j++) {

				Ladrillo ladrillo = new Ladrillo();
				ladrillo.setColor(colores[i]);
				ladrillo.setX(contadorx);
				ladrillo.setY(contadory);
				Arkanoid.getInstancia().getActores().add(ladrillo);

				contadorx += 35;
			}
			contadory += 20;
			contadorx = 20;
		}
	}

	/**
	 * Pintado del ladrillo en pantalla
	 */
	public void paint(Graphics2D g) {
		g.setColor(this.color);
		// Pinto el ladrillo como un rectángulo con vértices redondeados
		g.fillRoundRect(this.x, this.y, this.width, this.height, 3, 3);
	}

	// Métodos getter y setter
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public static Ladrillo getInstancia() {
		if (instancia == null) {
			instancia = new Ladrillo();
		}
		return instancia;
	}
}
