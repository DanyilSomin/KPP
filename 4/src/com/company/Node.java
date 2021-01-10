package com.company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    private int nodeId;
    private int value;
    private boolean visited;
    private HashMap<Integer,Integer> adjacentNodes;

    public Node(int nodeId){
       this.nodeId=nodeId;
       this.adjacentNodes=new HashMap<Integer, Integer>();
       this.value=Integer.MAX_VALUE;
       this.visited=false;
    }
    public boolean Visit(){
        if(!visited){
        this.visited=true;
        return true;
        }else {
            return false;
        }
    }
    public boolean ResetVisited(){
        if(visited){
            this.visited=false;
            return true;
        }else {
            return false;
        }
    }
    public boolean CheckIsVisted(){
        return !visited;
    }

    public Node(int nodeId, HashMap<Integer,Integer> adjacents){
        this.nodeId=nodeId;
        this.adjacentNodes=new HashMap<Integer, Integer>(adjacents);
    }
    public int GetNodeId(){
        return nodeId;
    }
    public boolean IsAdjastend(int nodeId){
        if(adjacentNodes.containsKey(nodeId)){
            return true;
        }else
            return false;
    }
    public void AddAdjacentNode(int node,int distance){
        if(!adjacentNodes.containsKey(node)){
            adjacentNodes.put(node,distance);
        }
    }
    public HashMap<Integer,Integer> GetAdjacentNodes() {
        return adjacentNodes;
    }
    public int GetDistanceToAdjastentNode(int nodeId){
        if(adjacentNodes.containsKey(nodeId)){
            return adjacentNodes.get(nodeId);
        }
        return -1;
    }
    @Override
    public boolean equals(Object obj) {
        if((obj!=null)){
            if(obj.getClass().equals(this.getClass())){
                if (nodeId==((Node)obj).nodeId) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public String toString() {
        String outString=new String("NodeId:"+nodeId);
        for (Map.Entry<Integer,Integer> adjacent: adjacentNodes.entrySet()) {
           outString=outString.concat("\t"+"("+adjacent.getKey()+":"+adjacent.getValue()+")");
        }
        return  outString;
    }
}
