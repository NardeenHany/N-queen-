package eight.queen.puzzle;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class Queen implements Runnable {
    private Thread t;
    int [][] Board;
    int BoardSize = 8;
    int QueenRow, QueenCol;
    boolean isSafeQueen = false;
    private CountDownLatch countDownLatch;

    JButton queenButton;

    public void setQueenButton(JButton queenButton) {
        this.queenButton = queenButton;
    }

    Icon greenIcon = new ImageIcon("E:\\Study\\4th\\PP\\Project\\N-queen-\\picture\\c1g.png");

    public Queen(int[][]board, int row, int col,CountDownLatch countDownLatch) {
        Board = board;
        QueenRow = row;
        QueenCol = col;
        this.countDownLatch = countDownLatch;
    }

    public boolean isSafeQueen() {
        return isSafeQueen;
    }


//  Checks that is the queen is safely placed, as there is no other queens in the same row and column , diagonal
    boolean isSafe() throws InterruptedException {

        int timer = (int)(Math.random() * (3000 - 1000 + 1) + 1000);
//        C in the comments is the current queen being checked it's safety. Q is the other queen on the board
        int i, j;
        countDownLatch.countDown();
       //Check in each Row
        /*
         * | C - Q - |
         * | - - - - |
         * | - - - - |
         * | - - - - |
         * */
        for (i = 0; i < 8; i++)
            if (Board[QueenRow][i] == 1 && i != QueenCol)
                return false;

        //Check in each Column
        /*
         * | C - - - |
         * | - - - - |
         * | Q - - - |
         * | - - - - |
         * */
        for (i = 0; i < 8; i++)
            if (Board[i][QueenCol] == 1 && i != QueenRow)
                return false;

        //Check left Main Diagonal
        /*
        * | Q - - - |
        * | - C - - |
        * | - - - - |
        * | - - - - |
        * */
        for (i = QueenRow - 1, j = QueenCol - 1; i >= 0 && j >= 0; i--, j--)
            if (Board[i][j] == 1)
                return false;

        //Check Left Anti-Main Diagonal
        /*
         * | - - - - |
         * | - C - - |
         * | Q - - - |
         * | - - - - |
         * */
        for (i = QueenRow + 1, j = QueenCol - 1; j >= 0 && i < 8; i++, j--)
            if (Board[i][j] == 1)
                return false;

        //Check Right Main Diagonal
        /*
         * | - - - - |
         * | - C - - |
         * | - - Q - |
         * | - - - - |
         * */
        for (i = QueenRow + 1, j = QueenCol + 1; i < 8 && j < 8; i++, j++)
            if (Board[i][j] == 1)
                return false;

        //Check Right Anti-Main Diagonal
        /*
         * | - - Q - |
         * | - C - - |
         * | - - - - |
         * | - - - - |
         * */
        for (i = QueenRow - 1, j = QueenCol + 1; j < 8 && i >= 0; i--, j++)
            if (Board[i][j] == 1)
                return false;

        isSafeQueen = true;

        sleep(timer);
        DisplayValidQueen();

        return true;
    }

    public void DisplayValidQueen(){
        queenButton.setIcon(greenIcon);
    }

    public void run() {
        try {
            isSafe();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        System.out.println(isSafe() + " Row: "+QueenCol);
    }

}
