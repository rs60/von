/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package npuzzle;

/**
 *
 * @author ZABER
 */
public class Node {

    private int f;
    private int g;
    private int h;
    int nodePattern[]=new int[9];
    private Node parent;
    int[] P=new int[9] ;
    int[] B=new int[9] ;

    public Node(int array[],Node par){
        nodePattern=array;
        parent=par;

        if(par==null)g=0;

        else g=par.g+1;

        h=nMaxSwap();

        f=g+h;
    }

    
    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    private void setF(){
        f=getG()+getH();
    }

    public int[] getNodePattern() {
        return nodePattern;
    }

    public Node getParent() {
        return parent;
    }

        /**** FOR DEBUGGING PURPOSE *****/
    private void print(int[]p){
        for(int i=0;i<9;i++){
            System.out.print(p[i]+" ");
        }
        System.out.println();
    }

    public boolean isGoal(){
        boolean foo=true;
        
        for(int i=0;i<9;i++){
          if(P[i]!=i){
              foo=false;
              break;
          }
        }
        return foo;
     }

    private int firstWrongPosition(){
        int pos=0;
        for(int i=0;i<8;i++){
            if(B[i]!=i){
                pos=i;
                break;
            }
        }
        return pos;
    }

    private int nMaxSwap() {
       P=nodePattern.clone();
       int cost=0;
       int temp;

       for(int i=0;i<9;i++){
           B[P[i]]=i;
       }

      // for(int k=0;k<9;k++)System.out.print(P[k]+" ");
      //     System.out.println();

       while(!isGoal()){

         if(P[8]==8){
             temp=P[8];
             P[8]=P[firstWrongPosition()];
             P[firstWrongPosition()]=temp;
             cost++;
             updateB();
             continue;
         }

         temp=P[B[8]];
         P[B[8]]=P[B[B[8]]];
         P[B[B[8]]]=temp;

        // System.out.println("Swapping : "+P[B[8]]+" - "+P[B[B[8]]]);
        // for(int k=0;k<9;k++)System.out.print(P[k]+" ");
        // System.out.println();

         cost++;
         updateB();
       }
       return cost;
    }

    private void updateB(){
       for(int i=0;i<9;i++){
           B[P[i]]=i;
       }
    }

    public void printNodePattern(){
        for(int i=0;i<9;i++){
            System.out.print(nodePattern[i]+" ");
        }
        System.out.println();
    }
}

