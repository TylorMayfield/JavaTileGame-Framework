import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Display extends JPanel implements KeyEventDispatcher{
//public class Display extends JPanel implements KeyListener {
    private int TILE_COLS, TILE_ROWS;
    private final int TILE_RES = 32;

	private final int PREFERRED_VIEW_RES = 13;
	private int VIEW_COLS, VIEW_ROWS;
	private int view_x, view_y;

	private UI ui;
    private Map map;
	private ArrayList<Map> mapList = new ArrayList<Map>();

    private String tileset_source;
    private BufferedImage tileset_image;

	private boolean menu = true;
	public Display(ArrayList<Map> mapList){
		this.mapList = mapList;
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		map = null;
	}
    public Display(ArrayList<Map> mapList, int mapID){
		this.mapList = mapList;
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		setMap(mapID);
    }
    public void init(Map map){
        this.map = map;
        TILE_COLS = map.getTileCols();
        TILE_ROWS = map.getTileRows();
		if (TILE_COLS < PREFERRED_VIEW_RES) { 
			VIEW_COLS = TILE_COLS;
		}
		else VIEW_COLS = PREFERRED_VIEW_RES;
		if (TILE_ROWS < PREFERRED_VIEW_RES) {
			VIEW_ROWS = TILE_ROWS;
		}
		else VIEW_ROWS = PREFERRED_VIEW_RES;
		view_x = (TILE_COLS-VIEW_COLS)/2;
		view_y = (TILE_ROWS-VIEW_ROWS)/2;
		setPreferredSize(new Dimension(TILE_RES*VIEW_COLS, TILE_RES*VIEW_ROWS));
    }
	public void setMap(int mapID){
		init(mapList.get(mapID));
	}
    public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage mapimg = (BufferedImage) map.getMapImage();
		g2d.drawImage(mapimg.getSubimage(view_x*TILE_RES, view_y*TILE_RES, VIEW_COLS*TILE_RES, VIEW_ROWS*TILE_RES), null, 0, 0);
		if (menu){
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, 80, 140);
			ui.drawUI();
		}
		/*
		if (ui.menuOpen()){
			getGraphics().setColor(Color.WHITE);
			getGraphics().drawRect(0,0, 30, 50);
			ui.drawUI();
		}*/
    }
	public void addUI(UI ui){
		this.ui = ui;
	}

	/*
	public void keyPressed(KeyEvent e){
		toggleKey(e.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent e){
		toggleKey(e.getKeyCode(), false);
	}
	public void keyTyped(KeyEvent e) {}
	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_LEFT){
			scene
			*/
	private boolean scroll = true;
	public boolean dispatchKeyEvent(KeyEvent ke) {
		if (ke.getID() == KeyEvent.KEY_PRESSED){
			if (ke.getKeyCode() == KeyEvent.VK_UP) {
				if (view_y > 0) view_y--;
			} else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
				if (view_y < TILE_ROWS-VIEW_ROWS) view_y++;
			} else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
				if (view_x > 0) view_x--;
			} else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (view_x < TILE_COLS-VIEW_COLS) view_x++;
			} else if (ke.getKeyCode() == KeyEvent.VK_X) {
			} else if (ke.getKeyCode() == KeyEvent.VK_Z) {
			} else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
				menu = !menu;
				System.out.println("GOT ENTER"+"\nmenu:"+menu);
				//if (!ui.menuOpen()) ui.openMenu();
				//else ui.closeMenu();
			} else return false;
		}
		else return false;
		repaint();
		//ui.drawUI();
		return true;
	}
	
}
