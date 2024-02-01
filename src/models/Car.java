package models;

import java.util.LinkedList;
import java.util.Queue;

public class Car {
    private int id;
    private int capacity;
    private int maxWaitingTime;
    private int remainingCapacity;
    private boolean fullyCharged;

    public Car(int id, int capacity, int maxWatingTime, int remainingCapacity, boolean fullyCharged) {
        this.id = id;
        this.capacity = capacity;
        this.maxWaitingTime=maxWaitingTime;
        this.remainingCapacity = remainingCapacity;
        this.fullyCharged = fullyCharged;
        
    }

    public int getId() {
        return id;
    }
    
    public int getCapacity() {
    	return this.capacity;    
    }

    public int getMaxWaitingTime() {
        return maxWaitingTime;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public boolean isFullyCharged() {
        return fullyCharged;
    }

    public void setFullyCharged(boolean fullyCharged) {
        this.fullyCharged = fullyCharged;
    }

    public void stopCharging() {
    }


}
