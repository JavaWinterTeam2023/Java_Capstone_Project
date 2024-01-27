package models;

import java.util.List;
import java.util.concurrent.Semaphore;

public class ChargingStation {
	private int Id;
	private String SName;
	private String Scode;
	private int YearOfConstruction;
	private List<User> Users;
	private final Semaphore resourceSemaphore;
	    
	    public ChargingStation(int maxAvailable) {
	    	this.resourceSemaphore = new Semaphore(maxAvailable);
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
	

    // Method to acquire a semaphore permit
	public boolean tryAcquireChargingPoint() {
        return resourceSemaphore.tryAcquire();
    }

    public void releaseChargingPoint() {
    	resourceSemaphore.release();
    }

	
}
