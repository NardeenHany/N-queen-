/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eight.queen.puzzle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author Arsany
 */
public class Table {
    private final JFrame gameframe;
    private final BoardPanel boardPanel;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");
    
    public Table(int board[][]){
        this.gameframe = new JFrame("Jchess");
        this.gameframe.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = CreateTableMenuBar();
        this.gameframe.setJMenuBar(tableMenuBar);
        this.gameframe.setSize(OUTER_FRAME_DIMENSION);
    //    this.chessBoard = Board.createStandardBoard();
        this.boardPanel = new BoardPanel(board);
        this.gameframe.add(this.boardPanel, BorderLayout.CENTER);
        this.gameframe.setVisible(true);
        
    }

    private JMenuBar CreateTableMenuBar() {
        final JMenuBar tableMenuBar= new JMenuBar();
        tableMenuBar.add(createfileMenu()); 
        return tableMenuBar;
    }

    private JMenu createfileMenu() {
        final JMenu fileMenu = new JMenu("file");
        final JMenuItem openPGN = new JMenuItem("load PGN FILE");
        openPGN.addActionListener((ActionEvent e) -> {
            System.out.println("open up that pgn file");
        });
        fileMenu.add(openPGN);
        return fileMenu;
         }
    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;
        
        BoardPanel(int board[][]){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            boolean isValid=false;
            for (int i = 0; i < 8; i++) {
                    for(int k=0; k<8; k++){
                        System.out.print(board[i][k]);
                        if (board[i][k] == 1) {
                            isValid = true;
                            }
                        final TilePanel tilePanel = new TilePanel(this,isValid,i*8+k);
                        isValid = false;
                        this.boardTiles.add(tilePanel);
                        add(tilePanel);
                        }
                    System.out.println();
                    }
            
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }
    private class TilePanel extends JPanel{
        private final int tileId;
        TilePanel(final BoardPanel boardPanel,boolean isValid ,final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(isValid);
            validate();
        }

        public void assignTilePieceIcon ( boolean isValid){
            this.removeAll();
                String pieceIconPath = "picture/BQ.gif";
                if(isValid){
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath));
                    
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException ex) {
                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            //}
        }
        private void assignTileColor() {
            if(BoardUtils.FIRST_ROW.get(this.tileId) || 
                    BoardUtils.THIRD_ROW.get(this.tileId) || 
                    BoardUtils.FIFTH_ROW.get(this.tileId) || 
                    BoardUtils.SEVENTH_ROW.get(this.tileId) ){
                setBackground(this.tileId %2 == 0 ? lightTileColor : darkTileColor);
            }
            else if(BoardUtils.SECOND_ROW.get(this.tileId) || 
                    BoardUtils.FOURTH_ROW.get(this.tileId) || 
                    BoardUtils.SIXTH_ROW.get(this.tileId) || 
                    BoardUtils.EIGHTH_ROW.get(this.tileId) ){
                setBackground(this.tileId %2 != 0 ? lightTileColor : darkTileColor);
            }
        }
        private static boolean traverse(int first[][], int row, int column){
            for (int i = 0; i < row; i++) {
                    for(int k=0; k<column; k++){
                        if (first[i][k] == 1) {
                            return true;
                            }
                        }
                    }
            return false;
                }    
    }
}
