package by.epam.task1;

/**
 * @author anpoliakov
 * @version 1.0.0
 */
public class Runner {
    public static void main(String[] args) {
        MainController maController = new MainController(6,122.2);
        maController.fillGameRoom();
        System.out.println(maController.getOutput());

        System.out.println("Sort by cost... ");
        maController.sortByCost();
        System.out.println(maController.getOutput());

        System.out.println("Search by cost...");
        System.out.println(maController.findByCost(6, 12));
    }
}
