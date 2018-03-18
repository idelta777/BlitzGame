package com.visual.blitz;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import javax.swing.Timer;

public class Jugador {
	
	// Texturas y animacion
	private final int FRAMES_ANIMACION = 4;
	
	// Posicion y colisiones
	private int x_inicial;
	private int y_inicial;
	private int x;
	private int y;
	public Rectangle rectangulo;
	private Point posicionPrevia;
	public final int SPEED_X = 5;
	public final int SPEED_Y = 5;
	
	public int puntuacion = 0;
	
	// Saltos y gravedad
	private final int GRAVEDAD = 5;
	private boolean saltando = false;
	private final int ALTURA_MAX = (int)(Cubo.HEIGHT*2 + Cubo.HEIGHT/2);
	private int alturaSalto = 0;
	private boolean subida = false;
	public static boolean SALTO_SEGUIDO = false;	// No da saltos seguidos si de deja presionada la barra
	
	private Timer grav;
	private Timer salto;
	
	private Timer muerte;
	public boolean muerto = false;
	
	private Animacion animacion;
	
	private Sonido sonidoMoneda;
	private Sonido sonidoBrinco;
	private Sonido sonidoMovimiento;
	private Sonido sonidoExplosion;
	private Sonido sonidoGana;
	private Sonido sonidoCaida;
	
	public static boolean MOVIENDOSE = false;
	
