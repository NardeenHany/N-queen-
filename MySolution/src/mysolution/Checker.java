package mysolution;

import java.util.concurrent.CountDownLatch;

public class Checker implements Runnable{

    Queen queensThreads[];
    int CorrectQueens = 0;



    public int getCorrectQueens() {
        return CorrectQueens;
    }

    public Checker(Queen[] queensThreads) {
        this.queensThreads = queensThreads;
    }

    public void run() {
        for (int i = 0; i < 8; i++) {
            if(queensThreads[i].isSafeQueen()){
                CorrectQueens++;
            }
        }
        System.out.println("Correct Queens: " + CorrectQueens);
    }
}
