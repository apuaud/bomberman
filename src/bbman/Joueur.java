package bbman;

import java.awt.event.KeyEvent;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;

public class Joueur 
{	
	private int x; // Coordonnée en x du joueur
	private int y; // Coordonnée en y du joueur
	private int direction; // Sens du joueur (gauche, droite, haut, bas)
	private int viesRestante; // Nombre de vies restantes du joueur
	private int vitesseDeplacement; // Vitesse de déplacement du joueur
	private int nbBombes; // Nombre de bombes à disposition du joueur
	private int dureeInvincibilite; // Durée pendant laquelle le joueur ne peut pas perdre de vie car il a été touché par une bombe précédemment
	private boolean invincible; // Le joueur est-il pendant sa période d'invincibilité ?
	private int approxPositionX; // Comme les déplacements sont fluides et non case par case, il faut déterminer une valeur d'approximation en X pour savoir sur quelle case est le joueur (s'il n'est pas parfaitement au centre d'une case)
	private int approxPositionY; // Comme les déplacements sont fluides et non case par case, il faut déterminer une valeur d'approximation en Y pour savoir sur quelle case est le joueur (s'il n'est pas parfaitement au centre d'une case)
	private long chrono; // Chrono utilisé pour calculer la durée d'invincibilité
	private boolean passeMuraille; // Le joueur a-t-il le bonus passe muraille activé ?
	private int bouclier; // Le joueur a-t-il le bonus bouclier activé ?
	private boolean bomberouge; // Le joueur a-t-il le bonus bombe rouge activé ?
	private boolean flammeverte; // Le joueur a-t-il le bonus flamme verte activé ?
	
	Bombe [] bombe=new Bombe [10];

	public Joueur (Terrain terrain)
	{	
		this.viesRestante=3;
		this.vitesseDeplacement=2;
		this.nbBombes=3;
		this.chrono=0;
		this.invincible=false;
		this.dureeInvincibilite=2000;
		this.direction=1;
		this.approxPositionX=3*terrain.getLongueur()/4;
		this.approxPositionY=3*terrain.getLongueur()/4;
		this.passeMuraille = false;
		this.bouclier = 0;
		this.bomberouge = false;
		this.flammeverte = false;
		
		int i=0;
		
		for (i=0; i<10; i++)
		{	
			bombe[i]=new Bombe();
		}
		
	}
	
	// Getters et setters
	
	public int get_x ()
	{	
		return this.x;
	}
	
	public int get_y ()
	{	
		return this.y;
	}
	
	public void setViesRestante(int nbViesRestante)
	{
		this.viesRestante = nbViesRestante;
	}
	
	public int getViesRestante()
	{
		return this.viesRestante;
	}
	
	public int getNbBombes ()
	{	
		return this.nbBombes;
	}
	
	public void setNbBombes(int nbBombes)
	{
		this.nbBombes=nbBombes;
	}
	
	public Bombe getbomb (int num)
	{	
		return this.bombe[num];
	}
	
	public void setVitesseDeplacement(int vitesseDeplacement)
	{
		this.vitesseDeplacement = vitesseDeplacement;
	}
	
	public int getVitesseDeplacement()
	{
		return this.vitesseDeplacement;
	}

	public Bombe[] getBombe()
	{
		return this.bombe;
	}
	
	public int getBouclier()
	{
		return this.bouclier;
	}
	
	public boolean getBombeRouge()
	{
		return this.bomberouge;
	}
	
	public boolean getFlammeVerte()
	{
		return this.flammeverte;
	}
	
	public boolean getPasseMuraille()
	{
		return this.passeMuraille;
	}
	
	// Position initiale des deux joueurs au début de la partie
	
	public void positionInitiale(int idJoueur, int x, int y, int longueur, int hauteur)
	{	
		if (idJoueur==1)
		{	this.x=3*longueur;
			this.y=3*hauteur;
		}
		
		if (idJoueur==2)
		{	this.x=(x*2*longueur)-(3*longueur);
			this.y=(y*2*hauteur)-(3*hauteur);
		}
	}
	
	// Quand un joueur pose une bombe
		
