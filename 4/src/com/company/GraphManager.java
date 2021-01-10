package com.company;
import com.sun.source.tree.WhileLoopTree;

import java.io.Console;
import java.io.FileNotFoundException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
public class GraphManager extends Thread {
    private adjacentList graphList;
    private Clock clock=Clock.system(ZoneId.systemDefault());
    private ReadGraphFromFile readGraph;
    private DeicstraAlgorithm minWayAlgorithm;
    private int startNodeId;
    private  MyMonitor monitorThread;
    public ArrayList<Thread> GetAlgorithmThreads(){
        if (minWayAlgorithm!=null)
        return  minWayAlgorithm.GetThreadStack();
        else return new ArrayList<Thread>();
    }
    public void FindMinWay(int startNodeId) throws NullPointerException{
        try {
            if (graphList.GetNode(startNodeId) != null) {
                minWayAlgorithm.Algorithm();
            }
            //System.out.println();
            System.out.println(minWayAlgorithm.toString());
        }catch (InterruptedException exception){
            System.out.println(exception.getMessage());
        }
    }
    public  GraphManager(MyMonitor monitorThread){
        this.monitorThread=monitorThread;
        graphList=new adjacentList();
    }
    public GraphManager(int nodeCount, int maxEdgestCount, int maxValue, int StartNodeId,Monitor monitorThread ){
        this.monitorThread=monitorThread;
        graphList=new adjacentList();
        GenerateGraph(nodeCount,maxEdgestCount,maxValue);
        //ReadGraph("C:\\Users\\Taras\\source\\repos\\CPP\\lab4_2\\src\\com\\company\\testGraph.txt");
        this.startNodeId=StartNodeId;
        minWayAlgorithm = new DeicstraAlgorithm(graphList, graphList.GetNode(startNodeId),monitorThread);
    }
    public void PrintToConsoleGraph(){
        if (graphList!=null){
            System.out.println("Graph output by abjacency list");
            System.out.println(graphList.toString());
        }else{
            System.out.println("Error.Graph didnt readed");
        }
    }
    public void GenerateGraph(int nodeNumber, int maxEdgesNumber,int maxValue){
        Random random=new Random();
        graphList=new adjacentList();
        for (int i = 0; i < nodeNumber; i++) {
            graphList.AddNode(new Node(i));
        }
        int currentNodeId=random.nextInt(nodeNumber);
        while ((graphList.GetUnCommitedNoteCount()>0 )||(graphList.GetUnVisitedNodeCount()>0)) {
            int edgesCount=0;
            if (graphList.GetNode(currentNodeId).GetAdjacentNodes().size()<maxEdgesNumber) {
                edgesCount = Math.min(maxEdgesNumber - graphList.GetNode(currentNodeId).GetAdjacentNodes().size(),
                        random.nextInt(maxEdgesNumber - 1) + 1);
                graphList.GetNode(currentNodeId).Visit();
                ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
                while (randomNumbers.size() < edgesCount) {
                    int rand = random.nextInt(nodeNumber);
                    if ((!randomNumbers.contains(rand)) && (rand != currentNodeId)) {
                        randomNumbers.add(rand);
                    }
                }
                for (int i = 0; i < randomNumbers.size(); ++i) {
                    graphList.GetNode(currentNodeId).AddAdjacentNode(randomNumbers.get(i), random.nextInt(maxValue) + 1);
                }
            }
            if (graphList.GetNode(currentNodeId)==null){
                int k=0;
            }
            ArrayList<Integer> temp=new ArrayList<Integer>();
            for (Map.Entry<Integer,Integer> nodeEntry: graphList.GetNode(currentNodeId).GetAdjacentNodes().entrySet()) {
                temp.add(nodeEntry.getKey());
            }
            currentNodeId=temp.get(random.nextInt(temp.size()));
        }
        for(int i=0;i<nodeNumber;++i){
            graphList.GetNode(i).ResetVisited();
        }
    }
    public void ReadGraph(String fileName){
        try {
            if (readGraph==null){
                readGraph=new ReadGraphFromFile(fileName);
            }else {
                readGraph.ChangeFile(fileName);
            }
            ArrayList<String> input = new ArrayList<String>(readGraph.ReadFromFile());
            for (String line: input) {
            String[] parseline=line.split(" ");

            if(parseline.length>=1) {
                Node temp = new Node(Integer.parseInt(parseline[0]));
                for (int i = 1; i < parseline.length; ++i) {
                    String[] tempString=parseline[i].split(":");
                    temp.AddAdjacentNode(Integer.parseInt(tempString[0]),Integer.parseInt(tempString[1]));
                }
                graphList.AddNode(temp);
            }
            }
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());

        }
    }

    @Override
    public void run(){
        try{
            Thread.sleep(100);
            PrintToConsoleGraph();
            System.out.println("Deicstra Algorithm");
           long i=System.currentTimeMillis();
            FindMinWay(startNodeId);
            i=System.currentTimeMillis()-i;
            monitorThread.SetTime(i);

        }catch (Exception exception){
            System.out.println(exception.getMessage());

        }

    }
}
