package by.epam.task5;

import by.epam.task5.threads.ThreadMethodOne;
import by.epam.task5.threads.ThreadMethodTwo;
import by.epam.task5.threads.ThreadShow;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WorkClass {
    ThreadMethodOne thread1;
    ThreadMethodTwo thread2;
    ThreadShow thread3;

    private static Queue<Integer> queue = new LinkedList<>(); // Unsynchronized Queue
    private final int BOTTOM_QUEUE = 0; //low border
    private int sizeQueue; // Upper bound
    private List<Integer> listValues;

    public WorkClass(List <Integer> listValues, int sizeQueue) {
        this.listValues = listValues;
        this.sizeQueue = sizeQueue;

        if(listValues != null && sizeQueue > 0){
            startThreads();
        }
    }

    private void startThreads(){
        thread1 = new ThreadMethodOne(listValues);
        thread2 = new ThreadMethodTwo(listValues);
        thread3 = new ThreadShow(queue,sizeQueue);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addValue(int value){
        queue.offer(value);
    }
}
