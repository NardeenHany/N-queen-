

package mysolution;
import javafx.util.Pair;

import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Arsany
 */
public class MySolution  {

    public static void main(String[] args) {
        int[][] board = {{0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0}};
        int row = 8;
        int column = 8;

        int CorrectQueens = 0;


        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(8);

        //get Queens locations
        Vector<Pair<Integer, Integer>>  queens = GetQueens(board);
        Queen queensThreads[] = new Queen[8];
        for (int i = 0; i < 8; i++) {
            queensThreads[i] = new Queen(board,queens.get(i).getKey(),queens.get(i).getValue());
           service.execute(queensThreads[i]);
        }

        Checker check = new Checker(queensThreads);
      service.execute(check);
        service.shutdown();
//        ScheduledExecutorService ScheduledService = Executors.newScheduledThreadPool(coreCount);
//        ScheduledService.schedule(check,2,SECONDS);



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
    