	public Jugador(int x, int y) {
		this.x = x;
		this.y = y;
		this.x_inicial = x;
		this.y_inicial = y;
		
		animacion = new Animacion(250, "jugador.png", FRAMES_ANIMACION);
		animacion.iniciar();
		
		rectangulo = new Rectangle(this.x, this.y, this.animacion.getAncho()-1, this.animacion.getAltura()-1);
		
		// Aplica gravedad
		grav = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!estaEnSuelo() && !saltando) {	// Si no esta en tierra aplica gravedad
					mover(0, GRAVEDAD);
				}
			}
		});
		
		salto = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!subida) {	// termino el salto y va de bajada
					baja();
				}
				else {
					mover(0, -SPEED_Y); // sube
					if(terminoSalto()) {
						subida = false;
					}
				}
			}
		});

		grav.start();
		
		// Carga los sonidos
		
		this.sonidoBrinco = new Sonido("brinco.wav");
		this.sonidoExplosion = new Sonido("explosion.wav");
		this.sonidoGana = new Sonido("gana.wav");
		this.sonidoMoneda = new Sonido("moneda.wav");
		this.sonidoMovimiento = new Sonido("movimiento.wav");
		this.sonidoCaida = new Sonido("caida.wav");
		
	}

	public void dibujar(Graphics g) {
		if(muerto)
			Juego.DibujaCadena("Reapareciendo", g, Principal.WIDTH/2, Principal.HEIGHT/2);
		else
			animacion.dibujar(g, this.x, this.y);
		//g.drawRect(rectangulo.x, rectangulo.y, (int)rectangulo.getWidth(), (int)rectangulo.getHeight());
	}

	private boolean estaColisionando() {
		for(Cubo c : Nivel.cubos) {
			if(this.rectangulo.intersects(c.rectangulo))
				return true;
		}
		
		Moneda m;
		
		for(int i=0; i<Nivel.monedas.size(); i++) {
			m = Nivel.monedas.get(i);
			if(this.rectangulo.intersects(m.rectangulo))
				recogeMoneda(m);
		}
		
		for(CuboAgua a : Nivel.cubosAgua) {
			if(this.rectangulo.intersects(a.rectangulo)) {
				if(!this.sonidoExplosion.isPlaying())
					this.sonidoExplosion.play();
				
				Nivel.explosiones.add(new Explosion(a.x, a.y-CuboAgua.HEIGHT));
				
				muere();
			}
		}
		
		return false;
	}

	private void recogeMoneda(Moneda m) {
		puntuacion += m.VALOR;
		Nivel.monedas.remove(m);
		
		// Sonido moneda
		if(!sonidoMoneda.isPlaying())
			sonidoMoneda.play();
		
		if(Nivel.monedas.size() == 0) {
			// gana
			if(Juego.NIVEL_ACTUAL == Juego.NO_NIVELES) Juego.NIVEL_ACTUAL = 1;
			else Juego.NIVEL_ACTUAL++; 
			
			Nivel.cubos.clear();
			Nivel.cubosAgua.clear();
			Nivel.flechas.clear();
			
			Juego.NIVEL = new Nivel("nivel" + Juego.NIVEL_ACTUAL + ".txt");
			this.setLocation(Juego.X_INICIAL, Juego.Y_INICIAL);
			sonidoGana.play();
		}
		
		// Aumenta la puntuacion
		// Si recoge todas las monedas termina el nivel
	}
	
	public void mover(int x, int y) {
		this.x += x;
		this.y += y;
		
		if(Jugador.MOVIENDOSE && this.estaEnSuelo() && !this.sonidoMovimiento.isPlaying()) {
			this.sonidoMovimiento.play();
		}
		
		if(this.y > Principal.HEIGHT) {
			this.sonidoCaida.play();
			muere();
		}
		
		this.rectangulo.setLocation(this.x, this.y);
		
		if(this.estaColisionando()) {
			this.x = this.posicionPrevia.x;
			this.y = this.posicionPrevia.y;
		}
		
		this.posicionPrevia = new Point(this.x, this.y);
		this.rectangulo.setLocation(posicionPrevia);
	}
	
	public void muere() {	// Lo lleva a su posision de inicio y hace una pausa
		this.x = this.x_inicial;
		this.y = this.y_inicial;
		this.puntuacion -= 10;
		muerto = true;
		
		muerte = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				muerto = false;
			}
		});
		
		if(muerte.isRunning()) {
			muerte.stop();
			muerte.restart();
		}
		
		muerte.setInitialDelay(1000);
		muerte.start();
	}
	
	public void saltar() {	// Inicia el salto
		if(!estaEnSuelo()) return;
		
		if(SALTO_SEGUIDO) return;
		
		SALTO_SEGUIDO = true;
		saltando = true;
		subida = true;
		alturaSalto = this.y;
		salto.start();
		if(sonidoMovimiento.isPlaying())
			sonidoMovimiento.stop();
		
		sonidoBrinco.play();
	}
	
	private void baja() {	// Quita las banderas de salto y la gravedad se encarga del resto
		saltando = false;
		salto.stop();
		salto.stop();
		alturaSalto = 0;
	}
	
	private boolean terminoSalto() {		// Checa si se acabo el salto
		if( (this.y <= (alturaSalto - ALTURA_MAX)) || this.chocaConTecho() )
			return true;
		else
			return false;
	}
	
	private boolean estaEnSuelo() {	// Determina si el jugador esta tocando tierra
		for(Cubo c : Nivel.cubos) {
			Line2D.Float abajoJugador = new Line2D.Float((float)rectangulo.x, (float)(rectangulo.y + rectangulo.getHeight()+1),
											(float)(rectangulo.x+rectangulo.getWidth()),(float)(rectangulo.y + rectangulo.getHeight()+1));
			if(abajoJugador.intersects(c.rectangulo)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean chocaConTecho() {	// Determina si el jugador choca con un techo al saltar
		for(Cubo c : Nivel.cubos){
			Line2D.Float arribaJugador = new Line2D.Float((float)rectangulo.x, (float)rectangulo.y-1,
													(float)(rectangulo.x+rectangulo.getWidth()),(float)(rectangulo.y-1));
			
			if(arribaJugador.intersects(c.rectangulo)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void pausar(boolean p) {	// Pausa las animaciones
		if(!p) {
			grav.start();
		} else {
			grav.stop();
		}
	}
	
	public void voltear(int lado) {
		animacion.voltear(lado);
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.rectangulo.setLocation(this.x, this.y);
	}
}