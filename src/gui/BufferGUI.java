package gui;

import buffer.Buffer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BufferGUI {

    private Buffer buffer ;

    int height;

    int width;

    JPanel bufferPanel;

    List <JPanel> boxes;

    public BufferGUI (Buffer buffer,int height,int width) {
        this.buffer = buffer;
        this.height = height;
        this.width = width;
        this.boxes = new ArrayList<>();
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public JPanel generateBufferPanel () {
        int numberBoxes = buffer.getMaxCapacity();
        bufferPanel = new JPanel(new GridLayout(0,numberBoxes, 4, 4));
        bufferPanel.setBounds(0,0,width,height);
        bufferPanel.setBorder( BorderFactory.createLineBorder(Color.black));

        for(int i=0; i<numberBoxes; i++){
            JPanel box = new JPanel(new GridLayout(0, 6, 4, 4));
            int boxWidth = (int) width/numberBoxes;
            box.setBounds(0,0,boxWidth,height);
            box.setBorder( BorderFactory.createLineBorder(Color.black));
            boxes.add(box);
            bufferPanel.add(box);
        }

        return bufferPanel;
    }

    public void addLastDataOfBuffer ( ) {
        addDataToFirstEmptyBox(buffer.get(buffer.size()-1));
    }
    private void addDataToFirstEmptyBox (Integer data) {
        JLabel label = new JLabel(String.valueOf(data));
        //setFontSize(label);
        //label.setFont(new Font("myFont",label.getFont().getStyle(),100));
        boxes.get(buffer.size()-1).add(label);
    }

    public void removeDataFromLastNotEmptyBox () {
        boxes.get(buffer.size()-1).removeAll();
    }

    private void setFontSize (JLabel label) {
        Font labelFont = label.getFont();
        String labelText = label.getText();

        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = label.getWidth();

        // Find out how much the font can grow in width.
        double widthRatio = (double)componentWidth / (double)stringWidth;

        int newFontSize = (int)(labelFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();

        // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

        // Set the label's font size to the newly determined size.
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
    }


    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
        Buffer buffer = new Buffer(5);
        buffer.add(3);
        BufferGUI bufferGUI = new BufferGUI(buffer,100,500);
        JPanel bufferPanel = bufferGUI.generateBufferPanel();
        frame.add(bufferPanel);
        bufferGUI.addDataToFirstEmptyBox(3);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1000,1000);
        frame.add(bufferPanel);
        frame.setVisible(true);


        Thread.sleep(5000);

        bufferGUI.removeDataFromLastNotEmptyBox();
        SwingUtilities.updateComponentTreeUI(frame);

        /*JPanel bufferPanel = new JPanel(new GridLayout(0, 6, 4, 4));
        bufferPanel.setBounds(0,0,200,100 );
        bufferPanel.setBorder( BorderFactory.createLineBorder(Color.black));

        JPanel box1 = new JPanel(new GridLayout(0,1,1,4));
        box1.setBounds(0,0,50,100 );
        box1.setBorder( BorderFactory.createLineBorder(Color.black));
        bufferPanel.add(box1);

        JPanel box2 = new JPanel(new GridLayout(0,1,1,1));
        box2.setBounds(0,0,50,100 );
        box2.setBorder( BorderFactory.createLineBorder(Color.black));
        bufferPanel.add(box2);*/


    }

}
