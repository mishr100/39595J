package Step1Files;
import java.util.ArrayList;
public class Creature extends Displayable {
    private int hp;
    private int maxHit;
    private int hpMoves;
    private ArrayList<Action> allActions = new ArrayList<>();

    public Creature(){
        System.out.println("New Creature Created");
    }

    public void setHp(int _hp){
        hp = _hp;
        System.out.println("Hp is " + hp);
    }

    public int getHp(){
        return hp;
    }

    public void setMaxHit(int _maxHit){
        maxHit = _maxHit;
        System.out.println("Max Hit is " + maxHit);
    }

    public int getMaxHit(){
        return maxHit;
    }

    public void setHpMoves(int _hpMoves){
        hpMoves = _hpMoves;
        System.out.println("Hp Moves is "+ hpMoves);
    }

    public int getHpMoves(){
        return hpMoves;
    }

    public void addAction(Action action){
        allActions.add(action);
        System.out.println("Added action");
    }

    // returns the specific action at a particular index of the Action array 
    public Action getSpecificAction(int index){
        if(index >= 0 && index < allActions.size()){
            return allActions.get(index);
        }
        return null;
    }


}
