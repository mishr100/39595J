package Step1Files;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
public class DungeonXMLHandler extends DefaultHandler {
    private static final int DEBUG = 1;
    private Room roomToParse = null;
    private Dungeon dungeonToParse = null;
    private Creature toAdd = null;
    private StringBuilder data = null;
    private Passage newPass = null;
    private Integer xHolder = 0;
    private PassageFloor point = null;
    //private ArrayList<Passages> allPass = new ArrayList<Passages>();
    //private ArrayList<Room> allRooms = new ArrayList<Room>();
    private Action action = null;
    private static final String CLASSID = "DungeonXMLHandler";
    private String typeParsing = null; // tells us what is currently being parsed since there are classes that use same attributes
    public DungeonXMLHandler() {
    }
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (DEBUG > 1) {
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }
        //<Dungeon name="testDrawing" width="80" topHeight="2" gameHeight="34" bottomHeight="4">
        if(qName.equalsIgnoreCase("Dungeon")){
            String name = attributes.getValue("name"); // gets the name of the dungeon
            int width = Integer.parseInt(attributes.getValue("width")); // width of dungeon
            int topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
            typeParsing = "Dungeon";
            dungeonToParse = Dungeon.buildDungeon(name, width, topHeight, gameHeight, bottomHeight);
        }

        else if (qName.equalsIgnoreCase("Room")) {
            typeParsing = "Room";
            int roomId = Integer.parseInt(attributes.getValue("room"));
            roomToParse = new Room(roomId);
            dungeonToParse.addRoom(roomToParse);
        }

        else if(qName.equalsIgnoreCase("Monster")){
            typeParsing = "Creature";
            int room = Integer.parseInt(attributes.getValue("room"));
            String name = attributes.getValue("name");
            int serial = Integer.parseInt(attributes.getValue("serial"));
            toAdd = new Monster(name, room, serial);
            roomToParse.setCreature(toAdd);
            System.out.println("added creature");
        }
        else if(qName.equalsIgnoreCase("Passage")){
            typeParsing = "Passage";
            String room1 = attributes.getValue("room1");
            String room2 = attributes.getValue("room2");
            newPass = new Passage(room1, room2);
            roomToParse.setPassage(newPass);
        }
        else if(qName.equalsIgnoreCase("Player")){
            typeParsing = "Creature";
            int room = Integer.parseInt(attributes.getValue("room"));
            String name = attributes.getValue("name");
            int serial = Integer.parseInt(attributes.getValue("serial"));
            toAdd = Player.buildPlayer(name, room, serial);
            roomToParse.setCreature(toAdd);
            System.out.println("add creature");
        }
        else if(qName.equalsIgnoreCase("CreatureAction")){
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            action = new CreatureAction(name, type);
            toAdd.addAction(action);

        }
        data = new StringBuilder();
    } 
        /***************************************************************
         * instructor, credit, name, meetingTIme, meetingDay, number 
         * and location are handled in endElement.
         **************************************************************/
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("posX") && typeParsing.equals("Room")){
            roomToParse.setX(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("posY") && typeParsing.equals("Room")){
            roomToParse.setY(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("width") && typeParsing.equals("Room")){
            roomToParse.setWidth(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("height") && typeParsing.equals("Room")){
            roomToParse.setHeight(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("posX") && typeParsing.equals("Creature")){
            toAdd.setX(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("posY") && typeParsing.equals("Creature")){
            toAdd.setY(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("type") && typeParsing.equals("Creature")){
            toAdd.setType(data.toString().charAt(0));
        }
        else if(qName.equalsIgnoreCase("hp") && typeParsing.equals("Creature")){
            toAdd.setHp(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("maxhit") && typeParsing.equals("Creature")){
            toAdd.setMaxHit(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("maxhit") && typeParsing.equals("Creature")){
            toAdd.setMaxHit(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("hpMoves") && typeParsing.equals("Creature")){
            toAdd.setHpMoves(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("actionMessage")){
            action.setMessage(data.toString());
        }
        else if(qName.equalsIgnoreCase("actionIntValue")){
            action.setIntValue(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("actionIntValue")){
            action.setIntValue(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("actionCharValue")){
            action.setCharValue(data.toString());
        }
        else if(qName.equalsIgnoreCase("posX") && typeParsing.equals("Passage")){
            newPass.addX(Integer.parseInt(data.toString()));
        }
        else if(qName.equalsIgnoreCase("posY") && typeParsing.equals("Passage")){
            newPass.addY(Integer.parseInt(data.toString()));
        }
        
    }
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
            System.out.flush();
        }
    }
    @Override
    public String toString() {
        String str = "StudentsXMLHandler\n";
        return str;
    }
}