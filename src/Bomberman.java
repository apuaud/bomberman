import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.*;

public class Bomberman {
	
	public static Plateau plateau;
	public static Heros hero1;
	public static Heros hero2;
	
	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 650);
		plateau = new Plateau(21, 17);

		hero1 = new Heros(1);
		hero2 = new Heros(2);
		
		while(true && hero1.getIsDead() == false && hero2.getIsDead() == false)
		{
			StdDraw.enableDoubleBuffering();
			plateau.displayTableau();
			plateau.draw();

			hero1.drawHeros();
			hero2.drawHeros();
			if(StdDraw.isKeyPressed(KeyEvent.VK_D))
			{
				if(checkFranchissable(hero1.getPositionX()+1, hero1.getPositionY()))
				{
					hero1.setPositionX(hero1.getPositionX()+1);
				}
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_S))
			{
				if(checkFranchissable(hero1.getPositionX(), hero1.getPositionY()-1))
				{
					hero1.setPositionY(hero1.getPositionY()-1);
				}
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_Q))
			{
				if(checkFranchissable(hero1.getPositionX()-1, hero1.getPositionY()))
				{
					hero1.setPositionX(hero1.getPositionX()-1);
				}
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_Z))
			{
				if(checkFranchissable(hero1.getPositionX(), hero1.getPositionY()+1))
				{
					hero1.setPositionY(hero1.getPositionY()+1);
				}
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
			{
				System.out.println(hero2.getPositionX());
				System.out.println(hero2.getPositionY());
				if(checkFranchissable(hero2.getPositionX()+1, hero2.getPositionY()))
				{
					hero2.setPositionX(hero2.getPositionX()+1);
				}
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
			{
				if(checkFranchissable(hero2.getPositionX(), hero2.getPositionY()-1))
				{
					hero2.setPositionY(hero2.getPositionY()-1);
				}
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
			{
				if(checkFranchissable(hero2.getPositionX()-1, hero2.getPositionY()))
				{
					hero2.setPositionX(hero2.getPositionX()-1);
				}
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_UP))
			{
				if(checkFranchissable(hero2.getPositionX(), hero2.getPositionY()+1))
				{
					hero2.setPositionY(hero2.getPositionY()+1);
				}
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_F))
			{
				hero1.dropBomb(plateau);
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_M))
			{
				hero2.dropBomb(plateau);
			}
			
			StdDraw.show();
			StdDraw.pause(20);
		}
		
		if (hero1.getIsDead()==true)
		{
			System.out.println("Le Hero 1 est mort");
		}
		else
		{
			System.out.println("Le Hero 2 est mort");
		}
	}
	
	public static boolean checkFranchissable(int positionX, int positionY)
	{
		if(plateau.getContenu()[positionY][positionX].getType()==2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
