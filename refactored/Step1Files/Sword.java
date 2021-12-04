package Step1Files;

public class Sword extends Item {
    private String name;
    private int room;
    private int serial;
    private int effectiveness;
    public Sword(String _name, int _room, int _serial){
        System.out.println("New Sword being created");
        name = _name;
        System.out.println("Sword name is " + name);
        room = _room;
        System.out.println("Sword number is " + room);
        serial = _serial;
        System.out.println("Sword number is " + serial);
        this.setType(')');
        String numInName = "";
        for(int i = 0; i < name.length(); i++){
            if(name.charAt(i) == '-' || Character.isDigit(name.charAt(i))){
                numInName += name.charAt(i);
            }
            if(name.charAt(i) == ' '){
                break;
            }
        }
        effectiveness = Integer.parseInt(numInName.trim());
        System.out.println("Effectiveness is " + numInName);
    }

    public String getName(){
        return name;
    }

    public void setSwordVal(int alter){
        effectiveness += alter;
    }

    public int getSwordVal(){
        return effectiveness;
    }

    public void setStringName(){
        name = "+" + effectiveness + " Sword";

    }
    
    
}
