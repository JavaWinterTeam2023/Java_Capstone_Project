package models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class ChargingStation {
	private int Id;
	private String SName;
	private String Scode;
	private int YearOfConstruction;
	private List<User> Users;
	private final Semaphore resourceSemaphore;
	private Location[] locations;
	private String ChargingStationName;
	    
	    public ChargingStation( String ChargingStationName,int initialEnergy,int numLocations) {
	    	this.resourceSemaphore = new Semaphore(initialEnergy);
	    	 this.locations = new Location[numLocations];
	    	 this.ChargingStationName=ChargingStationName;
	    	 for (int i = 0; i < numLocations; i++) {
	             locations[i] = new Location(initialEnergy, (i+1));
	         }
	    	 
	    }
	
	public int GetId() {
		return Id;
	}
	public void SetId(int id) {
		this.Id=id;
	}
	public String GetSName() {
		return SName;
	}
	public void SetSName(String sName) {
		this.SName=sName;
	}
	public String GetScode() {
		return Scode;
	}
	public void SetScode(String sCode) {
		this.Scode=sCode;
	}
	public int GetYearOfConstruction() {
		return YearOfConstruction;
	}
	public void SetYearOfConstruction(int yConstruction) {
		this.YearOfConstruction=yConstruction;
	}
	
	 public Location[] getLocations() {
	        return locations;
	    }

    // Method to acquire a semaphore permit
	public boolean tryAcquireChargingPoint() {
        return resourceSemaphore.tryAcquire();
    }

    public void releaseChargingPoint() {
    	resourceSemaphore.release();
    }

    public boolean ChargCar(Car car, int CTurnTime) {

    	boolean IsExitFroQueue=false;
    	for (Location loc:locations) {
        	System.out.println("Car " + car.getId() + "has the capaciti of the " +car.getCapacity());
        	System.out.println("location  " + loc.getnumLocation() + "has the capacity of the " +loc.getchargeRate());
    		synchronized (loc) {
				if(loc.isAvailable() && loc.getchargeRate()>= car.getCapacity()) {
					loc.chargeVehicle(car.getCapacity());
                    System.out.println("car " +car.getId() + " charged at " + ChargingStationName + " - Location: " + loc.getnumLocation());
                    IsExitFroQueue = true;
                    break;
				}
				else if(!loc.isAvailable()  ) {
					LocalDateTime currentDateTime = LocalDateTime.now();
		        	int currentMinute=currentDateTime.getMinute();
		        	int waitTime=currentMinute-CTurnTime;
		        	if(car.getWatingTime()> waitTime) {
		        		IsExitFroQueue=true;
		        		break;
		        	}
		        	IsExitFroQueue = false;
                    break;
				}
			}
    	}
    	 if (!IsExitFroQueue) {
             System.out.println(car.getId() + " couldn't charge at " + ChargingStationName + ". it is looking for another station");
             // Add logic for waiting and potentially going to another station
         }
    	 return IsExitFroQueue;
    }
	
}
