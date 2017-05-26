import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.*;

public class Plateau {
	
	private static Brique [][] contenu;
	private int longeur;
	private int hauteur;
	
	public Plateau(int longueur, int hauteur)
	{
		this.longeur = longueur;
		this.hauteur = hauteur;
		this.contenu = new Brique[hauteur][longueur];
		
		int startGroundX = 4;
		int startGroundY = 4;
		Brique brique;
		StdDraw.setXscale(0,25+20*25);
		StdDraw.setYscale(0,25+16*25);
		for(int i=0;i<hauteur;i++)
		{
			for(int j=0;j<longueur;j++)
			{

				if(i==0 || j==0 || i==hauteur-1 || j==longueur-1)
				{
					brique = new Brique(0);
					StdDraw.setPenColor(StdDraw.GRAY);
				}
				else if(i%2==0 && j%2==0)
				{
					brique = new Brique(0);
					StdDraw.setPenColor(StdDraw.GRAY);
				}
				else if(setPlayerStartGround(i, j, startGroundX, startGroundY, contenu.length, contenu[0].length))
				{
					brique = new Brique(2);
					StdDraw.setPenColor(StdDraw.GREEN);
				}
				else
				{
					brique = new Brique(1);
					StdDraw.setPenColor(StdDraw.ORANGE);
				}
				contenu[i][j] = brique;
				StdDraw.filledRectangle(25+(j*25), 25+(i*25), 25, 25);
			}
		}
	}
	
	public int getLongeur() {
		return longeur;
	}

	public void setLongeur(int longeur) {
		this.longeur = longeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public Brique[][] getContenu()
	{
		return this.contenu;
	}
	
	public static boolean setPlayerStartGround(int i, int j, int startGroundX, int startGroundY, int length, int length2)
	{
		if((i <= startGroundX && j <= startGroundY && (i!=startGroundX && j!=startGroundY)) || (i >= length-startGroundX-1 && j >= length2-startGroundY-1 && (i!=length-startGroundX-1 && j!=length2-startGroundY-1)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void displayTableau()
	{
		for (int j = contenu[0].length-1 ; j >=0  ; j--)
		{
			for(int i = 0 ; i<contenu.length ; i++)
			{
				System.out.print(contenu[i][j].getType() + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public void draw()
	{
		for (int j = 0 ; j < this.hauteur ; j++)
		{
			for (int i = 0 ; i < this.longeur ; i ++)
			{
				Brique briqueParcourue = this.contenu[j][i];
				
				if(briqueParcourue.getType()==0)
				{
					StdDraw.setPenColor(StdDraw.GRAY);
				}
				else if (briqueParcourue.getType()==1)
				{
					StdDraw.setPenColor(StdDraw.ORANGE);
				}
				else
				{
					StdDraw.setPenColor(StdDraw.GREEN);
				}
				StdDraw.filledRectangle(25+(i*25), 25+(j*25), 25, 25);
			}
		}
	}
}
