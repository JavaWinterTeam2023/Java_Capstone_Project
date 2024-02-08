package simulation;

import java.util.ArrayList;
import java.util.Random;

import energy.*;
import models.Car;
import models.ChargingStation;
import models.Weather;
import services.ChargingStationManagement;
import services.QueueService;
import services.EnergyManagement;

public class ChargingStationSimulation {
	  
	public static void main(String[] args) {
		int numStations = 2;
        int numCars = 10;
        
        // Weather simulation
        Weather weather = new Weather();
        
        // Add Energy source 
        ArrayList<EnergySource> eSource = new ArrayList<>(); 
        Solar solarEnergy = new Solar(100, weather);
      	Grid gridPower = new Grid(200, weather);
      	Storage storageEnergy = new Storage(50, weather);
      	eSource.add(solarEnergy);
      	eSource.add(gridPower);
      	eSource.add(storageEnergy);
      	EnergyManagement EMS = new EnergyManagement(eSource, weather);
      	
      	for (int i = 0; i < eSource.size(); i++) {
			Thread updateThread = new Thread(eSource.get(i));
			updateThread.start();
		}	
		Thread thread = new Thread(EMS);
		thread.start();
	        
        // Create and configure charging stations and services dynamically
        ChargingStationManagement[] chargingServices = new ChargingStationManagement[numStations];
        Thread[] chargingThreads = new Thread[numStations];
        QueueService queueService= new QueueService();
	       
	    for (int j = 1; j <= numCars; j++) {
	    	
	    	int maxCapacity = (new Random().nextInt(2, 4))*25;
	    	int remainingCapacity = new Random().nextInt(maxCapacity - 30, maxCapacity - 10);
	    	int waitingTime = new Random().nextInt(10, 20);
	    	Car car = new Car(j, maxCapacity, waitingTime, remainingCapacity, false);
	    	
	        System.out.println("[Car " + car.getId() + "][Arriving]"); 
	        System.out.println("\tCapacity: " + car.getCapacity());
	        System.out.println("\tMax waiting time: " + car.getMaxWaitingTime());
	        System.out.println("\tRemaining capacity: " + car.getRemainingCapacity());
	        
	    	queueService.addToQueue(car);
	    }
		   
		// Create and configure charging stations
		for (int i = 0; i < numStations; i++) {
			ChargingStation chargingStation = new ChargingStation("Station " + (i + 1), new Random().nextInt(4, 5)*60, 4, EMS);
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
