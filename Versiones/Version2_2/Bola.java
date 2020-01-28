package Arkanoid.Version2_2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Clase que representa la bola del juego, que rebotará y destruirá ladrillos
 * 
 * @author R
 *
 */
public class Bola extends Actor {
	// Pienso que, aunque más adelante en el juego pueda haber varias bolas, en
	// principio su diámetro no cambia
	public static final int DIAMETRO = 10;

	// La bola necesita poder moverse en los dos ejes: x e y. Por tanto necesitamos
	// dar valores de velocidad en los dos
	private int velocidadX = 3;
	private int velocidadY = 3;

	private boolean space = false;

	private int respaldoDeX, respaldoDeY;

	private static Bola instancia = null;
	private double tiempo = 0;

	private PuntoAltaPrecision coordenadas = null;

	private TrayectoriaRecta trayectoriaRecta = null;
	private float velocidadPorFrame = 0f;
	// La velocidad de la bola aumentara
	private float incrementoBola = 1.0035f;
	// Para la velocidad maxima
	private static final float MAXIMAVELOCIDAD = 9;

	/**
	 * 
	 */
	public Bola() {
		super();
		spriteActual = null; // La bola se pinta de forma vectorial
		this.x = 207;
		this.y = 488;
		this.width = DIAMETRO;
		this.height = DIAMETRO;
		// le damos las cordenadas de X y Y
		this.coordenadas = new PuntoAltaPrecision(this.x, this.y);
	}

	/**
	 * Pintado de la bola en pantalla
	 */
	public void paint(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		// Se pinta la bola como un círculo
		g.fillOval(this.x, this.y, DIAMETRO, DIAMETRO);
	}
	/*
	 * Para parar la pelota
	 */

	public boolean comieza() {
		// Prueba si la variable boolean esta en true

		if (this.space == true) {
			// comienza por la tecla espacio
			return true;
		}

		if (Arkanoid.getInstancia().isBotonprincipal() == true) {
			return true;
		}
		if (tiempo >= 4.0f) {
			return true;
		}

		long inicio = System.currentTimeMillis();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Cojo el tiempo en mili
		long fin = System.currentTimeMillis();

		tiempo += (double) ((fin - inicio) / 1000);
		// Mensaje de indica los segundos

		return false;
	}

	/**
	 * La bola actúa en cada iteración del programa
	 */
	public void act() {
		// Si la bola se toca el borde por la izquierda o por la derecha, su velocidad
		// cambia de signo
		if (this.x < 0 || this.x > Arkanoid.getInstancia().getWidth() - DIAMETRO) {
			this.velocidadX = 0 - this.velocidadX;
		}
		// Si la bola se toca el borde por arriba o por abajo, su velocidad cambia de
		// signo
		if (this.y < 0 || this.y > Arkanoid.getInstancia().getHeight() - DIAMETRO) {
			this.velocidadY = 0 - this.velocidadY;
		}
		// Agregamos las velocidades respectivas a cada eje para la bola
		this.x += this.velocidadX;
		this.y += this.velocidadY;

		if (trayectoriaRecta != null) {

			PuntoAltaPrecision nuevoPuntoAltaPrecision = this.trayectoriaRecta
					.getPuntoADistanciaDePunto(this.coordenadas, this.velocidadPorFrame);
			this.coordenadas = nuevoPuntoAltaPrecision;
			// Actualizo las coordenadas
			this.x = Math.round(this.coordenadas.x);
			this.y = Math.round(this.coordenadas.y);

			// Miramos si hay que aumentar la velocidad
			if (this.velocidadPorFrame < MAXIMAVELOCIDAD) {
				this.velocidadPorFrame *= this.incrementoBola;

			}
		}
	}



