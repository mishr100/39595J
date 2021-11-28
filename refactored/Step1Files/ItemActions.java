package Step1Files;

public class ItemActions extends Action {
    private String name;
    private String type;
    
    public ItemActions(String _name, String _type){
        System.out.println("New Item action created");
        name = _name;
        System.out.println("Item action name is " + name);
        type = _type;
        System.out.println("Item action type is " + type);

    }
}
