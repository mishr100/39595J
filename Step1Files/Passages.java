package Step1Files;
import java.util.ArrayList;
public class Passages {
    private int room1;
    private int room2;
    public ArrayList<Integer> xCord= new ArrayList<Integer>();
    public ArrayList<Integer> yCord = new ArrayList<Integer>();

    public Passages(int _room1, int _room2){
        System.out.println("Passage created");
        room1 = _room1;
        System.out.println("Room one initialized");
        room2 = _room2;
        System.out.println("Room two initialized.");
    }

    public void addX(int _x){
        xCord.add(_x);
        System.out.println("X coordinate added to passages");
    }

    public void addY(int _y){
        yCord.add(_y);
        System.out.println("Y coordinate added to passages");
    }

    public ArrayList<Integer> getXCord(){
        return xCord;
    }
    public ArrayList<Integer> getYCord(){
        return yCord;
    }

}

