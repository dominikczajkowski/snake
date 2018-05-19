package snake1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Font;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
	@Override
	protected void paintComponent(Graphics g) {
		Snake snake = Snake.snake;
		super.paintComponent(g);
		g.setColor(Color.MAGENTA);
		g.fillRect(0, 0, snake.width, snake.height);
		g.setColor(Color.PINK);
		for (Point point : snake.snakeParts) {
			g.fillRect(point.x * snake.SCALE, point.y * snake.SCALE, snake.SCALE, snake.SCALE);
		}
		g.setColor(new Color(0xFF005E));
		g.fillRect(snake.cherry.x * snake.SCALE, snake.cherry.y * snake.SCALE, snake.SCALE, snake.SCALE);
		g.setColor(Color.CYAN);
		g.drawString("level " + snake.level +" score: " + snake.score, 150,20);
		Font font = g.getFont().deriveFont( 20.0f );
		Font font2 = g.getFont().deriveFont( 10.0f );
		if(snake.over) {
	    	g.setFont( font );
			g.drawString("You lose", snake.width/2-50,snake.height/2-3);
			g.setFont(font2);
			g.drawString("Press space to restart",snake.width/2-60,snake.height/2-20);
		}
	}
}