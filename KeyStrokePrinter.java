// defining changes to be made to display when a keyboard input is pressed 
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import Step1Files.*;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 1;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;
    int x = 0;
    int y = 0;

    public KeyStrokePrinter(ObjectDisplayGrid grid) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
        //processInput();
        
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {

        char ch;

        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() == null) {
                processing = false;
            } else {
                ch = inputQueue.poll();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                }
                if (ch == 'X') {
                    System.out.println("got an X, ending input checking");
                    return false;
                } else {
                    movement(ch);
                }
            }
            
            
        }
        return true;
    }

    private void movement(char pressed){
        System.out.println("character " + pressed + " entered on the keyboard");
        char holder = displayGrid.getObjectOnDisplay(displayGrid.playerxCord + 1, displayGrid.playeryCord + 1);
        displayGrid.objectGrid[displayGrid.playerxCord][displayGrid.playeryCord] = holder;
        displayGrid.objectGrid[displayGrid.playerxCord + 1][displayGrid.playeryCord + 1] = '@';
        
        //displayGrid.addObjectToDisplay('@', displayGrid.playerxCord + 1 , displayGrid.playeryCord + 1);
        //displayGrid.addObjectToDisplay(holder, displayGrid.playerxCord - 1, displayGrid.playeryCord - 1);
        displayGrid.playerxCord += 1;
        displayGrid.playeryCord += 1;
    }

    @Override
    public void run() {
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest();
            working = (processInput( ));
        }
    }
}
