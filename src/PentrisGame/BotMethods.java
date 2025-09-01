package PentrisGame;

import java.util.Random;

public class BotMethods {
    
    public static int squareSums(int[][] field){
        int sum = 0;
        for (int col = 0; col < field[0].length; col++){
            for (int row = 5; row < field.length; row++){
                if (field[row][col] == 1){
                    sum += 20-row;
                }
            }
        }
        return sum;
    }

    /*public Pentomino rotate(int tryRotation, int[][] tryField, int posRow, int colPos, int penID)
    {
        Pentomino tryPent = new Pentomino(penID, tryRotation, posRow, colPos);

        if (penFits(tryField, tryPent.getMatrix(), colPos, posRow)){
            return tryPent;
        }else{
            if (penFits(tryField, tryPent.getMatrix(), tryField[0].length-tryPent.getMatrix()[0].length, posRow)){
                while (!penFits(tryField, tryPent.getMatrix(), colPos, posRow)){
                    colPos--;
                }
                return new Pentomino(penID, tryRotation, posRow, colPos);
            }else{
                return new Pentomino(penID, tryRotation-1, posRow, colPos);
            }
        }
    }

    public static int[] initSeed ()
    {
        int[] ar = new int[150];
        for (int i=0; i<ar.length; i++){
            ar[i] = randomPenIds();
        }
        //System.out.println(Arrays.toString(ar));
        return ar;
    }
    public static int[] randomizeOrder(){
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,};
		
		Random rand = new Random();
		
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			int temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
        return(array);

    }

    public static int randomPenIds ()
    {
        return (int)(Math.random()*11);
    }

    public static void displayBoard (int[][] field)
    {
        for (int i=0; i<field.length; i++){
            for (int j=0; j<field[0].length; j++){
                System.out.print(field[i][j]+" ");
            }
            System.out.println("");
        }

        System.out.println("_________________________________________________");
    }

    public int drop0 (Pentomino pentomino, int[][] field, int[][] pieceMatrix,int row, int col)
    {
        int[][] newField = copyArray(field);
        Pentomino tryPent = new Pentomino(pentomino.getID(), pentomino.getCurRotation(), row, col);
        int add = 0;

        while (penFits(newField, tryPent.getMatrix(), col, row+add+1)){
            add++;
        }
        
        return add;
    }*/

    
    /**
     * returns total number of holes weighted
     */
    public static int bigHoles (int[][] field)
    {
        int colHoles = 0;
        int totalCount = 0;
        for (int col = 0; col<field[0].length; col++){
            colHoles += countHoleCol(field, col);
            totalCount += sum(colHoles);
            colHoles = 0;
        }
        return totalCount;
    }

    /**
     * compute factorial of a number
     * @param n: number to compute
     * @return factorial of n
     */
    public static int sum (int n)
    {
        return ((n*(n+1))/2);
    }

    /**
     * methods that counts number of holes under a piece
     * @param field: the binary field
     * @param c: the column I'm tracking
     * @return maximum number of adjacent holes under a piece
     */
    public static int countHoleCol (int[][] field, int c)
    {
        int cunt = 0;
        int max = 0;
        // go over all elements in col
        for (int i=0; i<field.length-1; i++){
            if (field[i][c] == 1){
                // highest
                for (int x = i+1; x<field.length; x++){

                    if (field[x][c] == 0){
                        // start counting empty squares
                        cunt++;
                        if (max < cunt) max = cunt;
                    }else{
                        // if another piece is found save the max and return to 0
                        if (max < cunt) max = cunt;
                        cunt = 0;
                        i=x;
                        break;
                    }
                }
            }
        }
        return max;
    }


    public int height(int[][] field){

        int height = 0;
        for (int i = 0; i<field.length; i++){
            for(int j = 0; j<field[0].length; j++){

                if(field[i][j] == 1){
                    return field.length -i;
                }

            }
        }
        return height;
    }   

    public static int countTransitions(int [] array){
        int countTransitions = 0;
        int[] elements = new int[array.length];
        for (int element = 1; element < array.length; element++ ){
            if ((array[element-1] == 0 && 0 != array[element] ) || (array[element-1] != 0 && 0 == array[element] )){
                elements[element] = 1;
            }

        }

        for(int element = array.length-2; element >= 0; element--){
            if((array[element+1] == 0 && 0 != array[element] ) || (array[element+1] != 0 && 0 == array[element] )){
                elements[element] = 1;
            }

        }

        for(int i = 0; i< elements.length; i++){
            if (elements[i] == 1){
                countTransitions++;
            }
        }


        return countTransitions;

    }
    public boolean fullSquareAbove(int[][] field, int i, int j){
        for (int k = i; k > 0; k-- ){
            if (field[k][j] == 1){
                return true;
            }
        }
        return false;

    }

    public int holes(int[][] field)
    {
        // go over every block and if the block is empty and there's a full square in the column above it count it as a hole
        // go over rows of the field
        int holeCount = 0;
        for (int i = 1; i < field.length; i++){
            // going over columns of rows
            for (int j = 0; j < field[0].length; j++){
                if (field[i][j] == 0 && fullSquareAbove(field, i, j)){
                    holeCount++;
                }

            }

        }
        return holeCount;
    }

    
    public static int[] getColumn(int [][] field, int index){

        int [] column = new int[field.length]; 

        for(int i=0; i<field.length; i++){
           column[i] = field[i][index];
        }

        return column;
    }

    public static int rowTransitions(int [][] field){
        int rowTransitions = 0;

        for(int i = 0; i<field.length; i++){
            rowTransitions += countTransitions(field[i]);
            //System.out.println(countTransitions(field[i]));
        }

        return rowTransitions;
    }

    public static int columnTransitions(int [][] field){
        int columnTransitions = 0;


        for(int i = 0; i<field[0].length; i++){
                int [] columns = getColumn(field, i);
                //System.out.println("column is " + Arrays.toString(columns));
                columnTransitions += countTransitions(columns);

                //System.out.println(columnTransitions);
        }

        return columnTransitions;
    }

    public int filledSquares(int [][] field){

        int filledSquares = 0;

        for(int i = 0; i < field.length; i++){
            for(int j = 0; j< field[0].length; j++){
                if(field[i][j]!=0){
                    filledSquares++;
                }
            }
        }

        return filledSquares;
    }

    // 
    /*public int[][] boardToBin (int[][] field)
    {
        int[][] newField = copyArray(field);
        for (int i=0; i<newField.length; i++){
            for (int j=0; j<newField[0].length; j++){
                if (newField[i][j] == -1) newField[i][j] = 0;
                else newField[i][j] = 1;
            }
        }
        return newField;
    }

    public static int[][] initfield ()
    {
        int[][] newField = new int[20][5];
        for (int i=0; i<newField.length; i++){
            for (int j=0; j<newField[0].length; j++){
                newField[i][j] = -1;
            }
        }
        return newField;
    }*/
}
