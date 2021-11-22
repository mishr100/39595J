
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

    public void setMaxHit(int _maxHit){
        maxHit = _maxHit;
        System.out.println("Max Hit is " + maxHit);
    }

    public void setHpMoves(int _hpMoves){
        hpMoves = _hpMoves;
        System.out.println("Hp Moves is "+ hpMoves);
    }

    public void addAction(Action action){
        allActions.add(action);
        System.out.println("Added action");
    }


}
