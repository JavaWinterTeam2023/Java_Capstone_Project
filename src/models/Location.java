package models;

public class Location {
	private double chargeRate;
	private int NumLocation;
	
	public Location(double ChargeRate, int numLocation) {
		this.chargeRate=ChargeRate;
		this.NumLocation=numLocation;
	}
	
    public double getchargeRate() {
    	return this.chargeRate;    
    }
    public void setchargeRate(Double chrRate) {
     this.chargeRate=chrRate;    
    }
    public int getnumLocation() {
    	return this.NumLocation;    
    }
    public void setnumLocation(int number) {
     this.NumLocation=number;    
    }
	
	public void chargeVehicle(int amount) {
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException ex) {
			
		}
		this.chargeRate=updateChargeRate( chargeRate,  amount);
	}
	
	 public boolean isAvailable() {
	        return chargeRate > 0;
	    }
	
	public double updateChargeRate(double chargeRate, int amount) {
		return chargeRate -= amount;
	}

	
}
