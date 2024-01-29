package simulation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import models.Car;
import models.ChargingStation;
import services.ChargingStationManagement;
import services.NweChargingStations;
import services.QueueService;

public class ChargingStationSimulation {
	   public static void main(String[] args) {
		   int numStations = 2; // Adjust as needed
           int numCars=5;
	        // Create and configure charging stations and services dynamically
           ChargingStationManagement[] chargingServices = new ChargingStationManagement[numStations];
	        Thread[] chargingThreads = new Thread[numStations];
	        QueueService queueService= new QueueService();
	        for (int j = 1; j <= numCars; j++) {
	            Car car = new Car(  j, new Random().nextInt(50) + 50, new Random().nextInt(10) + 20);
	            queueService.addToQueue(car);
	        }
		        
		       
		        
		     // Queue<Object> ObjectQueue = QService.getFromQueue();
		      //Queue<Car> carQueue= QService.convertToCarQueue(ObjectQueue);
		        // Create and configure charging stations
		        for (int i = 0; i < numStations; i++) {
		        	ChargingStation chargingStation  = new ChargingStation("Station " + (i + 1), new Random().nextInt(90) + 20, 5);
		        	chargingServices[i]= new ChargingStationManagement(chargingStation,queueService);
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
