package services;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import models.Car;
import models.ChargingStation;

public class ChargingStationManagement implements Runnable {
	
	private ChargingStation ChargingStations;
	private int NumCar;
    private Queue<Object> carQueue;
    private QueueService queueService;
    
	public ChargingStationManagement(ChargingStation chargingStations,QueueService queueService) {
		
		this.ChargingStations = chargingStations;
		this.queueService = queueService;
        
	}
	
	
	
	
	
	
	 public void addCarToQueue(Car car) {
		 carQueue.add(car);
	    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//QueueService qSrv= new QueueService();
		//carQueue=qSrv.getFromQueue();
		//Queue<Car> CQueue= convertToCarQueue(carQueue);
		 // while (!carQueue.isEmpty()) {
	            //Car car = (Car) carQueue.poll();
		
	           // ChargingStations.ChargCar(car);
	            //chargingThread.start()
		  //}
		Queue<Car> carsToRemove= new LinkedList<>();
		boolean Isdone= false;
		synchronized (queueService) {
			 if (!queueService.isQueueEmpty()) {
                 
             
			  Queue<Object> carQueue = queueService.getFromQueue();
              for (Object element : carQueue) {
                  if (element instanceof Car) {
                	  Isdone= ChargingStations.ChargCar((Car) element);
                	  if(Isdone==true) {
                		  carsToRemove.add((Car) element);
                	  }
                  }
              }
             carQueue.removeAll(carsToRemove);
             for (Object element : carQueue) {
            	 queueService.addToQueue((Car) element);
             }
			 }
			;
		}
	        
		 
	}
	

	   public void startChargingService() {
	        Thread chargingThread = new Thread(this);
	        chargingThread.start();
	    }
}
