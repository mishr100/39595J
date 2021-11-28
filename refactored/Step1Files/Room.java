package Step1Files;
import java.util.ArrayList;
public class Room extends Structure {
    private int roomNum;
    private ArrayList <Creature> things = new ArrayList<Creature> ();
    private ArrayList <Passage> passages = new ArrayList<Passage> ();
    private ArrayList <Item> roomItems = new ArrayList<Item>();
    
    public Room (int number){
        roomNum = number;
    }

    public void setCreature(Creature creature){
        things.add(creature);
    }

    public ArrayList<Creature> getCreatures(){
        return things;
    }

    public void setPassage(Passage passage){
        passages.add(passage);
    }

    public void setItem(Item item){
        roomItems.add(item);
        System.out.println("Added item to room");
    }

    public ArrayList<Passage> getPassages(){
        return passages;
    }

    public ArrayList<Item> getItems(){
        return roomItems;
    }
}