	public Terrain poserBombe(Terrain terrain)
	{
		int i=0;
		boolean conditionArret=false;
		
		// Détermine sur quelle case doit être posée la bombe (conversion valeur en pixels en numéro de case du tableau)
		int coordonneeX=this.x/(terrain.getLongueur()*2);
		int coordonneeY=this.y/(terrain.getHauteur()*2);
		
		while (!conditionArret)
		{	
			// Si le joueur a déjà posé toutes ses bombes on sort de la boucle immédiatement
			if(i==this.nbBombes)
			{	
				conditionArret=true;
			}
			
			// Sinon, on pose la bombes aux coordonnées calculées précédemment
			else
			{	
				if(!this.bombe[i].getBombePosee())
				{	
					terrain=this.bombe[i].poserBombe(this.bombe[i].getDureeAvantExplosion(), this.bombe[i].getDureeExplosion(), bombe[i].getPortee(), coordonneeX, coordonneeY, terrain);
					conditionArret=true;
				}
				else
				{
					i=i+1;
				}
					
			}
		}
		
		return terrain;
	}
	
	// Si un bonus a été récupéré, on applique l'effet associé dans cette fonction
	
	public void effetBonus(int type_bonus)
	{
		switch(type_bonus)
		{
			case 10: // Flamme bleue
				for (int i=0; i<10; i++)
				{
					if(bombe[i].getPortee() >=1)
					{
						bombe[i].setPortee(bombe[i].getPortee()-1);	
					}
				}
				break;
				
			case 11: // Flamme jaune
				for (int i=0; i<10; i++)
				{
					if(bombe[i].getPortee() < 10)
					{
						bombe[i].setPortee(bombe[i].getPortee()+1);	
					}
				}
				break;
				
			case 12: // Flamme rouge
				for (int i=0; i<10; i++)
				{
					bombe[i].setPortee(10);	
				}
				break;
				
			case 13: // Bombe rouge
				for (int i=0; i<10; i++)
				{
					bombe[i].setRouge(true);
				}
				this.bomberouge = true;
				break;
				
			case 14: // +1 vie
				this.setViesRestante(this.getViesRestante()+1);
				break;
				
			case 15: // Ralentir le joueur
				if(this.getVitesseDeplacement()>=1)
				{
					this.setVitesseDeplacement(this.getVitesseDeplacement()-1);
				}
				break;
				
			case 16: // Accélérer le joueur
				this.setVitesseDeplacement(this.getVitesseDeplacement()+1);
				break;
				
			case 17: // +2 bombes
				if(this.getNbBombes()<=5)
				{
					this.setNbBombes(this.getNbBombes()+2);
				}
				break;
				
			case 18: // -2 bombes
				if(this.getNbBombes()>3)
				{
					this.setNbBombes(this.getNbBombes()-2);
				}
				else
				{
					this.setNbBombes(2);
				}
				break;
				
			case 19: // Flamme verte
				for (int i=0; i<10; i++)
				{
					if(bombe[i].getDureeAvantExplosion() > 3000)
					{
						bombe[i].setDureeAvantExplosion(bombe[i].getDureeAvantExplosion()-1000);	
					}
					if(bombe[i].getPortee() < 10)
					{
						bombe[i].setPortee(bombe[i].getPortee()+1);
					}
				}
				this.flammeverte = true;
				break;
				
			case 20:
				this.passeMuraille = true;
				break;
				
			case 21:
				this.bouclier = 1;
				break;
		}
		
	}
	
	// Si un joueur se fait toucher par une bombe, on regarde s'il était invicible
	// Si oui, rien ne se passe, sinon, il perd une vie et devient invincible pendant le temps prédéfini
	
	public void perteVie()
	{
		if (this.invincible==false)
		{
			if(this.bouclier==0)
			{
				this.viesRestante=this.viesRestante-1;
		
				if (this.viesRestante<0)
				{
					this.viesRestante=0;
				}
			}
			else if(this.bouclier==1)
			{
				this.bouclier=0;
			}
			
			this.invincible=true;
			this.chrono=java.lang.System.currentTimeMillis();
		}
	}
	
