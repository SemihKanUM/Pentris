package PentrisGame;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.xml.transform.Templates;

import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;


//import GameEngine.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    
    public static final int[][][] database = {
        // P1
        {{0,0,0,0},
         {0,1,1,0},
         {0,1,1,1},
         {0,0,0,0}},

        // P2
        {{0,0,0,0},
         {0,1,1,1},
         {0,1,1,0},
         {0,0,0,0}},

        // X
        {{0,0,0,0,0},
         {0,0,1,0,0},
         {0,1,1,1,0},
         {0,0,1,0,0},
         {0,0,0,0,0}},

        // F1
        {{0,0,0,0,0},
         {0,0,1,1,0},
         {0,1,1,0,0},
         {0,0,1,0,0},
         {0,0,0,0,0}},

        // F2
        {{0,0,0,0,0},
         {0,1,1,0,0},
         {0,0,1,1,0},
         {0,0,1,0,0},
         {0,0,0,0,0}},

        // V
        {{0,0,0,0,0},
         {0,1,0,0,0},
         {0,1,0,0,0},
         {0,1,1,1,0},
         {0,0,0,0,0}},

        // W
        {{0,0,0,0,0},
         {0,1,0,0,0},
         {0,1,1,0,0},
         {0,0,1,1,0},
         {0,0,0,0,0}},

        // Y1
        {{0,0,0,0},
         {0,0,1,0},
         {1,1,1,1},
         {0,0,0,0}},

        // Y2
        {{0,0,0,0},
         {1,1,1,1},
         {0,0,1,0},
         {0,0,0,0}},

        // I
        {{0,0,0,0,0},
         {0,0,0,0,0},
         {1,1,1,1,1},
         {0,0,0,0,0},
         {0,0,0,0,0}},

        // T
        {{0,0,0,0,0},
         {0,1,1,1,0},
         {0,0,1,0,0},
         {0,0,1,0,0},
         {0,0,0,0,0}},

        // Z1
        {{0,0,0,0,0},
         {0,1,1,0,0},
         {0,0,1,0,0},
         {0,0,1,1,0},
         {0,0,0,0,0}},

        // Z2
        {{0,0,0,0,0},
         {0,0,1,1,0},
         {0,0,1,0,0},
         {0,1,1,0,0},
         {0,0,0,0,0}},

        // U
        {{0,0,0,0,0},
         {0,1,1,1,0},
         {0,1,0,1,0},
         {0,0,0,0,0},
         {0,0,0,0,0}},

        // N1
        {{0,0,0,0},
         {0,1,1,1},
         {1,1,0,0},
         {0,0,0,0}},
        
        // N2
        {{0,0,0,0},
         {1,1,0,0},
         {0,1,1,1},
         {0,0,0,0}},

        // L1
        {{0,0,0,0},
         {1,1,1,1},
         {1,0,0,0},
         {0,0,0,0}},
         
        // L2
        {{0,0,0,0},
         {1,0,0,0},
         {1,1,1,1},
         {0,0,0,0}}
    };
    public static final int cellSize = 900/25; // The size of the grid blocks in pixels
    public static final int boardX = 0; // The x position of the top left corner of the grid
    public static final int boardY = 0; // The y position of the top left corner of the grid
    private static String gameState = "play"; // The main state of the game (menu, play, game over etc.)
    private Board board; // The board object the Main will use to run the game
    private Bot bot; // The bot to be used if the bot is currently playing the game
    private GeneticAlgorithm ga; // The genetic algorithm to be used if a bot is being fit
    private UI ui; // The UI to run the UI logic of the game (menus and game board)
    private ArrayList<Block> blocks; // Arraylist of animated block pieces
    private Timer mainTick;
    private int HighestScore;
    private static FileReader reader;
    private String name;
    
    /**
     * Constructor of the game
     */
    public Main(Bot bot , String name) {
        board = new Board();
        ui = new UI(430, 680, "Pentris"); 
        blocks = new ArrayList<Block>();
        this.bot = bot;
        this.name = name;
        
    }


    public void HighestScore(){
        try{
            reader = new FileReader("Scores.txt");
        }
        catch(FileNotFoundException exception){
            System.out.println("File couldnt found.");
        }
        
        int lines = 0;

        try{
            BufferedReader in = new BufferedReader(reader);
            ArrayList<String> string = new ArrayList<>();
            String line;

            line = in.readLine();
            string.add(line);
            while(line != null){
                lines++;
                line = in.readLine();
                string.add(line);
            }
            string.remove(lines);

            int nameLine = -1;
            String tempString = "";
            for (int i = 0; i < string.size(); i++) {
                if(string.get(i).contains(name)){
                    nameLine =i;
                    for (int j = 0; j < string.get(nameLine).length(); j++) {
                        if(Character.isDigit(string.get(nameLine).charAt(j))){
                            tempString += string.get(nameLine).charAt(j);
                            HighestScore = Integer.parseInt(tempString);
                        }
                    }
                }
            }
            



            if(nameLine == -1){
                BufferedWriter writer = new BufferedWriter(new FileWriter("Scores.txt"));
                string.add(name + " " + 0);
                String score = "";
                for (int i = 0; i < string.size(); i++) {
                    score += string.get(i) + "\n";
                }
                writer.write(score);
                writer.close();
            }

            if(board.getNumRemovedRows() > HighestScore){
                BufferedWriter writer = new BufferedWriter(new FileWriter("Scores.txt"));
                HighestScore = board.getNumRemovedRows();
                string.set(nameLine, name + " " + HighestScore);
                String score = "";
                for (int i = 0; i < string.size(); i++) {
                    score += string.get(i) + "\n";
                }
                writer.write(score);
                writer.close();
            }

            
        }
        catch(IOException ie){
            ie.printStackTrace();
        }
    }

    /**
     * Class used for running the game step and draw event (logic and UI)
     */
    class GameRunner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            step();
            ui.getBoardComponent().clear();
            draw();
            ui.frame.repaint();
            ui.resetInputs();
        }

    }

    class BotEvaluator implements Runnable {
        public void run() {
            Bot b = new Bot();
            b.calculateFitness(1000);
            System.out.println(b.getFitness());
        }
    }

    /**
     * Object used for animating pentomino pieces
     */
    class Block {
        double x, y, xDraw, yDraw;
        Board.Cell cell;
        int pentID;
        double vspd, grav;
        boolean isVisible, stoppedAnimating;

        /**
         * Constructor
         * @param pentID The pentID of the cell
         * @param cell The cell connected to the block
         */
        public Block(int pentID, Board.Cell cell) {
            this.pentID = pentID;
            this.cell = cell;
            vspd = 0;
            grav = 3;
            x = cell.xx*Main.cellSize + Main.boardX;
            y = cell.yy*Main.cellSize + Main.boardY;
            xDraw = x;
            yDraw = y;
            isVisible = false;
        }

        /**
         * Called every game step
         */
        public void tick() {
            // Check if visible
            isVisible = false;
            for (int i=0; i<board.field.length; i++) {
                for (int j=0; j<board.field[i].length; j++) {
                    if (cell == board.field[i][j]) {
                        isVisible = true;
                        break;
                    }
                }
                if (isVisible) {
                    break;
                }
            }
            // Update position
            x = cell.xx*Main.cellSize + Main.boardX;
            y = cell.yy*Main.cellSize + Main.boardY;
            xDraw += (x-xDraw)/2;
            if (Math.abs(x-xDraw) < 0.001) {
                xDraw = x;
            }
            vspd += grav;
            yDraw += vspd;
            if (yDraw >= y) {
                yDraw = y;
                vspd = 0;
            }
            // Check if the block stopped animating
            stoppedAnimating = false;
            if (y == yDraw && x == xDraw) {
                stoppedAnimating = true;
            }
            // Delete if not used
            if (!isVisible) {
                boolean delete = true;
                for (int i=0; i<board.currentPent.shapeRotations.length; i++) {
                    Board.Shape s = board.currentPent.shapeRotations[i];
                    for (int j=0; j<s.size(); j++) {
                        if (cell.equals(s.cells[j])) {
                            delete = false;
                            break;
                        }
                    }
                    if (!delete) {
                        break;
                    }
                }
                if (delete) {
                    blocks.remove(this);
                }
            }  
        }
    }

    public void run() {
        boolean running = true;
        mainTick = new Timer(1000/60, new GameRunner());
        mainTick.start();
        while (running) {
            // Run
        }
    }

    /**
     * Rotates a square matrix (without changing the inputed matrix) numRotations times to the left
     * @param matrix The matrix to rotate
     * @param numRotations The number of times to rotate the matrix to the left
     * @return The rotated matrix
     */
    public static int[][] rotateSquareMatrix(int[][] matrix, int numRotations) {
        int [][] temporaryMatrix = new int [matrix.length][matrix[0].length];
        int [][] temporaryMatrix2 = new int [temporaryMatrix.length][temporaryMatrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                temporaryMatrix[i][j] = matrix[i][j];
                }
            }
            for(int z = 0; z < numRotations; z++){
                    for(int i = 0; i < temporaryMatrix.length; i++){
                        for(int j = 0; j < temporaryMatrix[0].length; j++){
                            if(temporaryMatrix.length == 2){
                                temporaryMatrix2 [i][j] = temporaryMatrix[j][Math.abs(i-1)];

                            }
                            if(temporaryMatrix.length == 3){
                                temporaryMatrix2 [i][j] = temporaryMatrix[j][Math.abs(i-2)];

                            }
                            if(temporaryMatrix.length == 4){
                                temporaryMatrix2 [i][j] = temporaryMatrix[j][Math.abs(i-3)];

                            }
                            if(temporaryMatrix.length == 5){
                                temporaryMatrix2 [i][j] = temporaryMatrix[j][Math.abs(i-4)];

                            }
                        
                        }
                    }

                for (int i = 0; i < temporaryMatrix.length; i++){
                    for (int j = 0; j <temporaryMatrix[0].length; j++){
                        temporaryMatrix[i][j] = temporaryMatrix2[i][j]; 
                    }

                }
            }
        return temporaryMatrix;
    }

    /**
     * Creates Block objects that can be animated
     */
    public void createNewBlocks() {
        // Loop through the pentomino animations to create blocks
        for (int i=0; i<board.currentPent.shapeRotations.length; i++) {
            Board.Shape shape = board.currentPent.shapeRotations[i];
            for (int j=0; j<shape.size(); j++) {
                Block block = new Block(board.currentPent.pentID, shape.cells[j]);
                blocks.add(block);
            } 
        }
    }

    /**
     * Checks if all the blocks have stopped animating
     * @return True if they stopped animating and false otherwise
     */
    public boolean blocksStoppedAnimating() {
        for (int i=0; i<blocks.size(); i++) {
            if (!blocks.get(i).stoppedAnimating) {
                return false;
            }
        }
        return true;
    }
    

    /**
     * Main loop of the main game object.
     * Switches between the gameState (play, menu, game over) and creates/destroys objects accordingly
     */
    public void step() {
        for (int i=0; i<blocks.size(); i++) {
            blocks.get(i).tick();
        }

        


        if (Main.gameState.equals("play")) {
            if (!board.getBoardState().equals("lose")) {
                HighestScore();
                if (bot == null) {
                    if (ui.checkKeyPressed(Input.D)) {
                        board.addMove(Board.MOVE_RIGHT);
                    }
                    if (ui.checkKeyPressed(Input.A)) {
                        board.addMove(Board.MOVE_LEFT);
                    }
                    if (ui.checkKeyPressed(Input.E)) {
                        board.addMove(Board.ROTATE_RIGHT);
                    }
                    if (ui.checkKeyPressed(Input.Q)) {
                        board.addMove(Board.ROTATE_LEFT);
                    }
                    if (ui.checkKeyPressed(Input.SPACE)) {
                        board.addMove(Board.HARD_DROP);
                        for (int i=0; i<blocks.size(); i++) {
                            blocks.get(i).vspd = 0;
                        }
                    }
                    if (ui.checkKeyDown(Input.S)) {
                        board.addMove(Board.SOFT_DROP);
                    }
                } else {
                }
                boolean updateCondition = true;
                for (int i=0; i<blocks.size(); i++) {
                    if (!blocks.get(i).stoppedAnimating){
                        updateCondition = false;
                        break;
                    }
                }
                board.setUpdateCondition(updateCondition);
                String state1 = board.getBoardState();
                board.tick();
                String state2 = board.getBoardState();
                if (state1.equals("init move") && state2.equals("move")) {
                    createNewBlocks();
                    if (bot != null) {
                        bot.chooseMoves(board);
                        for (int i=0; i<bot.botMove.length(); i++) {
                            board.addMove(bot.botMove.charAt(i));
                            if (bot.botMove.charAt(i) == Board.HARD_DROP) {
                                for (int j=0; j<blocks.size(); j++) {
                                    blocks.get(j).vspd = 0;
                                }
                            }
                        }
                        bot.botMove = "";
                    }
                }
            }
            else{
                
                    mainTick.stop();

                    JFrame frameGameDone = new JFrame();
                    JPanel panelGameDone = new JPanel();
                    //panelGameDone.setLayout(new BorderLayout());
        
                    JButton tryAgain = new JButton("Try Again");
                    tryAgain.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e) {
                            board = new Board();
                            blocks = new ArrayList<Block>();
                            frameGameDone.setVisible(false); 
                            frameGameDone.removeAll();      
                            mainTick.start(); 
                        }
                    });
                    panelGameDone.add(tryAgain , BorderLayout.CENTER);
        
        
                    JButton exit = new JButton("Exit");
                    exit.addActionListener(new ActionListener(){
        
                        public void actionPerformed(ActionEvent e) {
                              System.exit(0);
                        }
                        
                    });
                    panelGameDone.add(exit, BorderLayout.EAST);
                    
                    frameGameDone.add(panelGameDone);
                    frameGameDone.setLocation(500, 400);
                    frameGameDone.pack();
                    frameGameDone.setSize(200, 100);
                    frameGameDone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frameGameDone.setResizable(false);
                    frameGameDone.setVisible(true);

            }

        }
    }

    /**
     * Draws the board while the game is being played
     * Draws all other UI aspects of the game (next pentomino, score, menu...)
     */
    public void draw() {
        //
        // Drawing the game board
        //
        // Draw the grid
        int off = Main.cellSize/10;
        for (int i = 3; i < board.field.length; i++) {
            for (int j = 0; j < board.field[0].length; j++) {
                ui.getBoardComponent().drawRectangleColor(Main.boardX+j*Main.cellSize+off, Main.boardY+i*Main.cellSize+off, Main.cellSize-off, Main.cellSize-off, new Color(200, 200, 200));   
            }        
        }
        // Draw the animated blocks
        for (int i=0; i<blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block.isVisible && block.cell.yy >= 3 && block.cell.xx >= 0 && block.cell.xx < board.field[0].length) {
                // Check neighbouring cells
                ui.getBoardComponent().drawRectangleColor((int) block.xDraw+off, (int) block.yDraw+off, Main.cellSize-off, Main.cellSize-off, Color.BLACK);
                ui.getBoardComponent().drawRectangleColor((int) block.xDraw, (int) block.yDraw, Main.cellSize-off, Main.cellSize-off, new Color(block.pentID*10, 100, 100));
                if (block.cell.xx+1 < board.field[0].length && board.field[block.cell.yy][block.cell.xx+1] != null && board.field[block.cell.yy][block.cell.xx+1].shapeID == block.cell.shapeID) {
                    ui.getBoardComponent().drawRectangleColor((int) block.xDraw+Main.cellSize, (int) block.yDraw+off, off, Main.cellSize-off, Color.BLACK);
                    ui.getBoardComponent().drawRectangleColor((int) block.xDraw+Main.cellSize-off, (int) block.yDraw, off, Main.cellSize-off, new Color(block.pentID*10, 100, 100));
                }
                if (block.cell.yy+1 < board.field.length && board.field[block.cell.yy+1][block.cell.xx] != null && board.field[block.cell.yy+1][block.cell.xx].shapeID == block.cell.shapeID) {
                    ui.getBoardComponent().drawRectangleColor((int) block.xDraw+off, (int) block.yDraw+Main.cellSize, Main.cellSize-off, off, Color.BLACK);
                    ui.getBoardComponent().drawRectangleColor((int) block.xDraw, (int) block.yDraw+Main.cellSize-off, Main.cellSize-off, off, new Color(block.pentID*10, 100, 100));
                }
                
            }
        }
        
        
        // **********Interface**********

        Image img = null;
        try {
            img = ImageIO.read(new File("INTERFACE.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        ui.getBoardComponent().drawImage(Main.boardX + Main.cellSize*5+3, 0, img);

       
        // Next Pentomino
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                    ui.getBoardComponent().drawRectangleColor(217+cellSize*i, 435+cellSize*j, Main.cellSize-off, Main.cellSize-off, Color.LIGHT_GRAY);
            }
        }

        int[][] nextPent = database[board.nextPent()];
        for (int i = 0; i < nextPent.length; i++) {
            for (int j = 0; j < nextPent[0].length; j++) {
                    if(nextPent[i][j] != 0){
                        if(j+1 < nextPent[0].length && nextPent[i][j+1] == nextPent[i][j]){
                            ui.getBoardComponent().drawRectangleColor(217+cellSize*i, 435+cellSize*j, Main.cellSize, Main.cellSize, new Color(board.nextPent()*10, 100, 100));
                        }
                        if(i+1 < nextPent.length && nextPent[i+1][j] == nextPent[i][j]){
                            ui.getBoardComponent().drawRectangleColor(217+cellSize*i, 435+cellSize*j, Main.cellSize, Main.cellSize, new Color(board.nextPent()*10, 100, 100));
                        }
                        else{
                            ui.getBoardComponent().drawRectangleColor(217+cellSize*i, 435+cellSize*j, Main.cellSize, Main.cellSize, new Color(board.nextPent()*10, 100, 100));
                        }                       
                    }
            }
        }

        // Draw the score, name , highestScore
        ui.getBoardComponent().drawTextFontColor(288, 206 , Integer.toString(board.getNumRemovedRows()), new Font("TimesRoman", Font.PLAIN, 40), Color.BLACK);
        ui.getBoardComponent().drawTextFontColor(300, 150 , name, new Font("TimesRoman", Font.PLAIN, 40), Color.BLACK);
        ui.getBoardComponent().drawTextFontColor(395, 260 , Integer.toString(HighestScore), new Font("TimesRoman", Font.PLAIN, 25), Color.BLACK);
    }
}
