package services;

import java.util.LinkedList;
import java.util.Queue;

import models.Car;

public class QueueService {
	private Queue<Object> sharedQueue = new LinkedList<>();
	 
	public synchronized void addToQueue(Car car) {
	    sharedQueue.add(car);
	}
	 
	public synchronized Queue<Object> getFromQueue() {
	    Queue<Object> clonedQueue = new LinkedList<>(sharedQueue);
	    sharedQueue.clear();
	    return clonedQueue;
	}
	 
	public boolean isQueueEmpty() {
	    return sharedQueue.isEmpty();
	}
	    
	public static Queue<Car> convertToCarQueue(Queue<Object> originalQueue) {
		Queue<Car> carQueue = new LinkedList<>();
		
		while (!originalQueue.isEmpty()) {
		    Object element = originalQueue.poll();

		    if (element instanceof Car) {
		        carQueue.add((Car) element);
		    } else {
		        // Handle non-Car objects if needed
		        System.out.println("Non-Car object encountered: " + element);
		    }
		}
		return carQueue;
	}
}
