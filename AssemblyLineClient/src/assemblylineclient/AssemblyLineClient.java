/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assemblylineclient;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import product.impl.AssemblyLineStageImpl;
import product.impl.ProductBuilder;
import product.interfces.Product;

/**
 *
 * @author gguzm
 */
public class AssemblyLineClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Queue<Product> products = new LinkedList<>();
            
            fillPorductList(products);
            
            AssemblyLineStageImpl assemblyLineStage = new AssemblyLineStageImpl(products);
            
            while (!products.isEmpty()) {
                if (!assemblyLineStage.putProductInTheOven())
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(AssemblyLineClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * fillPorductList
     * @param products 
     */
    private static void fillPorductList(Queue<Product> products) {
        ProductBuilder pb = new ProductBuilder();
        
        for (int i = 1; i < 11; i++) {
            pb.setCookTime(Duration.ofSeconds(i*10));
            pb.setSize(100*i);
            pb.setName("PRD-"+i);
            products.offer(pb.build());
        }
    }
    
}
