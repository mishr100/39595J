import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import Step1Files.*;
public class Test implements Runnable {

    private static final int DEBUG = 0;
    private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid = null;
    private Thread keyStrokePrinter;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    public Test(int width, int height) {
        if(displayGrid == null){
            displayGrid = new ObjectDisplayGrid(width, height);
        }
    }

    @Override
    public void run() { // how we can alter the display
        displayGrid.fireUp();
  
    }
    public void roomPassageDriver(int xCord, int yCord, int width, int height, Dungeon playingSpace){
        // necessary declarations 
        int playerxCord = 0;
        int playeryCord = 0;
        ArrayList<Room> roomsToDisplay = playingSpace.getRooms(); // gets arraylist of rooms to display
        for(Room room: roomsToDisplay){
            xCord = room.getX();
            yCord = room.getY();
            width = room.getWidth();
            height = room.getHeight(); 
            createInitial(xCord, yCord, width, height);
            ArrayList<Item> available = room.getItems();
            displayItems(available);
            ArrayList<Creature> allCreat = room.getCreatures();
            displayCreatures(allCreat, xCord, yCord);
            ArrayList<Passage> allPass = room.getPassages();
            for (int i = 0; i < allPass.size(); i++){
                Passage pathway = allPass.get(i);
                ArrayList<Integer> horizontal = pathway.getXCord();
                ArrayList<Integer> vertical = pathway.getYCord();
                createPassage(horizontal, vertical);
                
            }
        }
        roomForDisplay(displayGrid, playingSpace.getTopHeight());
        writeInitialTop(displayGrid);
        writeInitialBottom(displayGrid);

        
    }

    private void displayItems(ArrayList<Item> display){
        for(int i = 0; i < display.size(); i++){
            displayGrid.addObjectToDisplay(display.get(i), display.get(i).getX(), display.get(i).getY());
        }
    }

    private void writeInitialTop(ObjectDisplayGrid displayGrid){
        
        Player player = Player.buildPlayer("", 0, 0);
        String hp = Integer.toString(player.getHp());
        String top = "HP: " + hp + " Score:  0";
        for(int i = 0; i < top.length(); i++){
            char write = top.charAt(i);
            //Displayable writing = new Displayable();
            //writing.setType(write);
            //displayGrid.addObjectToDisplay(writing, i, 0);
            displayGrid.terminalWrite(write,i,0);
        }
        displayGrid.repainter();
    }

    private void writeInitialBottom(ObjectDisplayGrid displayGrid){
        String firstBottom = "Pack: ";
        String secBottom = "Info: ";
        for(int i = 0; i < firstBottom.length(); i++){
            char write = firstBottom.charAt(i);
            //Displayable writing = new Displayable();
            //writing.setType(write);
            //displayGrid.addObjectToDisplay(writing, i, displayGrid.objectGrid[0].length - 3);
            displayGrid.terminalWrite(write,i,displayGrid.objectGrid[0].length - 3);
        }
        for(int i = 0; i < secBottom.length(); i++){
            char write = secBottom.charAt(i);
            //Displayable writing = new Displayable();
            //writing.setType(write);
            //displayGrid.addObjectToDisplay(writing, i, displayGrid.objectGrid[0].length - 1);
            displayGrid.terminalWrite(write,i,displayGrid.objectGrid[0].length - 1);
        }
    }

    
    private void roomForDisplay(ObjectDisplayGrid displayGrid, int topHeight){
        System.out.println(displayGrid.objectGrid[0].length);
        for(int x = 0; x < displayGrid.objectGrid.length; x++){
            for(int y = displayGrid.objectGrid[0].length - 8; y > -1; y--){
                Displayable display = new Displayable();
                display.setType(' ');
                displayGrid.addObjectToDisplay(displayGrid.getObjectOnDisplay(x,y), x, y+topHeight);
                displayGrid.addObjectToDisplay(display, x, y);
            }
        }
        Player player = Player.buildPlayer("", 0, 0);
        player.setY(player.getY() + topHeight);
        displayGrid.removeObjectFromDisplay(player.getX(), player.getY());
        displayGrid.addObjectToDisplay(new RoomFloor('.'), player.getX(), player.getY());
        displayGrid.addObjectToDisplay(player, player.getX(), player.getY());
    }
    

    private void displayCreatures(ArrayList<Creature> allCreat, int xCord, int yCord){
        for(int i = 0; i < allCreat.size(); i++){
            displayGrid.addObjectToDisplay(allCreat.get(i), (int) (allCreat.get(i).getX() + xCord), (int) (allCreat.get(i).getY() + yCord));
            allCreat.get(i).setX(allCreat.get(i).getX() + xCord);
            allCreat.get(i).setY(allCreat.get(i).getY() + yCord);
        }
    }

