package com.company;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class Monitor extends MyMonitor {
    private JFrame mainFrame;
    private JTable tableThreadInformation;
    private DefaultTableModel tableModel;
    private JLabel rezultTime;
    private JPanel panel;
    private Integer time=null;
    private Box tableBox;
    private JLabel labelTitle;
    public Monitor() {
        super();
        Font font = new Font("Verdana", Font.PLAIN, 18);
        panel=new JPanel();
        panel.setLayout(new BorderLayout());
        JFrame.setDefaultLookAndFeelDecorated(false);
        mainFrame=new JFrame("Threads Monitor");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Object [] tableHeader=new String[]{"Id","Name","Status","Priority"};
        tableModel=new DefaultTableModel();
        tableModel.setColumnIdentifiers(tableHeader);
        tableThreadInformation=new JTable(tableModel);
        tableThreadInformation.setAutoCreateColumnsFromModel(true);
        tableThreadInformation.setVisible(true);
        tableThreadInformation.setRowHeight(24);
        tableThreadInformation.setBounds(50,100,200,600);
        tableBox=new Box(BoxLayout.Y_AXIS);
        tableBox.setVisible(true);
        tableBox.add(new JScrollPane(tableThreadInformation));

        labelTitle=new JLabel("Monitoring Deicstra Algorithm Threads");
        labelTitle.setFont(font);
        labelTitle.setVerticalAlignment(JLabel.CENTER);
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        rezultTime=new JLabel("Algorithm working Time:");
        rezultTime.setFont(font);
        rezultTime.setVerticalAlignment(JLabel.CENTER);
        rezultTime.setHorizontalAlignment(JLabel.LEFT);

        panel.add(tableBox,BorderLayout.CENTER);
        panel.add(labelTitle,BorderLayout.NORTH);
        panel.add(rezultTime,BorderLayout.SOUTH);
        mainFrame.getContentPane().add(panel);
        mainFrame.setPreferredSize(new Dimension(500, 400));
        mainFrame.pack();
        //mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    @Override
    public void SetTime(long time) {
        super.SetTime(time);
        rezultTime.setText("Algorithm working Time: "+time+"ms");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Override
    public void run() {

        mainFrame.setVisible(true);
        try {Thread.sleep(100);
            while (true) {
                Thread.sleep(monitorPeriod);
                for (Thread thread: monitoringThreads) {
                    tableModel.addRow(new Object[]{thread.getId(),thread.getName(),thread.getState(), thread.getPriority()});
                    //System.out.println(thread.getName() + " " + thread.getState().toString() + " " + thread.getPriority());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
