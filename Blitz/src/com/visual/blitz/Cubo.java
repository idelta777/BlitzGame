package com.visual.blitz;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Cubo {
	
	public int x;
	public int y;
	
	private BufferedImage textura;
	
	public static final int HEIGHT = 20;
	public static final int WIDTH = 20;
	
	public Rectangle rectangulo;
	
	public Cubo(int x, int y) {
		try {
			textura = ImageIO.read(Cubo.class.getClassLoader().getResource("cubo.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		
		this.rectangulo = new Rectangle(this.x, this.y, Cubo.WIDTH-1, Cubo.HEIGHT-1);
	}
	
	public void dibujar(Graphics g) {
		g.drawImage(textura, x, y, null);
	}
}
