package Step1Files;
import java.util.ArrayList;
public class Item extends Displayable {
    private Creature owner;
    private ArrayList<Action> itemActions = new ArrayList<Action>();
    private int itemVal;

    public Item(){
        System.out.println("New item being parsed in");
    }
    public void addItem(Action item){
        itemActions.add(item);
    }
    public void addItemValue(int _itemVal){
        itemVal = _itemVal;
        System.out.println("Item value is " + itemVal);
    }
    public String getName(){
        return ""; // solely here for overriding 
    }
    
}
