package Step1Files;
public class Displayable{
    protected boolean visible;
    protected int maxHit;
    protected int hpMoves;
    protected int hp;
    protected char playerType;
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected char type;
    
    
    public Displayable(){
        //System.out.println("New displayable object created");
    }

    public void setInvisible(){
        visible = false;
    }

    public void setVisible(){
        visible = true;
    }

    public void setX(int x){
        xPos = x;
        System.out.println("X pos is " + xPos);
    }

    public void setY(int y){
        yPos = y;
        System.out.println("Y pos is " + yPos);
    }

    public void setWidth(int _width){
        width = _width;
        System.out.println("Width is " + width);
    }

    public void setHeight(int _height){
        height = _height;
        System.out.println("Height is " + height);
    }

    public void setType(char _type){
        type = _type;
        //System.out.println("Type is " + type);
    }

    public int getMaxHit(){
        return 0;
    }

    public char getType(){
        return type;
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}