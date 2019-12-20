package by.epam.task5.threads;

import by.epam.task5.WorkClass;

import java.util.List;

public class ThreadMethodOne extends Thread {
    private List<Integer> listValues;

    public ThreadMethodOne(List <Integer> listValues) {
        this.listValues = listValues;
    }

    @Override
    public void run() {
        int sizeList = listValues.size();
        int index = 0;

        while (isInterrupted()) {
            if(index < sizeList){
                int value = listValues.get(index);

                if(value % 2 == 0){
                    WorkClass.addValue(value / 2);
                }
                index ++;

            } else {
                interrupt();
            }
        }
    }
}
