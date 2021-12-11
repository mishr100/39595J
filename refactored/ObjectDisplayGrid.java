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
    private static final int infoRow = 1;
    private static final int packRow = 3;

    private static AsciiPanel terminal;
     public Stack<Displayable> [][] objectGrid = null;

    private List<InputObserver> inputObservers = null;

    private static int height;
    private static int width;
    public int playerxCord;
    public int playeryCord;
    private boolean endGame = false;
    private boolean pressedE = false;
    private Integer index = null;
    private char letterPressed = 'z'; // for letters w,t,r, and d
    private int hallucinationLeft = 0; // number of moves player has left under hallucination 
    private Scroll hallucinateScroll = null;
    private int score = 0;
    private Random rand = new Random();
    private static final ArrayList<Character> allCommands= new ArrayList<Character>() {{
        add('h');
        add('j');
        add('k');
        add('l');
        add('i');
        add('p');
        add('d');
        add('E');
        add('r');
        add('T');
        add('c');
        add('w');
        add('?');
    }};
    private static final ArrayList<Displayable> allCharacters = new ArrayList<Displayable>() {{
        add(new Displayable('.'));
        add(new Displayable('#'));
        add(new Displayable(']'));
        add(new Displayable(')'));
        add(new Displayable('?'));
        add(new Displayable('H'));
        add(new Displayable('S'));
        add(new Displayable('T'));
        add(new Displayable('X'));
    }};

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
        //System.out.println(objectGrid[0][0]);
        // objectGrid = (Stack<Displayable>[][])new Stack[width][height];
        // 2D for loop calling stack constructor

        initializeDisplay(); // create initial display 

        super.add(terminal);
        super.setSize(width * 9, height * 17);
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
            //System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            //System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
        Player track = Player.buildPlayer("", 0, 0);
        if(track.getHp() > 0 && endGame == false){
            int origX = track.getX();
            int origY = track.getY();
            if(letterPressed == 'H' && allCommands.contains(keypress.getKeyChar())){
                char letter = keypress.getKeyChar();
                if(letter == 'h'){
                    infoSection("Info: press h to move left");
                }
                else if(letter == 'j'){
                    infoSection("Info: press j to move down");
                }
                else if(letter == 'k'){
                    infoSection("Info: press k to move up");
                }
                else if(letter == 'l'){
                    infoSection("Info: press l to move right");
                }
                else if(letter == 'T'){
                    infoSection("Info: press T and a number to wield sword from particular pack position");
                }
                else if(letter == 'w'){
                    infoSection("Info: press w and a number to wear armor from particular pack position");
                }
                else if(letter == 'c'){
                    infoSection("Info: press c to stop wearing armor");
                }
                else if(letter == 'i'){
                    infoSection("Info: press i to see contents of the player's pack");
                }
                else if(letter == 'p'){
                    infoSection("Info: press p to pick up an item player is standing on");
                }
                else if(letter == 'd'){
                    infoSection("Info: press d and a number to drop item from particular pack position");
                }
                else if(letter == 'E'){
                    infoSection("Info: press E and y or Y to end the game");
                }
                else if(letter == 'r'){
                    infoSection("Info: press r and a number to read a scroll from particular pack position");
                }
                else if(letter == '?'){
                    infoSection("Info: press ? to see list of letters representing commands");
                }

                letterPressed = 'z';
            }
            else if (keypress.getKeyChar() == 'h'){
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
                letterPressed = 'd';
            }
            else if(keypress.getKeyChar() == 'i'){
                showPack(track);
            }
            else if(keypress.getKeyChar() == 'p'){
                pickUp(track.getX(), track.getY());
            }
            else if(keypress.getKeyChar() == 'E'){
                infoSection("Info: Press y or Y to end game");
                pressedE = true;
                
            }
            else if(keypress.getKeyChar() == 'H'){
                letterPressed = 'H';
            }
            else if(pressedE && (keypress.getKeyChar() == 'Y' || keypress.getKeyChar() == 'y' )){
                infoSection("Info: Game quit by user");
                endGame = true;
            }
            else if(keypress.getKeyChar() == '?'){
                infoSection("Info: Possible commands: h,j,k,l,c,w,d,E,H,i,r,T,?");
            }
            else if(keypress.getKeyChar() == 'c'){
                removeArmor(track);
            }
            else if(keypress.getKeyChar() == 'w'){
                //addArmor(track);
                letterPressed = 'w';

            }
            else if(keypress.getKeyChar() == 'T'){
                letterPressed = 'T';
            }
            else if(keypress.getKeyChar() == 'r'){
                letterPressed = 'r';
            }
            else if(Character.isDigit(keypress.getKeyChar())){
                if(letterPressed == 'w'){
                    addArmor(track, keypress.getKeyChar());
                    letterPressed = 'z'; // sort of the reset 
                }
                else if(letterPressed == 'd'){
                    dropItem(track, keypress.getKeyChar());
                }
                else if(letterPressed == 'T'){
                    wieldSword(track, keypress.getKeyChar());
                }
                else if(letterPressed == 'r'){
                    readScroll(track,keypress.getKeyChar());
                }
            }
    }
    else if(track.getHp() <= 0){
        infoSection("Info: Player is dead. Game over!");
    }  
    }

    private void readScroll(Player track, char num){
        ArrayList<Item> playerItems = track.getPlayerItems();
        int numb = num - '0';
        numb = numb - 1;
        if(numb >= playerItems.size()){
            infoSection("Info: No such item exists");
            return;
        }
        Item toRead = playerItems.get(numb);
        if(toRead instanceof Scroll){
            Scroll reading = (Scroll) toRead;
            infoSection("Info: New scroll being read: " + toRead.getName());
            ItemActions stored = reading.getScrollAction();
            infoSection(stored.getName());
            if(stored.getCharValue() != null && stored.getCharValue().equals("a") && track.isWearing()){
                infoSection("Info: " + stored.getMessage() + " " + stored.getIntValue() + " to " +   "" + track.getArmor().getName());
                Armor armor = (Armor) track.getArmor();
                armor.setArmorVal(stored.getIntValue());
                armor.setName();
            }
            else if(stored.getCharValue() != null && stored.getCharValue().equals("a") && !track.isWearing()){
                infoSection("Info: " + reading.getName() + " of cursing/blessing does nothing because no armor is being worn");

            }
            else if(stored.getCharValue() != null  && stored.getCharValue().equals("w") && track.isWielding()){
                infoSection("Info: " + stored.getMessage() + " " + stored.getIntValue() + " to " + "" + track.getSword().getName());
                //playerItems.remove(numb);
                Sword sword = (Sword) track.getSword();
                sword.setSwordVal(stored.getIntValue());
            }
            else if(stored.getCharValue() != null  && stored.getCharValue().equals("w") && !track.isWielding()){
                infoSection("Info: " + reading.getName() + " of cursing/blessing does nothing because no sword is being used");
            }
            else if(stored.getName().equals("Hallucinate")){
                hallucinationLeft = stored.getIntValue() + 1;
                infoSection(stored.getMessage());
                hallucinateScroll = reading;
                hallucinate(reading);
            }
            playerItems.remove(numb);
        }
        else{
            infoSection("Info: Identified Item isn't a scroll.");
        }
    }

    // should also use the hallucination left variable 
    private void hallucinate(Scroll reading){
        infoSection("Info: Hallucination left for " + hallucinationLeft + " moves");
        if (hallucinationLeft > 0){
            for(int i = 0; i < objectGrid.length; i++){
                for(int j = 0; j < objectGrid[0].length; j++){
                    if(objectGrid[i][j].peek().getType() != ' ' && objectGrid[i][j].peek().getType() != '@' ){
                        Random r= new Random();        
      	                int randomNumber=r.nextInt(allCharacters.size());
      	                terminal.write(allCharacters.get(randomNumber).getType(),i, j );
                    }
                }
            }
        }
        else {
            for(int i = 0; i < objectGrid.length; i++){
                for(int j = 0; j < objectGrid[0].length; j++){
                    if(getObjectOnDisplay(i,j).getType() != ' '){
                        terminal.write(objectGrid[i][j].peek().getType(), i, j);
                    }
                }
            }
        }
        terminal.repaint();
    }

    private void wieldSword(Player track, char num){
        ArrayList<Item> playerItems = track.getPlayerItems();
        int numb = num - '0';
        numb = numb - 1;
        if(numb >= playerItems.size()){
            infoSection("Info: No such item exists");
            return;
        }
        Item toWield = playerItems.get(numb);
        if(track.isWielding()){
            Sword wielding = (Sword) track.getSword();
            infoSection("Info: Sword already being wielded: " + wielding.getName());
        }
        else if(toWield instanceof Sword){
            Sword wielding = (Sword) toWield;
            track.setWielding(wielding);
            infoSection("Info: New sword being wielded: " + wielding.getName());
        }
        else{
            infoSection("Info: Identified Item isn't sword.");
        }
    }
    private int getInfoRow(){
        return this.objectGrid[0].length - 1;
    }

    private int getPackRow(){
        return this.objectGrid[0].length - 3;
    }
    private void addArmor(Player track, char number){
        ArrayList<Item> playerItems = track.getPlayerItems();
        int num = number - '0';
        num = num - 1;
        if(num >= playerItems.size()){
            infoSection("Info: No such item exists");
            return;
        }
        Item toWear = playerItems.get(num);
        if(track.isWearing()){
            Armor wearing = (Armor) track.getArmor();
            infoSection("Info: Armor already being worn: " + wearing.getName());
        }
        else if(toWear instanceof Armor){
            Armor wearing = (Armor) toWear;
            track.setWearing(toWear);
            infoSection("Info: New item being worn: " + wearing.getName());
        }
        else{
            infoSection("Info: Identified Item isn't armor.");
        }
    }
    
    private void removeArmor(Player track){
        if(track.isWearing() == false){
            infoSection("Info: No armor being worn");
        }
        else{
            track.setWearing(null);
            infoSection("Info: Armor removed");
        }

    }

    private void infoSection(String str){
        int i = 0;
        for(i = 0; i < str.length(); i++){
            char write = str.charAt(i);
            Displayable writing = new Displayable();
            writing.setType(write);
            //this.addObjectToDisplay(writing, i, this.objectGrid[0].length - 1);
            terminal.write(str.charAt(i), i, getInfoRow());
        }
        for(;i<objectGrid.length; i++){
            terminal.write(' ', i, getInfoRow());
        }
        terminal.repaint();

    }

    private void showPack(Player track){
        String firstBottom = "Pack: ";
        String itemName = "";
        //clearRow(this.objectGrid[0].length - 3);
        ArrayList<Item> pack = track.getPlayerItems();
        for(int j = 0; j < pack.size(); j++){
            if(pack.get(j) == track.getArmor()){
                itemName = track.getArmor().getName();
                itemName += " (a)";
            }
            else if(pack.get(j) == track.getSword()){
                itemName = track.getSword().getName() + " (w)";
            }
            else{
                System.out.println("Item name is " + pack.get(j).getName());
                itemName = pack.get(j).getName();
            }
            firstBottom += Integer.toString(j+1) + ": " + itemName + " ";
        }
        if(pack.size() == 0){
            firstBottom = "Pack: ";
        }
        int i = 0;
        for(i = 0; i < firstBottom.length(); i++){
            /*
            char write = firstBottom.charAt(i);
            Displayable writing = new Displayable();
            writing.setType(write);
            this.addObjectToDisplay(writing, i, this.objectGrid[0].length - 3);
            */
            terminal.write(firstBottom.charAt(i), i, getPackRow());
        }
        for(;i<objectGrid.length; i++){
            terminal.write(' ', i, getPackRow());
        }

        terminal.repaint();
    }

    private void dropItem(Player track, char num){
        ArrayList<Item> playerItems = track.getPlayerItems();
        int numb = num - '0';
        numb = numb - 1;
        System.out.println("Number of items in pack " + numb);
        if(numb >= playerItems.size()){
            infoSection("Info: No such item exists");
            return;
        }
        Displayable player = removeObjectFromDisplay(track.getX(), track.getY());
        addObjectToDisplay(playerItems.get(numb), track.getX(), track.getY());
        addObjectToDisplay(player, track.getX(), track.getY());
        if(playerItems.get(numb) == track.getArmor()){
            track.setWearing(null);
            infoSection("Info: Armor dropped, no longer being worn");
        }
        if(playerItems.get(numb) == track.getSword()){
            track.setWielding(null);
            infoSection("Info: Sword dropped, no longer being wielded");
        }
        playerItems.remove(numb);
        System.out.println(playerItems);
    }

    private boolean movementPossible(int x, int y){
        Displayable atPoint = getObjectOnDisplay(x,y);
        if (atPoint instanceof RoomFloor || atPoint instanceof PassageJunction || atPoint instanceof PassageFloor || atPoint instanceof Item){
            return true;
        }
        return false;
    }

    private boolean pickUp(int x, int y){
        Player player = Player.buildPlayer("", 0, 0);
        //Stack<Displayable> allChar = objectGrid[x][y];
        Player hold = (Player) removeObjectFromDisplay(x,y);
        System.out.println("Stack before pickup: " + objectGrid[x][y]);
        Displayable atPoint = getObjectOnDisplay(x,y);
        if(atPoint instanceof RoomFloor){
            return false;
        }
        if(atPoint instanceof Item){
            Item item = (Item) atPoint;
            player.addPlayerItem(item);
            Displayable removed = removeObjectFromDisplay(x,y);
            System.out.println("TOp of stack: " + getObjectOnDisplay(x,y).getType());
            if (getObjectOnDisplay(x,y).getType()== ' '){
                RoomFloor floor = new RoomFloor('.');
                objectGrid[x][y].push(floor);
            }
            objectGrid[x][y].push(hold);
            System.out.println("Stack after pickup: " + objectGrid[x][y]);
            //addObjectToDisplay(objectGrid[x][y].peek(), x, y);
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
            Player player = Player.buildPlayer("", 0, 0);
            int playerMax = player.getMaxHit();
            int randomPlay = (int) (Math.random() * playerMax + 1);
            rewriteInfo(randomMon, randomPlay, player, (Monster) atPoint);
            inflictDeath(player, (Monster) atPoint, x, y); // checks the death related actions first
            getDeath(player, (Monster) atPoint, x, y);
            hitActions(player, (Monster) atPoint, x, y);
            return true;   
        }
        return false;
    }

    private void hitActions(Player player, Monster monster, int x, int y){
        int action = checkParticularAction(player, "DropPack");
        ArrayList<Step1Files.Action> playerActions = player.getActionList();
        ArrayList<Item> playerItems = player.getPlayerItems();
        if(action > -1){
            if(playerItems.size() > 0){
                dropItem(player,'1');
                infoSection("Info: " + playerActions.get(action).getMessage());
            }
        }
        action = checkParticularAction(player, "EmptyPack");
        if(action > -1){
            while(playerItems.size() > 0){
                dropItem(player, '1');
                infoSection("Info: " + playerActions.get(action).getMessage());
            }
        }

        //implementing Teleport action 
        action = checkParticularAction(monster, "Teleport");
        if(action > -1){
            teleport(monster, x, y);
        }
        action = checkParticularAction(player, "Teleport");
        if(action > -1){
            teleport(monster, x, y);
        }
    }

    // return random value in [start,end)
    private int randRange(int start, int end) {
        return  rand.nextInt(end - start);
    }

    private void getCandidates(ArrayList<Point> candidates){
        for(int i = 0; i < objectGrid.length; i++){
            for(int j = 0; j < objectGrid[0].length; j++){
                Displayable el = getObjectOnDisplay(i,j);
                if(el instanceof TraversableStructure){
                    candidates.add(new Point(i,j));
                }
            }
        }
    }

    private void teleport(Monster monster, int x, int y){
        boolean transported = false;
        ArrayList<Point> candidates = new ArrayList<Point>();
        getCandidates(candidates);
        int randNum = randRange(1, candidates.size());
        Point toMove = candidates.get(randNum);
        Displayable moving = removeObjectFromDisplay(x,y);
        if(getObjectOnDisplay(x,y).getType() == ' '){
            addObjectToDisplay(new RoomFloor('.'), x, y);
        }
        //terminalWrite(moving.getType(), x, y);
        addObjectToDisplay(moving, toMove.getX(), toMove.getY());
        writeToTerminal(x,y);
        transported = true;
        /*
        while(transported == false){
            int randx = (int) (Math.random() * objectGrid.length);
            int randy = (int) (Math.random() * objectGrid[0].length);
            System.out.println("x and y" + randx + " and " + randy);
            Displayable el = getObjectOnDisplay(randx, randy);
            System.out.println("displayable character is " + el.getType());
            if(el.getType() != ' ' && el.getType() != 'X' && !(el instanceof Creature)){
                infoSection("Entered");
                Displayable moving = removeObjectFromDisplay(x,y);
                terminalWrite(moving.getType(), x, y);
                addObjectToDisplay(moving, x, y);
                transported = true;
            }
        }
        */
    }

    private void getDeath(Player player, Monster monster, int x, int y ){
        System.out.println("player hp is " + player.getHp());
        ArrayList<Step1Files.Action> allActions = player.getActionList();
         if(player.getHp() <= 0){
            int action = checkParticularAction(player, "Remove");
            if(action > -1){
                removeObjectFromDisplay(x,y);
                RoomFloor floor = new RoomFloor('.');
                addObjectToDisplay(floor,x,y);
            }
            else{
                char ch = getObjectOnDisplay(x, y).getType();
                removeObjectFromDisplay(x,y);
                RoomFloor deadMonster = new RoomFloor(ch);
                addObjectToDisplay(deadMonster, x, y);
            }
            action = checkParticularAction(player, "YouWin");
            if(action > -1){
                infoSection("Info: " + allActions.get(action).getMessage());
                rewriteScore(allActions.get(action).getIntValue());
            }
            action = checkParticularAction(player, "ChangeDisplayedType");
            if(action > - 1){
                CreatureAction implement = (CreatureAction) allActions.get(action);
                player.setType(implement.getCharValue().charAt(0)); // change displayed type
                addObjectToDisplay(player, player.getX(), player.getY()); // update display
            }
            action = checkParticularAction(player, "EndGame");
            if(action > -1){
                CreatureAction implement = (CreatureAction) allActions.get(action);
                infoSection("Info: " + implement.getMessage());
            }
        }
    }

    // x and y refer to the monster's location 
    private void inflictDeath(Player player, Monster monster, int x , int y){
        // taking care of the remove action 
        ArrayList<Step1Files.Action> allActions = monster.getActionList();
        if(monster.getHp() <= 0){
            int action = checkParticularAction(monster, "Remove");
            if(action > -1){
                removeObjectFromDisplay(x,y);
                RoomFloor floor = new RoomFloor('.');
                addObjectToDisplay(floor,x,y);
            }
            else{
                char ch = getObjectOnDisplay(x, y).getType();
                removeObjectFromDisplay(x,y);
                RoomFloor deadMonster = new RoomFloor(ch);
                addObjectToDisplay(deadMonster, x, y);
            }
            action = checkParticularAction(monster, "YouWin");
            if(action > -1){
                infoSection("Info: " + allActions.get(action).getMessage());
                rewriteScore(allActions.get(action).getIntValue());

            }
            action = checkParticularAction(monster, "ChangeDisplayedType");
            if(action > - 1){
                CreatureAction implement = (CreatureAction) allActions.get(action); 
                monster.setType(implement.getCharValue().charAt(0)); // ChangeDisplayedType
                addObjectToDisplay(monster, x, y); // update display
            }
        }
    }
    
    private int checkParticularAction(Creature creature, String actionType){
        ArrayList<Step1Files.Action> allActions = creature.getActionList();
        System.out.println("size of this list is " + allActions.size());
        for(int i = 0; i < creature.getActionList().size(); i++){
            System.out.println("name is " + allActions.get(i).getName());
            CreatureAction action = (CreatureAction) allActions.get(i);
            System.out.println("CreatureAction updated " + action.getName());
            if(action.getName().equals(actionType)){
                return i;
            }
        }
        return -1;

    }

    private void rewriteScore(int val){
        Player player = Player.buildPlayer("", 0, 0);
        score += val;
        String str = "HP: " + player.getHp() + " Score: " + score;
        int i = 0;
        for(i = 0; i < str.length(); i++){
            char write = str.charAt(i);
            //Displayable writing = new Displayable();
            //writing.setType(write);
            //this.addObjectToDisplay(writing, i, this.objectGrid[0].length - 1);
            terminal.write(str.charAt(i), i, 0);
        }
        for(;i<objectGrid.length; i++){
            terminal.write(' ', i, 0);
        }
        terminal.repaint();

    }
    private void rewriteInfo(int randomMon, int randomPlay, Player player, Monster monster){
        int i = 0;
        int hp = player.getHp() - randomMon;
        player.setHp(hp);
        int monsthp = monster.getHp() - randomPlay;
        monster.setHp(monsthp);
        String info = "Info: Attack! New monster Hp " + monsthp + " and new player Hp " + hp;
        infoSection(info);
        String str = "HP: " + hp + " Score: 0";
        for(i = 0; i < str.length(); i++){
            char write = str.charAt(i);
            Displayable writing = new Displayable();
            writing.setType(write);
            //this.addObjectToDisplay(writing, i, this.objectGrid[0].length - 1);
            terminal.write(str.charAt(i), i, 0);
        }
        for(;i<objectGrid.length; i++){
            terminal.write(' ', i, 0);
        }
        terminal.repaint();
    }

    /*
    // rewrites the info part and the hitpoints part as well
    private void rewriteInfo(int randomMon, int randomPlay, Player player){
        String secBottom = "Info: Monster attacked player -" + randomMon + " and player attacked monster - " + randomPlay;
        for(int i = 0; i < secBottom.length(); i++){
            char write = secBottom.charAt(i);
            //Displayable writing = new Displayable();
            //writing.setType(write);
            //this.addObjectToDisplay(writing, i, this.objectGrid[0].length - 1);
            terminal.write(write, i, this.objectGrid[0].length - 1);
        }
        int hp = player.getHp() - randomMon;
        player.setHp(hp);
        String top = "HP: " + hp + " Score: 0";
        for(int i = 0; i < top.length(); i++){
            char write = top.charAt(i);
            //Displayable writing = new Displayable();
            //writing.setType(write);
            //this.addObjectToDisplay(writing, i, 0);
            terminal.write(write, i, 0);
        }
        terminal.repaint();

    }
    */

    //private void updateInfoSection()

    private void moveLeft(Player track){
        if(movementPossible(track.getX() - 1, track.getY())){
            addObjectToDisplay(track, track.getX() - 1, track.getY());
            objectGrid[track.getX()][track.getY()].pop();
            //System.out.println(objectGrid[track.getX()][track.getY()].peek().getType());
            //addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY());
            char ch = getObjectOnDisplay(track.getX(), track.getY()).getType();
            if(ch == ' '){
                RoomFloor floor = new RoomFloor('.');
                addObjectToDisplay(floor,track.getX(), track.getY());
                return;
            }
            writeToTerminal(track.getX(), track.getY());
            track.setX(track.getX() - 1);
            if(hallucinationLeft > 0){
                hallucinationLeft -= 1;
                hallucinate(hallucinateScroll);
            }
            
        }
        else{
            combat(track.getX() - 1, track.getY());
        }
    }

    private void moveRight(Player track){
        if(movementPossible(track.getX() + 1, track.getY())){
            addObjectToDisplay(track, track.getX() + 1, track.getY()); // add player to new location
            objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
            //System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
            //addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
            char ch = getObjectOnDisplay(track.getX(), track.getY()).getType();
            if(ch == ' '){
                RoomFloor floor = new RoomFloor('.');
                addObjectToDisplay(floor,track.getX(), track.getY());
                return;
            }
            writeToTerminal(track.getX(), track.getY());
            track.setX(track.getX() + 1);
            if(hallucinationLeft > 0){
                hallucinationLeft -= 1;
                hallucinate(hallucinateScroll);
            }
        }
        else{
            combat(track.getX() + 1, track.getY());
        }
    }

    private void moveDown(Player track){
        if(movementPossible(track.getX(), track.getY() + 1)){
            addObjectToDisplay(track, track.getX(), track.getY() + 1); // add player to new location
            objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
            //System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
            //addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
            char ch = getObjectOnDisplay(track.getX(), track.getY()).getType();
            if(ch == ' '){
                RoomFloor floor = new RoomFloor('.');
                addObjectToDisplay(floor,track.getX(), track.getY());
                return;
            }
            writeToTerminal(track.getX(), track.getY());
            track.setY(track.getY() + 1);
            if(hallucinationLeft > 0){
                hallucinationLeft -= 1;
                hallucinate(hallucinateScroll);
            }
        }
        else{
            combat(track.getX(), track.getY() + 1);

        }
    }

    private void moveUp(Player track){
        if(movementPossible(track.getX(), track.getY() - 1)){
            addObjectToDisplay(track, track.getX(), track.getY() - 1); // add player to new location
            objectGrid[track.getX()][track.getY()].pop(); // remove player from old location
            //System.out.println(objectGrid[track.getX()][track.getY()].peek().getType()); // debugging
            //addObjectToDisplay(objectGrid[track.getX()][track.getY()].peek(), track.getX(), track.getY()); // rewrite old location
            char ch = getObjectOnDisplay(track.getX(), track.getY()).getType();
            if(ch == ' '){
                RoomFloor floor = new RoomFloor('.');
                addObjectToDisplay(floor,track.getX(), track.getY());
                return;
            }
            writeToTerminal(track.getX(), track.getY());
            track.setY(track.getY() - 1);
            if(hallucinationLeft > 0){
                hallucinationLeft -= 1;
                hallucinate(hallucinateScroll);
            }
            
        }
        else{
            combat(track.getX(), track.getY() - 1);
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
                //System.out.println("Newly added " + ch);
                writeToTerminal(x, y);
            }
        }
    }

    public Displayable getObjectOnDisplay(int x, int y){
        return objectGrid[x][y].peek();//peek
    }

    public Displayable removeObjectFromDisplay(int x, int y){
        return objectGrid[x][y].pop();
    }

    // add pop(x, y)

    public void terminalWrite(char ch, int x, int y){
        terminal.write(ch,x,y);
    }

    public void repainter(){
        terminal.repaint();
    }
    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].peek().getType();//.getChar(); //peek
        terminal.write(ch, x, y); // staging a change for 
        terminal.repaint(); // executing the change 
    }
}
