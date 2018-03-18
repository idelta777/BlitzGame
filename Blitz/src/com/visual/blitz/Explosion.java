package com.visual.blitz;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Explosion {
	
	private int x = 0;
	private int y = 0;
	private final int FRAMES_ANIMACION = 5;
	
	public static final int WIDTH = Cubo.WIDTH;
	public static final int HEIGHT = Cubo.HEIGHT;
	
	public Rectangle rectangulo;
	
	private Animacion animacion;
	
	public boolean alive = true;
	
	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.rectangulo = new Rectangle(this.x, this.y, Moneda.WIDTH, Moneda.HEIGHT);
		
		animacion = new Animacion(100, "explosion.png", FRAMES_ANIMACION);
		animacion.loop = false;
		animacion.iniciar();
	}
	
	public void dibujar(Graphics g) {
		animacion.dibujar(g, this.x, this.y);
		if(animacion.over) this.alive = false;
	}
}
