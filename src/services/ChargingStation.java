package services;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import models.Car;

public class ChargingStation {
    private int stationId;
    private Queue<Car> queue;

    public ChargingStation(int stationId) {
        this.stationId = stationId;
        this.queue = new LinkedList<>();
    }

    public void chargeCar(Car car) {
       
        int chargingTime = new Random().nextInt(26) + 5;
        try {
            Thread.sleep(chargingTime * 1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Car " + car.getId() + " charged at Station " + stationId + " in " + chargingTime + " seconds");
    }

    public synchronized void addToQueue(Car car, int currentTime) {
        car.setArrivalTime(currentTime);
        queue.add(car);
        int waitTime = currentTime - car.getArrivalTime();
        if (queue.size() > 1 && waitTime > 15 * 60) {
            System.out.println("Car " + car.getId() + " left the queue at Station " + stationId);
            queue.poll(); 
        } else {
            chargeCar(car);
            queue.poll();
        }
    }
}
