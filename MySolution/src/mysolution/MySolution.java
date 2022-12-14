

package mysolution;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MySolution  {

    public static void main(String[] args) throws InterruptedException {
        int[][] board = {
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0}
                };

        int coreCount = Runtime.getRuntime().availableProcessors();

        // just run first 8 threads that contains each queens location checker
        final int noOfQueens = 8;
        ExecutorService service = Executors.newFixedThreadPool(noOfQueens);


        //get Queens locations

        Vector<Pair<Integer, Integer>>  queens = GetQueensLocations(board);

        ArrayList<Thread> queensThreads = new ArrayList<>();
        Queen[] qt = new Queen[8];

        //make for each queen a thread
        for (int i = 0; i < noOfQueens; i++) {
            Queen q = new Queen(board,queens.get(i).getKey(),queens.get(i).getValue());
            qt[i] = q;
            Thread t = new Thread(q);
            queensThreads.add(t);
        }

//        submitting all threads
        for (Thread t : queensThreads){
            service.submit(t);
        }

//        create Checker( checks that all queens are in correct positions)
        Checker check = new Checker(qt);
        Thread t = new Thread(check);
        service.submit(t);

        service.shutdown();

    }

    private static void print2dArray(int[][] matrix) {
        for (int i = 0; i < 8; i++) {
            for (int c = 0; c < 8; c++) {
                System.out.print(matrix[i][c] + "\t");
            }
            System.out.println();
        }
    }



    private static Vector<Pair<Integer, Integer>> GetQueensLocations(int[][] board) {
        Vector<Pair<Integer, Integer>> Queens = new Vector<>();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (board[row][column] == 1) {
                    Queens.add(new Pair<>(row,column));
                }
            }
        }
        return Queens;
    }

}
    

