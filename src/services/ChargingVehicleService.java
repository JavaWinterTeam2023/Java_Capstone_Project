package services;

import models.ChargingStation;

public class ChargingVehicleService implements Runnable {
    private final ChargingStation chargingStation;
    private final int sessionId;

    public ChargingVehicleService(ChargingStation chargingStation, int sessionId) {
        this.chargingStation = chargingStation;
        this.sessionId = sessionId;
    }
    
    public synchronized void run() {
            try {
                if (chargingStation.tryAcquireChargingPoint()) {
                    System.out.println("Vehicle " + sessionId + " is charging.");
                    Thread.sleep((long) (Math.random() * 5000));
                    System.out.println("Vehicle " + sessionId + " has finished charging.");
                } else {
                    System.out.println("Vehicle " + sessionId + " is waiting for an available charging point.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                chargingStation.releaseChargingPoint();
            }
    
    }
    

}
