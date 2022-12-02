package workers;

import buffer.Buffer;
import semaphore.Semaphore;

public abstract class Worker {

    private String Name;

    private boolean randomWaiting;

    private int waitingStepTime;

    public Worker(boolean randomWaiting, int waitingStepTime) {
        this.randomWaiting = randomWaiting;
        this.waitingStepTime = waitingStepTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isRandomWaiting() {
        return randomWaiting;
    }

    public void setRandomWaiting(boolean randomWaiting) {
        this.randomWaiting = randomWaiting;
    }

    public int getWaitingStepTime() {
        return waitingStepTime;
    }

    public void setWaitingStepTime(int waitingStepTime) {
        this.waitingStepTime = waitingStepTime;
    }

    public void readyToAct () {
        System.out.println(this.getClass().getName().substring(8).toUpperCase() + " is ready to act");
        if(randomWaiting) {
            try {
                Thread.sleep((int)Math.floor(Math.random())*10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                Thread.sleep(waitingStepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void wait (Semaphore semaphore)  {
        System.out.println(this.getClass().getName().substring(8).toUpperCase() + " wait the semaphore " + semaphore.getName());
        while(semaphore.getCurrentInt()<=0) {
            try {
                Thread.sleep(1000);
                System.out.println(".....waiting semaphore....");
               // System.out.println("info semaphore: " + semaphore);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            semaphore.decreaseCurrentInt();
           // System.out.println(semaphore);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void signal (Semaphore semaphore){
        System.out.println(this.getClass().getName().substring(8).toUpperCase() + " signal the semaphore " + semaphore.getName());
        try {
            semaphore.increaseCurrentInt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void actOnBuffer (Buffer buffer) {
        System.out.println(this.getClass().getName().substring(8).toUpperCase() + " acts on buffer " + buffer);
        try {
            actionOnBuffer(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract void actionOnBuffer (Buffer buffer)  ;

}
