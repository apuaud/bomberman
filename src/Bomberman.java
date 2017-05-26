import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.*;

public class Bomberman {

	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 650);
			
		Brique[][] plateau = createPlayerGround(21, 17);
		
		displayTableau(plateau);

		Heros hero1 = new Heros();
		
	}

	private static Brique[][] createPlayerGround(int largeur, int longueur) {
		
		Brique[][] plateau = new Brique[largeur][longueur];
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
				else if(setPlayerStartGround(i, j, startGroundX, startGroundY, plateau.length, plateau[0].length))
				{
					brique = new Brique(2);
					StdDraw.setPenColor(StdDraw.GREEN);
				}
				else
				{
					brique = new Brique(1);
					StdDraw.setPenColor(StdDraw.ORANGE);
				}
				plateau[i][j] = brique;
				StdDraw.filledRectangle(25+(j*25), 25+(i*25), 25, 25);
			}
		}
		StdDraw.show(500);
		return plateau;
	}
	
	private static boolean setPlayerStartGround(int i, int j, int startGroundX, int startGroundY, int length, int length2)
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
	
	private static void displayTableau(Brique[][] tableau)
	{
		for (int j = tableau[0].length-1 ; j >=0  ; j--)
		{
			for(int i = 0 ; i<tableau.length ; i++)
			{
				System.out.print(tableau[i][j].getType() + " ");
			}
			System.out.println("");
		}
	}
}
