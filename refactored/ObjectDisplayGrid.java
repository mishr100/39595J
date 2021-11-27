// creating the display 
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import Step1Files.*;


public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject{

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Stack<Displayable> [][] objectGrid = null;

    private List<InputObserver> inputObservers = null;

    private static int height;
    private static int width;
    public int playerxCord;
    public int playeryCord;

    public ObjectDisplayGrid(int _width, int _height) {
        width = _width;
        height = _height;

        terminal = new AsciiPanel(width, height);
        objectGrid = (Stack<Displayable>[][]) new Stack[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                objectGrid[i][j] = new Stack<Displayable>();
            }
        }
        System.out.println(objectGrid[0][0]);
        // objectGrid = (Stack<Displayable>[][])new Stack[width][height];
        // 2D for loop calling stack constructor

        initializeDisplay(); // create initial display 

        super.add(terminal);
        super.setSize(width * 9, height * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // super.repaint();
        // terminal.repaint( );
        super.setVisible(true);
        terminal.setVisible(true);
        terminal.addKeyListener(this);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint(); // repaints the screen ()
    }

    public void setPlayer(int x, int y){
        playerxCord = x;
        playeryCord = y;
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
        Player track = Player.buildPlayer("", 0, 0);
        if (keypress.getKeyChar() == 'h'){
            moveLeft(track);
        }
        else if(keypress.getKeyChar() == 'l'){
            moveRight(track);
        }
        else if(keypress.getKeyChar() == 'j'){
            moveDown(track);
        }
        else if(keypress.getKeyChar() == 'k'){
            moveUp(track);
        }
        
        
    }

    private void moveLeft(Player track){
        addObjectToDisplay(track, track.getX() - 1, track.getY());
        objectGrid[track.getX()][track.getY()].pop();
        System.out.println(objectGrid[track.getX()][track.getY()].peek().getType());
        addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY());
        track.setX(track.getX() - 1);
    }

    private void moveRight(Player track){
        addObjectToDisplay(track, track.getX() + 1, track.getY()); // add player to new location
        objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
        System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
        addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
        track.setX(track.getX() + 1);
    }

    private void moveDown(Player track){
        addObjectToDisplay(track, track.getX(), track.getY() + 1); // add player to new location
        objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
        System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
        addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
        track.setY(track.getY() + 1);
    }

    private void moveUp(Player track){
        addObjectToDisplay(track, track.getX(), track.getY() - 1); // add player to new location
        objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
        System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
        addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
        track.setY(track.getY() - 1);
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void initializeDisplay() {
        char ch = ' ';
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Displayable initial = new Displayable();
                initial.setType(' ');
                addObjectToDisplay(initial, i, j);
            }
        }
        terminal.repaint();
    }

    public void fireUp() {
        if (this.requestFocusInWindow()) {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow Succeeded");
        } else {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow FAILED");
        }
    }

    public void addObjectToDisplay(Displayable ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y].push(ch);
                writeToTerminal(x, y);
            }
        }
    }

    public char getObjectOnDisplay(int x, int y){
        return objectGrid[x][y].peek().getType();//peek
    }

    // add pop(x, y)

    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].peek().getType();//.getChar(); //peek
        terminal.write(ch, x, y); // staging a change for 
        terminal.repaint(); // executing the change 
    }
}
