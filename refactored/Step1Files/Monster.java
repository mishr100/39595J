package Step1Files;
public class Monster extends Creature {
    private String name;
    private int room;
    private int serial;

    public Monster(String _name, int _room, int _serial){
        System.out.println("New Monster Created");
        name = _name;
        System.out.println("Monster name " + name);
        room = _room;
        System.out.println("Monster room " + room);
        serial = _serial;
        System.out.println("Monster serial " + serial);
    }

    
    
}