	/**
	 * Método que determina el comportamiento cuando este actor colisione con otro
	 */
	@Override
	public void collisionWith(Actor actorCollisioned) {
		super.collisionWith(actorCollisioned);

		// Debo comprobar el tipo del actor que colisiona con este
		if (actorCollisioned instanceof Ladrillo) {
			// Si este actor colisiona con un misil o una bomba, debo eliminar el monstruo
			// Si el monstruo colisiona con un misil o una bomba, también debo eliminar el
			// mision o la bomba
			int ladrix = actorCollisioned.x;
			int ladriy = actorCollisioned.y;

			actorCollisioned.setMarkedForRemoval(true);
			/**
			 * Crear un nuevo actor de explosión en el lugar que ocupa el monstruo
			 */

			if (actorCollisioned.isMarkedForRemoval() == true) {
				Explosion explosion = new Explosion();
				explosion.setX(ladrix);
				explosion.setY(ladriy);
				Arkanoid.getInstancia().getNewActorsForNextIteration().add(explosion);

				SoundsRepository.getInstance().playSound(SoundsRepository.EXPLOSION);
			}

			// Rebote de la pelota
			this.velocidadX = -this.velocidadX;
			this.velocidadY = -this.velocidadY;

		}
		if (actorCollisioned instanceof Nave) {
			// rebote de la pelota nave
			this.respaldoDeX = this.velocidadX;
			this.respaldoDeY = this.velocidadY;
			// la bola se para en la nave si no la pasa
			this.velocidadX -= this.velocidadX;
			this.velocidadY -= this.velocidadY;
			// Volvemos a darle velocidad
			this.velocidadX = this.respaldoDeX;
			this.velocidadY = 3;
			// hace el rebote
			this.velocidadX = -this.velocidadX;
			this.velocidadY = -this.velocidadY;

			SoundsRepository.getInstance().playSound(SoundsRepository.BOLA);

			colisionNave((Nave) actorCollisioned);
		}
	}

	// Metodo calcular la trayecoria de la pelota
	private void colisionNave(Nave nave) {
		// Creamos un rectangulo para saber con que parte choca la bola
		int anchoRebote = 7; // Zona especial para delimitar el rebote
		Rectangle recIzq = new Rectangle(Nave.getInstancia().getX(), Nave.getInstancia().getY(), anchoRebote,
				Nave.getInstancia().getHeight());
		Rectangle recDer = new Rectangle(Nave.getInstancia().getX() + Nave.getInstancia().getWidth() - anchoRebote,
				Nave.getInstancia().getY(), anchoRebote, Nave.getInstancia().getHeight());
		Rectangle recBol = new Rectangle(this.x + this.width / 2 - 4, this.y + this.height / 2 - 4, 8, 8);
		;

		// Colisión con el lado derecho de la nave
		if (recBol.intersects(recDer)) {
			this.y = nave.getY() - nave.getHeight();
			this.coordenadas.y = this.y;
			if (Math.abs(this.trayectoriaRecta.getPendiente()) > 1) { // La bola viene on una pendiente mayor a 1
				this.trayectoriaRecta.setPendiente(-(float) (Math.random() * (0.8 - 0.3) + 0.3), this.coordenadas,
						true);
			} else { // La bola viene con una pendiente suave ( > 0 y < 1 )
				this.trayectoriaRecta.setPendiente(-(float) (Math.random() * (10 - 2) + 2), this.coordenadas, true);
			}
			// Colisión con el lado izquierdo de la nave
			if (recBol.intersects(recIzq)) {
				this.y = nave.getY() - nave.getHeight();
				this.coordenadas.y = this.y;
				if (Math.abs(this.trayectoriaRecta.getPendiente()) > 1) { // La bola viene con una pendiente mayor a 1
					this.trayectoriaRecta.setPendiente((float) (Math.random() * (0.8 - 0.3) + 0.3), this.coordenadas,
							false);
				} else { // La bola viene con una pendiente suave ( > 0 y < 1 )
					this.trayectoriaRecta.setPendiente((float) (Math.random() * (10 - 2) + 2), this.coordenadas, false);
				}
				return;
			} else { // La bola intersecta la parte central de la nave
				this.trayectoriaRecta.reflejarHaciaArriba(this.coordenadas);
				return;
			}
		}
	}

	public boolean isSpace() {
		return space;
	}

	public void setSpace(boolean space) {
		this.space = space;
	}

	public static Bola getInstancia() {
		if (instancia == null) {
			instancia = new Bola();
		}
		return instancia;
	}

}