        private void createInitial(int xCord, int yCord, int width, int height){
            //displayGrid.fireUp();
            int endingy = 0;
            int endingx = 0;
            displayGrid.addObjectToDisplay(new RoomWall('X'), (int) xCord, (int) yCord);
    
            
            for(int i = 0; i < height; i++){
                displayGrid.addObjectToDisplay(new RoomWall('X'), (int) xCord, (int) (yCord + i));
                endingy = (int) yCord + i;
                
            }
            
            for(int k = 1; k < width; k++){
                displayGrid.addObjectToDisplay(new RoomWall('X'), (int) (xCord + k),  endingy);
            }
            
            for(int j = 0; j < width; j++){
                displayGrid.addObjectToDisplay(new RoomWall('X'), (int) (xCord + j), (int) (yCord));
                endingx = (int) xCord + j;
            }
            
            for(int l = 1; l < height; l++){
                displayGrid.addObjectToDisplay(new RoomWall('X'), endingx, (int) yCord + l);
            }
            for(int x = 1; x < width - 1;  x++){
                for(int y = 1; y < height - 1; y++ ){
                    displayGrid.addObjectToDisplay(new RoomFloor('.'), x + (int) xCord,  y + (int) yCord);
                }
            }    
        }

        private void createPassage(ArrayList<Integer> horizontal, ArrayList<Integer> vertical){
            int initialx;
            int initialy;
            int nextx;
            int nexty;
            char up;
            char down;
            char left;
            char right;
            for(int i = 0; i < horizontal.size() - 1; i++){
                initialx = horizontal.get(i);
                initialy = vertical.get(i);
                nextx = horizontal.get(i+1);
                nexty = vertical.get(i+1);
                System.out.println("(" + initialx + " ," + initialy + ")");
                System.out.println("(" + nextx + " ," + nexty + ")");
                System.out.println("---------------------------------------");
                if(i == 0){
                    displayGrid.addObjectToDisplay(new PassageJunction('+'), initialx, initialy);
                }
                else if(i == horizontal.size() - 2){
                    displayGrid.addObjectToDisplay(new PassageJunction('+'), nextx, nexty);
                }
                else{
                    displayGrid.addObjectToDisplay(new PassageFloor('#'), initialx, initialy);
                    displayGrid.addObjectToDisplay(new PassageFloor('#'), nextx, nexty);
                }
                int initialxCopy = initialx;
                while(initialx != nextx){
                    if (nextx > initialx){
                        displayGrid.addObjectToDisplay(new PassageFloor('#'), initialx + 1, initialy);
                        initialx += 1;
                    }
                    else {
                        displayGrid.addObjectToDisplay(new PassageFloor('#'), initialx - 1, initialy);
                        initialx -= 1;
                    }
                    
                }
                up = displayGrid.getObjectOnDisplay(initialx, initialy + 1).getType();
                down = displayGrid.getObjectOnDisplay(initialx, initialy - 1).getType();
                left = displayGrid.getObjectOnDisplay(initialx - 1, initialy).getType();
                right = displayGrid.getObjectOnDisplay(initialx + 1, initialy).getType();
                if(up == 'X' || down == 'X' || left == 'X' || right == 'X'){
                    displayGrid.addObjectToDisplay(new PassageJunction('+'), initialx, initialy);
                }
                while(initialy != nexty){
                    if(nexty > initialy){
                        displayGrid.addObjectToDisplay(new PassageFloor('#'), initialxCopy, initialy + 1);
                        initialy += 1;
                    }
                    else {
                        displayGrid.addObjectToDisplay(new PassageFloor('#'), initialxCopy, initialy - 1);
                        initialy -= 1;
                    }
                }
                up = displayGrid.getObjectOnDisplay(initialxCopy, initialy + 1).getType();
                down = displayGrid.getObjectOnDisplay(initialxCopy, initialy - 1).getType();
                left = displayGrid.getObjectOnDisplay(initialxCopy - 1, initialy).getType();
                right = displayGrid.getObjectOnDisplay(initialxCopy + 1, initialy).getType();
                if(up == 'X' || down == 'X' || left == 'X' || right == 'X'){
                    displayGrid.addObjectToDisplay(new PassageJunction('+'), initialxCopy, initialy);
                }
            }
        }
    
    
    public static void main(String[] args) throws Exception {
        	// check if a filename is passed in.  If not, print a usage message.
	// If it is, open the file
    String fileName = null;
    switch (args.length) {
    case 1:
       /******************************************************************
        * note that the relative file path may depend on what IDE you are
    * using.  You might needs to add to the beginning of the filename, 
        * e.g., filename = "src/xmlfiles/" + args[0]; worked for me in
        * netbeans, what is here works for me from the command line in
        * linux.
        ******************************************************************/
       fileName = args[0];
       break;
    default:
       System.out.println("java Test <xmlfilename>");
   return;
    }
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    try {
        SAXParser saxParser = saxParserFactory.newSAXParser();
        DungeonXMLHandler handler = new DungeonXMLHandler();
        saxParser.parse(new File(fileName), handler);
    } catch (ParserConfigurationException | SAXException | IOException e) {
        System.out.println(e.getMessage());
        //e.printStackTrace(System.out);
        return;
    }

    Dungeon playingSpace = Dungeon.buildDungeon(null, 0, 0, 0, 0);
    // extracting room information for the display

    Test test = new Test(playingSpace.getWidth(), playingSpace.getTopHeight() + playingSpace.getHeight() + playingSpace.getBottomHeight());
    Thread testThread = new Thread(test);
    testThread.start();
    // coordinate room and passage creation through variable initialization and roomPassageDriver function
    int xCord = 0;
    int yCord = 0;
    int width = 0;
    int height = 0;
    test.roomPassageDriver(xCord, yCord, width, height, playingSpace);

    // additional necessary multithreading for display
    test.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid));
    test.keyStrokePrinter.start();
    testThread.join();
    test.keyStrokePrinter.join();
    }
}