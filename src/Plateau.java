import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.*;

public class Plateau {
	
	private static Brique [][] contenu;
	
	public Plateau(int largeur, int longueur)
	{

		this.contenu = new Brique[largeur][longueur];
		int startGroundX = 4;
		int startGroundY = 4;
		Brique brique;
		StdDraw.setXscale(0,25+16*25);
		StdDraw.setYscale(0,25+20*25);
		for(int i=0;i<largeur;i++)
		{
			for(int j=0;j<longueur;j++)
			{

				if(i==0 || j==0 || i==largeur-1 || j==longueur-1)
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
		StdDraw.show(500);
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
	}
}
