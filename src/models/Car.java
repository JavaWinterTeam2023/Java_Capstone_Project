package models;

import java.util.LinkedList;
import java.util.Queue;

public class Car {
    private int id;
    private int capacity;
    private int WatingTime;

    public Car(int id, int capacity, int WatingTime) {
        this.id = id;
        this.capacity = capacity;
        this.WatingTime=WatingTime;

        
    }

    public int getId() {
        return id;
    }
    
    public int getCapacity() {
    	return this.capacity;    
    }
    public int getWatingTime() {
    	return this.WatingTime;    
    }
    public void setWatingTime(int wTime) {
     this.WatingTime=wTime;    
    }
    

   
    public void stopCharging() {
    	
    }


}
