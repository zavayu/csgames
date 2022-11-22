import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * This Class is the Runner for the 2048 game. 
 * 
 * 2048 is a game played on a 4x4 board. Each position on the board may either be
 * empty or contain a tile, and each tile has a corresponding value. When the game starts,
 * two random tiles are set to the value of two. A player can make moves that shift all the
 * tiles towards one side of the board - left, right, up, or down. Any tiles of the same
 * value adjacent to each other when moving will merge together to form one tile equal to
 * the sum of the two tiles. After each move is made, a random unoccupied tile is either
 * set to the value of 2 or 4, with a 10% chance of it being 4, and a 90% chance of it being
 * 4. The game ends when no move is possible, and a game is considered "won" once a 2048
 * tile is created.
 * 
 * 
 * @author Zavier Vega-Yu
 *
 */
public class Game extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;
	private Model model;
	private Image icon;
	
	Game()
	{
		board = new Board();
		board.setBackground(new Color(252, 251, 248));
		
		board.addKeyListener(this);
		board.setFocusable(true);
		
		this.add(board);
		
		model = new Model(board);
		icon = new ImageIcon("2048icon.png").getImage();
		
	}
	
	
	public static void main(String[] args) 
	{
		Game runner = new Game();
		runner.setSize(600, 670);
		runner.setIconImage(runner.icon);
		runner.setLocationRelativeTo(null);
		runner.setTitle("2048");
		runner.setVisible(true);
		
		// When program is closed, best score is recorded in a file
		runner.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				runner.board.getpw().close();
				System.exit(0);
			}
		});
	}
	

	/**
	 * Listens for key strokes that control player moves
	 */
	@Override
	public void keyPressed(KeyEvent e) 
	{
	if(e.getKeyCode() == KeyEvent.VK_UP)
		model.moveUp();
	if(e.getKeyCode() == KeyEvent.VK_DOWN)
		model.moveDown();
	if(e.getKeyCode() == KeyEvent.VK_LEFT)
		model.moveLeft();
	if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		model.moveRight();
	if(model.isMovable())
		model.move();
	
	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		model.reset();
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
