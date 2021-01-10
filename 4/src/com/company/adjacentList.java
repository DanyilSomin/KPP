package com.company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class adjacentList implements Cloneable {
    private ArrayList<Node> nodeList;
    public adjacentList(){
        nodeList=new ArrayList<Node>();
    }
    public void AddNode(Node node){
        nodeList.add(node);
    }
    public ArrayList<Node> GetNodeList(){
        return nodeList;
    }
    public int GetNodeCount(){
        return nodeList.size();
    }
    public int GetUnVisitedNodeCount(){
        return nodeList.stream().filter(node->node.CheckIsVisted()).collect(Collectors.toList()).size();
    }
    public  Node GetNode(int nodeId){
        return nodeList.stream().filter(node -> node.GetNodeId()==nodeId).findFirst().orElse(null);
    }
    public ArrayList<Node>GetUnVisitedNodes(){
        return new ArrayList<Node>( nodeList.stream().filter(node->node.CheckIsVisted()).collect(Collectors.toList()));
    }
    public Node GetUnVisitedNode(int nodeId){
        return nodeList.stream().filter(node -> (node.GetNodeId()==nodeId)&&(node.CheckIsVisted())).findFirst().orElse(null);
    }
    public  int GetUnCommitedNoteCount(){
        ArrayList<Integer> CommitedNode=new ArrayList<Integer>();
        for (Node node:nodeList) {
          HashMap<Integer,Integer> adjestance=node.GetAdjacentNodes();
            for (Map.Entry<Integer,Integer> adjestantNode:adjestance.entrySet()) {
                if(!CommitedNode.contains(adjestantNode.getKey())){
                    CommitedNode.add(adjestantNode.getKey());
                }
            }
        }
        return nodeList.size()-CommitedNode.size();
    }
    @Override
    public String toString() {
        String outString=new String();
        for (Node node: nodeList) {
          outString=outString.concat(node.toString()+"\n");
        }
        return outString;
    }
}
