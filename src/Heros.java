import edu.princeton.cs.introcs.StdDraw;

public class Heros
{
	private int positionX;
	private int positionY;
	private boolean isDead;
	private int idHeros;
	
	public Heros(int idHeros)
	{
		this.idHeros = idHeros;
		if(idHeros == 1)
		{
			this.positionX = 1;
			this.positionY = 1;
			this.isDead = false;
		}
		if(idHeros == 2)
		{
			this.positionX = 16;
			this.positionY = 20;
			this.isDead = false;
		}
	}
	
	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public void drawHeros()
	{
		if(idHeros == 1)
		{
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.filledCircle(12.5+25*positionX, 12.5+25*positionY, 10);
		}
		if(idHeros == 2)
		{
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.filledCircle(12.5+25*(positionX-1), 12.5+25*(positionY-1), 10);
		}
	}

}
