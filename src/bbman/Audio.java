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
	private URL url;
	private AudioClip son;
	
	public Audio(String nomFichier)
	{
 		this.url = Audio.class.getResource(nomFichier); 
 		this.son = Applet.newAudioClip(this.url); 							
	}
	
	public void play()
	{
		this.son.play();
	}
	
	public void stop()
	{
		this.son.stop();
	}
	
}
