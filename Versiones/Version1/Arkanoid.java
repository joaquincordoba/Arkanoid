package Arkanoid.Version1;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Arkanoid extends Canvas {

	// Creamos la venta las dimensiones

	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;

	public static final int SPEED = 60;
	public int velox = 5;
	public int veloy = 5;
	private long usedTime;
	

	
	Pelota pelota = new Pelota(0, 44, 10, 10);
	Ladrillo ladrillo = new Ladrillo(12,15,29,15);
	// Ventana del juego

	JFrame ventana = new JFrame("ARKANOID");

	public Arkanoid() {
		JPanel panel = (JPanel) ventana.getContentPane();
		
		panel.setLayout(new BorderLayout());
		panel.add(this, BorderLayout.CENTER);
		ventana.setBounds(0, 0, WIDTH, HEIGHT);
		ventana.setVisible(true);
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cerrarAplicacion();
			}
		});
		ventana.setResizable(false);
		
		
		
		requestFocus();
	}

	/**
	 * Pintar la ventana
	 */

	// Graphics ponerlo dentro de paint si no peta
	public void paint(Graphics g) {

		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		this.ladrillo.paint(g);
		this.pelota.paint(g);
		Nave.getInstance().paint(getGraphics());
		
	}

	public void updateWorld() {
		this.pelota.xventana += velox;
		this.pelota.yventana += veloy;
		
		if (this.pelota.xventana < 0 || this.pelota.xventana > (this.getWidth()- this.pelota.ancho)) {
			this.velox = -this.velox;
		}
		if (this.pelota.yventana < 0 || this.pelota.yventana > (this.getHeight()- this.pelota.alto)) {
			this.veloy = -this.veloy;
		}
		
	}
	
	public void game() {
		
		while (this.isVisible()) {
			long startTime = System.currentTimeMillis();
			
			updateWorld();
			repaint();
			
			usedTime = System.currentTimeMillis()-startTime;
			
			try {
				int millisToSleep = (int) (1000/SPEED - usedTime);
				if (millisToSleep < 0) {
					millisToSleep = 0;
				}
				Thread.sleep(millisToSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void cerrarAplicacion() {
		String [] opciones ={"Aceptar","Cancelar"};
		int eleccion = JOptionPane.showOptionDialog(ventana,"¿Desea cerrar la aplicación?","Salir de la aplicación",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
		if (eleccion == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	


}
