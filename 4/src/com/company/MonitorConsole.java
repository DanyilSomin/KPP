package com.company;

import java.util.ArrayList;

public class MonitorConsole extends MyMonitor {
        public MonitorConsole(){
            super();
            this.monitorPeriod=5;
        }

    @Override
    public void run() {
       try {
           //AddMonitoringThreads(manager.GetAlgorithmThreads());
           while (true) {
                Thread.sleep(monitorPeriod);
               for (Thread thread : monitoringThreads) {
                   System.out.println(thread.getName() + " " + thread.getState().toString() + " " + thread.getPriority());
               }
           }
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
    }
}