	public Terrain verifEffetCaseEnCours(Terrain terrain)
	{
		// Détermine sur quelle case est situé le joueur (conversion valeur en pixels en numéro de case du tableau)
		int coordonneeX=this.x/(terrain.getLongueur()*2);
		int coordonneeY=this.y/(terrain.getHauteur()*2);
		
		// On teste si le chrono de l'invincibilité est fini
		if (this.invincible)
		{
			if (java.lang.System.currentTimeMillis()-this.chrono>this.dureeInvincibilite)
			{
				this.invincible=false;
			}
		}
		
		// Si le joueur est sur une case qui explose/case enflammée (code case=100), il perd une vie
		if (terrain.getPlateau(coordonneeX, coordonneeY)==100)
		{
			perteVie();
		}
		
		// Si le joueur est sur un bonus, on active l'effet associé et on arrête d'afficher le bonus sur la carte
		if ((terrain.getPlateau(coordonneeX, coordonneeY)>=10)&&(terrain.getPlateau(coordonneeX, coordonneeY)<=21))
		{
			this.effetBonus(terrain.getPlateau(coordonneeX, coordonneeY));
			terrain.setPlateau(coordonneeX, coordonneeY, 0);
		}
		
		// Il faut désormais faire les mêmes tests avec les approximations des 4 côtés
		// Approximation vers la droite
		
		if (this.x+this.approxPositionX>(coordonneeX+1)*2*terrain.getLongueur())
		{
			if (terrain.getPlateau(coordonneeX+1, coordonneeY)>=100)
			{
				perteVie();
			}
			
			if ((terrain.getPlateau(coordonneeX+1, coordonneeY)>=10)&&(terrain.getPlateau(coordonneeX+1, coordonneeY)<=21))
			{
				this.effetBonus(terrain.getPlateau(coordonneeX+1, coordonneeY));
				terrain.setPlateau(coordonneeX+1, coordonneeY, 0);
			}
		}
		
		// Approximation vers la gauche
		if (this.x-this.approxPositionX<(coordonneeX)*2*terrain.getLongueur())
		{
			if (terrain.getPlateau(coordonneeX-1, coordonneeY)>=100)
			{
				perteVie();
			}
			
			if ((terrain.getPlateau(coordonneeX-1, coordonneeY)>=10)&&(terrain.getPlateau(coordonneeX-1, coordonneeY)<=21))
			{
				this.effetBonus(terrain.getPlateau(coordonneeX-1, coordonneeY));
				terrain.setPlateau(coordonneeX-1, coordonneeY, 0);
			}
		}
		
		// Approximation vers le haut
		if (this.y+this.approxPositionY>(coordonneeY+1)*2*terrain.getHauteur())
		{
			if (terrain.getPlateau(coordonneeX, coordonneeY+1)>=100)
			{
				perteVie();
			}
			
			if ((terrain.getPlateau(coordonneeX, coordonneeY+1)>=10)&&(terrain.getPlateau(coordonneeX, coordonneeY+1)<=21))
			{
				this.effetBonus(terrain.getPlateau(coordonneeX, coordonneeY+1));
				terrain.setPlateau(coordonneeX, coordonneeY+1, 0);
			}
		}
		
		// Approximation vers le bas
		if (this.y-this.approxPositionY<(coordonneeY)*2*terrain.getHauteur())
		{	
			if (terrain.getPlateau(coordonneeX, coordonneeY-1)>=100)
			{
				perteVie();
			}
			
			if ((terrain.getPlateau(coordonneeX, coordonneeY-1)>=10)&&(terrain.getPlateau(coordonneeX, coordonneeY-1)<=21))
			{
				this.effetBonus(terrain.getPlateau(coordonneeX, coordonneeY-1));
				terrain.setPlateau(coordonneeX, coordonneeY-1, 0);
			}
		}
		
		return terrain;
	}

	// Permet de déterminer lors des déplacements quelle est la case la plus proche
	// Les déplacements sont fluides et non case par case mais il faut quand même pouvoir déterminer des coordonnées (x,y) dans le tableau de 21x17 qui correspond au plateau
	
