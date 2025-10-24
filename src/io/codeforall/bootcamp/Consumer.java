package io.codeforall.bootcamp;

import io.codeforall.bootcamp.bqueue.Pizza;
import io.codeforall.bootcamp.bqueue.BQueue;

/**
 * Consumer of integers from a blocking queue
 */
public class Consumer implements Runnable {

    private final BQueue<Pizza> queue;
    private int elementNum;

    /**
     * @param queue the blocking queue to consume elements from
     * @param elementNum the number of elements to consume
     */
    public Consumer(BQueue queue, int elementNum) {
        this.queue = queue;
        this.elementNum = elementNum;
    }

    @Override
    public void run() {

            // Loop to consume the specified number of pizzas
            // elementNum was set in the constructor (how many pizzas this waiter serves)
            for (int i = 0; i < elementNum; i++) {

                // Take a pizza from the queue
                // poll() will automatically wait if the queue is empty
                Pizza pizza = queue.poll();

                // The pizza variable now has the pizza, but we don't need to do anything with it
                // In a real scenario, we would "serve" it, but here consuming = just removing it

                // Optional: Small pause to simulate that serving takes time
                try {
                    Thread.sleep(150); // Sleep for 150 milliseconds (a bit slower than producers)
                } catch (InterruptedException e) {
                    // If interrupted, just continue
                }
            }

            // When done consuming all pizzas, print a message
            System.out.println("✅ " + Thread.currentThread().getName() + " finished consuming " + elementNum + " pizzas");
        }

}

