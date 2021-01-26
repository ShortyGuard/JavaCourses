package lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Semaphore tunnelSemaphore = new Semaphore((CARS_COUNT / 2));

        Race race = new Race(new Road(60), new Tunnel(tunnelSemaphore), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        CyclicBarrier startBarrier = new CyclicBarrier(CARS_COUNT + 1);
        CountDownLatch finishCountDownLatch = new CountDownLatch(CARS_COUNT);
        Lock lock   = new ReentrantLock();

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),
                startBarrier,
                finishCountDownLatch,
                lock);
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
        new Thread(() -> {
            try {
                startBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        }).start();

        new Thread(() -> {
            try {
                finishCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        }).start();

    }
}
