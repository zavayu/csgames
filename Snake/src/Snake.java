
public class Snake extends Tile
{
	private int ySpeed;
	private int xSpeed;
	
	public Snake(int x, int y, int xSpeed, int ySpeed)
	{
		super(x, y, true);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public int getySpeed()
	{
		return ySpeed;
	}

	public void setySpeed(int ySpeed)
	{
		this.ySpeed = ySpeed;
	}

	public int getxSpeed()
	{
		return xSpeed;
	}

	public void setxSpeed(int xSpeed)
	{
		this.xSpeed = xSpeed;
	}

	public int getX()
	{
		return super.getX();
	}

	public void setX(int x)
	{
		super.setX(x);
	}

	public int getY()
	{
		return super.getY();
	}

	public void setY(int y)
	{
		super.setY(y);
	}
	
	
	
}
