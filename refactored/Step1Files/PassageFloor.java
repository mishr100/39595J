package Step1Files;
public class PassageFloor extends TraversableStructure {
    private int x;
    private int y;
    public PassageFloor(char ch){
        System.out.println("New passage floor point being created");
        this.setType(ch);
    }

    public void  setX(int _x){
        x = _x;
    }

    public void setY(int _y){
        y = _y;
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    
}
