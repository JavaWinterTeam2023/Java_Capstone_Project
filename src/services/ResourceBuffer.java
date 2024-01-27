package services;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ResourceBuffer {
	
    private final BlockingQueue<Integer> queue;
    private final int maxSize;
    

    public ResourceBuffer(int maxSize) {
        this.queue = new LinkedBlockingQueue<>();
        this.maxSize = maxSize;
    }

    public synchronized void add(int id) {
        while (queue.size() == maxSize) {
            try {
                System.out.println("No energy source is available, battery-" + id + " is waiting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        queue.add(id);
        System.out.println("Battery " + id +": enter the queue");
        notify();
    }

    public synchronized void free(String energySource) {
        while (queue.isEmpty()) {
            try {
            	System.out.println("All energy sources are free, waiting for battery charging...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int value = queue.poll();
        System.out.println(energySource + ": finish charging for battery-" + value);
        notify();
    }

	public int getQueueSize() {
		// TODO Auto-generated method stub
		return queue.size();
	}
}