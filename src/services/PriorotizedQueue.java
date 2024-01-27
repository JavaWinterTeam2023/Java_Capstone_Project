package services;
import java.util.PriorityQueue;

import models.TimeSlot;

public class PriorotizedQueue {
	
    protected PriorityQueue<TimeSlot> queue = new PriorityQueue<>();
    private int Capacity;
    public PriorotizedQueue(int capacity) {
    	this.Capacity=capacity;
    }

    public synchronized void bookTimeSlot(TimeSlot timeSlot) throws InterruptedException {
    	while (queue.size()==Capacity) {
			wait();
			
		}
    	queue.add(timeSlot);
         notifyAll();
    }

    public synchronized void getNextTimeSlot()throws InterruptedException {
    	while (queue.isEmpty()) {
			wait();
			
		}
         queue.poll(); 
         notifyAll();
    }

}
