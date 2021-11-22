
public class Player extends Creature {
    private String name;
    private int room;
    private int serial;
    public Player(String _name, int _room, int _serial){
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
