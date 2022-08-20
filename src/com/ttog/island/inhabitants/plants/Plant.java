package com.ttog.island.inhabitants.plants;

import com.ttog.island.inhabitants.Animal;
import com.ttog.island.inhabitants.AnimalType;

public class Plant extends Animal {
    private static double maxWeight;
    private static int maxNumberOnCell;
    private static final AnimalType animalType = AnimalType.PLANT;

    public static void initInhType(double newWeight, int newMaxNumberOnCell, int newSpeed, double newMaxAppetite) {
        maxWeight = newWeight;
        maxNumberOnCell = newMaxNumberOnCell;
    }

    public Plant(AnimalType type, int y, int x) {
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
    }

    @Override
    public void move() {
    }

    @Override
    public void eat() {
    }
}
