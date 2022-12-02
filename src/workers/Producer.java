package workers;

import buffer.Buffer;

public class Producer extends Worker {


    public Producer(boolean randomWaiting, int waitingStepTime) {
        super(randomWaiting, waitingStepTime);
    }

    @Override
    void actionOnBuffer(Buffer buffer) {
        System.out.println("Generate data");
        buffer.addInt(generateData());
        System.out.println("buffer.buffer.Buffer: " + buffer);
       /* try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/
    }

    private int generateData () {
        return (int)Math.floor(Math.random()*1000);
    }
}
