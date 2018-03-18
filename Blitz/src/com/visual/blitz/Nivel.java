package com.visual.blitz;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Nivel {
	
	public static ArrayList<Cubo> cubos = new ArrayList<Cubo>();
	public static ArrayList<Moneda> monedas = new ArrayList<Moneda>();
	public static ArrayList<CuboAgua> cubosAgua = new ArrayList<CuboAgua>();
	public static ArrayList<Explosion> explosiones = new ArrayList<Explosion>();
	public static ArrayList<Flecha> flechas = new ArrayList<Flecha>();
	private Fondo fondo;
	
	public Nivel(String nombre) {
		
		BufferedReader br;
		
	    try {
	    	br = new BufferedReader(new InputStreamReader(Nivel.class.getClassLoader().getResourceAsStream(nombre)));
	        String line = br.readLine();
	        fondo = new Fondo(line);
	        line = br.readLine();

	        int y = 0;
	        
	        while (line != null) {						// Recorre renglones
	        	for(int i=0; i<line.length(); i++) {	// Recorre columnas
	        		char c = line.charAt(i);
	        		if(c == 'j') {	// Da la posicion inicial al jugador
	        			Juego.X_INICIAL = i*Cubo.WIDTH;
	        			Juego.Y_INICIAL = y;
	        		}
	        		if(c == 'c') {	// Pone los cubos
	        			cubos.add(new Cubo(i*Cubo.WIDTH,y));
	        		}
	        		if(c == 'a') {
	        			cubosAgua.add(new CuboAgua(i*CuboAgua.WIDTH,y));
	        		}
	        		if(c == 'p') {
	        			//cubos.add(new Cubo(i*Cubo.WIDTH,y,"pasto.png"));
	        		}
	        		if(c == 'm') {
	        			monedas.add(new Moneda(i*Moneda.WIDTH, y));
	        		}
	        		if(c == 'f') {
	        			flechas.add(new Flecha(i*Flecha.WIDTH, y, "flecha.png"));
	        		}
	        	}
	        	
	        	y += Cubo.HEIGHT;
	            line = br.readLine();
	        }
	        
	        br.close();
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void dibujar(Graphics g) {
		for(Cubo c : cubos) {
			c.dibujar(g);
		}
		
		for(Moneda m : monedas) {
			m.dibujar(g);
		}
		
		for(CuboAgua a : cubosAgua) {
			a.dibujar(g);
		}
		
		for(Flecha f : flechas) {
			f.dibujar(g);
		}
		
		Explosion e;
		for(int i=0; i<explosiones.size(); i++) {
			e = explosiones.get(i);
			e.dibujar(g);
			if(!e.alive)
				explosiones.remove(i);
		}
	}
	
	public void dibujarFondo(Graphics g) {
		fondo.dibujar(g);
	}
	
	public void pausar(boolean p) {
		fondo.pausar(p);
	}
}
