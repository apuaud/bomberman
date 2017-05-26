import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.awt.event.*;

public class Bomberman {

	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 650);
			
		Plateau plateau = new Plateau(21, 17);
		
		plateau.displayTableau();

		Heros hero1 = new Heros();
		
	}
	
}

