package com.visual.blitz;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Principal extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Juego juego;
	
	public static final int HEIGHT = 509;
	public static final int WIDTH = 606;
	
	private boolean pausado = false;
	
	public Principal() {
		super("Blitz");
		
		Nivel.cubos.clear();
		Nivel.monedas.clear();
		Nivel.cubosAgua.clear();
		Nivel.explosiones.clear();
		Nivel.flechas.clear();
		
		Jugador.SALTO_SEGUIDO = false;
		Jugador.MOVIENDOSE = false;
		Animacion.PAUSADO = false;
		
		juego = new Juego();
		this.add(juego);
		juego.pausar(pausado);
		
		this.addKeyListener(this);
		
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(100,100);
		this.setResizable(false);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
        juego.pressedKeys.add(arg0.getKeyCode());
        
        if(arg0.getKeyCode() == 27)	{// Pausa el juego al presionar ESC
        	pausado = !pausado;
        	juego.pausar(pausado);
        }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
        juego.pressedKeys.remove(arg0.getKeyCode());
        Jugador.MOVIENDOSE = false;
        
        if(arg0.getKeyCode() == 32) {	// Libera la barra espaciadora
        	Jugador.SALTO_SEGUIDO = false;
        }
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
