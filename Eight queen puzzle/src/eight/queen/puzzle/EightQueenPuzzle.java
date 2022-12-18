
package eight.queen.puzzle;

import java.util.ArrayList;
import java.util.Arrays;

import static javafx.collections.FXCollections.copy;

public class EightQueenPuzzle {
    static final int N = 8;
    static  int[][] boardd ;
   // print the final solution matrix 
    static void printSolution(int[][] board)
    {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(" " + board[i][j]
                        + " ");
            System.out.println();
        }
        System.out.println("--------------------------------");
    }

    // function to check whether the position is safe or not 
    static boolean isSafe(int[][] board, int row, int col)
    {
        int i, j;
        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

       
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;
            
        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    public static int[][] copy(int[][] src) {
        if (src == null) {
            return null;
        }

        int[][] copy = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = src[i].clone();
        }

        return copy;
    }

    // The function that solves the problem using backtracking 
    public static boolean solveNQueen(int[][] board, int col,ArrayList<int[][]> boards)
    {
        if (col >= N) {
//            printSolution(board);

            boards.add(copy(board));

            return true;
        }
        for (int i = 0; i < N; i++) {
            //if it is safe to place the queen at position i,col -> place it
            if (isSafe(board, i, col)) {
                board[i][col] = 1;

//                if (solveNQueen(board, col + 1))
//                    return true;
                solveNQueen(board, col + 1,boards);
                //backtrack if the above condition is false
                board[i][col] = 0;
            }
        }
        return false;
    }

//    public static void main(String[] args)
//    {
//        int[][] board = { { 0, 0, 0, 0, 0, 0, 0, 0 },
//                          { 0, 0, 0, 0, 0, 0, 0, 0 },
//                          { 0, 0, 0, 0, 0, 0, 0, 0 },
//                          { 0, 0, 0, 0, 0, 0, 0, 0 },
//                          { 0, 0, 0, 0, 0, 0, 0, 0 },
//                          { 0, 0, 0, 0, 0, 0, 0, 0 },
//                          { 0, 0, 0, 0, 0, 0, 0, 0 },
//                          { 0, 0, 0, 0, 0, 0, 0, 0 }};
//
//        ArrayList<int[][]> boards = new ArrayList<>();
//        solveNQueen(board, 0,boards);
////        printSolution(boards.get(15));
//        int boardNumber = (int)(Math.random() * (boards.size() - 1 + 1) + 1);
//        printSolution(boards.get(boardNumber));
//
//
////        Table table = new Table(board);
//    }


}


    

