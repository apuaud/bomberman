import java.awt.Color;

public class Brique {

	private int type;
	private boolean franchissable;
	private boolean destructible;
	private boolean isDestroyed;
	private Color color;
	
	public Brique(int type)
	{
		this.type = type;
		if(type==0) // Bloc gris
		{
			this.franchissable = false;
			this.destructible = false;
			this.isDestroyed = false;
		}
		else if(type==1) // Bloc orange
		{
			this.franchissable = false;
			this.destructible = true;
			this.isDestroyed = false;
		}
		else // Bloc vert
		{
			this.franchissable = true;
			this.destructible = true;
			this.isDestroyed = true;
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
