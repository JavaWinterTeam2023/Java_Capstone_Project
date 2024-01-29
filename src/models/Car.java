package models;

import java.util.LinkedList;
import java.util.Queue;

public class Car {
    private int id;
    private int capacity;
    private int arrivalTime;

    public Car(int id, int capacity, int ArrivalTime) {
        this.id = id;
        this.capacity = capacity;
        this.arrivalTime=ArrivalTime;

        
    }

    public int getId() {
        return id;
    }
    
    public int getCapacity() {
    	return this.capacity;    
    }
    public int getArrivalTime() {
    	return this.arrivalTime;    
    }
    public void setArrivalTime(int arrTime) {
     this.arrivalTime=arrTime;    
    }
    

   
    public void stopCharging() {
    	
    }


}
