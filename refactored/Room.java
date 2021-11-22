
import java.util.ArrayList;
public class Room extends Structure {
    private int roomNum;
    private ArrayList <Creature> things = new ArrayList<Creature> ();
    
    public Room (int number){
        roomNum = number;
    }

    public void setCreature(Creature creature){
        things.add(creature);
    }
}
