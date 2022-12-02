package gui;

import buffer.Buffer;
import workers.Producer;
import workers.Worker;

import javax.swing.*;
import java.awt.*;

public class WorkerGUI {

    Worker worker ;

    JPanel workerPanel;

    JPanel boxTitle;

    JPanel boxInfo;

    int width;

    int height;

    public WorkerGUI(Worker worker, int width, int height) {
        this.worker = worker;
        this.width = width;
        this.height = height;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public JPanel getWorkerPanel() {
        return workerPanel;
    }

    public void setWorkerPanel(JPanel workerPanel) {
        this.workerPanel = workerPanel;
    }

    public JPanel getBoxTitle() {
        return boxTitle;
    }

    public void setBoxTitle(JPanel boxTitle) {
        this.boxTitle = boxTitle;
    }

    public JPanel getBoxInfo() {
        return boxInfo;
    }

    public void setBoxInfo(JPanel boxInfo) {
        this.boxInfo = boxInfo;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public JPanel generateWorkerPanel () {

        workerPanel = new JPanel(new GridLayout(2,0, 4, 4));
        workerPanel.setBounds(0,0,width,height);
        workerPanel.setBorder( BorderFactory.createLineBorder(Color.black));


        boxTitle = new JPanel(new GridLayout(1, 0, 4, 4));
        int boxTitleHeight = (int) height/2;
        boxTitle.setBounds(0,0,width,boxTitleHeight);
        boxTitle.setBorder( BorderFactory.createLineBorder(Color.black));
        JLabel title = new JLabel(worker.getClass().getName().substring(8).toUpperCase());
        boxTitle.add(title);
        workerPanel.add(boxTitle);

        boxInfo = new JPanel(new GridLayout(1, 0, 4, 4));
        int boxInfoHeight = (int) height/2;
        boxInfo.setBounds(0,0,width,boxInfoHeight);
        boxInfo.setBorder( BorderFactory.createLineBorder(Color.black));
        workerPanel.add(boxInfo);

        return workerPanel;
    }

    public void updateStatus (String status) {
        boxInfo.removeAll();
        boxInfo.add(new JLabel(status));
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1000,1000);
        frame.setVisible(true);

        Producer producer = new Producer(false,2000);

        WorkerGUI workerGUI = new WorkerGUI(producer,200,400);

        JPanel workerPanel = workerGUI.generateWorkerPanel();
        frame.add(workerPanel);
        SwingUtilities.updateComponentTreeUI(frame);

        Thread.sleep(2000);

        workerGUI.updateStatus("new status");
        SwingUtilities.updateComponentTreeUI(frame);
    }

}
