package eight.queen.puzzle;


import javafx.util.Pair;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.border.*;

    public class TestBoard {



        private final JPanel gui = new JPanel(new BorderLayout(3, 3));
        private final JButton[][] chessBoardSquares = new JButton[8][8];
        private JPanel chessBoard;

        private static final String COLS = "ABCDEFGH";

        TestBoard() {
            initializeGui();
        }
        Icon icon = new ImageIcon("E:\\Study\\4th\\PP\\Project\\N-queen-\\picture\\c1.png");

        public final void initializeGui() {
            // set up the main GUI
            gui.setBorder(new EmptyBorder(5, 5, 5, 5));
            JToolBar tools = new JToolBar();
            tools.setFloatable(false);
            gui.add(tools, BorderLayout.PAGE_START);

            JButton solveButton = new JButton("Solve");
            JButton resetButton = new JButton("Reset");
            JButton submitButton = new JButton("Submit");

            tools.add(solveButton);
            tools.add(resetButton);
            tools.addSeparator();
            tools.add(submitButton);
            tools.addSeparator();

            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                chessBoardSquares[i][j].setIcon(null);
                            }
                        }
                }
            });

            solveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EightQueenPuzzle solver = new EightQueenPuzzle();
                    getboardValues();
                    int[][] board = { { 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0 }};

                    ArrayList<int[][]> boards = new ArrayList<>();
                    solver.solveNQueen(board,0,boards);
                    int boardNumber = (int)(Math.random() * (boards.size() - 1 + 1) + 1);
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if(boards.get(boardNumber -1)[i][j] == 1){
                                chessBoardSquares[i][j].setIcon(icon);
                            }
                            else{
                                chessBoardSquares[i][j].setIcon(null);
                            }
                        }
                    }
               }
            });


            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int [][]board = new int[8][8];
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if(chessBoardSquares[i][j].getIcon() == icon){
                                board[j][i] = 1;
                            }
                            else{
                                board[j][i] = 0;
                            }
                        }
                    }

                    int coreCount = Runtime.getRuntime().availableProcessors();

                    // just run first 8 threads that contains each queens location checker
                    final int noOfQueens = 8;
                    ExecutorService service = Executors.newFixedThreadPool(8);

                    CountDownLatch latch = new CountDownLatch(8);
                    //get Queens locations

                    Vector<Pair<Integer, Integer>> queens = GetQueensLocations(board);

                    if(queens.size() != 8){
                        JOptionPane.showMessageDialog(null, "Please enter 8 queens only, not more or less ", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    ArrayList<Thread> queensThreads = new ArrayList<>();
                    Queen[] qt = new Queen[8];

                    //make for each queen a thread
                    for (int i = 0; i < noOfQueens; i++) {
                        Queen q = new Queen(board,queens.get(i).getKey(),queens.get(i).getValue(),latch);
                        qt[i] = q;
                        Thread t = new Thread(q);
                        queensThreads.add(t);
                    }

//        submitting all threads
                    for (Thread t : queensThreads){
                        service.submit(t);
                    }

                    try {
                        latch.await();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    CountDownLatch latch2 = new CountDownLatch(1);

//        create Checker( checks that all queens are in correct positions)
                    Checker check = new Checker(qt,latch2);
                    Thread t = new Thread(check);
                    service.submit(t);
                    try {
                        latch2.await();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("Answer:"+check.CorrectQueens);
                    if(check.CorrectQueens == 8){
//                    System.out.println("___________________c:"+check.CorrectQueens);
                        JOptionPane.showMessageDialog(null, "Your Answer is Correct", "Nice", JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Your Answer is Wrong", "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                    service.shutdown();
                }
            });

            gui.add(new JLabel("?"), BorderLayout.LINE_START);

            chessBoard = new JPanel(new GridLayout(0, 9));
            chessBoard.setBorder(new LineBorder(Color.BLACK));
            gui.add(chessBoard);

            // create the chess board squares
            Insets buttonMargin = new Insets(0,0,0,0);
            for (int ii = 0; ii < chessBoardSquares.length; ii++) {
                for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                    JButton b = new JButton();
                    b.setMargin(buttonMargin);
                    // our chess pieces are 64x64 px in size, so we'll
                    // 'fill this in' using a transparent icon..
                    ImageIcon icon = new ImageIcon(
                            new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                    b.setIcon(icon);
                    if ((jj % 2 == 1 && ii % 2 == 1)
                            //) {
                            || (jj % 2 == 0 && ii % 2 == 0)) {
                        b.setBackground(Color.WHITE);
                    } else {
                        b.setBackground(Color.BLACK);
                    }
                    chessBoardSquares[jj][ii] = b;
                }
            }

            //fill the chess board
            chessBoard.add(new JLabel(""));
            // fill the top row
            for (int ii = 0; ii < 8; ii++) {
                chessBoard.add(
                        new JLabel(COLS.substring(ii, ii + 1),
                                SwingConstants.CENTER));
            }
            // fill the black non-pawn piece row
            for (int ii = 0; ii < 8; ii++) {
                for (int jj = 0; jj < 8; jj++) {
                    if (jj == 0) {
                        chessBoard.add(new JLabel("" + (ii + 1),
                                SwingConstants.CENTER));
                    }
                    chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
        private void QueenIconToggleButton(){
            for (int ii = 0; ii < 8; ii++) {
                for (int jj = 0; jj < 8; jj++) {
                     int x = ii;
                     int y = jj;
                    chessBoardSquares[ii][jj].setIcon(null);
                    chessBoardSquares[ii][jj].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(chessBoardSquares[x][y].getIcon() == null){

                                chessBoardSquares[x][y].setIcon(icon);
                            }
                            else {
                                chessBoardSquares[x][y].setIcon(null);
                            }

                        }
                    });

                }
            }
        }

        public void resetBoard(){

        }
        public final JComponent getChessBoard() {
            return chessBoard;
        }

        public final JComponent getGui() {
            return gui;
        }

        public static void main(String[] args) {
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    TestBoard cb =
                            new TestBoard();

                    JFrame f = new JFrame("ChessChamp");
                    f.add(cb.getGui());
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setLocationByPlatform(true);
                    cb.QueenIconToggleButton();
                    // ensures the frame is the minimum size it needs to be
                    // in order display the components within it
                    f.pack();
                    // ensures the minimum size is enforced.
                    f.setMinimumSize(f.getSize());
                    f.setVisible(true);
                    f.setBounds(100,100,700,700);
                }
            };
            SwingUtilities.invokeLater(r);
        }

        static void printSolution(int[][] board)
        {
            for (int i = 0; i <8; i++) {
                for (int j = 0; j < 8; j++)
                    System.out.print(" " + board[i][j]
                            + " ");
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

        public int[][] getboardValues() {
            int [][]board = new int[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(chessBoardSquares[i][j].getIcon() == icon){
                        board[j][i] = 1;
                    }
                    else{
                        board[j][i] = 0;
                    }
                }
            }
            return board;
        }
    }

