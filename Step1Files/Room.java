package Step1Files;
import java.util.ArrayList;
public class Room{
    private double posx;
    private double posy;
    private double width;
    private double height;
    private boolean visible;
    private int id;
    ArrayList <Creature> resides = new ArrayList <Creature> (); // package protected arraylist
     ArrayList<Passages> allPass = new ArrayList<Passages>();

    public Room(int _id){
        System.out.println("Creating room");
        id = _id;
        System.out.println("id set");
    }

    public int getid(){
        return id;
    }

    public void setX(double _posx){
        posx = _posx;
        System.out.println("X pos set");
    }

    public double getX(){
        return posx;
    }

    public void setY(double _posy){
        posy = _posy;
        System.out.println("Y pos set");
    }

    public double getY(){
        return posy;
    }

    public void setWidth(double _width){
        width = _width;
        System.out.println("Width set");
    }
    public double getWidth(){
        return width;
    }

    public void setHeight(double _height){
        height = _height;
        System.out.println("Height set");
    }

    public double getHeight(){
        return height;
    }

    public void setVisibility(int _visible){
        if (_visible == 1){
            visible = true;
        }
        else{
            visible = false;
        }
    }

    public boolean getVisibility(){
        return visible;
    }

    public ArrayList<Creature> getResides(){
        return resides;
    }
    public ArrayList<Passages> getPassage(){
        return allPass;
    }

    @Override
    public String toString( ) {
        String str = "Room: \n";
        str += "   Starting x-coordinate: "+posx + "\n";
        str += "   Starting y-coordinate "+posy + "\n";
        str += "   Room width: " + width + "\n";
        str += "   Height: " + height + "\n";
        str += "   Visibility Status " + visible +"\n";
        return str;
    }  
}