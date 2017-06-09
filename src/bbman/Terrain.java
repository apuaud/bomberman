package bbman;

import edu.princeton.cs.introcs.StdDraw;

public class Terrain 
{	
	private int nbLignes; // Nombre de lignes
	private int nbColonnes; // Nombre de colonnes
	private int longueur; // Longueur d'une case
	private int hauteur; // Hauteur d'une case
	private int [][] plateau; // Terrain de jeu
	
	// Initialisation du terrain
	
	public Terrain (int nbLignes, int nbColonnes, int longueur, int hauteur)
	{	
		this.plateau=new int[nbLignes][nbColonnes];
		this.nbLignes=nbLignes;
		this.nbColonnes=nbColonnes;
		
		this.longueur=longueur;
		this.hauteur=hauteur;
		
		// On initialise les cases grises et oranges
		
		for (int i=0;i<nbLignes;i++)
		{	
			for (int j=0;j<nbColonnes;j++)
			{	
				if ((i==0)||(j==0)||(i==nbLignes-1)||(j==nbColonnes-1)||((i%2==0)&&(j%2==0)))
				{
					this.plateau[i][j]=1;
				}
				else
				{
					this.plateau[i][j]=2;
				}
			}
		}
		
		// On initialise les cases vertes en haut à droite et en bas à gauche du terrain
		
		this.plateau[1][1]=0;
		this.plateau[1][2]=0;
		this.plateau[1][3]=0;
		this.plateau[2][1]=0;
		this.plateau[2][3]=0;
		this.plateau[3][1]=0;
		this.plateau[3][2]=0;
		
		this.plateau[nbLignes-2][nbColonnes-2]=0;
		this.plateau[nbLignes-2][nbColonnes-3]=0;
		this.plateau[nbLignes-2][nbColonnes-4]=0;
		this.plateau[nbLignes-3][nbColonnes-2]=0;
		this.plateau[nbLignes-3][nbColonnes-4]=0;
		this.plateau[nbLignes-4][nbColonnes-2]=0;
		this.plateau[nbLignes-4][nbColonnes-3]=0;
	}

	// Getters et setters
	
	public int getLongueur()
	{
		return this.longueur;
	}
	
	public int getHauteur()
	{
		return this.hauteur;
	}
	
	public void setPlateau(int x, int y, int valeur)
	{
		this.plateau[x][y]=valeur;
	}
	
	public int getPlateau(int x, int y)
	{
		return this.plateau[x][y];
	}
		
	//
	
	public int test(int x, int y)
	{
		if(this.plateau[x][y]==1)
		{
			return 1;
		}
	
		if(this.plateau[x][y]==2)
		{
			return 3;
		}
		if(this.plateau[x][y]==99)
		{
			return 2;
		}
		
		return 0;
	}

	// Fonction interface graphique
	
	public void dessinerPlateau (Joueur [] joueur, int nb)
	{
		int x=this.longueur;
		int y=this.hauteur;

		// On dessine le plateau de jeu en fonction de la valeur du type de la case
		
		for (int i=0;i<this.nbLignes;i++)
		{	
			for (int j=0;j<this.nbColonnes;j++)
			{	
				if (this.plateau[i][j]==0) //Bloc vert
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
				}
				else if (this.plateau[i][j]==2) //Bloc orange
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"orange.png",40,40);
				}
				else if (this.plateau[i][j]==1) //Mur gris
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"bloc.png",40,40);
				}
				else if (this.plateau[i][j]>=100) //Bombe en cours d'explosion
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x, (j*2*this.hauteur)+y, "feu.png", 40, 40);
				}
				else if (this.plateau[i][j]==10) //Flamme bleue
				{
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"flammebleue.png",40,40);
				}
				else if (this.plateau[i][j]==11) //Flamme jaune
				{
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"flammejaune.png",40,40);
				}
				else if (this.plateau[i][j]==12) //Flamme rouge
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"flammerouge.png",40,40);
				}
				else if (this.plateau[i][j]==13) //Bombe rouge
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"bomberouge.png",40,40);
				}
				else if (this.plateau[i][j]==14) //+1 vie
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"oneup.png",40,40);
				}
				else if (this.plateau[i][j]==15) //Ralentir le joueur (tortue)
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"slow.png",40,40);
				}
				else if (this.plateau[i][j]==16) //Accéler le joueur (lièvre)
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"speed.png",40,40);
				}
				else if (this.plateau[i][j]==17) //+2 bombes
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"bombeplus.png",40,40);
				}
				else if (this.plateau[i][j]==18) //-2 bombes
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"bombemoins.png",40,40);
				}
				else if (this.plateau[i][j]==19) //Flamme verte
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"flammeverte.png",40,40);
				}
				
				else if (this.plateau[i][j]==20) //Passe muraille
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"cle.png",40,40);
				}
				
				else if (this.plateau[i][j]==21) //Bouclier
				{	
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"vert.png",40,40);
					StdDraw.picture((i*2*this.longueur)+x,(j*2*this.hauteur)+y,"bouclier.png",40,40);
				}
			}
		}

		// Dessin des bombes
		
		for (int i=0;i<nb;i++)
		{
			for (int j=0; j<joueur[i].getNbBombes();j++)
			{
				joueur[i].bombe[j].dessinerBombe(this);
			}
		}

		// Dessin du joueur
		
		for (int i=0;i<nb;i++)
		{
			joueur[i].dessiner_joueur();
		}
		
	}
	
}