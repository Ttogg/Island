package com.ttog.island;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        IslandInitializer islandInitializer = IslandInitializer.getInstance();
        islandInitializer.initAll();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Simulation());

        executorService.shutdown();
        if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }
}
