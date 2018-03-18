package com.visual.blitz;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Nube {
	
	private int x;
	private int y;
	
	private final int SPEED_X = 1;
	private BufferedImage textura;
	
	public Nube(int x, int y) {
		
		try {
			Random aleatorio = new Random();
			String forma = "nube" + (aleatorio.nextInt(4)+1) + ".png";
			textura = ImageIO.read(Nube.class.getClassLoader().getResource(forma));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
	}
	
	public void dibujar(Graphics g) {
		g.drawImage(textura, x, y, null);
	}
	
	public void mover() {
		this.x -= SPEED_X;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getWidth() {
		return this.textura.getWidth();
	}
}
