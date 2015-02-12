import java.awt.*;
import javax.swing.*;

public class UITest{
	public static void main(String[] args){
		JFrame frame = new JFrame("testing...");
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 300));
		frame.getContentPane().add(panel);
		UI ui = new UI(panel);
		frame.pack();
		frame.setVisible(true);

		Graphics g = panel.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,400,300);


		ui.openMenu();
		ui.drawUI();
	}
}
