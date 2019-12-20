package by.epam.task5.threads;

import by.epam.task5.WorkClass;

import java.util.List;

public class ThreadMethodTwo extends Thread{
    private List<Integer> listValues;

    public ThreadMethodTwo(List<Integer> listValues) {
        this.listValues = listValues;
    }

    @Override
    public void run() {
        int sizeList = listValues.size();
        int index = 0;

        while (isInterrupted()) {
            if(index < sizeList){
                int value = listValues.get(index);

                if(value % 5 == 0){
                    WorkClass.addValue(value / 5);
                }
                index ++;

            } else {
                interrupt();
            }
        }
    }
}
