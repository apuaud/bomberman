package bbman;

import edu.princeton.cs.introcs.StdDraw;

public class Bombe 
{	
	private int x; // Coordonnée en x de la bombe
	private int y; // Coordonnée en y de la bombe
	private boolean bombePosee;  // Une bombe est-elle posée ?
	private boolean bombeExplose; // La bombe est-elle en train d'exploser ?
	private int dureeAvantExplosion; // Durée en ms avant explosion de la bombe
	private int dureeExplosion; // Durée en ms de l'explosion
	private int portee; // Portée de l'explosion
	private int nbCasesDetruitesDroite; // Nombre de cases détruites vers la droite avant de rencontrer un bloc indestructible
	private int nbCasesDetruitesGauche; // Nombre de cases détruites vers la gauche avant de rencontrer un bloc indestructible
	private int nbCasesDetruitesHaut; // Nombre de cases détruites vers le haut avant de rencontrer un bloc indestructible
	private int nbCasesDetruitesBas; // Nombre de cases détruites vers le bas avant de rencontrer un bloc indestructible
	private long chrono; // Temps écoulé depuis que la bombe a été posée
	private boolean rouge; // Le joueur a-t-il le bonus des bombes rouges ?
	
	public Bombe()
	{	
		this.bombePosee = false;
		this.bombeExplose = false;
		this.x = 0;
		this.y = 0;
		this.chrono = 0;
		this.dureeAvantExplosion = 4000;
		this.dureeExplosion = 1000;
		this.portee = 3;
		this.rouge = false;
	}

	// Getters et setters
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public boolean getBombePosee()
	{	
		return this.bombePosee;
	}

	public int getPortee()
	{
		return this.portee;
	}

	public void setPortee(int portee)
	{
		this.portee=portee;
	}

	public boolean getRouge()
	{
		return this.rouge;
	}

	public void setRouge(boolean rouge)
	{
		this.rouge = rouge;
	}

	public int getDureeAvantExplosion()
	{
		return this.dureeAvantExplosion;
	}

	public void setDureeAvantExplosion(int duree)
	{
		this.dureeAvantExplosion = duree;
	}

	public int getDureeExplosion()
	{
		return this.dureeExplosion;
	}

	// Aspect visuel de la bombe
	
	public void dessinerBombe(Terrain terrain)
	{	if ((this.bombePosee==true) && (this.bombeExplose==false)) 
		{	
			// Calcul des coordonnées du centre de la bombe
			int x = terrain.getLongueur()*((this.x*2)+1);
			int y = terrain.getHauteur()*((this.y*2)+1);

			if(this.getRouge()==false)
			{
				StdDraw.picture(x,y,"vert.png",40,40);
				StdDraw.picture(x,y, "bombe.png", 40, 40);
			}
			else
			{
				StdDraw.picture(x,y,"vert.png",40,40);
				StdDraw.picture(x,y, "bomberouge.png", 40, 40);
			}
		}
	}
	
	public Terrain poserBombe (int dureeAvantExplosion, int dureeExplosion, int portee, int x, int y, Terrain terrain)
	{	
		// Lorsqu'on pose une bombe on attribue à la case le code 99
		// On vérifie donc que la case en question n'a pas déjà le code 99 (une bombe est déjà posée dessus)
		if (terrain.getPlateau(x, y)!=99)
		{	
			this.dureeAvantExplosion = dureeAvantExplosion;
			this.dureeExplosion = dureeExplosion;
			this.portee = portee;
			this.nbCasesDetruitesDroite = 0;
			this.nbCasesDetruitesGauche = 0;
			this.nbCasesDetruitesHaut = 0;
			this.nbCasesDetruitesBas = 0;
			this.bombePosee = true;
			this.bombeExplose = false;
			this.x = x; 
			this.y = y;
			this.chrono = java.lang.System.currentTimeMillis();
			terrain.setPlateau(x,y,99);
		}
		
		return terrain;
	}
	
	public Terrain gestion(Terrain terrain, Joueur[] joueur)
	{	
		if ((this.bombePosee) && (!this.bombeExplose))
		{	if (java.lang.System.currentTimeMillis()-this.chrono>this.dureeAvantExplosion)
			{ 
				terrain=this.boum(terrain,joueur,true);
			}
		}
		
		if ((this.bombePosee) && (this.bombeExplose))
		{	
			if (java.lang.System.currentTimeMillis()-this.chrono>this.dureeExplosion)
			{ 
				terrain=this.caseApresExplosion(terrain);
			}
			
		}
		
		return terrain;
	}
	
	// On fait en sorte qu'un bonus apparaisse 20% du temps après une explosion
	
