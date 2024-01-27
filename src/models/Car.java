package models;

public class Car {
    private int id;
    private int capacity;

    public Car(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }
    
    public int getCapacity() {
    	return this.capacity;    }
}
