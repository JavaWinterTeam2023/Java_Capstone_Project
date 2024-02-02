package services;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

import models.Car;
import models.ChargingStation;

public class ChargingStationManagement implements Runnable {
	
	private ChargingStation ChargingStations;
    private Queue<Object> carQueue;
    private QueueService queueService;
    
	public ChargingStationManagement(ChargingStation chargingStations, QueueService queueService) {		
		this.ChargingStations = chargingStations;
		this.queueService = queueService;        
	}
	
	public void addCarToQueue(Car car) {
		carQueue.add(car);
	}
	
	@Override
	public void run() {
		Queue<Car> carsToRemove = new LinkedList<>();
		boolean Isdone = false;
		
		System.out.println("Enter thread " + this.ChargingStations.getName());
		
//		synchronized (queueService) {
		if (!queueService.isQueueEmpty()) {
			System.out.println("Enter queue service");
			Queue<Object> carQueue = queueService.getFromQueue();
			for (Object element : carQueue) {
				if (element instanceof Car) {
					LocalDateTime currentDateTime = LocalDateTime.now();
					int carTurnTime = currentDateTime.getMinute();
					Isdone = ChargingStations.assignedToLocation((Car) element);
            	  
					if(Isdone == true) {
						carsToRemove.add((Car) element);
					}
				}
			}
			carQueue.removeAll(carsToRemove);
			for (Object element : carQueue) {
				queueService.addToQueue((Car) element);
			}
		}	
//		}
	}
	

	public void startChargingService() {
	    Thread chargingThread = new Thread(this);
	    chargingThread.start();
	}
}
