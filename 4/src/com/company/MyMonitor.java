package com.company;

import java.time.Clock;
import java.util.ArrayList;

public class MyMonitor implements Runnable{
    protected ArrayList<Thread> monitoringThreads;
    protected Clock monitorClock;
    protected int monitorPeriod;
    public MyMonitor(){
        monitorClock=Clock.systemDefaultZone();
        monitoringThreads=new ArrayList<Thread>();
    }
    public void AddMonitoringThreads(ArrayList<Thread> addedThreads){
        if (monitoringThreads==null){
            monitoringThreads=new ArrayList<Thread>();
        }
        if (addedThreads!=null){
            for (Thread thread :addedThreads) {
                if (!monitoringThreads.contains(thread)){
                    monitoringThreads.add(thread);
                }
            }
        }
    }
    public void SetTime(long time){
    }
    public void AddMonitoringThread(Thread thread){
        if (monitoringThreads==null){
            monitoringThreads=new ArrayList<Thread>();
        }

        if (thread!=null){
            if (!monitoringThreads.contains(thread)){
                monitoringThreads.add(thread);
            }
        }

    }

    @Override
    public String toString() {
        String outString=new String(super.toString());
        if (monitoringThreads!=null){
            for (Thread thread:monitoringThreads) {
                outString += new String(thread.getName() + " " + thread.getPriority() + " " + thread.getState());
            }
        }
        return super.toString();
    }

    @Override
    public void run() {

    }
}
