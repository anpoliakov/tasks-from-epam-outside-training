package by.epam.task1;

import by.epam.task1.models.AbstractToy;
import by.epam.task1.models.balls.Ball;
import by.epam.task1.models.blocks.Block;
import by.epam.task1.models.cars.BigCar;
import by.epam.task1.models.cars.MediumCar;
import by.epam.task1.models.cars.SmallCar;
import by.epam.task1.models.dolls.Doll;
import by.epam.task1.sorts.SortByCost;
import by.epam.task1.sorts.SortByName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Manage the program */
public class MainController {
    private int maxToys;
    private double maxMoneys;
    private double money;

    private List<AbstractToy> gameRoom;
    private FabricToys fabricToys;
    private List<AbstractToy> toys;

    private List <Class<? extends AbstractToy>> toyClasses =
            new ArrayList <Class <? extends AbstractToy>> (Arrays.asList(
                    Doll.class, SmallCar.class,
                    MediumCar.class, BigCar.class,
                    Ball.class, Block.class));

    /* Object of this class sets basic room information */
    public MainController(int maxToys, double maxMoneys) {
        PriceController price = new PriceController(toyClasses);
        this.maxToys = maxToys;
        this.maxMoneys = maxMoneys;
        this.gameRoom = new ArrayList<AbstractToy>();
        this.toys = new ArrayList<AbstractToy>();
        this.fabricToys = new FabricToys(toyClasses);
    }

    /* Method checks limits:  maxToys and maxMoneys*/
    private boolean addToyToGameRoom(AbstractToy toy) {
        boolean flag = false;
        if (gameRoom.size() < maxToys && money + toy.getCost() <= maxMoneys) {
            gameRoom.add(toy);
            flag = true;
        }
        return flag;
    }

    private AbstractToy getToy() {
        return fabricToys.getRandomToy();
    }

    /* Display information about toys */
    private String getOutput(List <AbstractToy> toys) {
        String result = "Information:\n";
        for (AbstractToy toy : toys) {
            result += toy.toString() + "\n";
        }
        result += getResult(toys);
        return result;
    }

    private String getResult(List <AbstractToy> toys) {
        return "\n>> USED moneys: " + getTotalCost(toys) +
                " of total: " + maxMoneys +
                "\n>> USED toys: " + toys.size() +
                " of total: " + maxToys + "\n ------------------\n";
    }

    /* The method returns the full cost of all toys */
    private double getTotalCost(List <AbstractToy> toys) {
        double result = 0;
        for (AbstractToy toy : toys) {
            result += toy.getCost();
        }
        return result;
    }


    public void fillGameRoom () {
        while(true) {
            AbstractToy toy = getToy();
            if (!addToyToGameRoom(toy)) {
                break;
            }
            money += toy.getCost();
        }
    }

    public String getOutput() {
        return getOutput(gameRoom);
    }

    public String getResult() {
        return getResult(gameRoom);
    }

    public void sortByCost() {
        gameRoom.sort(new SortByCost());
    }

    public void sortByName() {
        gameRoom.sort(new SortByName());
    }

    /* This method sets confines sort by cost */
    public String findByCost(double startCost, double endCost) {
        toys.clear();
        for (AbstractToy toy : gameRoom) {
            if (toy.getCost() >= startCost && toy.getCost() <= endCost) {
                toys.add(toy);
            }
        }
        return getOutput(toys);
    }
}
