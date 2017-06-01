package bbman;

import java.awt.event.KeyEvent;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;

public class Joueur 
{	
	private int x;
	private int y;
	
	private int sens;
	
	private int hitx;
	private int hity;
	
	private int life;
	
	private int speed;
	
	private int who;
	
	private int nb_bomb;
	Bombe [] bombe=new Bombe [10] ;
	
	private int bb_pc;
	private int bb_bef;
	private int bb_exp;
	
	private long timer;
	private int ttm;
	private int inv;
	
	private Color color;
	

	public Joueur (Terrain terrain, Color color)
	{	this.life=1;
		this.speed=2;
		this.nb_bomb=2;
		this.timer=0;
		this.inv=0;
		this.ttm=2000;
		this .sens=1;
		
		this.color=color;
		
		this.hitx=3*terrain.getwidth()/4;
		this.hity=3*terrain.getwidth()/4;
		
		int i=0;
		
		for (i=0; i<10; i++)
		{	bombe [i]=new Bombe();
		}
		
		this.bb_pc=3;
		this.bb_bef=5000;
		this.bb_exp=1000;
	}
	
	
	public int get_x ()
	{	
		return this.x;
	}
	
	public int get_y ()
	{	
		return this.y;
	}
	
	public int getnbbombe ()
	{	
		return this.nb_bomb;
	}
	
	public int getlife ()
	{	
		return this.life;
	}
	public int getnbbomb ()
	{	
		return this.nb_bomb;
	}
	public Bombe getbomb (int num)
	{	
		return this.bombe[num];
	}
	
	public void init (int who, int x, int y, int width, int heigth)
	{	
		this.who=who;

		if (who==1)
		{	this.x=3*width;
			this.y=3*heigth;
		}
		if (who==2)
		{	this.x=(x*2*width)-(3*width);
			this.y=(y*2*heigth)-(3*heigth);
		}
	}
		
	public Terrain put_bombe(Terrain terrain)
	{
		int i=0;
		int test=0;
		
		int sx=this.x/(terrain.getwidth()*2);
		int sy=this.y/(terrain.getheigth()*2);
		
		while (test==0)
		{	if (i==this.nb_bomb)
			{	test=1;
			}
			else
			{	if (this.bombe[i].getactivate()==0)
				{	terrain=this.bombe[i].put_bombe(this.bb_bef, this.bb_exp, this.bb_pc, sx, sy, terrain);
					//terrain.set(sx,sy,665);
					test=1;
				}
				else
					i=i+1;
			}
		}
		
		return terrain;
	}
	
	public void setbon (int val)
	{
		if (val==10)
		{
			this.nb_bomb=this.nb_bomb+1;
			if (this.nb_bomb>9)
				this.nb_bomb=9;
		}
	
	}
	
	public void setdeg()
	{
		if (this.inv==0)
		{
			this.life=this.life-1;
	
			if (this.life<0)
				this.life=0;
			
			this.inv=1;
			this.timer=java.lang.System.currentTimeMillis();
		}
	}
	
	public Terrain bon_deg (Terrain terrain)
	{
		int sx=this.x/(terrain.getwidth()*2);
		int sy=this.y/(terrain.getheigth()*2);
		
		if (this.inv!=0)
		{
			if (java.lang.System.currentTimeMillis()-this.timer>this.ttm)
				this.inv=0;
		}
		
		if (terrain.gettab(sx, sy)>=666)
		{
			setdeg();
		}
		
		if ((terrain.gettab(sx, sy)>=10)&&(terrain.gettab(sx, sy)<=50))
		{
			this.setbon(terrain.gettab(sx, sy));
			terrain.set(sx, sy, 0);
		}
		/*--------------------------------------------------------------------------------------------------------------------------*/
		if (this.x+this.hitx>(sx+1)*2*terrain.getwidth())
		{
			if (terrain.gettab(sx+1, sy)>=666)
			{
				setdeg();
			}
			
			if ((terrain.gettab(sx+1, sy)>=10)&&(terrain.gettab(sx+1, sy)<=50))
			{
				this.setbon(terrain.gettab(sx+1, sy));
				terrain.set(sx+1, sy, 0);
			}
		}
		if (this.x-this.hitx<(sx)*2*terrain.getwidth())
		{
			if (terrain.gettab(sx-1, sy)>=666)
			{
			setdeg();
			}
			
			if ((terrain.gettab(sx-1, sy)>=10)&&(terrain.gettab(sx-1, sy)<=50))
			{
				this.setbon(terrain.gettab(sx-1, sy));
				terrain.set(sx-1, sy, 0);
			}
		}
		if (this.y+this.hity>(sy+1)*2*terrain.getheigth())
		{
			if (terrain.gettab(sx, sy+1)>=666)
			{
				setdeg();
			}
			
			if ((terrain.gettab(sx, sy+1)>=10)&&(terrain.gettab(sx, sy+1)<=50))
			{
				this.setbon(terrain.gettab(sx, sy+1));
				terrain.set(sx, sy+1, 0);
			}
		}
		if (this.y-this.hity<(sy)*2*terrain.getheigth())
		{	
			if (terrain.gettab(sx, sy-1)>=666)
			{
				setdeg();
			}
			
			if ((terrain.gettab(sx, sy-1)>=10)&&(terrain.gettab(sx, sy-1)<=50))
			{
				this.setbon(terrain.gettab(sx, sy-1));
				terrain.set(sx, sy-1, 0);
			}
		}
		
		return terrain;
	}