	public void determinationCase(int x, int y, Terrain terrain)
	{
		// Détermine sur quelle case est situé le joueur (conversion valeur en pixels en numéro de case du tableau)
		int coordonneeX=this.x/(terrain.getLongueur()*2);
		int coordonneeY=this.y/(terrain.getHauteur()*2);
		
		if (this.x+this.approxPositionX>(coordonneeX+1)*2*terrain.getLongueur())
		{
			if (terrain.test(coordonneeX+1,coordonneeY)==1)
			{
				this.x=x;
			}
			
			if(!this.passeMuraille)
			{
				if (terrain.test(coordonneeX+1,coordonneeY)==2)
				{
					this.x=x-1;
				}
				
				if (terrain.test(coordonneeX+1,coordonneeY)==3)
				{
					this.x=x;
				}
			}

			if ((this.y+this.approxPositionY>(coordonneeY+1)*2*terrain.getHauteur()) && (terrain.test(coordonneeX+1,coordonneeY+1)==1))
			{
				this.y=y;
			}
			
			if ((this.y-this.approxPositionY<(coordonneeY)*2*terrain.getHauteur()) && (terrain.test(coordonneeX+1,coordonneeY-1)==1))
			{	
				this.y=y;
			}		
		
		}
		
		if (this.x-this.approxPositionX<(coordonneeX)*2*terrain.getLongueur())
		{	
			if (terrain.test(coordonneeX-1,coordonneeY)==1)
			{
				this.x=x;
			}
			
			if(!this.passeMuraille)
			{
				if (terrain.test(coordonneeX-1,coordonneeY)==2)
				{
					this.x=x+1;
				}
				
				if (terrain.test(coordonneeX-1,coordonneeY)==3)
				{
					this.x=x;
				}
			}
			
			if ((this.y+this.approxPositionY>(coordonneeY+1)*2*terrain.getHauteur())&&(terrain.test(coordonneeX-1,coordonneeY+1)==1))
			{	
				this.y=y;
			}
			
			if ((this.y-this.approxPositionY<(coordonneeY)*2*terrain.getHauteur())&&(terrain.test(coordonneeX-1,coordonneeY-1)==1))
			{	
				this.y=y;
			}
		}
		
		if (this.y+this.approxPositionY>(coordonneeY+1)*2*terrain.getHauteur())
		{	
			if (terrain.test(coordonneeX,coordonneeY+1)==1)
			{
				this.y=y;
			}
			
			if(!this.passeMuraille)
			{
				if (terrain.test(coordonneeX,coordonneeY+1)==2)
				{
					this.y=y-1;
				}
				
				if (terrain.test(coordonneeX,coordonneeY+1)==3)
				{
					this.y=y;
				}
			}
				
			if ((this.x+this.approxPositionX>(coordonneeX+1)*2*terrain.getLongueur())&&(terrain.test(coordonneeX+1,coordonneeY+1)==1))
			{	
				this.x=x;
			}
			
			if ((this.x-this.approxPositionY<(coordonneeX)*2*terrain.getLongueur())&&(terrain.test(coordonneeX-1,coordonneeY+1)==1))
			{	
				this.x=x;
			}
		}
		
		if (this.y-this.approxPositionY<(coordonneeY)*2*terrain.getHauteur())
		{	
			if (terrain.test(coordonneeX,coordonneeY-1)==1)
			{
				this.y=y;
			}
			
			if(!this.passeMuraille)
			{
				if (terrain.test(coordonneeX,coordonneeY-1)==2)
				{
					this.y=y+1;
				}
				
				if (terrain.test(coordonneeX,coordonneeY-1)==3)
				{
					this.y=y;
				}
			}
			
			if ((this.x+this.approxPositionX>(coordonneeX+1)*2*terrain.getLongueur())&&(terrain.test(coordonneeX+1,coordonneeY-1)==1))
			{	
				this.x=x;
			}
			
			if ((this.x-this.approxPositionY<(coordonneeX)*2*terrain.getLongueur())&&(terrain.test(coordonneeX-1,coordonneeY-1)==1))
			{	
				this.x=x;
			}
		}
	}
	
	public void deplacement(int sens, Terrain terrain)
	{	
		int x=this.x;
		int y=this.y;
		
		if (sens==1)
		{	
			this.y=this.y+this.vitesseDeplacement;
			this.direction=4;
		}
		else if (sens==2)
		{	
			this.x=this.x-this.vitesseDeplacement;
			this.direction=3;
		}
		else if (sens==3)
		{	
			this.y=this.y-this.vitesseDeplacement;
			this.direction=1;
		}
		else if (sens==4)
		{	
			this.x=this.x+this.vitesseDeplacement;
			this.direction=2;
		}
		determinationCase(x, y,terrain);
	}
	
	public void dessiner_joueur()
	{	
		// Vers le bas
		if (this.direction==1)
		{	
			if(this.bouclier==0)
			{
				StdDraw.picture(this.x, this.y, "bomberman1.png", 40, 40);
			}
			else
			{
				StdDraw.picture(this.x, this.y, "bomberman1bouclier.png", 40, 40);
			}
		}
		
		// Vers la droite
		else if (this.direction==2)
		{	
			if(this.bouclier==0)
			{
				StdDraw.picture(this.x, this.y, "bomberman3.png", 40, 40);
			}
			else
			{
				StdDraw.picture(this.x, this.y, "bomberman3bouclier.png", 40, 40);
			}
		}
		
		// Vers la gauche
		else if (this.direction==3)
		{	
			if(this.bouclier==0)
			{
				StdDraw.picture(this.x, this.y, "bomberman4.png", 40, 40);
			}
			else
			{
				StdDraw.picture(this.x, this.y, "bomberman4bouclier.png", 40, 40);
			}
		}
		
		else if (this.direction==4)
		{	
			if(this.bouclier==0)
			{
				StdDraw.picture(this.x, this.y, "bomberman2.png", 40, 40);
			}
			else
			{
				StdDraw.picture(this.x, this.y, "bomberman2bouclier.png", 40, 40);
			}
		}
	}
	
}