import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame implements KeyListener, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mat mat;
	private Timer timer;
	
	public Game()
	{
		addKeyListener(this);
		setFocusable(true);
		setTitle("Snake Game");
		setResizable(false);
		
		this.setIconImage(new ImageIcon("snake.png").getImage());
		mat = new Mat();
		mat.setBackground(new Color(50, 50, 50));
		this.add(mat);
		
		timer = new Timer(80, this);
		timer.setCoalesce(true);
		timer.start();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		Game runner = new Game();
		runner.setSize(585, 610);
		runner.setLocationRelativeTo(null);
		runner.setVisible(true);
	}

	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			mat.setSpeed(0,-1);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			mat.setSpeed(0,1);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			mat.setSpeed(1,0);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			mat.setSpeed(-1,0);
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			mat.restart();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e)	{}
	
	@Override
	public void keyTyped(KeyEvent e)	{}
	
	@Override
	public void actionPerformed(ActionEvent e)	
	{
		mat.move();
	}
	
}
