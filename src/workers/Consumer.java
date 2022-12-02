package workers;

import buffer.Buffer;

public class Consumer extends Worker {


    public Consumer(boolean randomWaiting, int waitingStepTime) {
        super(randomWaiting, waitingStepTime);
    }

    void actionOnBuffer(Buffer buffer)  {
        int readData = buffer.removeLast();
        System.out.println("The read data is " + readData);
        System.out.println("buffer.buffer.Buffer: " + buffer);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
