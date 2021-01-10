package com.company;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
public class DeicstraAlgorithm extends Thread {
    private static adjacentList nodeList;
    private static int threadsCount=8;
    private Node baseNode;
    private MyMonitor monitorThread;
    private Semaphore semaphore;
    private AtomicBoolean FindMin=new AtomicBoolean(true);
    public static ArrayList<Thread> threadStack;
    private static ArrayList<Integer> ways;
    private static HashSet<Integer> unVisitedNodes;
    private static  ArrayList<Integer> distance;
    private static  ArrayList<Integer> Mins;
    private Monitor monitor=new Monitor();
    public DeicstraAlgorithm(adjacentList nodeList, Node baseNode,MyMonitor monitorThread){
        this.baseNode=baseNode;
        this.monitorThread=monitorThread;
        if(DeicstraAlgorithm.nodeList==null) {
            DeicstraAlgorithm.nodeList = nodeList;
        }
        this.baseNode=baseNode;
        if(DeicstraAlgorithm.ways==null){
            DeicstraAlgorithm.ways=new ArrayList<Integer>();
            for (int i=0;i<nodeList.GetNodeCount();++i){
               DeicstraAlgorithm.ways.add(0);
            }
        }
        if (DeicstraAlgorithm.ways.size()<nodeList.GetNodeCount()){
            for (int i=DeicstraAlgorithm.ways.get(DeicstraAlgorithm.ways.size()-1)+1;i<nodeList.GetNodeCount();++i){
                DeicstraAlgorithm.ways.add(i);
            }
        }
        if(DeicstraAlgorithm.distance==null){
            DeicstraAlgorithm.distance=new ArrayList<Integer>();
            for (int i=0;i<nodeList.GetNodeCount();++i){
               DeicstraAlgorithm.distance.add(Integer.MAX_VALUE);
            }
            distance.set(baseNode.GetNodeId(),0);
        }
        if (DeicstraAlgorithm.distance.size()<nodeList.GetNodeCount()){
            for (int i=DeicstraAlgorithm.distance.get(DeicstraAlgorithm.distance.size()-1)+1;i<nodeList.GetNodeCount();++i){
                DeicstraAlgorithm.distance.add(Integer.MAX_VALUE);
            }
        }
       unVisitedNodes=new HashSet<Integer>();
        for (int i=0;i<nodeList.GetNodeCount();++i){
            unVisitedNodes.add(i);
        }
        threadStack=new ArrayList<Thread>();

    }
    public int FindNearestAdjacentNode(Node node){
     Map.Entry<Integer,Integer> find=null;
        for (Map.Entry<Integer,Integer> temp: node.GetAdjacentNodes().entrySet()) {
            if(nodeList.GetNode(temp.getKey()).CheckIsVisted()){
                if(find==null){
                    find=temp;
                }else if(find.getValue()>temp.getValue()){
                    find=temp;
                }
            }
        }
        if(find!=null) {
            return find.getKey();
        }
        else return -1;
    }
    public int FindNearestNode(){
        if (Mins!=null){
            int rezult=Integer.MAX_VALUE;
            for (Integer min: Mins) {
                if (min>=0) {
                    if ((distance.get(min) < rezult))
                        rezult = min;
                }
            }
            return rezult;
        }else {
            return -1;
        }
    }
    public class NearestNodeFinder implements Runnable {
        private ArrayList<Integer>nodeIdList;
        private int minDistanceId;
        final AtomicBoolean stop=new AtomicBoolean(true);
        public  NearestNodeFinder(int minId,int maxId,int minDistanceId) {
            nodeIdList = new ArrayList<Integer>();
            if ((maxId >= minId) && (maxId < distance.size()))
                for (int i = minId; i <= maxId; ++i) {
                    if (unVisitedNodes.contains(i))
                        nodeIdList.add(i);
                }
            this.minDistanceId=minDistanceId;
        }
        @Override
        public void run()
        {
            try{
                while (stop.get()) {
                    for (int i=0;i<10000000;++i){
                    }
                    //Thread.sleep(50);
                    //System.out.println("Start thread"+ minDistanceId);
                    nodeIdList = new ArrayList<Integer>(nodeIdList.stream().filter(id -> unVisitedNodes.contains(id)).collect(Collectors.toList()));
                    if (nodeIdList.size()==0){
                        Mins.set(minDistanceId,-1);
                        System.out.println("Thread: "+minDistanceId+" stoped" );
                        stop.set(false);
                        semaphore.release();
                        break;
                    }
                    if (nodeIdList.size() > 0) {
                        int min = nodeIdList.get(0);
                        for (Integer id : nodeIdList) {
                            if (distance.get(id) < distance.get(min)) {
                                min = id;
                            }
                        }
                        Mins.set(minDistanceId, min);
                    } else {
                        Mins.set(minDistanceId, -1);
                        stop();
                    }
                    semaphore.release();
                    synchronized (monitor){
                        monitor.wait();
                    }
                }
            }catch(InterruptedException e){
                this.stop();
            }
                //System.out.println("End thread" + minDistanceId);
        }
        public boolean stop(){
            if (stop.get()){
                stop.set(false);
                return  true;
            }else {
                return false;
            }

        }
    }
    public int DoAlgorithmStep(Node StartNode){
        if(StartNode.Visit()){
            unVisitedNodes.remove(StartNode.GetNodeId());

        if(StartNode.GetNodeId()==baseNode.GetNodeId()){
           DeicstraAlgorithm.distance.set(StartNode.GetNodeId(),0);
        }
            for (Map.Entry<Integer,Integer> node: StartNode.GetAdjacentNodes().entrySet()) {
                if(distance.get(node.getKey())>(distance.get(StartNode.GetNodeId()).intValue()+node.getValue())){
                    distance.set(node.getKey(),distance.get(StartNode.GetNodeId()).intValue()+node.getValue());
                    ways.set(node.getKey(),StartNode.GetNodeId());
                }
            }
        }
        return 0;
    }
    public ArrayList<Thread> GetThreadStack(){
        if (this!=null) {
            return threadStack;
        }
        return null;
    }
    public void Algorithm() throws InterruptedException {
        ArrayList<NearestNodeFinder> threads=new ArrayList<NearestNodeFinder>();
        if ((nodeList.GetNodeCount()/threadsCount)>2){
            int NodePart=nodeList.GetNodeCount()/threadsCount;
            Mins=new ArrayList<Integer>();
            for(int i=0;i<threadsCount;++i){
                Mins.add(-1);
                if (i==(threadsCount-1)){
                    threads.add(new NearestNodeFinder(i*NodePart,nodeList.GetNodeCount()-1,i));
                }else
                threads.add(new NearestNodeFinder(i*NodePart,(i+1)*NodePart-1, i));
            }
        }else {
            threadsCount=1;
            Mins=new ArrayList<Integer>();
            Mins.add(0,-1);
            threads.add(new NearestNodeFinder(0,nodeList.GetNodeCount()-1,0));
        }
        semaphore=new Semaphore(threads.size());
        int threadsCount=0;
        int clock=0;
        for (int i=0;i<threads.size();++i) {
            if (threadStack.size() < (i + 1)) {
                threadStack.add(new Thread(threads.get(i), "Deicstra Thread" + i));
            }
        }
        if (monitorThread!=null) {
            monitorThread.AddMonitoringThreads(threadStack);
        }
        Thread.sleep(100);
        //Algorithm Cycle
        while(unVisitedNodes.size()>0){
            semaphore.acquire(threads.size());
            threadsCount=0;
            boolean btemp=true;
            for (int i=0;i< threadStack.size();++i) {
                if (threadStack.get(i).getState().equals(State.NEW)){
                    threadStack.get(i).start();
                    btemp=false;
                }
                if (!threadStack.get(i).getState().equals(State.TERMINATED)){
                    ++threadsCount;
                }
            }
            if(btemp) {
                synchronized (monitor) {
                    //FindMin.set(false);
                    monitor.notifyAll();
                }
            }
            //Thread.sleep(50);
            ++clock;
            //System.out.println("Error "+clock);
            if (threadsCount>semaphore.availablePermits()){

                //System.out.println("Error");
            }
            if (threadsCount!=0) {
                semaphore.tryAcquire(threadsCount,100000, TimeUnit.MILLISECONDS);
            //acquire(threadsCount);

            }else
            {
                semaphore.acquire();
            }
            boolean b=true;
            Node temp=nodeList.GetNode(FindNearestNode());
            if(temp!=null) {
                DoAlgorithmStep(temp);
            }else {
                break;
            }
            semaphore.release(threads.size());
            }
        for (Thread thread:threadStack) {
            thread.interrupt();
        }
    }
    @Override
    public String toString() {
        String outString=new String();
        outString+="Distances to nodes from node ID:"+baseNode.GetNodeId()+"\n";
        for (Integer dis: distance) {
            outString+=dis.toString()+" ";
        }
        outString+="\n Ways to nodes from node ID:"+baseNode.GetNodeId()+"\n";
        for (int i=0;i<ways.size();++i) {
            ArrayList<Integer> wayIds=new ArrayList<Integer>();
            wayIds.add(i);
            while((!wayIds.get(wayIds.size()-1).equals(baseNode.GetNodeId()))&&(wayIds.size()<ways.size())) {
                wayIds.add(ways.get(wayIds.get(wayIds.size()-1)));
            }
            outString+="Node "+i+": ";
            for (int j=wayIds.size()-1;j>=0;--j) {
                outString+=wayIds.get(j).toString();
                if (j!=0){
                    outString+=" -> ";
                }
            }
            outString+="\n";
        }
        return outString;
    }

    @Override
    public void run() {
        try {
            Algorithm();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
