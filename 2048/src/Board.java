import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JPanel;

/**
 * The Board class is a representation of a 2D array of Tiles.
 * Is responsible for displaying the Graphical User Interface.
 * 
 * @author Zavier Vega-Yu
 *
 */
public class Board extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2195379760914381351L;
	private static final int SIZE = 4;
	private Tile[][] mat;
	private int score;
	private int moves;
	private int best;
	private boolean gameOver;
	private File gameStats;
	private FileWriter fw;
	private PrintWriter pw;
	
	/**
	 * Default constructor instantiates the 2D array of tiles,
	 * uses the final value SIZE for the size of the array.
	 * 
	 */
	public Board()
	{
		mat = new Tile[SIZE][SIZE];
		gameStats = new File("gameStats.dat");
		try 
		{
			fw = new FileWriter(gameStats, true);
		} catch (IOException e) 
		{
			System.out.println("Invalid file path");
		}
		pw = new PrintWriter(fw);
		Scanner scan;
		try 
		{
			scan = new Scanner(gameStats);
			if(scan.hasNext())
				while(scan.hasNext())
					best = scan.nextInt();
			else
				best = 0;
		} catch (FileNotFoundException e) 
		{
			System.out.println("Invalid file path");
		}
	}
	
	/**
	 * Refreshes the GUI, given a new 2D array of Tiles
	 * @param t	the new 2D array of Tiles
	 */
	public void updateView(Tile[][] t)
	{
		mat = t;
		repaint();
	}
	
	/**
	 * Refreshes the GUI given a new score
	 * @param score	the new score
	 */
	public void updateScore(int score)
	{
		this.score = score;
		repaint();
	}
	
	/**
	 * Refreshes the GUI given a new best score
	 * @param best	the new best score
	 */
	public void updateBest(int best)
	{
		this.best = best;
		pw.println(best);
		repaint();
	}
	
	public void setGameOver(boolean g)
	{
		gameOver = g;
		repaint();
	}
	
	public PrintWriter getpw()
	{
		return pw;
	}
	
	public int getScore() {return score;}
	public int getBest() {return best;}
	public int getMoves() {return moves;}
	public void addMove(){moves++; repaint();}
	public void clearMoves(){moves = 0; repaint();}
	
	/**
	 * Ovveridden method paints all the elements of the GUI
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
        int boxSize = 100;
        
        // Drawing the current score
        {
	        g.setColor(new Color(59, 59, 58));
	        g.fillRect(290, 5, 100, 50);
	        g.fillRect(400, 5, 100, 50);
	
	        g.setColor(new Color(250, 240, 246));
	        g.setFont(new Font("Clear Sans", 1, 16));
	        g.drawString("SCORE", 310, 25);
	        
	        g.setColor(Color.WHITE);
	        if(score < 10)
	        	g.drawString("" + score, 335, 46);
	        else if(score < 100)
	        	g.drawString("" + score, 330, 46);
	        else if(score < 1000)
	        	g.drawString("" + score, 325, 46);
	        else if(score < 10000)
	        	g.drawString("" + score, 320, 46);
	        else if(score < 100000)
	        	g.drawString("" + score, 315, 46);
	        else
	        	g.drawString("" + score, 310, 46);
        }
        
        // Drawing the best score
        {
	        g.setColor(new Color(250, 240, 246));
	        g.drawString("BEST", 428, 25);
	        
	        g.setColor(Color.WHITE);
	        if(best < 10)
	        	g.drawString("" + best, 448, 46);
	        else if(best < 100)
	        	g.drawString("" + best, 443, 46);
	        else if(best < 1000)
	        	g.drawString("" + best, 438, 46);
	        else if(best < 10000)
	        	g.drawString("" + best, 433, 46);
	        else if(best < 100000)
	        	g.drawString("" + best, 428, 46);
	        else
	        	g.drawString("" + best, 423, 46);
        }
	    
        g.setColor(new Color(138, 127, 114));
	    g.setFont(new Font("Clear Sans", 1, 16));
	    g.drawString("Join the numbers and get to the 2048 tile!", 135, 80);
	    g.drawString(moves + " moves", 92 , 525);
	    
        
        
        //Draws the outline box of the board
        g.fillRect(boxSize - 10, boxSize -10, (boxSize * 4) + 10, (boxSize * 4) + 10);
        
        // Creates the boxes of each tile inside the board and draws their values
        for ( int r = 0; r < SIZE; r++ )
        {
            for (int c = 0; c < SIZE; c++ )
            {
            	String num = "";
            	
                if (mat[r][c] != null)
                {
                    if (mat[r][c].isOccupied())
                    {
                        g.setFont(new Font("Clear Sans", 1, 60));
                        g.setColor(mat[r][c].getColor());
                        num = ""+mat[r][c].getVal();
                    }
                    else
                        g.setColor( new Color(185, 175, 157) );

                    g.fillRect( (c+1)*boxSize, (r+1)* boxSize, boxSize-10, boxSize-10);
                    if(mat[r][c].getVal() <= 4)
                    	g.setColor(new Color(82, 79, 74));
                    else
                    	g.setColor(Color.white);
                    
                    
                    /* Position of the number labeling the tile is adjusted according to 
                     * the number of digits in the value.
                     */
                    if(mat[r][c].getVal() < 10)
                    	g.drawString(num, ((c+1)*boxSize) + 29, ((r+1)* boxSize) + 65);
                    else if(mat[r][c].getVal()<100)
                    	g.drawString(num, ((c+1)*boxSize) + 11, ((r+1)* boxSize) + 65);
                    else if(mat[r][c].getVal() < 1000)
                    {
                    	g.setFont(new Font("Clear Sans", 1, 50));
                    	g.drawString(num, ((c+1)*boxSize)+2, ((r+1)* boxSize) + 65);
                    }
                    else if(mat[r][c].getVal() < 10000)
                    {
                    	g.setFont(new Font("Clear Sans", 1, 40));
                    	g.drawString(num, ((c+1)*boxSize)+2, ((r+1)* boxSize) + 65);
                    }
                    else if(mat[r][c].getVal() < 100000)
                    {
                    	g.setFont(new Font("Clear Sans", 1, 30));
                    	g.drawString(num, ((c+1)*boxSize)+2, ((r+1)* boxSize) + 65);
                    }
                }
            }
        }
        
        if(gameOver)
        {
        	g.setColor(new Color(59, 59, 58));
        	g.fillRect(200, 250, 190, 70);
        	
        	g.setColor(new Color(240, 92, 92));
        	g.setFont(new Font("Clear Sans", 1, 30));
        	
        	g.drawString("GAME OVER", 205, 293);
        }
	}
	
}
