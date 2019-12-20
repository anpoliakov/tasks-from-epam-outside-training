package by.epam.task5;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoaderFile {
    private String path;
    private List<Integer> listValues;
    private Scanner sc;

    public LoaderFile(String path) {
        this.path = path;
        listValues = new ArrayList<>();
        getValuesFromFile();
    }

    private void getValuesFromFile(){
        try {
            sc = new Scanner(new FileReader(path));
            sc.useDelimiter(",");

            if(sc.hasNext()){
                listValues.add(Integer.parseInt(sc.next()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List <Integer> getListValues(){
        return listValues;
    }
}
