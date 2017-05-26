import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.*;

public class Bomberman {

	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 650);
		Plateau plateau = new Plateau(21, 17);

		Heros hero1 = new Heros(1);
		Heros hero2 = new Heros(2);
		
		while(true)
		{
			plateau.displayTableau();
			plateau.draw();
			
			if(StdDraw.isKeyPressed(KeyEvent.VK_D))
			{
				hero1.setPositionX(hero1.getPositionX()+1);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_S))
			{
				hero1.setPositionY(hero1.getPositionY()-1);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_Q))
			{
				hero1.setPositionX(hero1.getPositionX()-1);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_Z))
			{
				hero1.setPositionY(hero1.getPositionY()+1);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
			{
				hero2.setPositionX(hero2.getPositionX()+1);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
			{
				hero2.setPositionY(hero2.getPositionY()-1);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
			{
				hero2.setPositionX(hero2.getPositionX()-1);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_UP))
			{
				hero2.setPositionY(hero2.getPositionY()+1);
			}
			
			
			hero1.drawHeros();
			hero2.drawHeros();
			hero1.dropBomb(plateau);

			StdDraw.show();
			StdDraw.pause(20);
		}
	}
	
}
