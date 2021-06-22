/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product.impl;

import java.util.LinkedList;
import java.util.Queue;
import product.interfces.Place;
import product.interfces.Product;
import product.interfces.Store;

/**
 * The store where to put the products if the oven is not avialable. This class is thread safe.
 *
 * @author gguzm
 */
public class StoreImpl extends Place implements Store {
    protected final Queue<Product> products = new LinkedList<>();
    
    /**
     * The size of the oven
     * @return 
     */
    public double size() {
        return 1000;
    }
    
    /**
     * Put a product in this store, if there is no space left in the store, it
     * will block until enough space frees up. This operation will put the
     * products in FIFO order
     * @param product The Product to put in this Store
     * @throws CapacityExceededException
     */
    @Override
    public synchronized void put(Product product) throws CapacityExceededException {
        if ((occupedCapacity(products) + product.size()) > this.size()) {
            throw new CapacityExceededException("There is no space left in the store");
        }
        products.offer(product);    
    }

    /**
     * Take the next element that has to be processed respecting FIFO
     *
     * @return
     */
    @Override
    public synchronized Product take() {
        return products.poll();
    }

    /**
     * Take the specified Product from the Store
     *
     * @param product
     */
    @Override
    public synchronized void take(Product product) {
        products.remove(product);
    }
    
    /**
     * getProducts
     * @return The products
     */
    public Queue<Product> getProducts() {
        return products;
    }
    
}
