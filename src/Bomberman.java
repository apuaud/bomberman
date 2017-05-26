import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.*;

public class Bomberman {

	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 650);
		Heros hero1 = new Heros(1);
		Heros hero2 = new Heros(2);
		
		Plateau plateau = new Plateau(21, 17);
		plateau.displayTableau();
		while(true)
		{
			hero1.drawHeros();
			hero2.drawHeros();
			StdDraw.show(500);
		}
		
		
	}
	
}
