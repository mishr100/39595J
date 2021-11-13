package Step1Files;
import java.util.ArrayList;
public class Dungeon {
    //<Dungeon name="testDrawing" width="80" topHeight="2" gameHeight="34" bottomHeight="4">
    private String name;
    private int width;
    private int topHeight;
    private int gameHeight;
    private int bottomHeight;
    private ArrayList <Room> allRooms = new ArrayList<Room>();
    static Dungeon dungeon = null;

    public static Dungeon buildDungeon (String _name, int _width, int _topHeight, int _gameHeight, int _bottomHeight) {
        if(dungeon == null){
            dungeon = new Dungeon(_name,  _width,  _topHeight,  _gameHeight,  _bottomHeight);
        }
        return dungeon;
    }
    private Dungeon(String _name, int _width, int _topHeight, int _gameHeight, int _bottomHeight){
        name = _name;
        width = _width;
        topHeight = _topHeight;
        gameHeight = _gameHeight;
        bottomHeight = _bottomHeight;
        System.out.println("Creating dungeon with the specified attributes");
    }

    public ArrayList <Room> getRooms() {
        return this.allRooms;
    }
    public String getName(){
        return this.name;
    }

    public int getWidth(){
        return this.width;
    }

    public int getTopHeight(){
        return this.topHeight;
    }

    public int getHeight(){
        return this.gameHeight;
    }
    public int getBottomHeight(){
        return this.bottomHeight;
    }

}
