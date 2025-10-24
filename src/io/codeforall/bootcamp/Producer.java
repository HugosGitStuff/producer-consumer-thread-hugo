package io.codeforall.bootcamp;

import io.codeforall.bootcamp.bqueue.BQueue;
import io.codeforall.bootcamp.bqueue.Pizza;

/**
 * Produces and stores integers into a blocking queue
 */
public class Producer implements Runnable {

    private final BQueue<Pizza> queue;
    private int elementNum;

    /**
     * @param queue the blocking queue to add elements to
     * @param elementNum the number of elements to produce
     */
    public Producer(BQueue queue, int elementNum) {
        this.queue = queue;
        this.elementNum = elementNum;
    }

    @Override
    public void run() {

            // Loop to produce the specified number of pizzas
            // elementNum was set in the constructor (how many pizzas this chef makes)
            for (int i = 0; i < elementNum; i++) {

                // Create a new pizza with random topping
                Pizza pizza = new Pizza();

                // Add the pizza to the queue
                // offer() will automatically wait if the queue is full
                queue.offer(pizza);

                // Optional: Small pause to make output easier to read
                // This simulates that making pizza takes time
                try {
                    Thread.sleep(100); // Sleep for 100 milliseconds
                } catch (InterruptedException e) {
                    // If interrupted, just continue
                }
            }

            // When done producing all pizzas, print a message
            System.out.println("\n" + Thread.currentThread().getName() + " made " + elementNum + " pizzas.");
    }

}