	public int apparitionBonus ()
	{
		int conditionArret = (int)(Math.random()*100);
		int type_bonus = 0;
		if (conditionArret<=100)
		{
			int newTest=(int)(Math.random()*12);
			switch(newTest)
			{
				case 0:
					type_bonus = 10;
					break;
					
				case 1:
					type_bonus = 11;
					break;
					
				case 2:
					type_bonus = 12;
					break;
				
				case 3 :
					type_bonus = 13;
					break;
					
				case 4:
					type_bonus = 14;
					break;
				
				case 5:
					type_bonus = 15;
					break;
				
				case 6:
					type_bonus = 16;
					break;
				
				case 7:
					type_bonus = 17;
					break;
				
				case 8:
					type_bonus = 18;
					break;
					
				case 9:
					type_bonus = 19;
					break;
					
				case 10:
					type_bonus = 20;
					break;
					
				case 11:
					type_bonus = 21;
					break;
			}
		}
		return type_bonus;
	}

	
// Lorsque la bombe explose 
	
	public Terrain boum(Terrain terrain, Joueur[] joueur, boolean bombeDeBase)
	{	
		boolean conditionArret = false;
		int i=1;

		Audio sonbombe = new Audio("bombe.wav");
		sonbombe.play();
		
		// Le code 100 correspond à une case où une bombe qui vient d'exploser
		terrain.setPlateau(this.x, this.y, 100);
		
		// Vers la droite

		while (!conditionArret)
		{	
			if(terrain.getPlateau(this.x+i,this.y)==1) // Si le bloc est indestructible
			{
				conditionArret=true;
			}
			
			else if(terrain.getPlateau(this.x+i,this.y)==2) // Si le bloc était orange
			{	
				if(!this.getRouge()) 
				{
					terrain.setPlateau(this.x+i,this.y,101); // On lui attribue une valeur spéciale pour qu'il soit éligible à un bonus (la valeur 101)
					conditionArret=true;
				}
				else
				{
					terrain.setPlateau(this.x+i,this.y,101); 
				}
			}
			
			else if(terrain.getPlateau(this.x+i,this.y)>=10 && terrain.getPlateau(this.x+i,this.y)<=19) // S'il y avait déjà un bonus sur la case
			{
				terrain.setPlateau(this.x+i, this.y, terrain.getPlateau(this.x+i,this.y));
			}
			
			else
			{
				terrain.setPlateau(this.x+i,this.y,100);
			}
			
			if(bombeDeBase) //On vérifie si la bombe en explosion a explosé à cause d'une autre bombe (pour éviter de faire boucler le programme)
			{
				verifBombeDansPortee(this.x+i,this.y,joueur,terrain);
			}
			
			// Si on a traité toutes les cases dans la portée de la bombe, on sort du while
			if (i==this.portee || this.x+i>=terrain.getLongueur()-1 || this.y>terrain.getHauteur()-1 || this.x+i<=0 || this.y<=0) 
			{
				conditionArret=true;
			}
		
			if (!conditionArret) // Si elles n'ont pas toutes été traitées, on passe à la case suivante
			{
				i=i+1;
			}
		}
		
		this.nbCasesDetruitesDroite=i;
		i=0;
		conditionArret=false;
		
		// Vers le bas
		
		while (!conditionArret)
		{	
			if(terrain.getPlateau(this.x,this.y-i)==1)
			{
				conditionArret=true;
			}
			
			else if (terrain.getPlateau(this.x,this.y-i)==2)
			{	
				if(this.getRouge() == false)
				{
					terrain.setPlateau(this.x,this.y-i,101);
					conditionArret=true;
				}
				else
				{
					terrain.setPlateau(this.x,this.y-i,101);
				}
			}
			
			else if(terrain.getPlateau(this.x,this.y-i)>=10 && terrain.getPlateau(this.x,this.y-i)<=19)
			{
				terrain.setPlateau(this.x, this.y-i, terrain.getPlateau(this.x,this.y-i));
			}
			
			else
			{
				terrain.setPlateau(this.x,this.y-i,100);
			}
		
			if (i==this.portee || this.x>20 || this.y-i>=16 || this.x<=0 || this.y-i<=0)
			{
				conditionArret=true;
			}
		
			if (!conditionArret)
			{
				i=i+1;
			}
			
			if(bombeDeBase)
			{
			verifBombeDansPortee(this.x,this.y-i,joueur,terrain);
			}
			
		}
		this.nbCasesDetruitesBas=i;
		i=0;
		conditionArret=false;
		
		// Vers la gauche
		
		while (!conditionArret)
		{	
			if(terrain.getPlateau(this.x-i,this.y)==1)
			{
				conditionArret=true;
			}
			
			else if (terrain.getPlateau(this.x-i,this.y)==2)
			{	
				if(this.getRouge() == false)
				{
					terrain.setPlateau(this.x-i,this.y,101);
					conditionArret=true;
				}
				
				else
				{
					terrain.setPlateau(this.x-i,this.y,101);
				}

			}
			
			else if(terrain.getPlateau(this.x-i,this.y)>=10 && terrain.getPlateau(this.x-i,this.y)<=19)
			{
				terrain.setPlateau(this.x-i, this.y, terrain.getPlateau(this.x-i,this.y));
			}
			
			else
			{
				terrain.setPlateau(this.x-i,this.y,100);
			}
			
			if (i==this.portee || this.x-i>=20 || this.y>16 || this.x-i<=0 || this.y<=0)
			{
				conditionArret=true;
			}
		
			if (!conditionArret)
			{
				i=i+1;
			}
			
			if(bombeDeBase)
			{
				verifBombeDansPortee(this.x-i,this.y,joueur,terrain);
			}
			
		}
		
		this.nbCasesDetruitesGauche=i;
		i=0;
		conditionArret=false;
		
		// Vers le haut
		while (!conditionArret)
		{	
			if(terrain.getPlateau(this.x,this.y+i)==1)
			{
				conditionArret=true;
			}
			
			else if (terrain.getPlateau(this.x,this.y+i)==2)
			{	
				if(this.getRouge() == false)
				{
					terrain.setPlateau(this.x,this.y+i,101);
					conditionArret=true;
				}
				
				else
				{
					terrain.setPlateau(this.x,this.y+i,101);
				}
			}
			
			else if(terrain.getPlateau(this.x,this.y+i)>=10 && terrain.getPlateau(this.x,this.y+i)<=19)
			{
				terrain.setPlateau(this.x, this.y+i, terrain.getPlateau(this.x,this.y+i));
			}
			
			else
			{
				terrain.setPlateau(this.x,this.y+i,100);
			}
			
			if (i==this.portee || this.x>20 || this.y+i>=16 || this.x<=0 || this.y+i<=0)
			{
				conditionArret=true;
			}
		
			if (!conditionArret)
			{
				i=i+1;
			}
			
			if(bombeDeBase)
			{
				verifBombeDansPortee(this.x,this.y+i,joueur,terrain);
			}
		}
		
		this.nbCasesDetruitesHaut=i;
		i=0;
		conditionArret=false;
		
		this.chrono=java.lang.System.currentTimeMillis() ;
		this.bombeExplose=true;
		
		return terrain;
	}

