package com.visual.blitz;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Moneda {
	
	private int x = 0;
	private int y = 0;
	private final int FRAMES_ANIMACION = 4;
	public final int VALOR = 10;
	
	public static final int WIDTH = Cubo.WIDTH;
	public static final int HEIGHT = Cubo.HEIGHT;
	
	public Rectangle rectangulo;
	
	private Animacion animacion;
	
	public Moneda(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.rectangulo = new Rectangle(this.x, this.y, Moneda.WIDTH, Moneda.HEIGHT);
		
		animacion = new Animacion(250, "moneda.png", FRAMES_ANIMACION);
		animacion.iniciar();
	}
	
	public void dibujar(Graphics g) {
		animacion.dibujar(g, this.x, this.y);
	}
}
