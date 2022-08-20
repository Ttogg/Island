package com.ttog.island;

import com.ttog.island.inhabitants.Animal;

import java.util.List;

public class Simulation implements Runnable {
    @Override
    public void run() {
            Thread thread1 = new Thread(new SimulationStep());
            thread1.start();
            try {
                thread1.join();
            } catch (InterruptedException e) {
            }
            Thread thread2 = new Thread(new Statistics());
            thread2.start();
    }

    class SimulationStep implements Runnable {
        @Override
        public void run() {
            Island island = Island.getInstance();
            List<Animal> globalInhList = island.getGlobalInhList();
            for (Animal animal : globalInhList) {
                animal.move();
                animal.eat();
                animal.multiply();
            }
            island.nextStep();
        }
    }
}










