package com.visual.blitz;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonido {
	
	private Clip clip;
	
	public Sonido(String nombre) {
		try {
			AudioInputStream sample;
			sample = AudioSystem.getAudioInputStream(Sonido.class.getClassLoader().getResource(nombre));
			clip = AudioSystem.getClip();
			clip.open(sample);
			clip.setFramePosition(0);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void play(){
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
    	clip.stop();
    }
	
	public boolean isPlaying() {
		if(clip.isRunning()) {
    		return true;
    	}
    	
    	return false;
	}
}
