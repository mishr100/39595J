package Step1Files;
public class CreatureAction extends Action{
    private String name;
    private String type;
    
    public CreatureAction(String _name, String _type){
        System.out.println("New Creature action created");
        name = _name;
        System.out.println("Creature action name is " + name);
        type = _type;
        System.out.println("Creature action type is " + type);

    }

    public String getName(){
        return name;
    }
}
