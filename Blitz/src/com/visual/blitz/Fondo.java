package com.visual.blitz;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Fondo {
	
	private BufferedImage textura;
	private ArrayList<Nube> nubes = new ArrayList<Nube>();
	
	private int x = 0;
	private int y = 0;
	
	private Timer mueveNubes;
	private Timer creaNube;
	
	public Fondo(String textura) {
		try {
			this.textura = ImageIO.read(Fondo.class.getClassLoader().getResource(textura));
		} catch(Exception e) {
			e.printStackTrace();
		}

		final Random aleatorio = new Random();
		
		// Agrega nuevas nubes
		creaNube = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nubes.add(new Nube(Principal.WIDTH, aleatorio.nextInt(100)));
			}
		});
		
		creaNube.start();
		
		// Mueve las nubes
		mueveNubes = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Nube n;
				for(int i=0; i<nubes.size(); i++) {
					n = nubes.get(i);
					n.mover();
					if((n.getX()+n.getWidth()) < 0) {	// Si la nube salio del cuadro la remueve
						nubes.remove(i);
					}
				}
			}
		});
		
		mueveNubes.start();
	}
	
	public void pausar(boolean p) {
		if(!p) {
			mueveNubes.start();
			creaNube.start();
		} else {
			mueveNubes.stop();
			creaNube.stop();
		}
	}
	
	// Dibuja el cielo mas las nubes
	public void dibujar(Graphics g) {
		g.drawImage(textura, x, y, null);
		
		for(Nube N : nubes) {
			N.dibujar(g);
		}
	}
}
