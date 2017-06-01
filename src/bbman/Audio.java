package bbman;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio
{
	public Audio(String nomFichier)
	{

 		URL url = Audio.class.getResource("boom3.wav"); 
 		AudioClip son = Applet.newAudioClip(url); 							
 		son.play();

 		/*
	     AudioInputStream audioInputStream =AudioSystem.getAudioInputStream(this.getClass().getResource("boum.wav"));
	     Clip clip = AudioSystem.getClip();
	     clip.open(audioInputStream);
	     clip.start( );
	    */

	}
}
