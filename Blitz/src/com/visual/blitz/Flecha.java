package com.visual.blitz;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Flecha {
	
	public int x;
	public int y;
	
	private BufferedImage textura;
	
	public static final int HEIGHT = Cubo.HEIGHT;
	public static final int WIDTH = Cubo.WIDTH;
	
	public Flecha(int x, int y, String imagen) {
		try {
			textura = ImageIO.read(Cubo.class.getClassLoader().getResource(imagen));
		} catch(Exception e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
	}
	
	public void dibujar(Graphics g) {
		g.drawImage(textura, x, y, null);
	}
}
