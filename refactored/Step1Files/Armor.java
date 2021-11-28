package Step1Files;

public class Armor extends Item{
    private String name;
    private int room;
    private int serial;
    public Armor(String _name, int _room, int _serial){
        System.out.println("New Armor being created");
        name = _name;
        System.out.println("Armor name is " + name);
        room = _room;
        System.out.println("Armor number is " + room);
        serial = _serial;
        System.out.println("Armor number is " + serial);
        this.setType(']');
    }
    
}
