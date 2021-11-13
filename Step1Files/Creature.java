package Step1Files;
import java.util.ArrayList;
public class Creature {
    private double posx;
    private double posy;
    private char type;
    private int hp;
    private int maxHit;
    private int serial;
    private String name;
    private int room;
    private int hpMoves;
    ArrayList <CreatureAction> actions = new ArrayList <CreatureAction> ();

    public Creature(String _name, int _room, int _serial){
        System.out.println("Creating creature");
        name = _name;
        if (name.equals("Player")){
            System.out.println("Player Created");
        }
        else {
            System.out.println("Monster Created");
        }
        System.out.println("Name set");
        room = _room;
        System.out.println("Id set");
        serial = _serial;
    }

    public void setX(double _posx){
        posx = _posx;
        System.out.println("X pos set");
    }

    public void setY(double _posy){
        posy = _posy;
        System.out.println("Y pos set");
    }

    public void setType(char _type){
        type = _type;
        System.out.println("DEBUGGING: name is" + name);
        if(name.equals("Player")){
            type = '@';
        }
        System.out.println("Type set");
    }

    public void sethp(int _hp){
        hp = _hp;
        System.out.println("set Hp");
    }

    public void setmaxHit(int _maxHit){
        maxHit = _maxHit;
        System.out.println("MaxHit set");
    }
    public void setHPMoves(int _setHPMoves){
        hpMoves = _setHPMoves;
        System.out.println("hp moves set");
    }
    public char getType(){
        return type;
    }

    public double getX(){
        return posx;
    }

    public double getY(){
        return posy;
    }

    public String getName(){
        return name;
    }
    public String toString(){
        String str = "Creature " + name + "\n";
        str += "Xpos " + posx + "\n";
        str += "Ypos " + posy + "\n";
        str += "type " + type + "\n";
        return str;
    }



}
