package com.company;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

    public static void main(String[] args) throws InterruptedException {

	MonitorConsole monitor=new MonitorConsole();


	Monitor monitor2=new Monitor();
	GraphManager manager=new GraphManager(100,20,1230,0,monitor2);
	manager.setName("Deicstra Algoritm Main Thread");
	Thread monitorThread=new Thread(monitor2);
	monitor2.AddMonitoringThread(manager);
	monitorThread.start();
	manager.start();
	manager.join();
	monitorThread.interrupt();
	//manager.ReadGraph("C:\\Users\\Taras\\source\\repos\\CPP\\lab4_2\\src\\com\\company\\testGraph.txt");
	//manager.GenerateGraph(300,10,1230);
//	manager.PrintToConsoleGraph();
//	ArrayList<Thread>threads manager.GetAlgorithmThreads();
	//manager.FindMinWay(0);
	//manager.PrintToConsoleGraph();
		/*javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Monitor.ShowGui();
			}});
	Thread.sleep(100000);*/
    }
}
