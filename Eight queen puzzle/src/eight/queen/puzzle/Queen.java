package eight.queen.puzzle;

import java.util.concurrent.CountDownLatch;

public class Queen implements Runnable {
    private Thread t;
    int [][] Board;
    int BoardSize = 8;
    int QueenRow, QueenCol;
    boolean isSafeQueen = false;
    private CountDownLatch countDownLatch;

    public Queen(int[][]board, int row, int col,CountDownLatch countDownLatch) {
        Board = board;
        QueenRow = row;
        QueenCol = col;
        this.countDownLatch = countDownLatch;
    }

    public int[][] getBoard() {
        return Board;
    }

    public void setBoard(int[][] board) {
        Board = board;
    }

    public boolean isSafeQueen() {
        return isSafeQueen;
    }

    public int getQueenRow() {
        return QueenRow;
    }

    public void setQueenRow(int queenRow) {
        QueenRow = queenRow;
    }

    public int getQueenCol() {
        return QueenCol;
    }

    public void setQueenCol(int queenCol) {
        QueenCol = queenCol;
    }

//  Checks that is the queen is safely placed, as there is no other queens in the same row and column , diagonal
    boolean isSafe() {
        int i, j;
        countDownLatch.countDown();
       //Check in each Row
        for (i = 0; i < 8; i++)
            if (Board[QueenRow][i] == 1 && i != QueenCol)
                return false;
        //Check in each Column
        for (i = 0; i < 8; i++)
            if (Board[i][QueenCol] == 1 && i != QueenRow)
                return false;
        //Check left Main Diagonal
        for (i = QueenRow - 1, j = QueenCol - 1; i >= 0 && j >= 0; i--, j--)
            if (Board[i][j] == 1)
                return false;
        //Check Right Main Diagonal
        for (i = QueenRow + 1, j = QueenCol - 1; j >= 0 && i < 8; i++, j--)
            if (Board[i][j] == 1)
                return false;
        //Check right Main Diagonal
        for (i = QueenRow + 1, j = QueenCol + 1; i < 8 && j < 8; i++, j++)
            if (Board[i][j] == 1)
                return false;
        //Check Right anti-Main Diagonal
        for (i = QueenRow - 1, j = QueenCol + 1; j < 8 && i >= 0; i--, j++)
            if (Board[i][j] == 1)
                return false;

        isSafeQueen = true;

        return true;
    }


    public void run() {
        isSafe();
//        System.out.println(isSafe() + " Row: "+QueenCol);
    }

}
