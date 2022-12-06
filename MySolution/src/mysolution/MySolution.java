/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package mysolution;
import java.util.Scanner;



/**
 *
 * @author Arsany
 */
public class MySolution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int[][] first = { { 0, 0, 0, 1, 0, 0, 0, 0 },
                          { 0, 0, 0, 0, 0, 0, 1, 0 },
                          { 0, 0, 1, 0, 0, 0, 0, 0 },
                          { 0, 0, 0, 0, 0, 0, 0, 1 }, 
                          { 0, 1, 0, 0, 0, 0, 0, 0 },
                          { 0, 0, 0, 0, 1, 0, 0, 0 },
                          { 1, 0, 0, 0, 0, 0, 0, 0 },
                          { 0, 0, 0, 0, 0, 1, 0, 0 }};
        int row = 8;
        int column = 8;
       

		// print both matrices
		System.out.println("First Matrix:\n");
		print2dArray(first);
                traverse(first,row,column);
                
               
	
        
    }
	private static void print2dArray(int[][] matrix) {
        for (int i=0; i<8 ; i++) {
            for (int c = 0; c < 8; c++) {
                System.out.print(matrix[i][c] + "\t");
            }
            System.out.println();
        }
	}
        
        static boolean isSafe(int board[][], int row, int col)
    {
        int i, j;
        for (i = 0; i < 8; i++)
            if (board[row][i] == 1 && i!=col )
                return false;

        for (i = 0; i < 8; i++)
            if (board[i][col] == 1 && i!=row )
                return false;
        
        for (i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;
            
        for (i = row+1, j = col-1; j >= 0 && i < 8; i++, j--)
            if (board[i][j] == 1)
                return false;
        
        for (i = row+1, j = col+1; i < 8 && j < 8 ; i++, j++)
            if (board[i][j] == 1)
                return false;
            
        for (i = row-1, j = col+1; j < 8 && i >= 0; i--, j++)
            if (board[i][j] == 1)
                return false;
        
        return true;
    }
        private static void traverse(int first[][], int row, int column){
            int counter = 0;
            for (int i = 0; i < row; i++) {
                    for(int k=0; k<column; k++){
                        if (first[i][k] == 1) {
                            System.out.println(isSafe(first,i,k));
                            if(isSafe(first,i,k)){
                                counter++;
                             // System.out.println(counter);
                            }
                        }
                    }
                }
            if(counter ==8){
                System.out.println("True");
            }
            else{
               System.out.println("False"); 
            }    
        }

    }
    

