package com.ttog.island;

import com.ttog.island.inhabitants.Animal;
import com.ttog.island.inhabitants.AnimalType;

import java.util.*;

import static java.lang.Thread.sleep;

public class Statistics implements Runnable {
    private static int total = Integer.MAX_VALUE;
    private static final Queue<StringBuffer> queue = new LinkedList<>();
    private static int stepNumber = 0;

    public static int getTotal() {
        return total;
    }

    @Override
    public void run() {
        /*
        * Правильно же тут использовать не if, а while?
        *
        * */

            if (!queue.isEmpty()) {
                System.out.println(queue.remove());
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                }
            }
    }

    private static int getAnimalsCount(List<Animal> animals) {
        int count = 0;
        for (Animal animal : animals) {
            if (animal.getAnimalType() != AnimalType.PLANT) {
                count++;
            }
        }
        return count;
    }

    public static Map<AnimalType, Integer> getAnimalTypeCountMap(List<Animal> animals) {
        Map<AnimalType, Integer> animalTypeCountMap = new HashMap<>();
        for (AnimalType animalType : AnimalType.values()) {
            animalTypeCountMap.put(animalType, 0);
        }
        for (Animal animal : animals) {
            AnimalType key = animal.getAnimalType();
            animalTypeCountMap.put(key, animalTypeCountMap.get(key) + 1);
        }
        return animalTypeCountMap;
    }

    private static StringBuffer getHeadOfStat(Island island, Map<AnimalType, Integer> animalTypeCountMap) {
        StringBuffer step = new StringBuffer();
        step.append("\n\n");
        step.append(String.format("[Island state at step %d]\n", stepNumber));
        step.append(String.format("Total number of creatures %d, died last step %d, born last step %d.\n",
                getAnimalsCount(island.getGlobalInhList()),
                getAnimalsCount(island.getDieInhList()),
                getAnimalsCount(island.getBornInhList())));
        step.append(String.format("Total number of green %d.\n", animalTypeCountMap.get(AnimalType.PLANT)));
        total = getAnimalsCount(island.getBornInhList());
        return step;
    }

    private static StringBuffer getAnimalStringList(Map<AnimalType, Integer> animalTypeCountMap) {
        StringBuffer step = new StringBuffer();
        Map<AnimalType, Integer> newMap = new HashMap<>(animalTypeCountMap);
        newMap.remove(AnimalType.PLANT);
        step.append("List of creatures: \n");
        for (Map.Entry<AnimalType, Integer> entry : newMap.entrySet()) {
            step.append(String.format("| %s - %d ", entry.getKey(), entry.getValue()));
        }
        return step;
    }

    public static void addNextStep(Island island) {
        StringBuffer step = new StringBuffer();

        List<Animal> animals = island.getGlobalInhList();
        Map<AnimalType, Integer> animalTypeCountMap = getAnimalTypeCountMap(animals);

        step.append(getHeadOfStat(island, animalTypeCountMap));
        step.append(getAnimalStringList(animalTypeCountMap));

        queue.add(step);
        stepNumber++;
    }
}
