package com.ttog.island;


import com.ttog.island.inhabitants.Animal;
import com.ttog.island.inhabitants.AnimalFactory;
import com.ttog.island.inhabitants.AnimalType;
import com.ttog.island.inhabitants.herbivores.*;
import com.ttog.island.inhabitants.plants.Plant;
import com.ttog.island.inhabitants.predators.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.ttog.island.inhabitants.AnimalType.*;

public class IslandInitializer {
    private static final int MAX_FAIL_COUNT = 100;

    private IslandInitializer() {
    }

    private static class SingletonHolder {
        public static final IslandInitializer HOLDER_INSTANCE = new IslandInitializer();
    }

    public static IslandInitializer getInstance() {
        return IslandInitializer.SingletonHolder.HOLDER_INSTANCE;
    }

    public void initAll() {
        initIsland();
        initInhTypes();
        initEdible();
        createStartInh();
    }

    private void initIsland() {
        Island.getInstance().initIsland(2, 2, 40);
    }

    private void initInhTypes() {
        Boar.initInhType(400, 50, 2, 50);
        Buffalo.initInhType(700, 10, 3, 100);
        Deer.initInhType(300, 20, 4, 50);
        Duck.initInhType(1, 200 , 4, 0.15);
        Goat.initInhType(60, 140, 3, 10);
        Horse.initInhType(400, 20, 4, 60);
        Mouse.initInhType(0.05, 500, 1, 0.01);
        Rabbit.initInhType(2, 150, 2, 0.45);
        Sheep.initInhType(70, 140, 3, 15);
        Bear.initInhabitant(500, 5, 2, 80);
        Eagle.initInhType(6, 20, 3, 1);
        Fox.initInhType(8, 30, 2, 2);
        Snake.initInhType(15, 30, 1, 3);
        Wolf.initInhType(50, 30, 3, 8);
        Caterpillar.initInhType(0.01, 1000, 0, 0);
        Plant.initInhType(1, 200, 0, 0);
    }

    private void initEdible() {
        Map<AnimalType, Integer> edibleMap = new HashMap<>();
        edibleMap.put(SNAKE, 80);
        edibleMap.put(HORSE, 40);
        edibleMap.put(DEER, 80);
        edibleMap.put(RABBIT, 80);
        edibleMap.put(MOUSE, 90);
        edibleMap.put(GOAT, 70);
        edibleMap.put(SHEEP, 70);
        edibleMap.put(BOAR, 50);
        edibleMap.put(BUFFALO, 20);
        edibleMap.put(DUCK, 10);
        Bear.initEdible(edibleMap);
        edibleMap.clear();

        edibleMap.put(FOX, 10);
        edibleMap.put(RABBIT, 90);
        edibleMap.put(MOUSE, 90);
        edibleMap.put(DUCK, 80);
        Eagle.initEdible(edibleMap);
        edibleMap.clear();

        edibleMap.put(RABBIT, 70);
        edibleMap.put(MOUSE, 90);
        edibleMap.put(DUCK, 60);
        edibleMap.put(CATERPILLAR, 40);
        Fox.initEdible(edibleMap);
        edibleMap.clear();

        edibleMap.put(FOX, 15);
        edibleMap.put(RABBIT, 20);
        edibleMap.put(MOUSE, 40);
        edibleMap.put(DUCK, 10);
        Snake.initEdible(edibleMap);
        edibleMap.clear();

        edibleMap.put(HORSE, 10);
        edibleMap.put(DEER, 15);
        edibleMap.put(RABBIT, 60);
        edibleMap.put(MOUSE, 80);
        edibleMap.put(GOAT, 60);
        edibleMap.put(SHEEP, 70);
        edibleMap.put(BOAR, 15);
        edibleMap.put(BUFFALO, 10);
        edibleMap.put(DUCK, 40);
        Wolf.initEdible(edibleMap);
        edibleMap.clear();

        edibleMap.put(CATERPILLAR, 90);
        Mouse.initEdible(edibleMap);
        edibleMap.clear();

        edibleMap.put(MOUSE, 50);
        edibleMap.put(CATERPILLAR, 90);
        Boar.initEdible(edibleMap);

        edibleMap.put(CATERPILLAR, 90);
        Duck.initEdible(edibleMap);
        edibleMap.clear();
    }

    private Map<AnimalType, Integer> createStartNumberOfInh() {
        Map<AnimalType, Integer> startMapOfInhCount = new HashMap<>();
        startMapOfInhCount.put(BEAR, 50);
        startMapOfInhCount.put(EAGLE, 200);
        startMapOfInhCount.put(FOX, 300);
        startMapOfInhCount.put(SNAKE, 300);
        startMapOfInhCount.put(WOLF, 300);
        startMapOfInhCount.put(BOAR, 500);
        startMapOfInhCount.put(BUFFALO, 100);
        startMapOfInhCount.put(CATERPILLAR, 10000);
        startMapOfInhCount.put(DEER, 200);
        startMapOfInhCount.put(DUCK, 2000);
        startMapOfInhCount.put(GOAT, 1400);
        startMapOfInhCount.put(HORSE, 200);
        startMapOfInhCount.put(MOUSE, 5000);
        startMapOfInhCount.put(RABBIT, 1500);
        startMapOfInhCount.put(SHEEP, 1400);
        return startMapOfInhCount;
    }

    private void createStartInh() {
        AnimalFactory animalFactory = AnimalFactory.getInstance();
        Island island = Island.getInstance();
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();

        for (Map.Entry<AnimalType, Integer> entry : createStartNumberOfInh().entrySet()) {
            AnimalType animalType = entry.getKey();
            Animal animal = animalFactory.newAnimal(animalType, 0, 0);
            for (int i = 0; i < entry.getValue(); i++) {
                int failCount = 0;
                while (failCount < MAX_FAIL_COUNT) {
                    failCount++;
                    int y = localRandom.nextInt(island.getMaxY());
                    int x = localRandom.nextInt(island.getMaxX());
                    if (island.checkNumberInhOfType(animalType, y, x) < animal.getMaxNumberOnCell()) {
                        island.addNewInh(animalFactory.newAnimal(animalType, y, x));
                        break;
                    }
                }
            }
        }

        island.nextStep();
    }
}
