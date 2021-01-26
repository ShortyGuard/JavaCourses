package lesson5;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private final CyclicBarrier startBarrier;
    private final CountDownLatch finishCountDownLatch;
    private final Lock lock;

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier startBarrier, CountDownLatch finishCountDownLatch, Lock lock) {
        this.race = race;
        this.speed = speed;
        this.startBarrier = startBarrier;
        this.finishCountDownLatch = finishCountDownLatch;
        this.lock = lock;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            startBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        lock.lock();
        if (finishCountDownLatch.getCount() == CARS_COUNT) {
            System.out.println(this.name + " WIN!!!");
        }
        finishCountDownLatch.countDown();
        lock.unlock();
    }
}

