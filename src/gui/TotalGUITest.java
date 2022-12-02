package gui;

import buffer.Buffer;
import semaphore.Semaphore;
import workers.Consumer;
import workers.Producer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TotalGUITest {

    JFrame frame;

    JPanel totalPanel;

    JPanel producersPanel;

    JPanel consumersPanel;

    JPanel bufferPanel;

    BufferGUI bufferGUI;

    Map<String,WorkerGUI> producersGUIs;

    Map<String,WorkerGUI> consumersGUIs;

    JPanel semaphoresPanel;


    public TotalGUITest(Buffer buffer, Map<String, Producer>producersMap, Map<String,Consumer> consumersMap){

        // create the workers GUIs

        producersGUIs = new HashMap<>();
        consumersGUIs = new HashMap<>();

        for(String id : producersMap.keySet()){
            WorkerGUI producerGUI = new WorkerGUI(producersMap.get(id),0,0);
            producersGUIs.put(id,producerGUI);
        }

        for(String id : consumersMap.keySet()){
            WorkerGUI consumerGUI = new WorkerGUI(consumersMap.get(id),0,0);
            consumersGUIs.put(id,consumerGUI);
        }

        int producersNumber = producersMap.size();

        int consumersNumber = consumersMap.size();

        // create the JFrame

        this.frame = new JFrame();
        int frameWidth = 800;
        int frameHeight = 500;
        frame.setSize(frameWidth,frameHeight);

        // create totalPanel

        totalPanel = new JPanel(new GridLayout(1,3));
        totalPanel.setBounds(0,0,frameWidth,frameHeight);
        frame.add(totalPanel);

        int divideFrameWidth =  frameWidth/4;

        this.bufferGUI = new BufferGUI(buffer,50,divideFrameWidth*2);


        frame.add(totalPanel);


    }


    public JFrame getFrame() {
        return frame;
    }

    public BufferGUI getBufferGUI() {
        return bufferGUI;
    }

    public WorkerGUI getWorkerGUI (TypeWorkerEnum typeWorker, String id) {
        switch (typeWorker){
            case PRODUCER :
                return producersGUIs.get(id);
            case CONSUMER:
                return consumersGUIs.get(id);
        }
        return null;
    }


    public void addProducersPanels () {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int divideFrameWidth =  frameWidth/4;
        int producersNumber = producersGUIs.size();

        this.producersPanel = new JPanel(new GridLayout(producersNumber,1));
        producersPanel.setBounds(0,0,divideFrameWidth,frameHeight);


        for(WorkerGUI producerGUI : producersGUIs.values()){
            producerGUI.setWidth(divideFrameWidth);
            producerGUI.setHeight(frameHeight/producersNumber);
            producersPanel.add(producerGUI.generateWorkerPanel());
        }

        totalPanel.add(producersPanel);
    }

    public void addConsumersPanels () {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int divideFrameWidth =  frameWidth/4;
        int consumersNumber = consumersGUIs.size();


        this.consumersPanel = new JPanel(new GridLayout(consumersNumber,1));
        consumersPanel.setBounds(0,0,divideFrameWidth,frameHeight);


        for(WorkerGUI consumerGUI : consumersGUIs.values()){
            consumerGUI.setWidth(divideFrameWidth);
            consumerGUI.setHeight(frameHeight/consumersNumber);
            consumersPanel.add(consumerGUI.generateWorkerPanel());
        }

        totalPanel.add(consumersPanel);
    }

    public void addBufferPanel () {  //SISTEMARE
        bufferPanel = new JPanel(new GridLayout(5,1));
        int frameWidth = frame.getWidth();
        int divideFrameWidth =  frameWidth/4;
        bufferPanel.setBounds(0,0,divideFrameWidth*2,50);
        bufferPanel.add(new JPanel());
        bufferPanel.add(new JPanel());
        bufferPanel.add(bufferGUI.generateBufferPanel());
        totalPanel.add(bufferPanel);
       // totalPanel.add(bufferGUI.generateBufferPanel());
    }



    public static void main(String[] args) {
        int maxInts = 5;
        Buffer buffer = new Buffer(maxInts);
        Producer producer = new Producer(false,1000);
        Consumer consumer = new Consumer(false,4000);
        Map <String,Producer> producerMap = new HashMap<>();
        producerMap.put("01",producer);
        Map <String,Consumer> consumerMap = new HashMap<>();
        consumerMap.put("01",consumer);

        TotalGUITest totalGUI = new TotalGUITest(buffer,producerMap,consumerMap);
        WorkerGUI producerGUI = totalGUI.getWorkerGUI(TypeWorkerEnum.PRODUCER,"01");
        WorkerGUI consumerGUI = totalGUI.getWorkerGUI(TypeWorkerEnum.CONSUMER,"01");
        BufferGUI bufferGUI = totalGUI.getBufferGUI();

        JFrame frame = totalGUI.getFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

        totalGUI.addProducersPanels();
        totalGUI.addBufferPanel();
        totalGUI.addConsumersPanels();

        SwingUtilities.updateComponentTreeUI(frame);

        // -----------------------------------------------------------------------------------------

        Semaphore empty = new Semaphore("empty",maxInts, maxInts);
        Semaphore full = new Semaphore("full",maxInts, 0);
        Semaphore mutex = new Semaphore("mutex",1, 1);

        Thread producerThread = new Thread(() -> {
            while (true) {
                producerGUI.updateStatus("ready to act");
                SwingUtilities.updateComponentTreeUI(frame);
                producer.readyToAct();
                producerGUI.updateStatus("waiting empty");
                SwingUtilities.updateComponentTreeUI(frame);
                producer.wait(empty);
                producer.wait(mutex);
                producerGUI.updateStatus("acting on buffer");
                SwingUtilities.updateComponentTreeUI(frame);
                producer.actOnBuffer(buffer);
                bufferGUI.addLastDataOfBuffer();
                SwingUtilities.updateComponentTreeUI(frame);
                producer.signal(mutex);
                producer.signal(full);
            }
        });


        Thread consumerThread = new Thread(()->{
            while(true){
                consumerGUI.updateStatus("ready to act");
                SwingUtilities.updateComponentTreeUI(frame);
                consumer.readyToAct();
                consumerGUI.updateStatus("waiting full");
                SwingUtilities.updateComponentTreeUI(frame);
                consumer.wait(full);
                consumer.wait(mutex);
                consumerGUI.updateStatus("acting on buffer");
                SwingUtilities.updateComponentTreeUI(frame);
                consumer.actOnBuffer(buffer);
                bufferGUI.removeDataFromLastNotEmptyBox();
                SwingUtilities.updateComponentTreeUI(frame);
                consumer.signal(mutex);
                consumer.signal(empty);
            }
        });

        producerThread.start();
        consumerThread.start();


    }



}
