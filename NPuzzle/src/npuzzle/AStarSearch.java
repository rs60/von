/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package npuzzle;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 *
 * @author ZABER
 **/
public class AStarSearch {

   // PriorityQueue<Node> open;
    PriorityQ open;
    int[][] grid;
    int[][] mother;
    PriorityQ close;
    int dimension;
    public AStarSearch(int[][] state,int dim){

        dimension=dim;
        grid=new int[dimension][dimension];
        mother=new int[dimension][dimension];
        int k=0;
        int[] foo=new int[dimension*dimension];
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                grid[i][j]=state[i][j];
                foo[k]=state[i][j];
                k++;
            }
        }

      //  open=new PriorityQueue<Node>(1,Com);

        open=new PriorityQ();
        close=new PriorityQ();
        Node intialNode=new Node(foo, null);
        open.add(intialNode);
        open.printQ();

    }
/*
   public static Comparator<Node> Com = new Comparator<Node>() {
        public int compare(Node n1, Node n2) {
            return (int) (n1.getF() - n2.getF());
        }
    };
*/
    /**** FOR DEBUGGING PURPOSE *****/
    private void print(int[]p){
        for(int i=0;i<dimension*dimension;i++){
            System.out.print(p[i]+" ");
        }
        System.out.println();
    }

    private void creadeGrid(Node node){
        int i,j,k;
        k=0;
        for(i=0;i<dimension;i++){
            for(j=0;j<dimension;j++){
              mother[i][j]=node.getNodePattern()[k];
              k++;
            }
        }
    }

    private int[] generateNodePattern(int[][] temp){
          int [] arr=new int[dimension*dimension];
          int k=0;
          for(int i=0;i<dimension;i++){
              for(int j=0;j<dimension;j++){
                  arr[k]=temp[i][j];
                  k++;
              }
          }
        return arr;
    }

    private int[][] arrayCopy(int[][] temp){
        int[][] foo=new int[dimension][dimension];
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                foo[i][j]=temp[i][j];
            }
        }
        return foo;
    }

    public void expandTree(Node node){

        int row=0;
        int column=0;
        creadeGrid(node);
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                if(mother[i][j]==8){
                   row=i;
                   column=j;
                   break;
                }
            }
        }
    int temp[][];
      if(row>0){
          temp=arrayCopy(mother);
          temp[row][column]=temp[row-1][column] ;
          temp[row-1][column]=8;
          int [] arr=generateNodePattern(temp);
          Node child=new Node(arr,node);
  //        if(!open.contains(child) && !close.contains(child)){

          
          if(!open.contains(child) && !close.contains(child)){
               open.add(child);
          }
      }

     if(row<dimension-1){
          temp=arrayCopy(mother);
          temp[row][column]=temp[row+1][column] ;
          temp[row+1][column]=8;
          int [] arr=generateNodePattern(temp);
          Node child=new Node(arr,node);
          if(!open.contains(child) && !close.contains(child)){
              System.out.println("hehe0");
               open.add(child);
          }
         else if(open.contains(child) && open.isGreater(child)){
              System.out.println("hehe1");
              open.add(child);
          }

         else if(close.contains(child) && close.isGreater(child)){
              System.out.println("hehe2");
              open.add(child);
          }
      }

      if(column>0){
          temp=arrayCopy(mother);
          temp[row][column]=temp[row][column-1] ;
          temp[row][column-1]=8;
          int [] arr=generateNodePattern(temp);
          Node child=new Node(arr,node);
          if(!open.contains(child) && !close.contains(child)){
               open.add(child);
          }
          else if(open.contains(child) && open.isGreater(child)){
              System.out.println("hehe1");
              open.add(child);
          }

         else if(close.contains(child) && close.isGreater(child)){
              System.out.println("hehe2");
              open.add(child);
          }
      }

      if(column<dimension-1){
           temp=arrayCopy(mother);
          temp[row][column]=temp[row][column+1] ;
          temp[row][column+1]=8;
          int [] arr=generateNodePattern(temp);
          Node child=new Node(arr,node);
         if(!open.contains(child) && !close.contains(child)){
               open.add(child);
          }

          else if(open.contains(child) && open.isGreater(child)){
              System.out.println("hehe1");
              open.add(child);
          }

         else if(close.contains(child) && close.isGreater(child)){
              System.out.println("hehe2");
              open.add(child);
          }
      }
    }

    private boolean isLowerCost(Node node){
        boolean foo=false;
        PriorityQueue<Node> tempQ=new PriorityQueue<Node>();
     //     System.out.print("isLowerCost() end here");
        while(!open.isEmpty()){
        //    Node tt=open.poll();
            Node tt=open.get();
            tempQ.add(tt);
        }

        while(tempQ.isEmpty()){
            Node tt=tempQ.poll();
            open.add(tt);
            if(tt.getNodePattern().equals(node.getNodePattern()) && tt.getF()>node.getF()){
                foo=true;
            }
        }   
        return foo;
    }

    private void printPath(Node node){
        if(node.getParent()==null){
            System.out.print("Starting Node : ");
            node.printNodePattern();
        }
        else{
            printPath(node.getParent());
            System.out.print("--->");
            node.printNodePattern();
        }
    }

    private boolean isGoal(Node node){
        boolean foo=true;
      //  print(P);
        for(int i=0;i<9;i++){
          if(node.getNodePattern()[i]!=i){
              foo=false;
              break;
          }
        }
        return foo;
    }

    public void performSearch(){
        Node currentNode;
        int count=0;
        while(!open.isEmpty()){
            count++;
            System.out.println("count is "+count);
            
            open.printQ();
            currentNode=open.get();
            
            System.out.println(currentNode.getF());

            if(isGoal(currentNode)){
                System.out.println("Goal Found Cost "+currentNode.getF());
                open.clear();
                close.clear();
                printPath(currentNode);
                break;
            }
          //  else if(!open.contains(currentNode) && !close.contains(currentNode)){
                close.add(currentNode);
                expandTree(currentNode);
           // }

           // else if(open.contains(currentNode)&& isLowerCost(currentNode)){

             //   close.add(currentNode);
              //  expandTree(currentNode);
            //}
        }
    }
}



