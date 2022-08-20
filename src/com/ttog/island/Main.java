package com.ttog.island;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        IslandInitializer islandInitializer = IslandInitializer.getInstance();
        islandInitializer.initAll();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
        executorService.scheduleAtFixedRate(new Simulation(), 0, 3, TimeUnit.SECONDS);

        while (Statistics.getTotal() != 0) {
            Thread.sleep(1000);
        }

        executorService.shutdown();
        if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }
}
