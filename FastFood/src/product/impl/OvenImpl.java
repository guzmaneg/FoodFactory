/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product.impl;

import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import product.interfces.Oven;
import product.interfces.Place;
import product.interfces.Product;

/**
 * OvenImpl
 * @author gguzm
 */
public class OvenImpl extends Place implements Oven {
    private boolean on;
    
    private Duration onDuration;
    
    protected final Queue<Product> products = new ConcurrentLinkedQueue<>();
    
    /**
     * This returns the size of the oven in cm2. As a simplification of the
     * problem, assume that the sizes of the products can be summed, and that
     * value should not exceed the size of the oven. Otherwise an exception is
     * thrown if adding a product.
     *
     * @return
     */
    @Override
    public double size() {
        return 1000;
    }

    /**
     * Puts a product in the oven to be cooked. The oven can be functioning at
     * the time the product is put in.
     *
     * @param product The product to put in the oven
     * @throws CapacityExceededException if the oven capacity is exceeded.
     */
    @Override
    public void put(Product product) throws CapacityExceededException {
        if ((occupedCapacity(products) + product.size()) > this.size()) {
            throw new CapacityExceededException("There is no space left in the oven");
        }
        products.offer(product);
    }
    
    /**
     * Take the specified Product out of the oven. The oven can be functioning
     * at the time the product is taken out.
     *
     * @param product
     */
    @Override
    public void take(Product product) {
        products.poll();
    }

    /**
     * Turns on the Oven. If the oven was turned on with a duration, the
     * duration is ignored.
     */
    @Override
    public void turnOn() {
        on = true;
        if (onDuration!=null) {
            onDuration = null;
        }
    }

    /**
     * Turn on the Oven for the specified duration. If the oven is turned on, it
     * updates the duration.
     *
     * @param duration the duration to mantain the oven before turning it off.
     */
    @Override
    public void turnOn(Duration duration) {
        on = true;
        onDuration = duration;
    }

    /**
     * Turn off the Oven immediately, even if it was turned on with a duration
     * which will be ignored.
     */
    @Override
    public void turnOff() {
        on = false;
    }

    public Queue<Product> getProducts() {
        return products;
    }

    public boolean isOn() {
        return on;
    }

    public Duration getOnDuration() {
        return onDuration;
    }
}