	public void test (int mem_x, int mem_y, Terrain terrain)
	{
		int sx=this.x/(terrain.getwidth()*2);
		int sy=this.y/(terrain.getheigth()*2);
		
		if (this.x+this.hitx>(sx+1)*2*terrain.getwidth())
		{
			if (terrain.test(sx+1,sy)==1)
				this.x=mem_x;
		
			if ((this.y+this.hity>(sy+1)*2*terrain.getheigth())&&(terrain.test(sx+1,sy+1)==1))
			{
				this.y=mem_y;
			}
			if ((this.y-this.hity<(sy)*2*terrain.getheigth())&&(terrain.test(sx+1,sy-1)==1))
			{	
				this.y=mem_y;
			}		
		}
		if (this.x-this.hitx<(sx)*2*terrain.getwidth())
		{	
			if (terrain.test(sx-1,sy)==1)
				this.x=mem_x;

			if ((this.y+this.hity>(sy+1)*2*terrain.getheigth())&&(terrain.test(sx-1,sy+1)==1))
			{	
				this.y=mem_y;
			}
			if ((this.y-this.hity<(sy)*2*terrain.getheigth())&&(terrain.test(sx-1,sy-1)==1))
			{	
				this.y=mem_y;
			}
		}
		
		if (this.y+this.hity>(sy+1)*2*terrain.getheigth())
		{	
			if (terrain.test(sx,sy+1)==1)
				this.y=mem_y;
		
			if ((this.x+this.hitx>(sx+1)*2*terrain.getwidth())&&(terrain.test(sx+1,sy+1)==1))
			{	
				this.x=mem_x;
			}
			if ((this.x-this.hity<(sx)*2*terrain.getwidth())&&(terrain.test(sx-1,sy+1)==1))
			{	
				this.x=mem_x;
			}
		}
		if (this.y-this.hity<(sy)*2*terrain.getheigth())
		{	
			if (terrain.test(sx,sy-1)==1)
				this.y=mem_y;
		
			if ((this.x+this.hitx>(sx+1)*2*terrain.getwidth())&&(terrain.test(sx+1,sy-1)==1))
			{	
				this.x=mem_x;
			}
			if ((this.x-this.hity<(sx)*2*terrain.getwidth())&&(terrain.test(sx-1,sy-1)==1))
			{	
				this.x=mem_x;
			}
		}
	}
	
	public void  move (int dep, Terrain terrain)
	{	
		int mem_x=this.x;
		int mem_y=this.y;
		
		if (dep==1)
		{	
			this.y=this.y+this.speed;
			this.sens=4;
		}
		else if (dep==2)
		{	
			this.x=this.x-this.speed;
			this.sens=3;
		}
		else if (dep==3)
		{	
			this.y=this.y-this.speed;
			this.sens=1;
		}
		else if (dep==4)
		{	
			this.x=this.x+this.speed;
			this.sens=2;
		}
	
		test (mem_x, mem_y,terrain);
	}
	
	public void draw2 (Terrain terrain)
	{	
		StdDraw.setPenColor(this.color);
		StdDraw.filledRectangle (this.x,this.y,terrain.getwidth()/2, terrain.getheigth()/2);
	}
	
	public void draw ()
	{	
		if (this.sens==1)
		{	
			StdDraw.picture(this.x, this.y, "bomberman1.png", 40, 40);
		}
		else if (this.sens==2)
		{	
			StdDraw.picture(this.x, this.y, "bomberman3.png", 40, 40);
		}
		else if (this.sens==3)
		{	
			StdDraw.picture(this.x, this.y, "bomberman4.png", 40, 40);
		}
		else if (this.sens==4)
		{	
			StdDraw.picture(this.x, this.y, "bomberman2.png", 40, 40);
		}
	}
	
}
