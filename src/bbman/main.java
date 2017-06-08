package bbman;

import java.awt.Font;
import java.awt.event.KeyEvent;
import edu.princeton.cs.introcs.StdDraw;

public class main 
{		
	public static void main(String[] args) 
	{	
		int nbColonnes=21;
		int nbLignes=17;
		int longueurCase=20;
		int hauteurCase=20;
		int nbJoueurs=2;
		
		StdDraw.setCanvasSize(longueurCase*nbColonnes*2,hauteurCase*nbLignes*2);
		StdDraw.setXscale(0,longueurCase*nbColonnes*2);
		StdDraw.setYscale(0,hauteurCase*nbLignes*2);
		
		Audio sonjeu = new Audio("jeu.wav");
		sonjeu.play();

		StdDraw.picture(longueurCase*nbColonnes,hauteurCase*nbLignes,"menu.png",longueurCase*nbColonnes*2,hauteurCase*nbLignes*2);
		boolean commencerJeu = false;
		boolean commandes = false;
		
		while(!commencerJeu || commandes)
		{
			StdDraw.enableDoubleBuffering();
			StdDraw.show();
			StdDraw.pause(5);
			if(StdDraw.mousePressed())
			{
				if(StdDraw.mouseX()>278
				&& StdDraw.mouseX()<600
				&& StdDraw.mouseY()>446
				&& StdDraw.mouseY()<521)
				{
					commencerJeu = true;
				}
			}
			
			if(StdDraw.mousePressed())
			{
				if(StdDraw.mouseX()>272
				&& StdDraw.mouseX()<618
				&& StdDraw.mouseY()>328
				&& StdDraw.mouseY()<401)
				{
					commandes = true;
					StdDraw.picture(longueurCase*nbColonnes,hauteurCase*nbLignes,"commandes.png",longueurCase*nbColonnes*2,hauteurCase*nbLignes*2);
				}
			}
			
			if(StdDraw.mousePressed())
			{
				if(StdDraw.mouseX()>761
				&& StdDraw.mouseX()<820
				&& StdDraw.mouseY()>634
				&& StdDraw.mouseY()<656)
				{
					commandes = false;
					StdDraw.picture(longueurCase*nbColonnes,hauteurCase*nbLignes,"menu.png",longueurCase*nbColonnes*2,hauteurCase*nbLignes*2);
				}
			}
			
		}
		// Création du terrain et des joueurs
		
		Terrain terrain = new Terrain(nbColonnes,nbLignes,longueurCase,hauteurCase);
		Joueur[] joueur = new Joueur[nbJoueurs] ;
	
		for (int i=0;i<nbJoueurs;i++)
		{	
			joueur[i]=new Joueur(terrain);
			joueur[i].positionInitiale(i+1,nbColonnes,nbLignes,longueurCase,hauteurCase);
		}
		
		
		terrain.dessinerPlateau(joueur,nbJoueurs);

		while(joueurEnVie(joueur))
		{	
			int mouvement = deplacement(joueur, nbJoueurs,terrain);
			
			for (int i=0; i<nbJoueurs; i++)
			{	
				terrain = joueur[i].verifEffetCaseEnCours(terrain);
				for (int j=0; j<joueur[i].getNbBombes();j++)
				{
					terrain=joueur[i].bombe[j].gestion(terrain,joueur);
				}
			}
		
			terrain.dessinerPlateau(joueur, nbJoueurs);
			afficherLegende(joueur);
			StdDraw.show();
			StdDraw.pause(5);
		}
		
		// Lorsqu'un des deux joueurs n'est plus en vie
		sonjeu.stop();
		Audio sonperdu = new Audio("perdu.wav");
		sonperdu.play();
		finDePartie(joueur, terrain);
	}

	// Key Listener
	
