package product.impl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * CapacityExceededException
 * @author gguzm
 */
public class CapacityExceededException extends Exception {
    
    /**
     * CapacityExceededException
     */
    public CapacityExceededException() {
        super();
    }
    
    /**
     * CapacityExceededException
     * @param message 
     */
    public CapacityExceededException(String message) {
        super(message);
    }
    
}
