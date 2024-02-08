package models;

import java.util.Queue;

public class Car {
    private int id;
    private int capacity;
    private int waitingTime;
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
        this.waitingTime = 0;
    }

    public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
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

    public void waitingForLocation(ChargingStation chargingStation, Queue<Object> carQueue) {
    	Thread thread = new Thread(() -> {    		
    		boolean ableToWait = true;
    		while (ableToWait) {
    			int remainWaitingTime = maxWaitingTime - waitingTime;
    			if (remainWaitingTime < 0) {
    				System.out.println("[Car " + this.id + "][Leaving] ");
    				System.out.println("\tReach maximum waiting time of " 
    						+ this.maxWaitingTime + " minutes. ");
    				System.out.println("\tLeaving " + chargingStation.getName());
    				this.isAssigned = false;
        			
    				break;
    			}
//    			System.out.println("Remaining time: " + remainWaitingTime + " of car " + this.id);
	    		for (Location location : chargingStation.getLocations()) {
	    				synchronized (location) {
	    				if(location.isAvailable() && !this.isAssigned) {
		    				isAssigned = true;
		    				location.setAvailable(false);
		    				ableToWait = false;
		    				chargingStation.updateLocationEnergy();		        			
		        			System.out.println("[Car " + this.getId() + "]" + "[Charging]");
		        			System.out.println("\tAt " + chargingStation.getName() 
		        					+ " - Location: " + location.getNumLocation());
		        			System.out.println("\tCurrent weather is: " 
		        					+ chargingStation.getEnergyManager().getWeather().getWeatherCondition());
		        			System.out.println("\tLocation charge rate: " + location.getChargeRate());
	        				
		        			location.chargeVehicle(this, chargingStation);
		        			this.isAssigned = true;
		        			break;
	    				}
	    			}
	    		}
	    		//try {
	    		//	waitingTime += 1;
					//Thread.sleep(1000);
				//} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
    		}
    	});
    	thread.start();
    }
}
