public class Tileset{
    private String filename;
    private int firstgid;
    private int width, height;
    private final int TILE_RES = 32;
    private int tilesdown, tilesacross;

    public Tileset(){
    }

    public String getFilename(){
        return filename;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }

    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
        tilesacross = width/TILE_RES;
    }

    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
        tilesdown = height/TILE_RES;
    }

    public int getFirstgid(){
        return firstgid;
    }
    public void setFirstgid(int firstgid){
        this.firstgid = firstgid;
    }

    public String toString(){
        return "Source: "+filename+"\nwidth: "+width+", height: "+height+"\nFirst gid starts at "+firstgid;
    }
    public int getNumTiles(){
        return height*width/(TILE_RES*TILE_RES);
    }
    public int getTilesAcross(){
        return tilesacross;
    }
    public int getTilesDown(){
        return tilesdown;
    }
}
