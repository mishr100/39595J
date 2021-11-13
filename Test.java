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

    public void roomPassageDriver(double xCord, double yCord, double width, double height){
        // necessary declarations 
        int playerxCord = 0;
        int playeryCord = 0;
        ArrayList<Room> roomsToDisplay = playingSpace.getRooms(); // gets arraylist of rooms to display
        for(Room room: roomsToDisplay){
            xCord = room.getX();
            yCord = room.getY();
            width = room.getWidth();
            height = room.getHeight();
            createInitial(xCord, yCord, width,height);
            ArrayList <Creature> allCreat = room.getResides();
            for(int i = 0; i < allCreat.size(); i++){
                System.out.println(allCreat.get(i));
                if(allCreat.get(i).getName().equals("Player")){
                    System.out.println("GETS NAME RIGHT");
                    displayGrid.addObjectToDisplay('@', (int) (allCreat.get(i).getX() + xCord), (int) (allCreat.get(i).getY() + yCord));
                    playerxCord = (int) allCreat.get(i).getX() + (int) xCord;
                    playeryCord = (int) allCreat.get(i).getY() + (int) yCord;
                    displayGrid.setPlayer(playerxCord, playeryCord);
                }
                else {
                    displayGrid.addObjectToDisplay((char) allCreat.get(i).getType(), (int) (allCreat.get(i).getX() + xCord), (int) (allCreat.get(i).getY() + yCord));
                }
            }
            ArrayList <Passages> passage = room.getPassage();
            for (int i = 0; i < passage.size(); i++){
                Passages pathway = passage.get(i);
                ArrayList<Integer> horizontal = pathway.getXCord();
                ArrayList<Integer> vertical = pathway.getYCord();
                createPassage(horizontal, vertical);
                
            }
            
        }
    }


    // outlines the dimension of the room with X's and the inside with proper 0s
    public void createInitial(double xCord, double yCord, double width, double height){
        //displayGrid.fireUp();
        int endingy = 0;
        int endingx = 0;
        displayGrid.addObjectToDisplay('X', (int) xCord, (int) yCord);

        
        for(int i = 0; i < height; i++){
            displayGrid.addObjectToDisplay('X', (int) xCord, (int) (yCord + i));
            endingy = (int) yCord + i;
            
        }
        
        for(int k = 1; k < width; k++){
            displayGrid.addObjectToDisplay('X', (int) (xCord + k),  endingy);
        }
        
        for(int j = 0; j < width; j++){
            displayGrid.addObjectToDisplay('X', (int) (xCord + j), (int) (yCord));
            endingx = (int) xCord + j;
        }
        
        for(int l = 1; l < height; l++){
            displayGrid.addObjectToDisplay('X', endingx, (int) yCord + l);
        }

        System.out.println("LOOK HERE" + xCord);
        System.out.println("LOOK HERE" + yCord);
        for(int x = 1; x < width - 1;  x++){
            for(int y = 1; y < height - 1; y++ ){
                displayGrid.addObjectToDisplay('.', x + (int) xCord,  y + (int) yCord);
            }
        }    
    }

    public void createPassage(ArrayList<Integer> horizontal, ArrayList<Integer> vertical){
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
                displayGrid.addObjectToDisplay('+', initialx, initialy);
            }
            else if(i == horizontal.size() - 2){
                displayGrid.addObjectToDisplay('+', nextx, nexty);
            }
            else{
                displayGrid.addObjectToDisplay('#', initialx, initialy);
                displayGrid.addObjectToDisplay('#', nextx, nexty);
            }
            int initialxCopy = initialx;
            while(initialx != nextx){
                if (nextx > initialx){
                    displayGrid.addObjectToDisplay('#', initialx + 1, initialy);
                    initialx += 1;
                }
                else {
                    displayGrid.addObjectToDisplay('#', initialx - 1, initialy);
                    initialx -= 1;
                }
                
            }
            up = displayGrid.getObjectOnDisplay(initialx, initialy + 1);
            down = displayGrid.getObjectOnDisplay(initialx, initialy - 1);
            left = displayGrid.getObjectOnDisplay(initialx - 1, initialy);
            right = displayGrid.getObjectOnDisplay(initialx + 1, initialy);
            if(up == 'X' || down == 'X' || left == 'X' || right == 'X'){
                displayGrid.addObjectToDisplay('+', initialx, initialy);
            }
            while(initialy != nexty){
                if(nexty > initialy){
                    displayGrid.addObjectToDisplay('#', initialxCopy, initialy + 1);
                    initialy += 1;
                }
                else {
                    displayGrid.addObjectToDisplay('#', initialxCopy, initialy - 1);
                    initialy -= 1;
                }
            }
            up = displayGrid.getObjectOnDisplay(initialxCopy, initialy + 1);
            down = displayGrid.getObjectOnDisplay(initialxCopy, initialy - 1);
            left = displayGrid.getObjectOnDisplay(initialxCopy - 1, initialy);
            right = displayGrid.getObjectOnDisplay(initialxCopy + 1, initialy);
            if(up == 'X' || down == 'X' || left == 'X' || right == 'X'){
                displayGrid.addObjectToDisplay('+', initialxCopy, initialy);
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
        e.printStackTrace(System.out);
    }
        // create dungeon and start multithreading to view it 
        Dungeon playingSpace = Dungeon.buildDungeon(null, 0, 0, 0, 0);
        Test test = new Test(playingSpace.getWidth(), playingSpace.getHeight());
        Thread testThread = new Thread(test);
        testThread.start();
        // coordinate room and passage creation through variable initialization and roomPassageDriver function
        double xCord = 0;
        double yCord = 0;
        double width = 0;
        double height = 0;
        test.roomPassageDriver(xCord, yCord, width, height);

        // additional necessary multithreading for display
        test.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid));
        test.keyStrokePrinter.start();
        testThread.join();
        test.keyStrokePrinter.join();
    }
}
