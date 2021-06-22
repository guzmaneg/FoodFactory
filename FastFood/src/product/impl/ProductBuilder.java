package product.impl;


import java.time.Duration;
import product.interfces.Product;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gguzm
 */
public class ProductBuilder {
    
    private String name;

    private double size;
    
    private Duration cookTime;

    public ProductBuilder() {
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setSize(double size) {
        this.size = size;
    }

    public void setCookTime(Duration cookTime) {
        this.cookTime = cookTime;
    }
    
    public Product build(){
        
        return new ProductImpl(name, size, cookTime);
    }
}
