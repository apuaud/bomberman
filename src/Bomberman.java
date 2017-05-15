import edu.princeton.cs.introcs.StdDraw;

public class Bomberman {

	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 650);
		Brique[][] plateau = new Brique[17][21];
		while(true)
		{
			StdDraw.setXscale(0,25+16*25);
			StdDraw.setYscale(0,25+20*25);
			for(int i=0;i<21;i++)
			{
				for(int j=0;j<17;j++)
				{
					if(i==0 || j==0 || i==20 || j==16)
					{
						StdDraw.setPenColor(StdDraw.GRAY);
					}
					else
					{
						StdDraw.setPenColor(StdDraw.ORANGE);
					}
					StdDraw.filledRectangle(25+(j*25), 25+(i*25), 25, 25);
				}
			}
			StdDraw.show(500);
		}
	}

}
