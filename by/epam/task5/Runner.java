package by.epam.task5;

import by.epam.task5.threads.ThreadMethodOne;
import by.epam.task5.threads.ThreadMethodTwo;
import by.epam.task5.threads.ThreadShow;

/**
 * This class is the starting point of the program.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * My OPTION #14
 * First method - > MethodsForWork.firstMethod() checking on 2
 * Second method - > MethodsForWork.secondMethod() checking on 5
 *
 * */
public class Runner {
    private static final int SIZE_QUEUE = 4;

    public static void main(String[] args) {
        LoaderFile loaderFile = new LoaderFile("src/by/epam/task5/sources/file.txt");
        WorkClass workClass = new WorkClass(loaderFile.getListValues(), SIZE_QUEUE);
    }
}
