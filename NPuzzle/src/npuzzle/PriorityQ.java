/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package npuzzle;

import java.util.ArrayList;

/**
 *
 * @author ZABER
 */
public class PriorityQ {

    ArrayList<Node> list=new ArrayList<Node>();
    public PriorityQ(){

    }

    public void add(Node node){
        list.add(node);
    }

    public Node get(){
        Node node;
        int temp=10000;
        int index=-1;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getF()<temp){
                temp=list.get(i).getF();
                index=i;
            }
        }
        node=list.get(index);
        list.remove(index);
        return node;   
    }

    public void printQ(){
        for(int i=0;i<list.size();i++){

            System.out.print(list.get(i).getF()+"-");
        
        }
            System.out.println();
    }



    public void clear(){
        list.clear();
    }

    public boolean isEmpty(){
        if(list.isEmpty())return true;
        return false;
    }

    public boolean equals(int []arr1,int []arr2){
        boolean foo=true;
        for(int i=0;i<9;i++){
            if(arr1[i]!=arr2[i]){
                foo=false;
                break;
            }
        }
        return foo;
    }

    public boolean contains(Node e){

        for(int i=0;i<list.size();i++){
            if(equals(list.get(i).getNodePattern(),e.getNodePattern())){
                return true;
            }
        }

        return false;
    }

    public boolean isGreater(Node e){
       for(int i=0;i<list.size();i++){
            if(equals(list.get(i).getNodePattern(),e.getNodePattern())){
                if(list.get(i).getF() >= e.getF())
                    return true;
            }
        }

        return false;
    }

}