	public static int deplacement(Joueur [] joueur, int nbJoueurs, Terrain terrain)
	{	
		int isTyped=0;
	
		if (StdDraw.isKeyPressed(KeyEvent.VK_Z))
		{	joueur[0].deplacement(1,terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_Q))
		{	joueur[0].deplacement(2,terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_S))
		{	joueur[0].deplacement(3,terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_D))
		{	joueur[0].deplacement(4,terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_A))
		{	terrain=joueur[0].poserBombe(terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
		{	joueur[1].deplacement(1,terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
		{	joueur[1].deplacement(2,terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
		{	joueur[1].deplacement(3,terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
		{	joueur[1].deplacement(4,terrain);
			isTyped=1;
		}
		
		if (StdDraw.isKeyPressed(KeyEvent.VK_ALT))
		{	terrain=joueur[1].poserBombe(terrain);
			isTyped=1;
		}
		
		return isTyped;
	}

	//  Vérification que les deux joueurs ont encore au moins 1 vie
	
	public static boolean joueurEnVie(Joueur[] joueur)
	{
		for (int i = 0 ; i < joueur.length ; i++)
		{
			if(joueur[i].getViesRestante()<=0)
			{
				return false;
			}
		}
		return true;
	}
	
	// Sinon on finit la partie
	
	public static void finDePartie(Joueur[] joueur, Terrain terrain)
	{
		
		String joueurGagnant;
		if(joueur[0].getViesRestante()<=0)
		{
			joueurGagnant = "Joueur 2" ;
			StdDraw.picture(20*21, 450, "win2.png", 450, 400);
			StdDraw.picture(20*21, (20*17)/2, "restart.png", 150, 150);
		}
		else
		{
			joueurGagnant = "Joueur 1";
			StdDraw.picture(20*21, 20*20, "win1.png", 450, 400);
			StdDraw.picture(20*21, (20*15)/2, "restart.png", 150, 150);
		}
		
		StdDraw.show();
		
		while(true)
		{
			if(StdDraw.mousePressed())
			{
				if(StdDraw.mouseX()>345
				&& StdDraw.mouseX()<496
				&& StdDraw.mouseY()>76
				&& StdDraw.mouseY()<228)
				{
					main(null);
				}
			}
		}
	}
	
	// Affiche la légende (nombre de bombes, portée, vitesse de déplacement et vies restantes)
	
	public static void afficherLegende(Joueur[] joueur)
	{
		for (int i=0;i<2;i++)
		{	
			int compteur = 0;
			for (int j=0;j<joueur[i].getNbBombes();j++)
			{
				if (joueur[i].getbomb(j).getBombePosee()==false)
				{
					compteur=compteur+1;
				}
			}
			
			if(i == 0)
			{
				/*
				StdDraw.textRight(15*25, 25, "Joueur " + (i+1) + " : " + 
				(joueur[i].getViesRestante()-joueur[i].getBouclier()) + " vies restantes, " + compteur + " bombes restantes");
				StdDraw.textRight(14*25, 8, "Portée : " + joueur[i].getBombe()[0].getPortee()
				+ ", Vitesse de déplacement : " + ((double) joueur[i].getVitesseDeplacement()/2 ) + "x"); 
				*/
				
				StdDraw.setPenColor(StdDraw.CYAN);
				Font font = new Font("Sans Serif", Font.BOLD, 11);
				StdDraw.setFont(font);
				StdDraw.text(20, 30, "Joueur");
				StdDraw.text(20, 10, "1");
				StdDraw.picture(20, 70, "coeur.png", 20, 20);
				StdDraw.text(20, 50, "x" + (joueur[i].getViesRestante()-joueur[i].getBouclier()));
				StdDraw.picture(20, 110, "bombe.png", 20, 20);
				StdDraw.text(20, 90, "x" + compteur);
				StdDraw.picture(20, 150, "feu.png", 20, 20);
				StdDraw.text(17, 130, " " + joueur[i].getBombe()[0].getPortee());
				StdDraw.picture(20, 190, "vitesse.png", 20, 20);
				StdDraw.text(20, 170, ((double) joueur[i].getVitesseDeplacement()/2 ) + "x");
				if(joueur[i].getBombeRouge())
				{
					StdDraw.picture(20, 230, "bomberouge.png", 20, 20);
					StdDraw.text(22, 210, "✓");
				}
				if(joueur[i].getFlammeVerte())
				{
					StdDraw.picture(20, 270, "flammeverte.png", 20, 20);
					StdDraw.text(22, 250, "✓");
				}
				if(joueur[i].getPasseMuraille())
				{
					StdDraw.picture(20, 310, "cle.png", 20, 20);
					StdDraw.text(22, 290, "✓");
				}
				if(joueur[i].getBouclier() == 1)
				{
					StdDraw.picture(20, 350, "cle.png", 20, 20);
					StdDraw.text(22, 330, "✓");
				}
			}
			else if(i == 1)
			{
				/*
				StdDraw.textRight(33*25, 25, "Joueur " + (i+1) + " : " + 
				(joueur[i].getViesRestante()-joueur[i].getBouclier()) + " vies restantes, " + compteur + " bombes restantes");
				StdDraw.textRight(32*25, 8, "Portée : " + joueur[i].getBombe()[0].getPortee()
				+ ", Vitesse de déplacement : " + ((double) joueur[i].getVitesseDeplacement()/2 ) + "x");
				*/
				
				StdDraw.setPenColor(StdDraw.PINK);
				Font font = new Font("Sans Serif", Font.BOLD, 11);
				StdDraw.setFont(font);
				StdDraw.text(820, 670, "Joueur");
				StdDraw.text(820, 650, "2");
				StdDraw.picture(820, 630, "coeur.png", 20, 20);
				StdDraw.text(820, 610, "x" + (joueur[i].getViesRestante()-joueur[i].getBouclier()));
				StdDraw.picture(820, 590, "bombe.png", 20, 20);
				StdDraw.text(820, 570, "x" + compteur);
				StdDraw.picture(820, 550, "feu.png", 20, 20);
				StdDraw.text(817, 530, " " + joueur[i].getBombe()[0].getPortee());
				StdDraw.picture(820, 510, "vitesse.png", 20, 20);
				StdDraw.text(820, 490, ((double) joueur[i].getVitesseDeplacement()/2 ) + "x");
				if(joueur[i].getBombeRouge())
				{
					StdDraw.picture(820, 430, "bomberouge.png", 20, 20);
					StdDraw.text(822, 410, "✓");
				}
				if(joueur[i].getFlammeVerte())
				{
					StdDraw.picture(820, 390, "flammeverte.png", 20, 20);
					StdDraw.text(822, 370, "✓");
				}
				if(joueur[i].getPasseMuraille())
				{
					StdDraw.picture(820, 350, "cle.png", 20, 20);
					StdDraw.text(822, 330, "✓");
				}
				if(joueur[i].getBouclier() == 1)
				{
					StdDraw.picture(820, 310, "cle.png", 20, 20);
					StdDraw.text(822, 290, "✓");
				}
			}
		}
	}
	
}