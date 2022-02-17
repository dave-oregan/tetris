package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tetris extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public JFrame f = new JFrame("Tetris");
	
	private final Point [] [] [] Tetrominos = 
	{
		{
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },		//    []
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },		//    []
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },		//    []
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }		//    []
		},
		{
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },		//
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },		//    []
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },		//    []
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }		//  [][]
		},
		{
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },		//  
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },		//  []
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },		//  []
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }		//  [][]
		},
		{
			{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },		//
			{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },		//  [][]
			{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },		//  [][]
			{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }		//
		},
		{
			{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },		//    
			{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },		//    [][]
			{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },		//  [][]
			{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }		//
		},
		{
			{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },		//
			{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },		//  [][][]
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },		//    []
			{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }		//
		},
		{
			{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },		//
			{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },		//  [][]
			{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },		//    [][]
			{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }		//
		}
	};
	
	private final Color [] tetrominoColours = 
	{
		Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.magenta, Color.red
	};
	
	private Point pieceOrigin;
	private int curPiece;
	private int rotation;
	private int county;
	private ArrayList <Integer> next = new ArrayList <Integer>();
	private boolean gameOver = false;
	private long score = 0;
	private Color [] [] wall;

	public final int gameWidth = 12;
	public final int gameHeight = 24;

	private boolean isColliding = false;
	
	public Tetris() { }
	
	public void startUp()
	{
		wall = new Color [gameWidth] [gameHeight];
		for (int i=0; i<12; i++) 
		{
			for (int k=0; k<23; k++) 
			{
				if (i==0 || i==11 || k==0 || k==22) 
				{
					wall [i] [k] = Color.BLACK;
				}
				else 
				{
					wall [i] [k] = Color.GRAY;
				}
			}
		}
		county = 0;
		printWall();
		spawnPiece();

	}
	
	public void spawnPiece()
	{
		pieceOrigin = new Point(5, 2);
		rotation = 0;
		if (next.isEmpty()) 
		{
			Collections.addAll(next, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(next);
		}
		curPiece = next.get(0);
		next.remove(0);
		if (collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) 
		{
			gameOver = true;
			county++;
			repaint();
		}	
	}
	
	private boolean collidesAt(int x, int y, int rotation)
	{
		for (Point p : Tetrominos [curPiece] [rotation]) 
		{
			if (wall [p.x + x] [p.y + y] != Color.GRAY) 
			{
				return true;
			}
		}
		return false;
	}
	
	public void rotate(int i)
	{
		int newRotation = (rotation + i) % 4;
		if (newRotation < 0) 
		{
			newRotation = 3;
		}
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) 
		{
			rotation = newRotation;
		}
		repaint();
	}
	
	public void move(int i)
	{
		if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation)) 
		{
			pieceOrigin.x += i;
		}
		repaint();
	}
	
	public void drop()
	{
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) 
		{
			pieceOrigin.y += 1;
			isColliding = false;
		} 
		else 
		{
			for (Point p : Tetrominos [curPiece] [rotation])
			{
				wall[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = tetrominoColours[curPiece];
			}
			isColliding = true;
			clearRows();
			// printWall();
			spawnPiece();
		}	
		repaint();
	}
	
	public void clearRows() 
	{
		boolean gap;
		int numClears = 0;
		
		for (int k=21; k>0; k--) 
		{
			gap = false;
			for (int i=1; i<11; i++) 
			{
				if (wall[i][k] == Color.GRAY) 
				{
					gap = true;
					break;
				}
			}
			if (!gap) 
			{
				for (int l=k-1; l>0; l--) 
				{
					for (int u=1; u<11; u++) 
					{
						wall [u] [l+1] = wall [u] [l];
					}
				}
				k += 1;
				numClears += 1;
			}
		}
		
		switch (numClears)
		{
			case 1:
				score += 100;
				break;
			case 2:
				score += 300;
				break;
			case 3:
				score += 500;
				break;
			case 4:
				score += 800;
				break;
		}
	}
	
	public void paintComponent(Graphics g)
	{
		g.fillRect(0,  0,  312, 598);
		for (int i=0; i<12; i++) 
		{
			for (int k=0; k<23; k++) 
			{
				g.setColor(wall [i] [k]);
				g.fillRect(26*i, 26*k, 25, 25);
			}
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier", Font.PLAIN, 20));
		g.drawString("Score: " + score, 30, 20);
		if (gameOver && county==1)
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier", Font.BOLD, 40));
			g.drawString("GAME OVER", 47, 245);
			GameOver();
		}
		g.setColor(tetrominoColours [curPiece]);
		for (Point p : Tetrominos [curPiece] [rotation]) 
		{
			g.fillRect((p.x + pieceOrigin.x) * 26, (p.y + pieceOrigin.y) * 26, 25, 25);
		}
	}
	
	public void GameOver()
	{
		String options [] = {"Quit"};
		int ans = JOptionPane.showOptionDialog(null, "Score: "+score, "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if (ans==0)
		{
			System.exit(0);
		}
	}
	
	public boolean getGameOver() 
	{
		return gameOver;
	}
	
	/**
	 * Gets array of where the wall is
	 * @return false if there is no block. true if there is
	 */
	public int[][] getWall(){
		int[][] bWall = new int[wall.length][wall[0].length - 1];
		for(int row=0; row < wall.length; row++){
			for(int col=0; col < wall[0].length - 1; col++)
			{
				if(!wall[row][col].equals(Color.GRAY))
					bWall[row][col] = 1;
				else
					bWall[row][col] = 0;
			}
		}

		return bWall;
	}

	public byte[] getByteArray(int[][] intArray) {
		int iMax = intArray.length;
		int jMax = intArray[0].length;

		// Set up a ByteBuffer called intBuffer
		ByteBuffer intBuffer = ByteBuffer.allocate(4*iMax*jMax); // 4 bytes in an int
		intBuffer.order(ByteOrder.LITTLE_ENDIAN); // Java's default is big-endian
	
		// Copy ints from intArray into intBuffer as bytes
		for (int i = 0; i < iMax; i++) {
			for (int j = 0; j < jMax; j++){
				intBuffer.putInt(intArray[i][j]);
			}
		}
	
		// Convert the ByteBuffer to a byte array and return it
		byte[] byteArray = intBuffer.array();

		System.out.println("converted to bytes");
		return byteArray;
	}

	public void printWall()
	{
		int [][] bW2 = getWall();
		for(int row=0; row < wall.length; row++)
		{
			for(int col=wall[0].length-2; col >= 0; col--)
			{
				if (bW2[row][col] == 1)
				{
					System.out.print(" X"); // "X" is printed where a sqaure is covered
				}
				else
				{
					System.out.print(" O"); // "O" is printed where the square is blank
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public int getX(){
		return pieceOrigin.x;
	}

	public int getY(){
		return pieceOrigin.y;
	}

	public int getRotation() 
	{
		// System.out.println(rotation);
		return rotation;
	}

	public byte getWidthBytes(){
		return  Integer.valueOf(gameWidth).byteValue();
	}

	public int getGameHeight(){
		return gameHeight;
	}

	public int getGameWidth(){
		return gameWidth;
	}

	public boolean getColliding(){
		return isColliding;
	}
}