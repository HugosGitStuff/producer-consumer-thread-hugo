package io.codeforall.bootcamp.bqueue;

import java.util.LinkedList;

/**
 * Blocking Queue
 * @param <T> the type of elements stored by this queue
 */
public class BQueue<T> {

    // pizza counter where pizzas are stored
    private LinkedList<T> queue;
    // max num. of pizzas
    private int limit;

    /**
     * Constructs a new queue with a maximum size
     * @param limit the queue size
     */
    public BQueue(int limit) {
        // save limit
        this.limit = limit;
        // new empty list to store pizzas
        this.queue = new LinkedList<>();

    }

    /**
     * Inserts the specified element into the queue
     * Blocking operation if the queue is full
     * @param data the data to add to the queue
     */
    public void offer(T data) {

        // synchronized means: only ONE thread can run this code at a time
        // "this" means we're locking the entire BQueue object
        synchronized (this) {

            // While the queue is full, we must wait
            // Think: "While counter is full, chef waits"
            while (queue.size() == limit) {
                try {
                    // wait() makes this thread sleep until another thread wakes it up
                    // It also releases the lock so other threads can work
                    wait();
                } catch (InterruptedException e) {
                    // If someone interrupts our wait, just continue
                }
            }

            // Now we have space! Add the pizza to the end of the queue
            queue.add(data);

            // Print what happened
            System.out.println(Thread.currentThread().getName() + " added: " + data + " (Queue size: " + queue.size() + ")");

            // EXTRA FEATURE: Alert when queue becomes full
            if (queue.size() == limit) {
                System.out.println("🔴 ALERT: " + Thread.currentThread().getName() + " has filled the queue!");
            }

            // Wake up ANY waiting threads (consumers who were waiting for pizzas)
            // notifyAll() tells all waiting threads: "Hey! Something changed, check again!"
            notifyAll();
        }

    }

    /**
     * Retrieves and removes data from the head of the queue
     * Blocking operation if the queue is empty
     * @return the data from the head of the queue
     */
    public T poll() {

        // synchronized means: only ONE thread can run this code at a time
        synchronized (this) {

            // While the queue is empty, we must wait
            // Think: "While counter is empty, waiter waits"
            while (queue.isEmpty()) {
                try {
                    // wait() makes this thread sleep until another thread wakes it up
                    wait();
                } catch (InterruptedException e) {
                    // If someone interrupts our wait, just continue
                }
            }

            // Now there's a pizza! Remove it from the front of the queue
            T data = queue.remove();

            // Print what happened
            System.out.println(Thread.currentThread().getName() + " removed: " + data + " (Queue size: " + queue.size() + ")");

            // EXTRA FEATURE: Alert when queue becomes empty
            if (queue.isEmpty()) {
                System.out.println("🟢 ALERT: " + Thread.currentThread().getName() + " has emptied the queue!");
            }

            // Wake up ANY waiting threads (producers who were waiting for space)
            notifyAll();

            // Return the pizza we removed
            return data;
        }

    }

    /**
     * Gets the number of elements in the queue
     * @return the number of elements
     */
    public int getSize() {
        // how many pizzas in queue
        return queue.size();

    }

    /**
     * Gets the maximum number of elements that can be present in the queue
     * @return the maximum number of elements
     */
    public int getLimit() {
        // max num. of pizzas in queue
        return limit;

    }

}
