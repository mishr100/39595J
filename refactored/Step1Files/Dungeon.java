package Step1Files;
import java.util.ArrayList;
public class Dungeon {
    //<Dungeon name="Death" width="80" topHeight="2" gameHeight="41" bottomHeight="4">
    private String name;
    private int width;
    private int topHeight;
    private int gameHeight;
    private int bottomHeight;
    static Dungeon dungeon = null;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public static Dungeon buildDungeon (String _name, int _width, int _topHeight, int _gameHeight, int _bottomHeight) {
        if(dungeon == null){
            dungeon = new Dungeon(_name,  _width,  _topHeight,  _gameHeight,  _bottomHeight);
        }
        return dungeon;
    }

    private  Dungeon(String _name, int _width, int _topHeight, int _gameHeight, int _bottomHeight){
        name = _name;
        System.out.println(name);
        width = _width;
        System.out.println(width);
        topHeight = _topHeight;
        System.out.println(topHeight);
        gameHeight = _gameHeight;
        System.out.println(gameHeight);
        bottomHeight = _bottomHeight;
        System.out.print(bottomHeight);
    }

    
    public void addRoom(Room toAdd){
        rooms.add(toAdd);
    }

    public ArrayList <Room> getRooms() {
        return this.rooms;
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

    /*
    public void addCreature(Creature creature){
        // something to peek at the last element of the arraylist and add creature to that room object
    }

    public void addPassage(Passage passage){

    }

    public void addItem(Item item){

    }
    */


}
