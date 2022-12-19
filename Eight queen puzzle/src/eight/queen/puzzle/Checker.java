package eight.queen.puzzle;

import java.util.concurrent.CountDownLatch;

public class Checker implements Runnable{

    Queen[] queensThreads;
    int CorrectQueens = 0;

    private final CountDownLatch countDownLatch;

    boolean solutionStatus = false;



    public Checker(Queen[] queensThreads,CountDownLatch countDownLatch) {
        this.queensThreads = queensThreads;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        for (int i = 0; i < 8; i++) {
            if(queensThreads[i].isSafeQueen()){
                CorrectQueens++;
            }
            else{
                System.out.println("wrong queen");
            }
        }
        if (CorrectQueens == 8){
            solutionStatus = true;
        }
        countDownLatch.countDown();
//        System.out.println("Correct Queens: " + CorrectQueens);
    }
}
