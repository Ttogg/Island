package com.ttog.island.inhabitants;

import com.ttog.island.Island;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal implements AnimalLife {
    private static double maxWeight;
    private static int maxNumberOnCell;
    private static int speed;
    private static double maxAppetite;
    private static Map<AnimalType, Integer> edibleMap = new HashMap<>();
    private static AnimalType animalType;
    private int y;
    private int x;
    private double appetite;
    private double currentWeight;
    private boolean isAlive = true;
    private boolean canMultiply = true;


    protected Animal(AnimalType animalType, int y, int x) {
        this.animalType = animalType;
        this.y = y;
        this.x = x;
        setAppetite(getMaxAppetite() * 0.7);
        setCurrentWeight(getMaxWeight());
    }

    public Map<AnimalType, Integer> getEdibleMap() {
        return edibleMap;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public int getMaxNumberOnCell() {
        return maxNumberOnCell;
    }

    public int getSpeed() {
        return speed;
    }

    public double getMaxAppetite() {
        return maxAppetite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getAppetite() {
        return appetite;
    }

    public void setAppetite(double appetite) {
        this.appetite = appetite;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void isDead() {
        isAlive = false;
    }

    public void setCanMultiply(boolean canMultiply) {
        this.canMultiply = canMultiply;
    }

    public boolean canMultiply() {
        return canMultiply;
    }

    @Override
    public void multiply() {
        if (!isAlive()) {
            return;
        }
        if (!canMultiply()) {
            return;
        }
        setAppetite(getAppetite() - getMaxAppetite() * 0.2);

        Island island = Island.getInstance();
        List<Animal> animalList = island.getCellInhList(getY(), getX());
        int animalTypeCount = 0;
        List<Animal> animalsToMultiply = new ArrayList<>();
        for (Animal animal : animalList) {
            if (animal.getAnimalType() == getAnimalType() && animal.isAlive()) {
                animalTypeCount++;
                if (this != animal && animal.canMultiply()) {
                    animalsToMultiply.add(animal);
                }
            }
        }

        if (animalTypeCount > 1 && animalTypeCount < getMaxNumberOnCell() && !animalsToMultiply.isEmpty()) {
            setCanMultiply(false);
            animalsToMultiply.get(0).setCanMultiply(false);
            island.addNewInh(AnimalFactory.getInstance().newAnimal(getAnimalType(), getY(),getX()));
        }
    }

    @Override
    public void move() {
        if (!isAlive()) {
            return;
        }
        setAppetite(getAppetite() - getMaxAppetite() * 0.4);

        Island island = Island.getInstance();
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        int newY = getY();
        int newX = getX();
        int dY, dX;
        int direction;

        for (int i = 0; i < getSpeed(); i++) {
            dX = 0;
            dY = 0;
            direction = localRandom.nextInt(4);
            switch (direction) {
                case 0:
                    dY = 1;
                    break;
                case 1:
                    dY = -1;
                    break;
                case 2:
                    dX = 1;
                    break;
                case 3:
                    dX = -1;
                    break;
            }
            if (newX + dX >= 0 && newY + dY >= 0 && newX + dX < island.getMaxX() && newY + dY < island.getMaxY()) {
                if (island.checkNumberInhOfType(getAnimalType(), newY + dY, newX + dX) < getMaxNumberOnCell()) {
                    newY += dY;
                    newX += dX;
                }
            }
        }

        if (newY != getY() && newX != getX()) {
            island.moveInh(this, getY(), getX(), newY, newX);
            setY(newY);
            setX(newX);
        }
    }

    @Override
    public void eat() {
        if (!isAlive()) {
            return;
        }

        Island island = Island.getInstance();
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        List<Animal> cellAnimalsList = island.getCellInhList(getY(), getX());

        for (Animal animalToEat : cellAnimalsList) {
            if (getEdibleMap().containsKey(animalToEat.getAnimalType()) &&
                    animalToEat.getCurrentWeight() / animalToEat.getMaxWeight() > 0.05) {
                if (getAppetite() < getMaxAppetite() * 0.95) {
                    boolean toEat = false;
                    if (!animalToEat.isAlive()) {
                        toEat = true;
                    } else {
                        double orNotToEat = (double) getEdibleMap().get(animalToEat.getAnimalType()) / localRandom.nextInt(100);
                        toEat = orNotToEat > 1;
                    }
                    if (toEat) {
                        double newAppetite;
                        double toMaxAppetite = getMaxAppetite() - getAppetite();
                        if (animalToEat.getCurrentWeight() < toMaxAppetite) {
                            newAppetite = getAppetite() + animalToEat.getCurrentWeight();
                            animalToEat.setCurrentWeight(0);
                        } else {
                            newAppetite = getMaxAppetite();
                            animalToEat.setCurrentWeight(animalToEat.getCurrentWeight() - toMaxAppetite);
                        }
                        setAppetite(newAppetite);
                        animalToEat.isDead();
                        island.dieInh(animalToEat);
                    }
                }

                if (getAppetite() > getMaxAppetite() * 0.95) {
                    break;
                }
            }
        }
        if (getAppetite() < 0.05 * getMaxAppetite()) {
            isDead();
            island.dieInh(this);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + getY() + " " + getX();
    }
}
