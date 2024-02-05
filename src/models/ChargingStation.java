package models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import services.EnergyManagement;


public class ChargingStation {
	private final Semaphore resourceSemaphore;
	private Location[] locations;
	private String ChargingStationName;
	private EnergyManagement energyManager;
	    
    public ChargingStation(String ChargingStationName, int initialEnergy, 
    			int numLocations, EnergyManagement EMS) {
    	this.resourceSemaphore = new Semaphore(initialEnergy);
    	this.locations = new Location[numLocations];
    	this.ChargingStationName = ChargingStationName;
    	this.energyManager = EMS; 
//    	this.updateLocationEnergy();
    	for (int i = 0; i < numLocations; i++) {
            this.locations[i] = new Location(EMS.updateTotalCapacity()/numLocations, i+1);
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
    
    public void updateLocationEnergy () {
//    	Thread thread = new Thread(() -> {
//    		while(true) {
    			synchronized (this.locations) {
	    			for (Location location : this.locations) {
	    				double updateChargeRate = energyManager.getTotalCapacity() / 4;
	    				location.setChargeRate(updateChargeRate);
	    			}
    			}
//    		}
//    	});
//    	thread.start();
    }

    public boolean chargeCar(Car car, int CTurnTime) {
    	boolean isExitFromQueue = false;
    	for (Location loc:locations) {
        	System.out.println("Car " + car.getId() + "has the capaciti of the " +car.getCapacity());
        	System.out.println("location  " + loc.getNumLocation() + "has the capacity of the " +loc.getChargeRate());
    		synchronized (loc) {
				if(loc.isAvailable()) {
//					loc.chargeVehicle(car.getCapacity());
                    System.out.println("car " +car.getId() + " charged at " + ChargingStationName 
                    		+ " - Location: " + loc.getNumLocation() + ".");
                    isExitFromQueue = true;
                    break;
				}
				else if(!loc.isAvailable()  ) {
					LocalDateTime currentDateTime = LocalDateTime.now();
		        	int currentMinute=currentDateTime.getMinute();
		        	int waitTime = currentMinute - CTurnTime;
		        	if(car.getMaxWaitingTime() > waitTime) {
		        		isExitFromQueue=true;
		        		break;
		        	}
		        	isExitFromQueue = false;
                    break;
				}
			}
    	}
    	
    	
    	if (!isExitFromQueue) {
            System.out.println("Car " + car.getId() + " couldn't charge at " + ChargingStationName + ". it is looking for another station");
            // Add logic for waiting and potentially going to another station
        }
    	return car.isExitFromQueue;
    }
    
    public void assignedToLocation(Car car, Queue<Object> queue) {
    	boolean isAssigned = false;
    	for (Location location : this.locations) {
    		if (location.isAvailable()) {
    			this.updateLocationEnergy();
    			System.out.println("[Car " + car.getId() + "]" + "[Charging]");
    			System.out.println("\tAt " + ChargingStationName + " - Location: " + location.getNumLocation());
    			System.out.println("\tCurrent weather is: " 
    					+ getEnergyManager().getWeather().getWeatherCondition());
    			System.out.println("\tLocation charge rate: " + location.getChargeRate());
    			
    			location.setAvailable(false);
    			isAssigned = true;
    			car.setAssigned(isAssigned);
    			location.chargeVehicle(car, this);
    			break;
    		}
    	}
    	
    	if (!isAssigned) {
    		System.out.println("[Car " + car.getId() + "]" + "[Not charging]");
    		System.out.println("\tNo location currently available at " + ChargingStationName);
    		boolean ableToWait = false;
    		for (Location location : this.locations) {
    			if (location.chargingTime < car.getMaxWaitingTime()) {
    				ableToWait = true;
    				break;
    			}
    		}    		
    		if (ableToWait) {
    			car.waitingForLocation(this, queue);
    		} else {
    			System.out.println("Car " + car.getId() + " couldn't charge at " 
    					+ ChargingStationName + ". It is looking for another station");
    		}
    	}
    }

	public EnergyManagement getEnergyManager() {
		return energyManager;
	}
}
