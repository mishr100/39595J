package Step1Files;

public class Sword extends Item {
    private String name;
    private int room;
    private int serial;
    public Sword(String _name, int _room, int _serial){
        System.out.println("New Sword being created");
        name = _name;
        System.out.println("Sword name is " + name);
        room = _room;
        System.out.println("Sword number is " + room);
        serial = _serial;
        System.out.println("Sword number is " + serial);
        this.setType(')');
    }
    
    
}
