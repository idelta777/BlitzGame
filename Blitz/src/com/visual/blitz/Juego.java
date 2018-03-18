package com.visual.blitz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Juego extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public Jugador jugador;
	public static Nivel NIVEL;
	public static int NIVEL_ACTUAL = 1;
	public static final int NO_NIVELES = 5;
	
	private Timer teclado;
	
	public static int X_INICIAL = 0;
	public static int Y_INICIAL = 0;
	
	public HashSet<Integer> pressedKeys = new HashSet<Integer>();
	
	private Font fuente;
	
	private String estado = "";
	
	public Juego() {
		super();

		Juego.NIVEL_ACTUAL = Integer.valueOf(Menu.NIVEL_SELECCIONADO.replace("nivel", ""));
		NIVEL = new Nivel("nivel" + Juego.NIVEL_ACTUAL + ".txt");
		// El jugador debe instanciarse despues del nivel para no provocar excepciones
		jugador = new Jugador(X_INICIAL, Y_INICIAL);
		fuente = new Font("SansSerif", Font.BOLD, 14);
		
		// Temporizador para checar siempre la entrada del teclado sin pausas
		// De esta manera tambien acepta varias teclas al mismo tiempo
		teclado = new Timer(15, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(!pressedKeys.isEmpty()){
                    Iterator<Integer> i = pressedKeys.iterator();
                    while(i.hasNext()){
                    	Integer a = i.next();
                        entradaTeclado(a);
                    }
                }
            }
        });
		
		teclado.start();
	}
	
	public static void DibujaCadena(String cadena, Graphics g, int x, int y) {
		int marco = 2;
		g.setColor(Color.red);
		g.drawString(cadena, x-marco, y);
		g.drawString(cadena, x+marco, y);
		g.drawString(cadena, x, y-marco);
		g.drawString(cadena, x, y+marco);
		g.drawString(cadena, x+marco, y+marco);
		g.drawString(cadena, x+marco, y-marco);
		g.drawString(cadena, x-marco, y+marco);
		g.drawString(cadena, x-marco, y-marco);
		g.setColor(Color.yellow);
		g.drawString(cadena, x, y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		g.clearRect(0,0,this.getWidth(),this.getHeight());
		
		// Se dibujan en el orden que aparecen, siendo el ultimo el que este hasta enfrente
		
		g.setColor(Color.GREEN);
		g.setFont(fuente);
		
		NIVEL.dibujarFondo(g);
		
		NIVEL.dibujar(g);
		jugador.dibujar(g);
		
		Juego.DibujaCadena(estado, g, this.getWidth()/2, this.getHeight()/2);
		Juego.DibujaCadena("Puntuacion: " + jugador.puntuacion, g, 30, 30);
		
		repaint();
	}
	
	public void entradaTeclado(int tecla) {
		
		if(jugador.muerto) return;
		
		Jugador.MOVIENDOSE = true;
		
		if(tecla == 65 || tecla == 37) {	// A
			jugador.mover(-jugador.SPEED_X, 0);
			jugador.voltear(Animacion.IZQ);
		}
		if(tecla == 68 || tecla == 39) {	// D
			jugador.mover(jugador.SPEED_X, 0);
			jugador.voltear(Animacion.DER);
		}
		if(tecla == 32)		// Espacio
			jugador.saltar();
		if(tecla == 82)		// R
			jugador.setLocation(X_INICIAL, Y_INICIAL);
	}
	
	public void pausar(boolean p) {	// Pausa todos los timers
		if(!p) {
			teclado.start();
			estado = "";
			Animacion.PAUSADO = false;
		} else {
			Animacion.PAUSADO = true;
			teclado.stop();
			estado = "Pausado";
		}
		jugador.pausar(p);
		NIVEL.pausar(p);
	}
}
