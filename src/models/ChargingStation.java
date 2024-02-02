package models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Semaphore;


public class ChargingStation {
	private int stationId;
	private List<User> Users;
	private final Semaphore resourceSemaphore;
	private Location[] locations;
	private String ChargingStationName;
	    
    public ChargingStation(String ChargingStationName, int initialEnergy, int numLocations) {
    	this.resourceSemaphore = new Semaphore(initialEnergy);
    	this.locations = new Location[numLocations];
    	this.ChargingStationName = ChargingStationName;
    	 
    	for (int i = 0; i < numLocations; i++) {
            this.locations[i] = new Location(initialEnergy, (i+1));
        }
    	 
    }
	
	public Location[] getLocations() {
	    return locations;
	}
	
	public String getName() {
		return this.ChargingStationName;
	}

    // Method to acquire a semaphore permit
	public boolean tryAcquireChargingPoint() {
        return resourceSemaphore.tryAcquire();
    }

    public void releaseChargingPoint() {
    	resourceSemaphore.release();
    }
    
    private int calculateChargingTime(double powerRate, int maxCarCap, int currentCarCap) {
        int remainingEnergy = maxCarCap - currentCarCap;
        double chargingSpeed = powerRate / remainingEnergy;
        return (int) (remainingEnergy / chargingSpeed);
    }
    
    private void calculateRemainingCapacity(Location loc, Car car) {
    	int increasedEnergy = (int) loc.getChargeRate()/60;               		
		int newRemainingCap = car.getRemainingCapacity() + increasedEnergy;               		
		if (newRemainingCap > car.getCapacity())
			newRemainingCap = car.getCapacity();
		
		car.setRemainingCapacity(newRemainingCap);
    }

    public boolean chargeCar(Car car, int CTurnTime) {
//    	int chargingTime = 0;   	
//    	boolean isExitFromQueue = false;
    	
    	for (Location loc : locations) {
    		if (car.isAssigned() == true)
    			break;
    		
    		System.out.println("Enter location: " + loc.getNumLocation() + " " + car.isAssigned());
    		
        	synchronized (loc) {
        		System.out.println("Enter here");
        		Thread thread = new Thread(() -> {
	    			if(loc.isAvailable()) {  
	    				loc.setAvailable(false);
	    				while (car.getRemainingCapacity() < car.getCapacity()) {    
	                		calculateRemainingCapacity(loc, car);
	                		loc.chargingTime = calculateChargingTime(loc.getChargeRate(), car.getCapacity(), car.getRemainingCapacity());	                    
		                    System.out.println("Car " + car.getId() + " is charging at " + this.ChargingStationName +
		                            " - Location: " + loc.getNumLocation() 
		                            + " for " + loc.chargingTime + " seconds " 
		                            + " remaining capacity: " + car.getRemainingCapacity());
		                    
		                    try {
		                        Thread.sleep(1000);
		                    } catch (InterruptedException e) {
		                        System.out.println("Charging process for car " + car.getId() + " interrupted: " + e.getMessage());
		                    }
	                	}  
	    				System.out.println("Car " + car.getId() + " finished charging at Location " + loc.getNumLocation());
	    				loc.setAvailable(true);
	    				car.isExitFromQueue = true;
	//                    break;
					}
	//				else if(!loc.isAvailable()) {
	//					LocalDateTime currentDateTime = LocalDateTime.now();
	//		        	int currentMinute = currentDateTime.getMinute();
	//		        	int waitTime = currentMinute - CTurnTime;
	//		        	if(chargingTime> waitTime) {
	//		        		isExitFromQueue=true;
	//		        		break;
	//		        	}
	//		        	
	//		        	isExitFromQueue = false;
	//                    break;
	//				}
	        		}
    			);
        		
        		thread.start();
        		car.setAssigned(true);
//        		loc.setAvailable(false);
			}
    	}
    	
    	
    	if (!car.isExitFromQueue) {
            System.out.println("Car " + car.getId() + " couldn't charge at " + ChargingStationName + ". it is looking for another station");
            // Add logic for waiting and potentially going to another station
        }
    	return car.isExitFromQueue;
    }
    
    public boolean assignedToLocation(Car car) {
    	boolean isAssigned = false;
    	for (Location location : this.locations) {
    		if (location.isAvailable()) {
    			System.out.println("Car " + car.getId() + " start charging at " 
    					+ ChargingStationName + " - Location: " + location.getNumLocation());
    			location.setAvailable(false);
    			isAssigned = true;
    			location.chargeVehicle(car);
    			break;
    		}
    	}
    	
    	if (!isAssigned) {
    		System.out.println("Car " + car.getId() + " not assigned");
    		boolean ableToWait = false;
    		for (Location location : this.locations) {
    			if (location.chargingTime < car.getMaxWaitingTime()) {
    				ableToWait = true;
    			}
    		}
    		
    		if (!ableToWait) {
    			System.out.println("Car " + car.getId() + " couldn't charge at " 
    					+ ChargingStationName + ". It is looking for another station");
    			return false;
    		}
    	}
		return true;
    }
}
