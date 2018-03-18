package com.visual.blitz;

import java.awt.Graphics;
import java.awt.Rectangle;

public class CuboAgua {
	
	public int x = 0;
	public int y = 0;
	private final int FRAMES_ANIMACION = 2;
	
	public static final int WIDTH = Cubo.WIDTH;
	public static final int HEIGHT = Cubo.HEIGHT;
	
	public Rectangle rectangulo; 
	
	private Animacion animacion;
	
	public CuboAgua(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.rectangulo = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
		
		animacion = new Animacion(250, "agua.png", FRAMES_ANIMACION);
		animacion.iniciar();
	}
	
	public void dibujar(Graphics g) {
		animacion.dibujar(g, this.x, this.y);
	}
}
