import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI{
	private JPanel gamescreen;
	private Dimension gamescreenRes;
	private int width, height;
	private Graphics2D g2d;
	private boolean menu = false;

	public UI(JPanel screen){
		gamescreen = screen;
		width = gamescreen.getWidth();
		height = gamescreen.getHeight();
		gamescreenRes = screen.getPreferredSize();
	}
	public void drawUI(){
		if (menu){
			g2d = (Graphics2D) gamescreen.getGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(getProp(width, .7), getProp(height, .2), getProp(width, .3), getProp(height, .6));
		}
	}
	public void openMenu(){
		menu = true;
	}
	public void closeMenu(){
		menu = false;
	}
	public boolean menuOpen(){
		if (menu)
			return true;
		return false;
	}
	private int getProp(int dimension, double prop){
		double ans = (double) dimension*prop;
		return (int) ans;
	}
}
