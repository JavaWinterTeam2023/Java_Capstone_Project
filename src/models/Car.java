package models;

public class Car {
    private int id;
    private int capacity;
    private int maxWaitingTime;
    private int remainingCapacity;
    private boolean fullyCharged;
    public boolean isExitFromQueue;
    private boolean isAssigned;

    public Car(int id, int capacity, int maxWaitingTime, int remainingCapacity, boolean fullyCharged) {
        this.id = id;
        this.capacity = capacity;
        this.maxWaitingTime = maxWaitingTime;
        this.remainingCapacity = remainingCapacity;
        this.fullyCharged = fullyCharged;
        this.isExitFromQueue = false;
        this.isAssigned = false;
    }

    public synchronized boolean isAssigned() {
		return isAssigned;
	}

	public synchronized void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public int getId() {
        return id;
    }
    
    public int getCapacity() {
    	return this.capacity;    
    }

    public int getMaxWaitingTime() {
        return this.maxWaitingTime;
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
