import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class Game extends JFrame{
    //private final int TILE_COLS;
    //private final int TILE_ROWS;
    //private final int TILE_RES = 32;

    private static Game game;
    public static void main(String[] args){
        game = new Game();
    }

    private Display disp;
	private UI ui;
    public Game(){
        super("Java Pokemon");                                                                                          
        setDefaultLookAndFeelDecorated(true);                                                                                
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

        Container cp = this.getContentPane();
        cp.setBackground(Color.WHITE);

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("sample menu");
        menu.setMnemonic(KeyEvent.VK_A);
		menu.addActionListener(new openuiListener());
        menubar.add(menu);
        setJMenuBar(menubar);

        //Map map1 = new Map("maps/pallet.tmx");
        //TILE_COLS = map1.getTileCols();
        //TILE_ROWS = map1.getTileRows();

        String[] maps = {"maps/pallet.tmx", "maps/grass.tmx", "maps/map1.tmx"};
        ArrayList<Map> mapList = loadMaps(maps);
        disp = new Display(mapList);
        disp.setBackground(Color.BLACK);
		ui = new UI(disp);
        disp.addUI(ui);
        disp.setMap(2);
        cp.add(disp);

        pack();
        setVisible(true);
    }
    private ArrayList<Map> loadMaps(String[] mapnames) {
        ArrayList<Map> maps = new ArrayList<Map>();
        Map map;
        for (String name: mapnames){
            map = new Map(name);
            maps.add(map);
        }
        return maps;
    }
	public class openuiListener implements ActionListener{
		public openuiListener(){
		}
		public void actionPerformed(ActionEvent e){
			if (ui.menuOpen())
				ui.closeMenu();
			else {
				ui.openMenu();
				ui.drawUI();
			}
		}
	}
}

