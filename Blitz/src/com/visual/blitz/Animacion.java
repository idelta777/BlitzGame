package com.visual.blitz;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Animacion {
	private int frames;
	private int frameActual = 0;
	private BufferedImage texturaCompleta;
	private BufferedImage textura;
	private int anchoTextura, altoTextura;
	
	private Timer temporizador;
	public boolean loop = true;
	public boolean over = false;
	
	public static boolean PAUSADO = false;
	
	public static final int IZQ = 0;
	public static final int DER = 1;
	private int actual = DER;
	
	public Animacion(int retraso, String nomTextura, int frames) {
		this.frames = frames;
		
		try {
			this.texturaCompleta = ImageIO.read(Jugador.class.getClassLoader().getResource(nomTextura));
			this.anchoTextura = texturaCompleta.getWidth() / frames;
			this.altoTextura = texturaCompleta.getHeight();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		this.temporizador = new Timer(retraso, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!over)
					animar();
			}
		});
	}
	
	private void animar() {
		if(PAUSADO) return;
		
		if(frameActual < frames) {
			textura = texturaCompleta.getSubimage(frameActual*anchoTextura, 0, anchoTextura, texturaCompleta.getHeight());
			frameActual++;
		}
		if(frameActual == this.frames)
			if(loop)
				frameActual = 0;
			else
				over = true;
	}
	
	public void dibujar(Graphics g, int x, int y) {
		g.drawImage(textura, x, y, null);
	}
	
	public void iniciar() {
		this.temporizador.start();
	}
	
	public void detener() {
		this.temporizador.stop();
	}
	
	public int getAncho() {
		return this.anchoTextura;
	}
	
	public int getAltura() {
		return this.altoTextura;
	}
	
	public void voltear(int lado) {
		if(lado == DER && actual == IZQ) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		    tx.translate(-texturaCompleta.getWidth(null), 0);
		    AffineTransformOp op = new AffineTransformOp(tx,
		        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		    texturaCompleta = op.filter(texturaCompleta, null);
		    actual = lado;
		}
		if(lado == IZQ && actual == DER) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		    tx.translate(-texturaCompleta.getWidth(null), 0);
		    AffineTransformOp op = new AffineTransformOp(tx,
		        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		    texturaCompleta = op.filter(texturaCompleta, null);
		    actual = lado;
		}
	}
}
