package Step1Files;
import java.util.ArrayList;
public class Passage extends Structure {
    String connection = ""; // between which two rooms the passage exists between
    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();
    public Passage(String room1, String room2){
        System.out.println("New passage created");
        connection = room1 + room2;
    }
    public void addX (Integer _x){
        x.add(_x);
        System.out.println("X added " + x.get(x.size() - 1));
        
    }

    public void addY (Integer _y){
        y.add(_y);
        System.out.println("Y added " + y.get(y.size() - 1));
        
    }

    public ArrayList<Integer> getXCord (){
        return x;
    }

    public ArrayList<Integer> getYCord(){
        return y;
    }
}
