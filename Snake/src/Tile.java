
public class Tile
{
	private int x;
	private int y;
	private boolean occupied;
	
	public  Tile(int x, int y, boolean oc)
	{
		occupied = oc;
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public boolean isOccupied()
	{
		return occupied;
	}

	public void setOccupied(boolean occupied)
	{
		this.occupied = occupied;
	}
	
	
}
