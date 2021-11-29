// creating the display 
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import Step1Files.*;
import java.lang.Math;
import java.util.Random;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject{

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
     public Stack<Displayable> [][] objectGrid = null;

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
        if(track.getHp() > 0){
            int origX = track.getX();
            int origY = track.getY();
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
            else if(keypress.getKeyChar() == 'd'){
                dropItem(track);
            }
            else if(keypress.getKeyChar() == 'i'){
                showPack(track);
            }
    }
        
        
    }

    private void clearRow(int y){
        for(int i = 0; i < this.objectGrid.length; i++){
            Displayable clear = new Displayable();
            clear.setType(' ');
            addObjectToDisplay(clear, i, y);
        }
    }

    private void showPack(Player track){
        String firstBottom = "Pack: ";
        clearRow(this.objectGrid[0].length - 3);
        ArrayList<Item> pack = track.getPlayerItems();
        for(int j = 0; j < pack.size(); j++){
            firstBottom += Integer.toString(j) + ": " + pack.get(j).getType() + " ";
        }
        if(pack.size() == 0){
            firstBottom = "Pack: ";
        }
        for(int i = 0; i < firstBottom.length(); i++){
            char write = firstBottom.charAt(i);
            Displayable writing = new Displayable();
            writing.setType(write);
            this.addObjectToDisplay(writing, i, this.objectGrid[0].length - 3);
        }
    }

    private void dropItem(Player track){
        ArrayList<Item> playerItems = track.getPlayerItems();
        objectGrid[track.getX()][track.getY()].add(objectGrid[track.getX()][track.getY()].size() - 1,playerItems.get(playerItems.size() - 1));
        playerItems.remove(playerItems.size() - 1);
        System.out.println(playerItems);
    }

    private boolean movementPossible(int x, int y){
        Displayable atPoint = getObjectOnDisplay(x,y);
        if (atPoint instanceof RoomFloor || atPoint instanceof PassageJunction || atPoint instanceof PassageFloor){
            return true;
        }
        return false;
    }

    private boolean pickUp(int x, int y){
        Player player = Player.buildPlayer("", 0, 0);
        Displayable atPoint = getObjectOnDisplay(x,y);
        if(atPoint instanceof Item){
            Item item = (Item) atPoint;
            player.addPlayerItem(item);
            System.out.println(getObjectOnDisplay(x,y).getType());
            objectGrid[x][y].pop();
            objectGrid[x][y].pop();
            System.out.println(objectGrid[x][y]);
            System.out.println(getObjectOnDisplay(x,y).getType());
            if (objectGrid[x][y].peek().getType() == ' '){
                RoomFloor floor = new RoomFloor('.');
                addObjectToDisplay(floor, x, y);
            }
            else{
                addObjectToDisplay(objectGrid[x][y].peek(), x, y);
            }
            ArrayList<Item> playerItem = player.getPlayerItems();
            System.out.println(playerItem);
            return true;
        }
        return false;
    }

    private boolean combat(int x, int y){
        Displayable atPoint = getObjectOnDisplay(x,y);
        if (atPoint instanceof Monster){
            // perform combat options
            int monsterMax = atPoint.getMaxHit();
            int randomMon = (int) (Math.random() * monsterMax + 1);
            //System.out.println(randomMon);
            Player player = Player.buildPlayer("", 0, 0);
            int playerMax = player.getMaxHit();
            int randomPlay = (int) (Math.random() * playerMax + 1);
            //System.out.println(randomPlay);
            clearRow(0);
            rewriteInfo(randomMon, randomPlay, player);

            return true;
            
            
        }
        return false;
    }

    // rewrites the info part and the hitpoints part as well
    private void rewriteInfo(int randomMon, int randomPlay, Player player){
        String secBottom = "Info: Monster attacked player -" + randomMon + " and player attacked monster - " + randomPlay;
        for(int i = 0; i < secBottom.length(); i++){
            char write = secBottom.charAt(i);
            Displayable writing = new Displayable();
            writing.setType(write);
            this.addObjectToDisplay(writing, i, this.objectGrid[0].length - 1);
        }
        int hp = player.getHp() - randomMon;
        player.setHp(hp);
        String top = "HP: " + hp + " Score: 0";
        for(int i = 0; i < top.length(); i++){
            char write = top.charAt(i);
            Displayable writing = new Displayable();
            writing.setType(write);
            this.addObjectToDisplay(writing, i, 0);
        }

    }

    //private void updateInfoSection()

    private void moveLeft(Player track){
        if(movementPossible(track.getX() - 1, track.getY())){
            addObjectToDisplay(track, track.getX() - 1, track.getY());
            objectGrid[track.getX()][track.getY()].pop();
            System.out.println(objectGrid[track.getX()][track.getY()].peek().getType());
            addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY());
            track.setX(track.getX() - 1);
        }
        else{
            combat(track.getX() - 1, track.getY());
            pickUp(track.getX() - 1, track.getY());
        }
    }

    private void moveRight(Player track){
        if(movementPossible(track.getX() + 1, track.getY())){
            addObjectToDisplay(track, track.getX() + 1, track.getY()); // add player to new location
            objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
            System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
            addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
            track.setX(track.getX() + 1);
        }
        else{
            combat(track.getX() + 1, track.getY());
            pickUp(track.getX() + 1, track.getY());
        }
    }

    private void moveDown(Player track){
        if(movementPossible(track.getX(), track.getY() + 1)){
            addObjectToDisplay(track, track.getX(), track.getY() + 1); // add player to new location
            objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
            System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
            addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
            track.setY(track.getY() + 1);
        }
        else{
            combat(track.getX(), track.getY() + 1);
            pickUp(track.getX(), track.getY() + 1);

        }
    }

    private void moveUp(Player track){
        if(movementPossible(track.getX(), track.getY() - 1)){
            addObjectToDisplay(track, track.getX(), track.getY() - 1); // add player to new location
            objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
            System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
            addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
            track.setY(track.getY() - 1);
            pickUp(track.getX(), track.getY() - 1);
            
        }
        else{
            combat(track.getX(), track.getY() - 1);
            pickUp(track.getX(), track.getY() - 1);
        }
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

    public Displayable getObjectOnDisplay(int x, int y){
        return objectGrid[x][y].peek();//peek
    }

    public void removeObjectFromDisplay(int x, int y){
        objectGrid[x][y].pop();
    }

    // add pop(x, y)

    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].peek().getType();//.getChar(); //peek
        terminal.write(ch, x, y); // staging a change for 
        terminal.repaint(); // executing the change 
    }
}
