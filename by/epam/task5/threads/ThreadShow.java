package by.epam.task5.threads;

import by.epam.task5.WorkClass;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadShow extends Thread {
    private Queue<Integer> queue = new LinkedList<>();
    private int sizeQueue;

    public ThreadShow(Queue<Integer> queue, int sizeQueue) {
        this.queue = queue;
        this.sizeQueue = sizeQueue;
    }

    @Override
    public void run() {
        try {
            sleep(1000);
            while (isInterrupted()){
                if(queue.size() > 0){
                    System.out.println(queue.poll());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
