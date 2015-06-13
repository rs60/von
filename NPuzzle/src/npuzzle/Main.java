/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package npuzzle;

import java.util.Scanner;

/**
 *
 * @author ZABER
 */
public class Main {

    /**
     * @param args the command line arguments
     */


    static int[] G=new int[]{0,1,2,3,4,5,6,7,8};
    static int[] B=new int[9];
    public static void main(String[] args) {


        int[] P=new int[]{0,1,2,3,4,8,5,6,7};
      //  int[] P=new int[]{4,5,8,7,3,2,0,1,6};
        int[][]arr=new int[3][3];
        // TODO code application logic here
        int k=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                arr[i][j]=P[k];
                k++;
            }
        }

        AStarSearch start=new AStarSearch(arr, 3);
        start.performSearch();
    }
}
