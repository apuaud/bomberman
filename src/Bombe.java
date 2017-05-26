import edu.princeton.cs.introcs.StdDraw;
import java.util.Timer;
import java.util.TimerTask;

public class Bombe
{
	private int positionX;
	private int positionY;
	private Timer timer;
	
	public Bombe(Plateau plateau, Heros hero)
	{
		this.positionX = hero.getPositionX();
		this.positionY = hero.getPositionY();
		this.timer = new Timer();
		
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
            	bombExplose(plateau);
            }
        }, 0, 4000);
	}
	
	public void drawBomb()
	{
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledCircle(12.5+25*positionX, 12.5+25*positionY, 5);
	}
	
	public TimerTask bombExplose(Plateau plateau)
	{
		System.out.println("Bomb explosed");
		Brique[][] briques = plateau.getContenu();
		Brique briqueParcourue;
		boolean stop = false;
		int positionBombX = this.positionX;
		int positionBombY = this.positionY;
		
		// Detruire à droite
		for(int d = 1 ; d <= 3 && !stop; d++)
		{
			if((positionBombX + d) <= plateau.getLongeur()-1)
			{
				briqueParcourue = briques[positionBombX + d][positionBombY];
				if(briqueParcourue.isDestructible())
				{
					briqueParcourue.setType(2);
					stop=true;
				}
			}
		}
		stop=false;
		
		// Detruire à gauche
		for(int g = -1 ; g >= -3 && !stop; g--)
		{
			if((positionBombX + g) >= 0)
			{
				briqueParcourue = briques[positionBombX + g][positionBombY];
				if(briqueParcourue.isDestructible())
				{
					briqueParcourue.setType(2);
					stop=true;
				}
			}
		}
		stop=false;
		
		// Detruire en haut
		for(int h = 1 ; h <= 3 && !stop; h++)
		{
			if((positionBombY + h) <= plateau.getHauteur()-1)
			{
				briqueParcourue = briques[positionBombX][positionBombY + h];
				if(briqueParcourue.isDestructible())
				{
					briqueParcourue.setType(2);
					stop=true;
				}
			}
		}
		stop=false;
		
		// Detruire en bas
		for(int h = -1 ; h >= -3 && !stop; h--)
		{
			if((positionBombY + h) >=0)
			{
				briqueParcourue = briques[positionBombX][positionBombY + h];
				if(briqueParcourue.isDestructible())
				{
					briqueParcourue.setType(2);
					stop=true;
				}
			}
		}
		return null;
	}
}
