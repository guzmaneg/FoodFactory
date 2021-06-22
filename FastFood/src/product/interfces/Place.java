/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product.interfces;

import java.util.Queue;
import java.util.stream.Collectors;

/**
 *
 * @author gguzm
 */
public abstract class Place {
            
   /**
     * Calculate the occuped capacity of the place
     * @param products 
     * @return The occuped capacity
     */
    protected double occupedCapacity(final Queue<Product> products) {
        Double occupedCapacity = products.stream()
            .map(product -> product.size())
            .collect(Collectors.summingDouble(Double::doubleValue));
        
        return occupedCapacity;
    }
    

}
