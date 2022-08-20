package com.ttog.island.inhabitants.herbivores;

import com.ttog.island.inhabitants.Animal;
import com.ttog.island.inhabitants.AnimalType;
import com.ttog.island.inhabitants.Herbivore;

import java.util.HashMap;
import java.util.Map;

public class Sheep extends Animal implements Herbivore {
    private static double maxWeight;
    private static int maxNumberOnCell;
    private static int speed;
    private static double maxAppetite;
    private static final AnimalType animalType = AnimalType.SHEEP;
    private static Map<AnimalType, Integer> edibleMap = new HashMap<>();


    public static void initInhType(double newWeight, int newMaxNumberOnCell, int newSpeed, double newMaxAppetite) {
        maxWeight = newWeight;
        maxNumberOnCell = newMaxNumberOnCell;
        speed = newSpeed;
        maxAppetite = newMaxAppetite;
    }

    public Sheep(AnimalType type, int y, int x) {
        super(type, y, x);
    }

    public static void initEdible(Map<AnimalType, Integer> newEdibleMap) {
        edibleMap.putAll(newEdibleMap);
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
    public int getSpeed() {
        return speed;
    }

    @Override
    public double getMaxAppetite() {
        return maxAppetite;
    }

    @Override
    public AnimalType getAnimalType() {
        return animalType;
    }

    @Override
    public Map<AnimalType, Integer> getEdibleMap() {
        return edibleMap;
    }
}
