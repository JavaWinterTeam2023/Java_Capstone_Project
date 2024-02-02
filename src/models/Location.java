package models;

public class Location {
	private int numLocation;
    private double chargeRate;
    private boolean available;
    public int chargingTime;
	
	public Location(double ChargeRate, int numLocation) {
		this.chargeRate=ChargeRate;
		this.numLocation=numLocation;
		this.available = true;
	}
	
    public double getChargeRate() {
    	return this.chargeRate;    
    }
    public void setChargeRate(Double chrRate) {
    	this.chargeRate = chrRate;    
    }
    public int getNumLocation() {
    	return this.numLocation;    
    }
    public void setNumLocation(int number) {
    	this.numLocation = number;    
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
    
	public void chargeVehicle(Car car) {
		Thread thread = new Thread(() -> {
			this.setAvailable(false);
			while (car.getRemainingCapacity() < car.getCapacity()) {    
        		calculateRemainingCapacity(this, car);
        		this.chargingTime = calculateChargingTime(this.getChargeRate(), car.getCapacity(), car.getRemainingCapacity());	                    
//              System.out.println("Car " + car.getId() + " is charging at " + this.ChargingStationName +
//                            " - Location: " + this.getNumLocation() 
//                            + " for " + this.chargingTime + " seconds " 
//                            + " remaining capacity: " + car.getRemainingCapacity());
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Charging process for car " + car.getId() + " interrupted: " + e.getMessage());
                }
        	}  
			System.out.println("Car " + car.getId() + " finished charging at Location " + this.getNumLocation());
			this.setAvailable(true);
			car.isExitFromQueue = true;
		});
		thread.start();
	}
	
	public boolean isAvailable() {
		return this.available;
	}
	
	public void setAvailable(boolean isAvailable) {
		this.available = isAvailable;
	}
}
