// XML & JAVA: http://www.java-samples.com/showtutorial.php?tutorialid=152
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Map extends DefaultHandler{
	private String filename_map;
	private Tileset tileset, tempTileset;
	private BufferedImage mapbuffer;
	private final int TILE_RES = 32;

	private int tile_cols, tile_rows;
	private int gRow, gCol;
	private int[][] tempLayer;
	private ArrayList<int[][]> layers = new ArrayList<int[][]>();
	private ArrayList<Integer> tilesUsed;
	private ArrayList<BufferedImage> tilepics;
	private BufferedImage tileset_image;

	private String tempVal;

	public Map(String fn){
		filename_map = fn;
		parsemap(filename_map);

		tilesUsed = getTilesUsed(layers);

		try {
			tileset_image = ImageIO.read(new File(tileset.getFilename().substring(1)));
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		tilepics = loadTiles(tileset_image, tilesUsed);

		mapbuffer = new BufferedImage(TILE_RES*tile_cols, TILE_RES*tile_rows, BufferedImage.TYPE_INT_ARGB);
		drawmap(mapbuffer);

		/*System.out.println(tileset);
		for (int x=0; x<2; x++){
			for (int y=0; y<5; y++){
				System.out.println(layers.get(0)[x][y]);
			}
		}*/
	}
	private void parsemap(String filename){
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			
			//get a new instance of SAX parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file, use this class for callbacks (startElement, endElement, characters)
			sp.parse(filename, this);

		}catch(SAXException se){
			se.printStackTrace();
		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}

	//Event Handlers for parsing
	public void startElement(String uri, String localname, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if (qName.equalsIgnoreCase("tileset")) {
			tempTileset = new Tileset();
			tempTileset.setFirstgid(Integer.parseInt(attributes.getValue("firstgid")));
		} else if (qName.equalsIgnoreCase("image")) {
			tempTileset.setFilename(attributes.getValue("source"));
			tempTileset.setWidth(Integer.parseInt(attributes.getValue("width")));
			tempTileset.setHeight(Integer.parseInt(attributes.getValue("height")));
		} else if (qName.equalsIgnoreCase("layer")) {
			tile_cols = Integer.parseInt(attributes.getValue("width"));
			tile_rows = Integer.parseInt(attributes.getValue("height"));
		} else if (qName.equalsIgnoreCase("data")) {
			gRow = 0;
			gCol = 0;
			tempLayer = new int[tile_rows][tile_cols];
		} else if (qName.equalsIgnoreCase("tile")) {
			if (gCol >= tile_cols) {
				gCol = 0;
				gRow += 1;
			}
			tempLayer[gRow][gCol] = Integer.parseInt(attributes.getValue("gid"));
			gCol += 1;
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch, start, length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("tileset")) {
			tileset = tempTileset;
		} else if (qName.equalsIgnoreCase("data")) {
			layers.add(tempLayer);
		}
	}
	public ArrayList<Integer> getTilesUsed(ArrayList<int[][]> l){
		ArrayList<Integer> tilesUsed = new ArrayList<Integer>();
		int tileID;
		for (int i=0; i<l.size(); i++){
			for (int x=0; x<l.get(i).length; x++){
				for (int y=0; y<l.get(i)[0].length; y++){
					tileID = l.get(i)[x][y];
					if (!tilesUsed.contains(tileID)){
						tilesUsed.add(tileID);
					}
				}
			}
		}
		return tilesUsed;
	}

	public ArrayList<BufferedImage> loadTiles(BufferedImage tileset_image, ArrayList<Integer> toLoad){
		int tilesx = tileset.getTilesAcross();
		int tilesy = tileset.getTilesDown();
		BufferedImage tile;
		ArrayList<BufferedImage> loadedTiles = new ArrayList<BufferedImage>();
		for (int i=0; i<(tilesx*tilesy); i++){
			loadedTiles.add(null);
		}

		int xoffset;
		int yoffset;
		for (Integer i: toLoad){
			if (i != 0){
				xoffset = TILE_RES*((i-1)%tilesx);
				yoffset = TILE_RES*((i-1)/tilesx);
				try {
					tile = tileset_image.getSubimage(xoffset, yoffset, 32, 32);
					loadedTiles.set(i,tile);
				} catch (RasterFormatException rfe) {
					System.out.println("" + i + ", " + xoffset + ", " + yoffset);
				}
			}
		}
		return loadedTiles;
	}


	public void drawmap(BufferedImage image){
		Graphics2D g2d = image.createGraphics();
		int x = 0;
		int y = 0;
		int tileid;
		int numlayers = layers.size();
		for (int r=0; r<tile_rows; r++){
			for (int c=0; c<tile_cols; c++){
				for (int l=0; l<numlayers; l++){
					try {
						tileid = layers.get(l)[r][c];
						if (tileid != 0) {
							g2d.drawImage(tilepics.get(tileid), null, x, y);
						}
					} catch (ArrayIndexOutOfBoundsException aie) {
						System.out.println(""+l+", "+r+", "+c);
					}
				}
				x += 32;
			}
			y += 32;
			x = 0;
		}
	}
	public BufferedImage getMapImage(){
		return mapbuffer;
	}

	public int getTileCols(){
		return tile_cols;
	}
	public int getTileRows(){
		return tile_rows;
	}
	/*
	public ArrayList<int[][]> getLayers(){
		return layers;
	}
	public String getTilesetFilename(){
		return tileset.getFilename();
	}
	public int getNumTiles(){
		return tileset.getNumTiles();
	}
	public int getTilesAcross(){
		return tileset.getTilesAcross();
	}
	public int getTilesDown(){
		return tileset.getTilesDown();
	}
	*/
}

