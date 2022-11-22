import java.awt.Color;

/**
 * The Tile Class represents a single square in 2048.
 * Each tile contains values that are responsible for what will be displayed.
 * 
 * @author Zavier Vega-Yu
 *
 */
public class Tile {
	
	private int value;
	private boolean occupied;
	private Color tileColor;
	
	Tile(){
		value = 0;
		occupied = false;
		tileColor = new Color(185, 175, 157);
	}
	
	Tile(int val, boolean o){
		value = val;
		occupied = o;
		updateColor();
	}
	
	public void setVal(int val){
		value = val;
		updateColor();
	}
	
	public int getVal() {
		return value;
	}
	
	public boolean isOccupied(){
		return occupied;
	}
	
	public void setOccupied(boolean o)
	{
		occupied = o;
	}
	
	public void updateColor()
	{
		if(value == 2)
			tileColor = new Color(244, 239, 230);
		if(value == 4)
			tileColor = new Color(248, 228, 194);
		if(value == 8)
			tileColor = new Color(247, 189, 108);
		if(value == 16)
			tileColor = new Color(231, 119, 48);
		if(value == 32)
			tileColor = new Color(241, 103, 70);
		if(value == 64)
			tileColor = new Color(239, 57, 13);
		if(value == 128)
			tileColor = new Color(255, 212, 90);
		if(value == 256)
			tileColor = new Color(255, 212, 80);
		if(value == 512)
			tileColor = new Color(255, 212, 70);
		if(value == 1024)
			tileColor = new Color(255, 212, 50);
		if(value == 2048)
			tileColor = new Color(255, 212, 39);
		if(value > 2048)
			tileColor = new Color(59, 59, 58);
		
	}
	
	public Color getColor()
	{
		return tileColor;
	}
	
}
