package bbman;

import java.awt.event.KeyEvent;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;

public class main 
{	
	public static void main(String[] args) 
	{	int nb_col=27;
		int nb_line=17;
		int size_x=20;
		int size_y=20;
		int nb_joueur=2;
		
		int i=0;
		int j=0;
		
		int rec=0;
		

		
		StdDraw.setCanvasSize (size_x*nb_col*2,size_y*nb_line*2);
	
		StdDraw.setXscale(0, size_x*nb_col*2);
		StdDraw.setYscale(0, size_y*nb_line*2);
		
		StdDraw.show(0);

		Terrain terrain=new Terrain(nb_col,nb_line,size_x,size_y);
		Joueur [] joueur=new Joueur [nb_joueur] ;
	
		for (i=0;i<nb_joueur;i++)
		{	Color color =new Color (255,50,50);
			joueur [i]=new Joueur(terrain,color);
			joueur [i].init(i+1,nb_col,nb_line,size_x,size_y);
		}
		
		terrain.draw_all (joueur, nb_joueur);

		while (noPlayerIsDead(joueur))
		{	rec=0;
			i=0;
			j=0;
		
			rec=move2 (joueur, nb_joueur,terrain);
			
			for (i=0; i<nb_joueur; i++)
			{	terrain=joueur[i].bon_deg (terrain);
				for (j=0; j<joueur[i].getnbbombe();j++)
					terrain=joueur[i].bombe[j].gestion(terrain);
			}
		
			terrain.draw_all (joueur, nb_joueur);
			
			for (i=0;i<2;i++)
			{	int compte = 0;
				for (j=0;j<joueur[i].getnbbomb();j++)
				{
					if (joueur[i].getbomb(j).getactivate()==0)
					{
						compte=compte+1;
					}
				}
				if(i == 0)
				{
					StdDraw.textRight(18*25, 25, "Joueur " + (i+1) + " : " + 
					joueur[i].getlife() + " vie(s) restante(s), " + compte + " bombe(s) restante(s)");
				}
				else if (i == 1)
				{
					StdDraw.textRight(42*25, 25, "Joueur " + (i+1) + " : " + 
					joueur[i].getlife() + " vie(s) restante(s), " + compte + " bombes restante(s)");
				}
			}
			
			clear();
			debug(joueur, nb_joueur);
			
			StdDraw.show();
			sleep (5);
			
		}
		
		displayGameOver(joueur, terrain);
	}
	public static void clear ()
	{	
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
	
	public static void sleep (int mili)
	{	long time=java.lang.System.currentTimeMillis() ;
	
		while (java.lang.System.currentTimeMillis()-time<mili);
	}
	
	public static void debug (Joueur [] joueur, int nb)
	{
		int i;
		int j;
		int nbj;
		
		int compte;
		
		for (i=0;i<nb;i++)
		{	compte=0;
			for (j=0;j<joueur[i].getnbbomb();j++)
			{	if (joueur[i].getbomb(j).getactivate()==0)
					compte=compte+1;
			}
			nbj=i+1;
			System.out.print("	JOUEUR "+nbj+":\nLIFE :"+joueur[i].getlife()+"\nBOMB: "+compte+"\n\n\n");
		}
	}

	public static int move2 (Joueur [] joueur, int nb_joueur, Terrain terrain)
	{	int ret=0;
	
		if (StdDraw.isKeyPressed(KeyEvent.VK_Z))
		{	joueur[0].move(1,terrain);
			ret=1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_Q))
		{	joueur[0].move(2,terrain);
			ret=1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_S))
		{	joueur[0].move(3,terrain);
			ret=1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_D))
		{	joueur[0].move(4,terrain);
			ret=1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_A))
		{	terrain=joueur[0].put_bombe(terrain);
			ret=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
		{	joueur[1].move(1,terrain);
			ret=1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
		{	joueur[1].move(2,terrain);
			ret=1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
		{	joueur[1].move(3,terrain);
			ret=1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
		{	joueur[1].move(4,terrain);
			ret=1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_ALT))
		{	terrain=joueur[1].put_bombe(terrain);
			ret=1;
		}
		
		return ret;
	}

	
	public static boolean noPlayerIsDead(Joueur[] joueur)
	{
		for (int i = 0 ; i < joueur.length ; i++)
		{
			if(joueur[i].getlife()<=0)
			{
				return false;
			}
		}
		return true;
	}
	
	public static void displayGameOver(Joueur[] joueur, Terrain terrain)
	{
		String joueurGagnant;
		if(joueur[0].getlife()<=0)
		{
			joueurGagnant = "Joueur 2" ;
		}
		else
		{
			joueurGagnant="Joueur 1";

		}
		
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledRectangle(terrain.getwidth()*2*14, terrain.getheigth()*2*8, 100, 50);
		StdDraw.setPenColor(Color.RED);
		StdDraw.text(terrain.getwidth()*2*14, terrain.getheigth()*2*8, "Le " + joueurGagnant + " a gagné !");
		
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledRectangle(terrain.getwidth()*2*14, terrain.getheigth()*2*5, 100, 50);
		StdDraw.setPenColor(Color.RED);
		StdDraw.text(terrain.getwidth()*2*14, terrain.getheigth()*2*5, "Recommencer");
		
		StdDraw.show();
		
		while(true)
		{
			if(StdDraw.mousePressed())
			{
				if(StdDraw.mouseX()>terrain.getwidth()*2*14-100
				&& StdDraw.mouseX()<terrain.getwidth()*2*14+100
				&& StdDraw.mouseY()>terrain.getheigth()*2*5-50
				&& StdDraw.mouseY()<terrain.getheigth()*2*5+50)
				{
					System.out.println("Clicked");
					main(null);
				}
			}
		}
		

	}
}