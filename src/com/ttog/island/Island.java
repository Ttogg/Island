package com.ttog.island;

import com.ttog.island.inhabitants.Animal;
import com.ttog.island.inhabitants.AnimalFactory;
import com.ttog.island.inhabitants.AnimalType;

import java.util.ArrayList;
import java.util.List;

public class Island {
    private int maxY;
    private int maxX;
    int plantsGrowCount;
    private Cell[][] area;

    private List<Animal> globalInhList = new ArrayList<>();
    private List<Animal> bornInhList = new ArrayList<>();
    private List<Animal> dieInhList = new ArrayList<>();


    private Island() {
    }

    private static class SingletonHolder {
        public static final Island HOLDER_INSTANCE = new Island();
    }

    public static Island getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public void initIsland(int maxY, int maxX, int plantsGrowCount) {
        this.maxY = maxY;
        this.maxX = maxX;
        this.plantsGrowCount = plantsGrowCount;
        area = new Cell[maxY][maxX];
        for (int i = 0; i < this.maxY; i++) {
            for (int j = 0; j < this.maxX; j++) {
                area[i][j] = new Cell();
            }
        }
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxX() {
        return maxX;
    }

    public void dieInh(Animal animal) {
        dieInhList.add(animal);
    }

    public void addNewInh(Animal animal) {
        bornInhList.add(animal);
    }

    public void moveInh(Animal animal, int oldY, int oldX, int newY, int newX) {
        area[oldY][oldX].delInh(animal);
        area[newY][newX].addInh(animal);
    }

    public int checkNumberInhOfType(AnimalType animalType, int y, int x) {
        int count = 0;
        List<Animal> animals = getCellInhList(y, x);
        for (Animal thisAnimal : animals) {
            if (thisAnimal.getAnimalType() == animalType && thisAnimal.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public List<Animal> getCellInhList(int y, int x) {
        return area[y][x].getCellInhList();
    }

    public List<Animal> getGlobalInhList() {
        return globalInhList;
    }

    public List<Animal> getBornInhList() {
        return bornInhList;
    }

    public List<Animal> getDieInhList() {
        return dieInhList;
    }

    public void nextStep() {
        globalInhList.removeAll(dieInhList);

        for (Animal animal : dieInhList) {
            area[animal.getY()][animal.getX()].delInh(animal);
        }

        growPlants(plantsGrowCount);

        for (Animal animal : bornInhList) {
            if (checkNumberInhOfType(animal.getAnimalType(), animal.getY(), animal.getX()) < animal.getMaxNumberOnCell()) {
                area[animal.getY()][animal.getX()].addInh(animal);
                globalInhList.add(animal);
            }
        }

        for (Animal animal : globalInhList) {
            animal.setCanMultiply(true);
        }

        Statistics.addNextStep(Island.getInstance());

        bornInhList.clear();
        dieInhList.clear();
    }

    public void growPlants(int plantsGrowCount) {
        AnimalFactory animalFactory = AnimalFactory.getInstance();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                for (int i = 0; i < plantsGrowCount; i++) {
                    addNewInh(animalFactory.newAnimal(AnimalType.PLANT, y, x));
                }
            }
        }
    }

    private class Cell {
        private List<Animal> cellInhList = new ArrayList<>();
        // cellType


        public void addInh(Animal animal) {
            cellInhList.add(animal);
        }

        public void delInh(Animal animal) {
            cellInhList.remove(animal);
        }

        public List<Animal> getCellInhList() {
            return cellInhList;
        }
    }
}

