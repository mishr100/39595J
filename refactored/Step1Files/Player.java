package Step1Files;
public class Player extends Creature {
    private String name;
    private int room;
    private int serial;
    static Player player;

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
    
}
