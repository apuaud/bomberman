
public class Heros
{
	private int positionX;
	private int positionY;
	
	public Heros()
	{
		this.positionX = 1;
		this.positionY = 1;
		this.isDead = false;
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

	private boolean isDead;
	

}
