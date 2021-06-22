/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product.impl;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import product.interfces.Product;

/**
 *
 * @author gguzm
 */
public class ProductImpl implements Product, Serializable{

    private String name;

    private final double size;
    
    private final Duration cookTime;
    
    /**
     * The Time when the product was put in the oven
     * @return
     */
    private LocalDateTime timePut;


    ProductImpl(String name, double size, Duration cookTime) {
        this.name = name;
        this.size = size;
        this.cookTime = cookTime;
    }
    
            
    @Override
    public double size() {
        
        return size;
    }

    @Override
    public Duration cookTime() {
        
        return cookTime;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimePut() {
        return timePut;
    }

    public void setTimePut(LocalDateTime timePut) {
        this.timePut = timePut;
    }

    
}
