package Arkanoid.Version2_2;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Clase principal del programa, act�a como la ventana que ver� el usuario. Se
 * utiliza un patr�n Singleton
 * 
 * @author R
 *
 */
public class Arkanoid extends Canvas {
	// Variables est�ticas
	public static final int ANCHO = 400;
	public static final int ALTO = 600;
	public static final int FPS = 60; // Frames por segundo
	// Ventana
	JFrame ventana = null;
	// Lista de actores que se representan en pantalla
	List<Actor> actores = new ArrayList<Actor>();

	private List<Actor> newActorsForNextIteration = new ArrayList<Actor>();

	Nave nav = null;

	Bola bola = null;

	// Estrategia de Doble Buffer
	private BufferStrategy strategy;
	// Variable para patr�n Singleton
	private static Arkanoid instancia = null;

	private boolean botonprincipal = false;

	/**
	 * Getter Singleton
	 * 
	 * @return
	 */
	public static Arkanoid getInstancia() {
		if (instancia == null) {
			instancia = new Arkanoid();
		}
		return instancia;
	}

	/**
	 * Constructor
	 */
	public Arkanoid() {
		// Creaci�n de la ventana
		ventana = new JFrame("Arkanoid");
		// Obtenemos el panel principal del JFrame
		JPanel panel = (JPanel) ventana.getContentPane();
		// Utilizo un BorderLayout para colocar el Canvas sobre el JPanel
		panel.setLayout(new BorderLayout());
		// Agregamos el Canvas al panel de manera que ocupe todo el espacio disponible
		panel.add(this, BorderLayout.CENTER);
		// Dimensionamos el JFrame
		ventana.setBounds(0, 0, ANCHO, ALTO);
		// Hacemos el JFrame visible
		ventana.setVisible(true);
		// Con el siguiente c�digo preguntamos al usuario si realmente desea cerrar la
		// aplicaci�n, cuando
		// pulse sobre el aspa de la ventana
		ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ventana.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cerrarAplicacion();
			}
		});
		// Creaci�n de la estrategia de doble b�ffer
		this.createBufferStrategy(2);
		strategy = getBufferStrategy();
		// Con ignoreRepaint le decimos al JFrame que no debe repintarse cuando el
		// Sistema Operativo se lo indique,
		// nosotros nos ocupamos totalmente del refresco de la pantalla
		ventana.setIgnoreRepaint(true);
		// La ventana no podr� redimensionarse
		ventana.setResizable(false);
		// Hacemos que el Canvas obtenga autom�ticamente el foco del programa para que,
		// si se pulsa una tecla, la pulsaci�n
		// se transmita directamente al Canvas.
		this.requestFocus();

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getButton() == e.BUTTON1) {
					botonprincipal = true;
				}
			}

		});

	}

	/**
	 * Al cerrar la aplicaci�n preguntaremos al usuario si est� seguro de que desea
	 * salir.
	 */
	private void cerrarAplicacion() {
		String[] opciones = { "Aceptar", "Cancelar" };
		int eleccion = JOptionPane.showOptionDialog(ventana, "�Desea cerrar la aplicaci�n?", "Salir de la aplicaci�n",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
		if (eleccion == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Al principio del juego, lo primero que se debe hacer es inicializar todo lo
	 * necesario para que se pueda mostrar la primera fase.
	 */
	public void initWorld() {
		// Creaci�n de los actores
		Ladrillo.getInstancia().ladrillos();
		this.bola = new Bola();
		this.actores.add(this.bola);

		// Agrego a la lista de jugadores al actor de tipo Player
		Nave player = new Nave();
		player.setX(200);
		player.setY(500);
		this.actores.add(player);
		// Mantengo una referencia al Player
		this.nav = player;
		// Agrego un listener para eventos de teclado y, cuando se produzcan, los derivo
		// al objeto de tipo Player
		this.addKeyListener(player);
		// Raton
		this.addMouseMotionListener(player);

	}

	/**
	 * Este m�todo actualiza la posici�n y valores de los diferentes actores del
	 * juego, se ejecuta en cada iteraci�n.
	 */
	public void updateWorld() {
		// Puede ocurrir que existan actores que se deben eliminar para el siguiente
		// pintado de escena.
		// Cuando estoy recorriendo una lista no puedo eliminar elementos sin
		// arriesgarme a provocar un problema de
		// concurrencia de acceso. Por ello lo que hago es crear una nueva lista con los
		// elementos a eliminar. Despu�s
		// se recorre esa lista eliminando los elementos de la lista principal y, por
		// �ltimo, limpio la lista
		List<Actor> actorsForRemoval = new ArrayList<Actor>();
		for (Actor actor : this.actores) {
			if (actor.isMarkedForRemoval()) {
				actorsForRemoval.add(actor);
			}
		}
		// Elimino los actores marcados para su eliminaci�n
		for (Actor actor : actorsForRemoval) {
			this.actores.remove(actor);
		}
		// Limpio la lista de actores para eliminar
		actorsForRemoval.clear();

		// Adem�s de eliminar actores, tambi�n puede haber actores nuevos que se deban
		// insertar en la siguiente iteraci�n.
		// Se insertan y despu�s se limpia la lista de nuevos actores a insertar
		this.actores.addAll(newActorsForNextIteration);
		this.newActorsForNextIteration.clear();

		// Finalmente, se llama al m�todo "act" de cada actor, para que cada uno
		// recalcule por si mismo sus valores.
		for (Actor actor : this.actores) {
			actor.act();
		}

		// Una vez que cada actor ha actuado, intento detectar colisiones entre los
		// actores y notificarlas. Para detectar
		// estas colisiones, no nos queda m�s remedio que intentar detectar la colisi�n
		// de cualquier actor con cualquier otro
		// s�lo con la excepci�n de no comparar un actor consigo mismo.
		// La detecci�n de colisiones se va a baser en formar un rect�ngulo con las
		// medidas que ocupa cada actor en pantalla,
		// De esa manera, las colisiones se traducir�n en intersecciones entre
		// rect�ngulos.

		// Creo un rect�ngulo para este actor.
		Rectangle rect1 = new Rectangle(this.bola.getX(), this.bola.getY(), this.bola.getWidth(),
				this.bola.getHeight());
		// Compruebo un actor con cualquier otro actor
		for (Actor actor2 : this.actores) {
			// Evito comparar un actor consigo mismo, ya que eso siempre provocar�a una
			// colisi�n y no tiene sentido
			if (!actor2.equals(bola) && !actor2.isMarkedForRemoval()) {
				// Formo el rect�ngulo del actor 2
				Rectangle rect2 = new Rectangle(actor2.getX(), actor2.getY(), actor2.getWidth(), actor2.getHeight());
				// Si los dos rect�ngulos tienen alguna intersecci�n, notifico una colisi�n en
				// los dos actores
				if (rect1.intersects(rect2)) {
					this.bola.collisionWith(actor2); // El actor 1 colisiona con el actor 2
					actor2.collisionWith(this.bola); // El actor 2 colisiona con el actor 1
					break;
				}
			}
		}
	}

	/**
	 * M�todo responsable de repintar cada frame del juego
	 */
	public void paintWorld() {
		Toolkit.getDefaultToolkit().sync();
		// Obtenemos el objeto Graphics (la brocha) desde la estrategia de doble b�ffer
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		// Lo primero que hace cada frame es pintar un rect�ngulo tan grande como la
		// escena,
		// para superponer la escena anterior.
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Ejecutamos el m�todo paint de cada uno de los actores
		for (Actor actor : this.actores) {
			actor.paint(g);
		}
		// Una vez construida la escena nueva, la mostramos.
		strategy.show();
	}

	/**
	 * M�todo principal del juego. Contiene el bucle principal
	 */
	public void game() {
		SoundsRepository.getInstance();

		initWorld();

		// Inicializaci�n del juego

		paintWorld();
		while (isVisible()) {
			if (Bola.getInstancia().comieza() == true) {

				// Este bucle se ejecutar� mientras el objeto Canvas sea visible.
				while (this.isVisible()) {
					// Tomamos el tiempo en milisegundos antes de repintar el frame
					long millisAntesDeConstruirEscena = System.currentTimeMillis();
					// Actualizamos y pintamos el nuevo frame
					updateWorld();
					paintWorld();
					// Calculamos la cantidad de milisegundos que se ha tardado en realizar un nuevo
					// frame del juego
					int millisUsados = (int) (System.currentTimeMillis() - millisAntesDeConstruirEscena);
					// Hago que el programa duerma lo suficiente para que realmente se ejecuten la
					// cantidad de FPS
					// que tenemos programados
					try {
						int millisADetenerElJuego = 1000 / FPS - millisUsados;
						if (millisADetenerElJuego >= 0) {
							Thread.sleep(millisADetenerElJuego);
						}
					} catch (InterruptedException e) {
					}
				}
			}
		}

	}

	/**
	 * M�todo main()
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Arkanoid.getInstancia().game();
	}

	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	public List<Actor> getNewActorsForNextIteration() {
		return newActorsForNextIteration;
	}

	public void setNewActorsForNextIteration(List<Actor> newActorsForNextIteration) {
		this.newActorsForNextIteration = newActorsForNextIteration;
	}

	public boolean isBotonprincipal() {
		return botonprincipal;
	}

	public void setBotonprincipal(boolean botonprincipal) {
		this.botonprincipal = botonprincipal;
	}

}
