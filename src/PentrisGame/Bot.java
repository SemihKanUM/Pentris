package PentrisGame;

import java.util.ArrayList;

public class Bot {
    Board model;
    Main game;
    String botMove = "";
    Board trainingGame;
    ArrayList<Integer> gameHistory;
    int[] parameterList;

    public static final int
    AGGREGATE_HEIGHT = 0,
    MAX_HEIGHT = 1,
    CENTER_HEIGHT = 2,
    ROW_TRANSITIONS = 3,
    COLUMN_TRANSITIONS = 4,
    NUM_HOLES = 5,
    DELETED_ROWS = 6,
    DEEPEST_WELL = 7,
    NUM_PITS = 8;

    public Bot() {
        model = new Board();
        botMove = "";
        minAlele = -5;
        maxAlele = 5;
        genotype = new double[7];
        for (int i=0; i<genotype.length; i++) {
            genotype[i] = Math.random()*(maxAlele-minAlele) + minAlele;
        }
        gameHistory = new ArrayList<Integer>();
        parameterList = new int[0];
    }

    public boolean arraysAreEqual(int[][] arr1, int[][] arr2) {
        if (arr1.length != arr2.length || arr1[0].length != arr2[0].length) {
            return false;
        }
        for (int i=0; i<arr1.length; i++) {
            for (int j=0; j<arr1[i].length; j++) {
                if (arr1[i][j] != arr2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void chooseMoves(Board gameBoard) {
        gameBoard.storeCheckpoint();
        model = gameBoard.getLastCheckpoint();
        String[] allMoves = new String[1000];
        int movesLength = 0;
        for (int rotation=0; rotation<4; rotation++) {
            model.storeCheckpoint(); // Store the board state before performing rotations
            model.removeShape(model.currentPent.getCurrentRotation());
            for (int i=0; i<rotation; i++) {
                model.rotateRight();
            }
            model.placeShape(model.currentPent.getCurrentRotation());
            for (int xx=-5; xx<=5; xx++) {
                model.storeCheckpoint(); // Store the board state before moving left/right
                model.removeShape(model.currentPent.getCurrentRotation());
                if (xx < 0) {
                    for (int i=0; i<-xx; i++) {
                        model.moveLeft();
                    }
                } else if (xx > 0) {
                    for (int i=0; i<xx; i++) {
                        model.moveRight();
                    }
                }
                model.placeShape(model.currentPent.getCurrentRotation());
                // If the pentomino is still placeable, drop it and store that move
                String move = "";
                for (int i=0; i<rotation; i++) {
                    move += Board.ROTATE_RIGHT;
                }
                for (int i=0; i<Math.abs(xx); i++) {
                    if (xx < 0) {
                        move += Board.MOVE_LEFT;
                    } else if (xx > 0) {
                        move += Board.MOVE_RIGHT;
                    }
                }
                move += Board.HARD_DROP;
                allMoves[movesLength] = move;
                movesLength++;
                model = model.getLastCheckpoint(); // Restore to model before moving left/right
            }
            model = model.getLastCheckpoint(); // Restore to model before rotating
        }
        String bestMove = "";
        double bestUtility = -10000;
        for (int i=0; i<movesLength; i++) {
            model.storeCheckpoint();
            int score1 = model.getNumRemovedRows();
            for (int j=0; j<allMoves[i].length(); j++) {
                model.addMove(allMoves[i].charAt(j));
            }
            while (!(model.getBoardState().equals("init move") || model.getBoardState().equals("lose"))) {
                model.tick();
            }
            int score2 = model.getNumRemovedRows();
            double utility = utilityFunction(model.getState(), score2-score1);
            model = model.getLastCheckpoint();
            if (utility > bestUtility) {
                bestMove = allMoves[i];
                bestUtility = utility;
            }
        }
        botMove = bestMove;
    }

    public double utilityFunction(int[][] boardState, int deletedRows){
        // Aggregate Height: Sum of all the columns height.
        // maxHeight: The height of the highest column
        // deepestWell: The size of the largest empty column from the top down
        int lastHeight = 0;
        int AggregateHeight = 0;
        double centerHeight = 0;
        int maxHeight = 0;
        int[] heightArray = new int[boardState[0].length];
        for (int i = 0; i < boardState[0].length; i++) {
            for (int j = 0; j < boardState.length; j++) {
                int height = 0;
                if(j == boardState.length || boardState[j][i] == 1){
                    height = boardState.length-j;
                    heightArray[i] = height;
                    AggregateHeight += height;
                    centerHeight += (double) height / ((Math.abs(i-boardState[0].length/2.0)+1));
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    lastHeight = height;
                    break;
                }
            }
        }
        // Connected holes
        int connectedHoles = 0;
        for (int j=0; j<boardState[0].length; j++) {
            // For each column, go through the rows and find the connected holes
            int holeSum = 0;
            for (int i=0; i<boardState.length; i++) {
                if (boardState[i][j] == 1) {
                    int num = 0;
                    for (int row = i+1; row<boardState.length; row++) {
                        if (boardState[row][j] == 0) {
                            num += 1;
                            holeSum += num;
                        } else {
                            i = row;
                            break;
                        }
                    }
                }
            }
            connectedHoles += holeSum;
        }

        // The Number of Holes on the board
        // A hole is defined as any empty cell that has a filled cell somewhere above it in the column
        int numberOfHoles = 0;
        for (int i = 1; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] == 0) {
                    for (int h=i-1; h>=0; h--) {
                        if (boardState[h][j] == 1) {
                            numberOfHoles += 1;
                            break;
                        }
                    }
                }
            }  
        }

        // Row Transitions and Column Transitions;
        int RowTransitions = 0;
        int ColTransitions = 0;
        for (int i = 0; i < boardState[0].length; i++) {
            for (int j = 0; j < boardState.length; j++) {
                if(i-1 >= 0 && boardState[j][i] == 1 && boardState[j][i-1] == 0){
                    RowTransitions +=1;
                }
                if(i+1 < boardState[0].length && boardState[j][i] == 1 && boardState[j][i+1] == 0){
                    RowTransitions +=1;
                }

                if(j-1 >= 0 && boardState[j][i] == 1 && boardState[j-1][i] == 0){
                    ColTransitions +=1;
                }
                if(j+1 < boardState.length && boardState[j][i] == 1 && boardState[j+1][i] == 0){
                    ColTransitions +=1;
                } 
            }      
        }

        //Number of Pits
        int pits = 0;
        int counter = 0;
        for (int i = 0; i < boardState[0].length; i++) {
            for (int j = 0; j < boardState.length; j++) {
                if(boardState[j][i] == 1){
                    counter +=1;
                    break;
                } 
            }
            if(counter == 0){
                pits +=1;
            }
            counter = 0; 
        }

        // Deepest Well; Founds the deep value according the sides of columns.
        int[] deepest = new int[boardState[0].length];
        for (int i = 0; i < heightArray.length; i++) {
            if(i!=0 && i != heightArray.length -1){
                if(heightArray[i-1]-heightArray[i] < heightArray[i+1]-heightArray[i]){
                    deepest[i] = heightArray[i-1]-heightArray[i];
                }
                else{
                    deepest[i] =  heightArray[i+1]-heightArray[i];
                }
            }
            else if(i == 0){
                deepest[i] = heightArray[i+1]-heightArray[i];
            }
            else if(i == heightArray.length -1){
                deepest[i] = heightArray[i-1]-heightArray[i];
            }
        }
        
        int deepestWell = deepest[0];
        for(int i=1;i<deepest.length;i++){ 
            if(deepest[i] > deepestWell){ 
                deepestWell = deepest[i]; 
            }
        } 

        // Check lose condition
        for (int i=0; i<3; i++) {
            for (int j=0; j<boardState[i].length; j++) {
                if (boardState[i][j] == 1) {
                    return -1000;
                }
            }
        }

        double[] allParameters = new double[] {AggregateHeight, maxHeight, centerHeight, RowTransitions, ColTransitions, numberOfHoles, deletedRows, deepestWell, pits};

        //genotype = new double[] {-5.0, -5.0, -4.21327562580109, 0, -0.5663311444791244, -4.872798075149198, 1.8944890993796275};
        double utility = 0;
        for (int i=0; i<parameterList.length; i++) {
            utility += genotype[i] * allParameters[parameterList[i]];
        }
        return utility;
        //return genotype[0]*numberOfHoles + genotype[1]*centerHeight + genotype[2]*RowTransitions + genotype[3]*ColTransitions + genotype[4]*connectedHoles + genotype[5]*deepestWell + genotype[6]*maxHeight; 
    }

    /**
     * Adds a parameter to the list of parameters to be used
     * @param paramID The ID of the parameter to be added
     */
    public void addParameter(int paramID) {
        int[] tempList = new int[parameterList.length+1];
        for (int i=0; i<parameterList.length; i++) {
            tempList[i] = parameterList[i];
        }
        tempList[tempList.length-1] = paramID;
        parameterList = tempList;
    }

    
    //
    // GENETIC ALGORITHM METHODS AND VARIABLES
    //
    private double[] genotype;
    private double minAlele, maxAlele;
    private double fitness = -1;

    /**
     * Runs 100 model games and takes the average score and sets it to the fitness
     */
    public void calculateFitness(int numGames) {
        double averageScore = 0;
        for (int i=0; i<numGames; i++) {
            // Play this game
            trainingGame = new Board();
            while (!trainingGame.getBoardState().equals("lose") && trainingGame.getNumRemovedRows() < 500) {
                String state1 = trainingGame.getBoardState();
                trainingGame.tick();
                String state2 = trainingGame.getBoardState();
                // Setting the moves
                if (state1.equals("init move") && state2.equals("move")) {
                    chooseMoves(trainingGame);
                    for (int n=0; n<botMove.length(); n++) {
                        trainingGame.addMove(botMove.charAt(n));
                    }
                    botMove = "";
                } 
            }
            averageScore += trainingGame.getNumRemovedRows();
            gameHistory.add(trainingGame.getNumRemovedRows());
        }
        averageScore /= (double) numGames;
        fitness = averageScore;
    }

    /**
     * Gets the fitness of this individual
     * @return The fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Sets a new genotype of this bot
     * @param genotype The array containing the aleles
     */
    public void setGenotype(double[] genotype) {
        this.genotype = genotype;
    }

    /**
     * Gets the current genotype of this bot
     * @return An array containing the aleles
     */
    public double[] getGenotype() {
        return genotype;
    }

    /**
     * Mutate this individual
     * @param mutationRate The chance that an alele will mutate
     */
    public void mutate(double mutationRate) {
        for (int i=0; i<genotype.length; i++) {
            if (Math.random() < mutationRate) {
                genotype[i] += (Math.random()*2-1)*(maxAlele-minAlele)/2; // Change the alele by max 50% of the possible range of alele values
                if (genotype[i] > maxAlele) {
                    genotype[i] = maxAlele;
                }
                if (genotype[i] < minAlele) {
                    genotype[i] = minAlele;
                }
            }
        }
    }

    //
    // Data analysis methods
    //

    /**
     * Gets the best score run by this bot
     * @return The best score
     */
    public int getBestScore() {
        int best = -1;
        for (int i=0; i<gameHistory.size(); i++) {
            if (gameHistory.get(i) > best) {
                best = gameHistory.get(i);
            }
        }
        return best;
    }

    /**
     * Gets the worst score run by this bot
     * @return The worst score
     */
    public int getWorstScore() {
        if (gameHistory.size() == 0) {
            return 0;
        }
        int worst = gameHistory.get(0);
        for (int i=0; i<gameHistory.size(); i++) {
            if (gameHistory.get(i) < worst) {
                worst = gameHistory.get(i);
            }
        }
        return worst;
    }

    /**
     * Gets the average score run by this bot
     * @return The average score
     */
    public double getAverageScore() {
        if (gameHistory.size() == 0) {
            return 0;
        }
        double average = 0;
        for (int i=0; i<gameHistory.size(); i++) {
            average += gameHistory.get(i);
        }
        return average / gameHistory.size();
    }
}
