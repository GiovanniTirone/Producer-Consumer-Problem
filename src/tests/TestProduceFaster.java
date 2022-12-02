package tests;

import buffer.Buffer;
import semaphore.Semaphore;
import workers.Consumer;
import workers.Producer;

public class TestProduceFaster {

    public static void main(String[] args) {

        int maxInts = 5;
        Buffer buffer = new Buffer(maxInts);

        Semaphore empty = new Semaphore("empty",maxInts, maxInts);
        Semaphore full = new Semaphore("full",maxInts, 0);
        Semaphore mutex = new Semaphore("mutex",1, 1);
        Producer producer = new Producer(false,1000);
        Consumer consumer = new Consumer(false,3000);


        Thread producerThread = new Thread(() -> {
            while (true) {
                producer.readyToAct();
                producer.wait(empty);
                producer.wait(mutex);
                producer.actOnBuffer(buffer);
                producer.signal(mutex);
                producer.signal(full);
            }
        });


        Thread consumerThread = new Thread(()->{
            while(true){
                consumer.readyToAct();
                consumer.wait(full);
                consumer.wait(mutex);
                consumer.actOnBuffer(buffer);
                consumer.signal(mutex);
                consumer.signal(empty);
            }
        });

        producerThread.start();
        consumerThread.start();

    }
}