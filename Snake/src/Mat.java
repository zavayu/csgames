import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Mat extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID= 1L;
	private static final int SIZE = 30;
	
	private Snake head;
	private Tile[][] mat;
	private LinkedList<Snake> snake;
	private boolean gameOver;
	private int play;
	private int score;
	
	public Mat()
	{
		gameOver = false;
		play = 0;
		mat = new Tile[SIZE][SIZE];
		snake = new LinkedList<Snake>();
		head = new Snake(10, 10, 1, 0);
		for(int i = 0; i < SIZE; i++)
		{
			for(int j = 0; j < SIZE; j++)
			{
				mat[i][j] = new Tile(i, j, false);
			}
		}
		mat[10][10] = head;
		snake.offer(head);
		
		createFood(mat);
	}
	
	
	public void createFood(Tile[][] t)
	{
		boolean available = false;
		
		while(!available)
		{
			int randX = (int)(Math.random() * SIZE);
			int randY = (int)(Math.random() * SIZE);
			if(!t[randX][randY].isOccupied())
			{
				available = true;
				t[randX][randY] = new Food(randX, randY, true);
				break;
			}
		}
		
	}
	
	public void move()
	{
		int x = head.getX() + head.getxSpeed();
		int y = head.getY() + head.getySpeed();
		
		if(!inBounds(x, y, mat) || mat[y][x] instanceof Snake)
		{
			gameOver = true;
			updateView(mat);
			return;
		}
		
		if(mat[y][x] instanceof Food)
		{
			Snake newHead = new Snake(x,y, head.getxSpeed(), head.getySpeed());
			snake.offer(newHead);
			mat[newHead.getY()][newHead.getX()] = newHead;
			
			head = newHead;
			score += 10;
			createFood(mat);
			StdAudio.play("chomp.wav");
		}
		else
		{
			Snake newHead = snake.poll();
			mat[newHead.getY()][newHead.getX()] = new Tile(newHead.getY(), newHead.getX(), false);
			newHead.setX(x); newHead.setxSpeed(head.getxSpeed());
			newHead.setY(y); newHead.setySpeed(head.getySpeed());
			snake.offer(newHead);
			mat[newHead.getY()][newHead.getX()] = newHead;
			head = newHead;
		}
			
		updateView(mat);
	}
	
	private static boolean inBounds(int x, int y, Tile[][] mat)
	{
		return x >= 0 && y >= 0 && x < mat.length && y < mat[x].length;
	}
	
	public void updateView(Tile[][] t)
	{
		mat = t;
		repaint();
	}
	
	public void setSpeed(int x, int y)
	{
		if((snake.size() > 1) && (-x == head.getxSpeed()  && x!= 0) || (-y == head.getySpeed() && y!= 0))
			return;
		
		head.setxSpeed(x);
		head.setySpeed(y);
	}
	
	public void restart()
	{
		gameOver = false;
		play = 0;
		mat = new Tile[SIZE][SIZE];
		snake = new LinkedList<Snake>();
		head = new Snake(10, 10, 1, 0);
		for(int i = 0; i < SIZE; i++)
		{
			for(int j = 0; j < SIZE; j++)
			{
				mat[i][j] = new Tile(i, j, false);
			}
		}
		mat[10][10] = head;
		snake.offer(head);
		
		createFood(mat);
		updateView(mat);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int boxSize = 19;
		
		if(gameOver)
		{
			if(play == 0)
			{
				play++;
				StdAudio.play("gameOver.wav");
			}
			
			g.setColor(Color.RED);
			g.setFont(new Font("Monospaced", Font.BOLD, 50));
			g.drawString("GAME OVER", 150, 100);
			
			
			g.setColor(Color.white);
			g.setFont(new Font("Monospaced", Font.BOLD, 20));
			g.drawString("Score: " + score, 230, 130);
			
			
			return;
		}
		
		for(int i = 0; i < SIZE; i++)
		{
			for(int j = 0; j < SIZE; j++)
			{
				if(mat[i][j] instanceof Snake)
				{
					g.setColor(Color.green);
					g.fillRect((j) * boxSize, (i) * boxSize, boxSize - 1, boxSize - 1);
				}
				else if(mat[i][j] instanceof Food)
				{
					g.setColor(Color.red);
					g.fillRect((j) * boxSize, (i) * boxSize, boxSize - 1, boxSize - 1);
				}
				else
				{
					g.setColor(getBackground());
					//g.setColor(Color.gray);
					g.fillRect((j) * boxSize, (i) * boxSize, boxSize - 1, boxSize - 1);
				}
			}
		}
		
		
	}
}
