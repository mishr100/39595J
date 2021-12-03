package Step1Files;
import java.util.ArrayList;
public class Player extends Creature {
    private String name;
    private int room;
    private int serial;
    static Player player;
    private ArrayList<Item> playerItems = new ArrayList<>();
    private Item isWearing = null;
    public static Player buildPlayer(String _name, int _room, int _serial){
        if(player ==  null){
           player = new Player(_name,  _room, _serial);
            
        }
        return player;
    }
    private Player(String _name, int _room, int _serial){
        System.out.println("New Player Created");
        name = _name;
        System.out.println("Player name " + name);
        room = _room;
        System.out.println("Player room " + room);
        serial = _serial;
        System.out.println("Player serial " + serial);
        this.setType('@');

    }
    public void addPlayerItem(Item playerItem){
        playerItems.add(playerItem);
        System.out.println("Added item to player");
    }

    public ArrayList<Item> getPlayerItems(){
        return playerItems;
    }

    public boolean isWearing(){
        if(isWearing != null){
            return true;
        }
        
        return false;
    }

    public Item getArmor(){
        return isWearing;
    }

    public void setWearing(Item item){
        isWearing = item;
    }
    
}
