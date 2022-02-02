package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Robot;

import java.awt.AWTException;



public class TetrisActions implements KeyListener {
	
	final Tetris game;
	static Robot robot;

	
	public TetrisActions(Tetris game) throws AWTException
	{
		robot = new Robot();
		this.game = game;
	}
	
	public void keyTyped(KeyEvent e) { }
		
	public void keyPressed(KeyEvent e) 
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				game.rotate(-1);
				break;
			case KeyEvent.VK_DOWN:
				game.rotate(+1);
				break;
			case KeyEvent.VK_LEFT:
				game.move(-1);
				break;
			case KeyEvent.VK_RIGHT:
				game.move(+1);
				break;
			case KeyEvent.VK_W:
				game.rotate(-1);
				break;
			case KeyEvent.VK_S:
				game.rotate(+1);
				break;
			case KeyEvent.VK_A:
				game.move(-1);
				break;
			case KeyEvent.VK_D:
				game.move(+1);
				break;
			case KeyEvent.VK_SPACE:
				game.drop();
				break;
		} 
	}
	
	public void keyReleased(KeyEvent e) { }
	
	public static void moveRight()
    {
        robot.keyPress(KeyEvent.VK_RIGHT);
    }

    public static void moveLeft()
    {
        robot.keyPress(KeyEvent.VK_LEFT);
    }

    public static void rotateClockwise()
    {
        robot.keyPress(KeyEvent.VK_DOWN);
    }

    public static void rotateCounterClockwise()
    {
        robot.keyPress(KeyEvent.VK_UP);
    }

    public static void dropDown()
    {
        robot.keyPress(KeyEvent.VK_SPACE);
    }

}
