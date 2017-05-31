package bbman;

import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import edu.princeton.cs.introcs.StdDraw;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;

public class Bombe 
{	private int activate;

	private int explose;

	private int x;	//co terrain
	private int y;	//co terrain
	
	private int time_bef;
	private int time_exp;
	
	private int mxp;
	private int mxm;
	private int myp;
	private int mym;
	
	private long timer;
	
	private int puissance;
	
	public Bombe ()
	{	this.activate=0;
		this.explose=0;
		this.x=0;
		this.y=0;
		this.timer=0;
		
		this.time_bef=5000;
		this.time_exp=1000;
		
		this.puissance=2;
	}
		
	
	public int getactivate ()
	{	return this.activate;
	}
		
	public void draw (Terrain terrain)
	{	if ((this.activate==1)&&(this.explose==0)) 
		{	int sx=this.x/(terrain.getwidth()*2);
		
			int x=this.x*2*terrain.getwidth()+terrain.getwidth();
			int y=this.y*2*terrain.getheigth()+terrain.getheigth();
			
			int larg;
			
			if (terrain.getwidth()<terrain.getheigth())
				larg=terrain.getwidth();
			else
				larg=terrain.getheigth();
			
			StdDraw.setPenColor(Color.GREEN);
			StdDraw.filledRectangle(x, y, terrain.getwidth(),terrain.getheigth());
			
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.filledCircle(x, y, larg);
		}
	}
	
	public Terrain put_bombe (int bef, int exp, int puissance, int x, int y, Terrain terrain)
	{	if (terrain.gettab(x, y)!=665)
		{	this.time_bef=bef;
			this.time_exp=exp;
			this.puissance=puissance;
			this.mxp=0;
			this.mxm=0;
			this.myp=0;
			this.mym=0;
			this.activate=1;
			this.explose=0;
			this.x=x; 
			this.y=y;
			
			this.timer=java.lang.System.currentTimeMillis() ;
			
			terrain.set(x,y,665);
		}
		
		return terrain;
	}
	
	public Terrain gestion (Terrain terrain)
	{	long test=0;
		if ((this.activate==1)&&(this.explose==0))
		{	if (java.lang.System.currentTimeMillis()-this.timer>this.time_bef)
			{ terrain=this.boum(terrain);
			}
		}
		if ((this.activate==1)&&(this.explose==1))
		{	if (java.lang.System.currentTimeMillis()-this.timer>this.time_exp)
			{ terrain=this.end_boum(terrain);
			}
			
		}
		
		return terrain;
	}
	
	public void sleep (int mili)
	{	long time=java.lang.System.currentTimeMillis() ;
	
		while (java.lang.System.currentTimeMillis()-time<mili);
	}
	
	public int test_bonus ()
	{	int test=(int)(Math.random()*100);
	
		if (test<50)
			return 10;
		
		return 0;
	}
	
	public Terrain end_boum (Terrain terrain)
	{	int test=0;
		int i=1;
		
		terrain.set(this.x,this.y,0);

		while (i<=this.mxp)
		{	if (terrain.gettab(this.x+i,this.y)==667)
				terrain.set(this.x+i,this.y,test_bonus());
			
			else if (terrain.gettab(this.x+i,this.y)==666)
				terrain.set(this.x+i,this.y,0);
			
			else
				test=1;
		
			i=i+1;
		}
		i=0;
		test=0;
		
		while (i<=this.mym)
		{	if (terrain.gettab(this.x,this.y-i)==667)
				terrain.set(this.x,this.y-i,test_bonus());
			
			else if (terrain.gettab(this.x,this.y-i)==666)
				terrain.set(this.x,this.y-i,0);
			
			else
				test=1;
		
			i=i+1;
		}
		i=0;
		test=0;
		
		while (i<=this.mxm)
		{	if (terrain.gettab(this.x-i,this.y)==667)
				terrain.set(this.x-i,this.y,test_bonus());
			
			else if (terrain.gettab(this.x-i,this.y)==666)
				terrain.set(this.x-i,this.y,0);
			
			else
				test=1;
		
			i=i+1;
		}
		i=0;
		test=0;
		
		while (i<=this.myp)
		{	if (terrain.gettab(this.x,this.y+i)==667)
				terrain.set(this.x,this.y+i,test_bonus());
			
			else if (terrain.gettab(this.x,this.y+i)==666)
				terrain.set(this.x,this.y+i,0);
			
			else
				test=1;
		
			i=i+1;
		}
		
		this.activate=0;
		this.explose=0;
		
		return terrain;
	}
	
	public Terrain boum (Terrain terrain)
	{	int test=0;
		int i=1;

		Audio sound = new Audio("boum");
		
		terrain.set(this.x, this.y, 666);
		
		while (test==0)
		{	if (terrain.gettab(this.x+i,this.y)==1)
				test=1;
			else if (terrain.gettab(this.x+i,this.y)==2)
			{	terrain.set(this.x+i,this.y,667);
				test=1;
			}
			else
				terrain.set(this.x+i,this.y,666);
			
			if (i==this.puissance)
				test=1;
		
			if (test==0)
				i=i+1;
		}
		this.mxp=i;
		i=0;
		test=0;
		
		while (test==0)
		{	if (terrain.gettab(this.x,this.y-i)==1)
				test=1;
			else if (terrain.gettab(this.x,this.y-i)==2)
			{	terrain.set(this.x,this.y-i,667);
				test=1;
			}
			else 
				terrain.set(this.x,this.y-i,666);
		
			if (i==this.puissance)
				test=1;
		
			if (test==0)
				i=i+1;
		}
		this.mym=i;
		i=0;
		test=0;
		
		while (test==0)
		{	if (terrain.gettab(this.x-i,this.y)==1)
				test=1;
			else if (terrain.gettab(this.x-i,this.y)==2)
			{	terrain.set(this.x-i,this.y,667);
				test=1;
			}
			else
				terrain.set(this.x-i,this.y,666);
			
			if (i==this.puissance)
				test=1;
		
			if (test==0)
				i=i+1;
		}
		this.mxm=i;
		i=0;
		test=0;
		
		while (test==0)
		{	if (terrain.gettab(this.x,this.y+i)==1)
				test=1;
			else if (terrain.gettab(this.x,this.y+i)==2)
			{	terrain.set(this.x,this.y+i,667);
				test=1;
			}
			else
				terrain.set(this.x,this.y+i,666);
			
			if (i==this.puissance)
				test=1;
		
			if (test==0)
				i=i+1;
		}
		this.myp=i;
		i=0;
		test=0;
		
		this.timer=java.lang.System.currentTimeMillis() ;
		this.explose=1;
		
		return terrain;
	}
	

	public void playSound(String nomFichier)
	{
		AudioClip sound; 
		try { 
		URL url = new URL("http://www.mediaforma.com/encours/son.wav"); 
		sound = Applet.newAudioClip(url); 
		sound.play(); 
		} catch (MalformedURLException e) { 
		e.printStackTrace(); 
		} 
	}

}
