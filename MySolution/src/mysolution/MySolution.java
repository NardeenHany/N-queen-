

package mysolution;

import javafx.util.Pair;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MySolution  {

    public static void main(String[] args) throws InterruptedException {
        int[][] board = {{0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0}};



        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(8);
        //get Queens locations
        Vector<Pair<Integer, Integer>>  queens = GetQueens(board);
        Queen[] queensThreads = new Queen[8];
        for (int i = 0; i < 8; i++) {
            queensThreads[i] = new Queen(board,queens.get(i).getKey(),queens.get(i).getValue(),latch);
           service.execute(queensThreads[i]);
        }



        latch.await();

        Checker check = new Checker(queensThreads);

        service.execute(check);

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



    private static Vector<Pair<Integer, Integer>> GetQueens(int[][] board) {
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
    

