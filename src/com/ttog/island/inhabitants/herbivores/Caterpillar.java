package com.ttog.island.inhabitants.herbivores;

import com.ttog.island.Island;
import com.ttog.island.inhabitants.Animal;
import com.ttog.island.inhabitants.AnimalFactory;
import com.ttog.island.inhabitants.AnimalType;
import com.ttog.island.inhabitants.Herbivore;


public class Caterpillar extends Animal implements Herbivore {
    private static double maxWeight;
    private static int maxNumberOnCell;
    private static final AnimalType animalType = AnimalType.CATERPILLAR;


    public static void initInhType(double newWeight, int newMaxNumberOnCell, int newSpeed, double newMaxAppetite) {
        maxWeight = newWeight;
        maxNumberOnCell = newMaxNumberOnCell;
    }

    public Caterpillar(AnimalType type, int y, int x) {
        super(type, y, x);
    }

    @Override
    public double getMaxWeight() {
        return maxWeight;
    }

    @Override
    public int getMaxNumberOnCell() {
        return maxNumberOnCell;
    }

    @Override
    public AnimalType getAnimalType() {
        return animalType;
    }

    @Override
    public void multiply() {
        if (!isAlive()) {
            return;
        }
        if (!canMultiply()) {
            return;
        }

        Island island = Island.getInstance();
        if (island.checkNumberInhOfType(getAnimalType(), getY(), getX()) < getMaxNumberOnCell()) {
            island.addNewInh(AnimalFactory.getInstance().newAnimal(getAnimalType(), getY(), getX()));
            setCanMultiply(false);
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void eat() {

    }
}
