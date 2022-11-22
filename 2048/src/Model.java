/**
 * The Model class is responsible for most of the actual calculations performed in 2048.
 * 
 * This Class is responsible for moving the position of the Tiles on the board,
 * calculating whether a move is legal or not, keeping track of the score, etc.
 * 
 * @author Zavier Vega-Yu
 *
 */
public class Model{

	private static int SIZE = 4;
	private Tile[][] mat;
	private boolean movable;
	private int score;
	
	Board board;
	
	/**
	 * Constructor stores a reference to a given Board and sets 2 random
	 * Tiles within the board set equal to 2
	 * @param b
	 */
	public Model(Board b)
	{
		int r, c;
		mat = new Tile[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
			for ( c = 0; c < SIZE; c++ )
				mat[r][c] = new Tile();
		
		int randRow1 = (int)(Math.random() * 4);
		int randCol1 = (int)(Math.random() * 4);
		int randRow2 = (int)(Math.random() * 4);
		int randCol2 = (int)(Math.random() * 4);
		
		while((randRow1 == randRow2) && (randCol1 == randCol2))
		{
			randRow1 = (int)(Math.random() * 4);
			randCol1 = (int)(Math.random() * 4);
		}
		
		
		mat[randRow1][randCol1].setOccupied(true);
		mat[randRow1][randCol1].setVal(2);
		
		mat[randRow2][randCol2].setOccupied(true);
		mat[randRow2][randCol2].setVal(2);
		
		board = b;
		score = 0;
		board.updateView(mat);
	}
	
	/**
	 * Resets the values of the tiles and score and sets 2 randomly generated Tiles
	 * equal to 2.
	 */
	public void reset()
	{
		for(int r = 0; r < SIZE; r++)
		{
			for(int c = 0; c < SIZE; c++)
			{
				mat[r][c].setOccupied(false);
				mat[r][c].setVal(0);
			}
		}
		
		int randRow1 = (int)(Math.random() * 4);
		int randCol1 = (int)(Math.random() * 4);
		int randRow2 = (int)(Math.random() * 4);
		int randCol2 = (int)(Math.random() * 4);
		
		while((randRow1 == randRow2) && (randCol1 == randCol2))
		{
			randRow1 = (int)(Math.random() * 4);
			randCol1 = (int)(Math.random() * 4);
		}
		
		mat[randRow1][randCol1].setOccupied(true);
		mat[randRow1][randCol1].setVal(2);
		
		mat[randRow2][randCol2].setOccupied(true);
		mat[randRow2][randCol2].setVal(2);
		
		board.updateView(mat);
		score = 0;
		board.updateScore(0);
		board.clearMoves();
		board.setGameOver(false);
	}
	
	/**
	 * After every move the player makes, a random unoccupied Tile
	 * is set to either the value of 2 or 4. there is a 10% chance that a new tile
	 * is formed with the value of 4.
	 */
	public void move()
	{
		int val = (int)(Math.random() * 10);
		if(val >= 9)
			val = 4;
		else
			val = 2;
		
		
		int rr = (int)(Math.random() * 4);
		int rc = (int)(Math.random() * 4);
		
		while(mat[rr][rc].isOccupied())
		{
			rr = (int)(Math.random() * 4);
			rc = (int)(Math.random() * 4);
		}
		
		mat[rr][rc].setOccupied(true);
		mat[rr][rc].setVal(val);
		board.addMove();
		
		if(gameOver())
		{
			board.setGameOver(true);
		}
	}
	
	/**
	 * Updates the score and refreshes the GUI
	 * @param val	the amount to add to the score (equal to the value of a tile resulting from a combination)
	 */
	public void updateScore(int val)
	{
		score += val;
		board.updateScore(score);
		
		if(score > board.getBest())
			board.updateBest(score);
	}
	
	public boolean isMovable()
	{
		return movable;
	}
	
	/**
	 * Moves each Tile up to the farthest legal north position on the board.
	 * If no position is legal, no move is made.
	 */
	public void moveUp()
	{
		movable = false;
		for(int r = 0; r < SIZE; r++)
		{
			for(int c = 0; c < SIZE; c++)
			{

				if(mat[r][c].isOccupied()) 
				{
					int lowestR = r;
					for(int i = r; i >= 0; i--)
					{
						if(inBounds(i,c))
						{
							if((mat[i][c].getVal() == mat[r][c].getVal()))
							{
								lowestR = i;
							}
							else if(!mat[i][c].isOccupied())
								lowestR = i;
							else
								break;
						}
					}
					if(lowestR != r)
					{
						if(mat[lowestR][c].getVal() == mat[r][c].getVal())
							updateScore(mat[lowestR][c].getVal() * 2);
						combine(r, c, lowestR, c, mat);
						movable = true;
					}
				}
			}
		}
		
		board.updateView(mat);
	}
	
	/**
	 * Moves each Tile up to the farthest legal south position on the board.
	 * If no position is legal, no move is made.
	 */
	public void moveDown()
	{
		movable = false;
		for(int r = SIZE - 1; r >= 0; r--)
		{
			for(int c = 0; c < SIZE; c++)
			{

				if(mat[r][c].isOccupied()) 
				{
					int highestR = r;
					for(int i = r; i < SIZE; i++)
					{
						if(inBounds(i,c))
						{
							if((mat[i][c].getVal() == mat[r][c].getVal()))
							{
								highestR = i;
							}
							else if(!mat[i][c].isOccupied())
								highestR = i;
							else
								break;
						}
					}
					if(highestR != r)
					{
						if(mat[highestR][c].getVal() == mat[r][c].getVal())
							updateScore(mat[highestR][c].getVal() * 2);
						combine(r, c, highestR, c, mat);
						movable = true;
					}
				}
			}
		}
		board.updateView(mat);
	}
	
	/**
	 * Moves each Tile up to the farthest legal west position on the board.
	 * If no position is legal, no move is made.
	 */
	public void moveLeft()
	{
		movable = false;
		for(int r = 0; r < SIZE; r++)
		{
			for(int c = 0; c < SIZE; c++)
			{

				if(mat[r][c].isOccupied()) 
				{
					int lowestC = c;
					for(int i = c; i >=0; i--)
					{
						if(inBounds(r,i))
						{
							if((mat[r][i].getVal() == mat[r][c].getVal()))
							{
								lowestC = i;
							}
							else if(!mat[r][i].isOccupied())
								lowestC = i;
							else
								break;
						}
					}
					if(lowestC != c)
					{
						if(mat[r][lowestC].getVal() == mat[r][c].getVal())
							updateScore(mat[r][lowestC].getVal() * 2);
						combine(r, c, r, lowestC, mat);
						movable = true;
					}
				}
			}
		}
		board.updateView(mat);
	}
	
	/**
	 * Moves each Tile up to the farthest legal east position on the board.
	 * If no position is legal, no move is made.
	 */
	public void moveRight()
	{
		movable = false;
		for(int r = 0; r < SIZE; r++)
		{
			for(int c = SIZE - 1; c >= 0; c--)
			{

				if(mat[r][c].isOccupied()) 
				{
					int highestC = c;
					for(int i = c; i < SIZE; i++)
					{
						if(inBounds(r,i))
						{
							if((mat[r][i].getVal() == mat[r][c].getVal()))
							{
								highestC = i;
							}
							else if(!mat[r][i].isOccupied())
								highestC = i;
							else 
								break;
						}
					}
					if(highestC != c)
					{
						if(mat[r][highestC].getVal() == mat[r][c].getVal())
							updateScore(mat[r][highestC].getVal() * 2);
						combine(r, c, r, highestC, mat);
						movable = true;
					}
				}
			}
		}
		board.updateView(mat);
	}
	
	/**
	 * Checks if a move is possible. If no move is possible, the game is over.
	 * @return	true if game is over, false otherwise.
	 */
	public boolean gameOver()
	{
		for(int r = 0; r < SIZE; r++)
		{
			for(int c = 0; c < SIZE; c++)
			{
				if(!mat[r][c].isOccupied())
				{
					return false;
				}
				for(int i = -1; i <= 1; i+= 2)
				{
					if(inBounds(r + i, c))
					{
						if(mat[r+i][c].getVal() == mat[r][c].getVal())
							return false;
					}
					if(inBounds(r, c +i))
					{
						if(mat[r][c + i].getVal() == mat[r][c].getVal())
							return false;
					}
				}
			}
		}
		return true;
	}
	
	private static boolean inBounds(int r, int c)
	{
		return((r >= 0 && r < SIZE) && (c >= 0 && c < SIZE));
	}
	
	/**
	 * Combines two Tiles into one. The first tile is removed, and the second tile is set
	 * to the sum of the two tiles' values.
	 * @param r		the row of the first Tile
	 * @param c		the column of the first Tile
	 * @param r2	the row of the second Tile
	 * @param c2	the column of the second Tile
	 * @param mat	the 2D array of Tiles being changed
	 */
	private static void combine(int r, int c, int r2, int c2, Tile[][] mat)
	{
		Tile temp = mat[r][c];
		mat[r][c] = new Tile();
		mat[r2][c2].setVal(mat[r2][c2].getVal() + temp.getVal());
		mat[r2][c2].setOccupied(true);
		mat[r2][c2].updateColor();
	}
	
}
