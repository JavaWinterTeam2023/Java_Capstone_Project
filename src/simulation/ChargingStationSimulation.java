package simulation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import models.Car;
import models.ChargingStation;
import services.ChargingStationManagement;
import services.QueueService;

public class ChargingStationSimulation {
	  
	public static void main(String[] args) {
		   
		int numStations = 2;
        int numCars = 10;
	        
        // Create and configure charging stations and services dynamically
        ChargingStationManagement[] chargingServices = new ChargingStationManagement[numStations - 1];
        Thread[] chargingThreads = new Thread[numStations - 1];
        QueueService queueService= new QueueService();
	       
	    for (int j = 1; j <= numCars; j++) {
	    	int maxCapacity = (new Random().nextInt(2, 4))*25;
	    	int remainingCapacity = new Random().nextInt(20, maxCapacity - 20);
	    	int waitingTime = new Random().nextInt(10, 20);
	    	Car car = new Car(j, maxCapacity, waitingTime, remainingCapacity, false);
	        System.out.println("Car capacity: " + car.getCapacity()
	        		+ " waiting time: " + car.getMaxWaitingTime()
	        		+ " remaining capacity: " + car.getRemainingCapacity());
	    	queueService.addToQueue(car);
	    }
		   
		// Create and configure charging stations
		for (int i = 0; i < numStations - 1; i++) {
			System.out.println("Create charging station.");
			ChargingStation chargingStation = new ChargingStation("Station " + (i + 1), new Random().nextInt(50, 80) + 20, 4);
			chargingServices[i] = new ChargingStationManagement(chargingStation, queueService);
			chargingThreads[i] = new Thread(chargingServices[i]);
		}
		
		for (Thread chargingThread : chargingThreads) {
		    chargingThread.start();
		}
        // Simulate cars arriving at the charging stations
        // Start the charging service thread        
		// Wait for the charging service thread to finish
        for (Thread chargingThread : chargingThreads) {
        	 try {
        		 if (chargingThread != null) {
                 chargingThread.join();
        		 }
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
        }      
	}
}
