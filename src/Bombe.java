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
		drawBomb();
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
				briqueParcourue = briques[positionBombY][positionBombX + d];
				if(briqueParcourue.getType()==0)
				{
					checkIfPlayerHere(positionBombY,positionBombX + d);
					stop=true;
				}
				else if(briqueParcourue.isDestructible())
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
				briqueParcourue = briques[positionBombY][positionBombX + g];
				if(briqueParcourue.getType()==0)
				{
					checkIfPlayerHere(positionBombY,positionBombX + g);
					stop=true;
				}
				else if(briqueParcourue.isDestructible())
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
				checkIfPlayerHere(positionBombY + h, positionBombX);
				briqueParcourue = briques[positionBombY + h][positionBombX];

				if(briqueParcourue.getType()==0)
				{
					stop=true;
				}
				else if(briqueParcourue.isDestructible())
				{
					briqueParcourue.setType(2);
					stop=true;
				}
			}
		}
		stop=false;
		
		// Detruire en bas
		for(int b = -1 ; b >= -3 && !stop; b--)
		{
			if((positionBombY + b) >=0)
			{
				briqueParcourue = briques[positionBombY + b][positionBombX];
				if(briqueParcourue.getType()==0)
				{
					checkIfPlayerHere(positionBombY + b, positionBombX);
					stop=true;
				}
				else if(briqueParcourue.isDestructible())
				{
					briqueParcourue.setType(2);
					stop=true;
				}
			}
		}
		return null;
	}
	
	public void checkIfPlayerHere(int positionYExplosion, int positionXExplosion)
	{
		Heros hero1 = Bomberman.hero1;
		Heros hero2 = Bomberman.hero2;
		int hero1PositionX = Bomberman.hero1.getPositionX();
		int hero1PositionY = Bomberman.hero1.getPositionY();
		int hero2PositionX = Bomberman.hero2.getPositionX();
		int hero2PositionY = Bomberman.hero2.getPositionY();
		
		if(hero1PositionX == positionXExplosion  && hero1PositionY == positionYExplosion)
		{
			hero1.setIsDead(true);
		}
		else if (hero2PositionX == positionXExplosion  && hero2PositionY == positionYExplosion)
		{
			hero2.setIsDead(true);
		}
	}
}
