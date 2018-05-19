package snake1;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {
	public JFrame jframe;
	
	public static Snake snake;
	Dimension dim;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	public RenderPanel renderPanel;	
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public int eatenCherries = 0, direction = DOWN, score, tailLength, level = 1,  timeDelay = 150;
	public Timer timer = new Timer(timeDelay/level, this);
	public Point head, cherry;
	public Random random;
	public boolean over, paused;
	static int width = 400, height = 300;
		
	public Snake(){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//jframe.setResizable(false);
		jframe.add(renderPanel = new RenderPanel());
		renderPanel.setPreferredSize(new Dimension(width, height));
		jframe.pack();
		jframe.addKeyListener(this);
		jframe.setBounds(dim.width/2 - jframe.getWidth()/2,  dim.height /2 - jframe.getHeight()/2, jframe.getWidth(), jframe.getHeight());
		jframe.setVisible(true);
		startGame();
	}
	
	public void startGame(){
		level = 1;
		over = false;
		paused = false;
		score = 0;
		tailLength = 5;
		direction = DOWN;
		snakeParts.clear();
		head = new Point(0, 0);
		random = new Random();
		timer.setDelay(timeDelay);
		cherry = new Point(Math.floorDiv(random.nextInt(width - SCALE*2), SCALE) + 1, Math.floorDiv(random.nextInt(height - SCALE*2),SCALE) + 1);
		for (int i = 0; i <= tailLength; i++) {
			snakeParts.add(new Point(head.x, head.y));
		}
		timer.start();
		
	}
	public static void main(String[] args) {
		snake = new Snake();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		renderPanel.repaint();
		
		if (head != null && !over && !paused) {
			
			if (direction == UP) {
				if (noTailAt(head.x, head.y - 1)) {
					if(head.y - 1 < 0)
						head = new Point(head.x, height/SCALE-1);
					else
						head = new Point(head.x, head.y - 1);
				} 
				else 
					over = true;
				
			} 
			else if (direction == DOWN) {
				if (noTailAt(head.x, head.y + 1)) {
					if(head.y + 1 >= height/SCALE)
						head = new Point(head.x,0);
					else	
						head = new Point (head.x, head.y + 1);
				} 
				else 
					over = true;
			}
			else if (direction == LEFT) {
				if (noTailAt(head.x - 1, head.y)) {
					if(head.x + 1 <= 0)
						head = new Point(width/SCALE-1,head.y);
					else
						head = new Point(head.x - 1, head.y);
				}
				else 
					over = true;

				
			}
			else if (direction == RIGHT) {
				if (noTailAt(head.x + 1, head.y)) {
					if(head.x  + 1 >= width/SCALE)
						head = new Point(0,head.y);
					else
						head = new Point(head.x + 1, head.y);
				} 
				else 
					over = true;
			}
			tailLength++;
			snakeParts.add(head);
			snakeParts.remove(0);
				
			if (cherry != null) {
				if (head.x == cherry.x && head.y == cherry.y) {
				if (head.equals(cherry)){
					
					score+= 10;	
	
					if(score%100 == 0) {
						level++;
						timer.setDelay(timeDelay - 10*level);
					}
					snakeParts.add(new Point(head.x, head.y));
					cherry.setLocation(Math.floorDiv(random.nextInt(width - SCALE), SCALE) + 1, Math.floorDiv(random.nextInt(height - SCALE),SCALE) + 1);
				} 
			}
			}
		}
	}
	private boolean noTailAt(int i, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(i, y))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		
		if (i == KeyEvent.VK_LEFT && direction != RIGHT) 
			direction = LEFT;
		if (i == KeyEvent.VK_RIGHT && direction != LEFT)
			direction = RIGHT;
		if (i == KeyEvent.VK_UP && direction != DOWN)
			direction = UP;
		if (i == KeyEvent.VK_DOWN && direction != UP)
			direction = DOWN;
		if (i == KeyEvent.VK_SPACE) {
			if (over)
				startGame();
			else paused = !paused;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
