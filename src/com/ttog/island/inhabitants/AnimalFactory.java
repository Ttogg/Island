package com.ttog.island.inhabitants;

import com.ttog.island.inhabitants.herbivores.*;
import com.ttog.island.inhabitants.plants.Plant;
import com.ttog.island.inhabitants.predators.*;

public class AnimalFactory {
    private AnimalFactory() {
    }

    private static class SingletonHolder {
        public static final AnimalFactory HOLDER_INSTANCE = new AnimalFactory();
    }

    public static AnimalFactory getInstance() {
        return AnimalFactory.SingletonHolder.HOLDER_INSTANCE;
    }

    public Animal newAnimal (AnimalType type, int y, int x) {
        return switch (type) {
            case BOAR -> new Boar(type, y, x);
            case BUFFALO -> new Buffalo(type, y, x);
            case DEER -> new Deer(type, y, x);
            case DUCK -> new Duck(type, y, x);
            case GOAT -> new Goat(type, y, x);
            case HORSE -> new Horse(type, y, x);
            case MOUSE -> new Mouse(type, y, x);
            case RABBIT -> new Rabbit(type, y, x);
            case SHEEP -> new Sheep(type, y, x);
            case BEAR -> new Bear(type, y, x);
            case EAGLE -> new Eagle(type, y, x);
            case FOX -> new Fox(type, y, x);
            case SNAKE -> new Snake(type, y, x);
            case WOLF -> new Wolf(type, y, x);
            case CATERPILLAR -> new Caterpillar(type, y, x);
            case PLANT -> new Plant(type, y, x);
        };
    }
}
