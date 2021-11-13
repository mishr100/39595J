package Step1Files;
public class CreatureAction {
    private String name;
    private String type;
    private String actionMessage;
    private int actionIntValue;
    private String actionCharValue;

    public CreatureAction(String _name, String _type){
        System.out.println("New creature action being created");
        name = _name;
        type = _type;
    }

    public void setActionMessage(String _actionMessage){
        actionMessage = _actionMessage;
        System.out.println("New Action Message set.");
    }

    public void setActionIntValue(int _actionIntVal){
        actionIntValue = _actionIntVal;
        System.out.println("New Action int value set.");
    }
    public void setActionCharValue(String _actionCharVal){
        actionCharValue = _actionCharVal;
        System.out.println("New Action char value set.");
    }
}
