package gui;

import buffer.Buffer;
import workers.Consumer;
import workers.Producer;
import workers.Worker;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TotalGUI {

    JFrame frame;

    JPanel totalPanel;

    JPanel producersPanel;

    JPanel consumersPanel;

    JPanel bufferPanel;

    BufferGUI bufferGUI;

    Map<String,WorkerGUI> producersGUIs;

    Map<String,WorkerGUI> consumersGUIs;

    JPanel semaphoresPanel;


    public TotalGUI (Buffer buffer, Map<String, Producer>producersMap, Map<String,Consumer> consumersMap){

        // create the workers GUIs

        producersGUIs = new HashMap<>();
        consumersGUIs = new HashMap<>();

        for(String id : producersMap.keySet()){
            WorkerGUI producerGUI = new WorkerGUI(producersMap.get(id),0,0);
            producersGUIs.put(id,producerGUI);
        }

        for(String id : consumersMap.keySet()){
            WorkerGUI consumerGUI = new WorkerGUI(consumersMap.get(id),0,0);
            producersGUIs.put(id,consumerGUI);
        }

        int producersNumber = producersMap.size();

        int consumersNumber = consumersMap.size();

        // create the JFrame

        this.frame = new JFrame();
        int frameWidth = 1000;
        int frameHeight = 1000;
        frame.setSize(frameWidth,frameHeight);

        // create totalPanel

        totalPanel = new JPanel(new GridLayout(1,3));
        totalPanel.setBounds(0,0,frameWidth,frameHeight);
        frame.add(totalPanel);

        int divideFrameWidth =  frameWidth/4;


        // Add the producers panels to the producersPanel and then to the totalPanel

        this.producersPanel = new JPanel(new GridLayout(producersNumber,1));
        producersPanel.setBounds(0,0,divideFrameWidth,frameHeight);


        for(WorkerGUI producerGUI : producersGUIs.values()){
            producerGUI.setWidth(divideFrameWidth);
            producerGUI.setHeight(frameHeight/producersNumber);
            producersPanel.add(producerGUI.generateWorkerPanel());
        }

        totalPanel.add(producersPanel);

        // Add the buffer panel to the total panel

        this.bufferGUI = new BufferGUI(buffer,50,divideFrameWidth*2);
        totalPanel.add(bufferGUI.generateBufferPanel());

        //  Add the consumers panels to the consumersPanel and then to the totalPanel

        this.consumersPanel = new JPanel(new GridLayout(consumersNumber,1));
        consumersPanel.setBounds(0,0,divideFrameWidth,frameHeight);


        for(WorkerGUI consumerGUI : consumersGUIs.values()){
            consumerGUI.setWidth(divideFrameWidth);
            consumerGUI.setHeight(frameHeight/consumersNumber);
            consumersPanel.add(consumerGUI.generateWorkerPanel());
        }

        totalPanel.add(consumersPanel);

        // add the panel to the frame

        frame.add(totalPanel);


    }


    public JFrame getFrame() {
        return frame;
    }







    public static void main(String[] args) {

        Buffer buffer = new Buffer(5);
        Producer producer = new Producer(false,1000);
        Consumer consumer = new Consumer(false,4000);
        Map <String,Producer> producerMap = new HashMap<>();
        producerMap.put("01",producer);
        Map <String,Consumer> consumerMap = new HashMap<>();
        consumerMap.put("01",consumer);

        TotalGUI totalGUI = new TotalGUI(buffer,producerMap,consumerMap);

        JFrame frame = totalGUI.getFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

        SwingUtilities.updateComponentTreeUI(frame);


    }



}