	// On vérifie si une autre bombe n'est pas dans la portée de la bombe qui vient d'exploser (si oui, elle explose aussi)
	
	public void verifBombeDansPortee(int x, int y, Joueur[] joueur, Terrain terrain)
	{
		for(int i=0; i<joueur.length; i++)
		{
			for(int j=0; j<joueur[i].getBombe().length; j++)
			{
				if(joueur[i].getBombe()[j].getX() == x && joueur[i].getBombe()[j].getY() == y)
				{
					joueur[i].getBombe()[j].boum(terrain,joueur,false);
				}
			}
		}
	}
	
	// On regarde de quel type doivent devenir les cases qui étaient dans la portée de la bombe
	
	public Terrain caseApresExplosion (Terrain terrain)
	{	
		boolean conditionArret = false;
		int i = 1;
		
		terrain.setPlateau(this.x,this.y,0);

		// Vers la droite
		while (i<=this.nbCasesDetruitesDroite)
		{	
			// Si la case était un bloc orange avant l'explosion, un bonus a 20% de chance d'apparaître
			if(terrain.getPlateau(this.x+i,this.y)==101) 
			{
				terrain.setPlateau(this.x+i,this.y,apparitionBonus());
			}
			
			// Sinon, c'est le cas normal, la case devient simplement verte
			else if(terrain.getPlateau(this.x+i,this.y)==100)
			{
				terrain.setPlateau(this.x+i,this.y,0);
			}
			
			// Dans le cas où il s'agit d'un mur indestructible
			else
			{
				conditionArret = true;
			}

			i = i+1;
		}
		
		i = 0;
		conditionArret = false;
		
		// Vers le bas
		
		while (i<=this.nbCasesDetruitesBas)
		{	
			if(terrain.getPlateau(this.x,this.y-i)==101)
			{
				terrain.setPlateau(this.x,this.y-i,apparitionBonus());
			}
				
			
			else if(terrain.getPlateau(this.x,this.y-i)==100)
			{
				terrain.setPlateau(this.x,this.y-i,0);
			}
		
			else
			{
				conditionArret = true;
			}
				
			i=i+1;
		}
		
		i=0;
		conditionArret = false;
		
		// Vers la gauche
		
		while (i<=this.nbCasesDetruitesGauche)
		{	
			if(terrain.getPlateau(this.x-i,this.y)==101)
			{
				terrain.setPlateau(this.x-i,this.y,apparitionBonus());
			}

			else if(terrain.getPlateau(this.x-i,this.y)==100)
			{
				terrain.setPlateau(this.x-i,this.y,0);
			}
			
			else
			{
				conditionArret = true;
			}
				
			i=i+1;
		}
		i=0;
		conditionArret = false;
		
		// Vers le haut
		
		while (i<=this.nbCasesDetruitesHaut)
		{	
			if(terrain.getPlateau(this.x,this.y+i)==101)
			{
				terrain.setPlateau(this.x,this.y+i,apparitionBonus());
			}
	
			else if (terrain.getPlateau(this.x,this.y+i)==100)
			{
				terrain.setPlateau(this.x,this.y+i,0);
			}
			
			else
			{
				conditionArret = true;
			}

			i=i+1;
		}
		
		this.bombePosee=false;
		this.bombeExplose=false;
		
		return terrain;
	}
}