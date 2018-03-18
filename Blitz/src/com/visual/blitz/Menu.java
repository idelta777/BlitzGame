package com.visual.blitz;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Menu extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton iniciar;
	private JComboBox<String> niveles;
	
	public static String NIVEL_SELECCIONADO = "";
	
	public Menu() {
		super("Blitz");
		iniciar = new JButton("Iniciar");
		iniciar.addActionListener(this);
		
		niveles = new JComboBox<String>();
		
		for(int i = 1; i<=Juego.NO_NIVELES; i++) {
			niveles.addItem("Nivel " + i);
		}
		
		Menu.NIVEL_SELECCIONADO = niveles.getItemAt(0);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		this.add(iniciar);
		this.add(niveles);
		
		this.setLocation(100,100);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		Sonido musica = new Sonido("musicafondo.wav");
		musica.loop();
		musica.play();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(iniciar)) {
			Menu.NIVEL_SELECCIONADO = niveles.getSelectedItem().toString().toLowerCase().replace(" ", "");
			
			Principal p = new Principal();
			p.setVisible(true);
			p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
	
	public static void main(String[] args) {
		Menu m = new Menu();
		m.pack();
	}
}
