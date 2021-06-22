/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import product.interfces.Product;
import product.interfces.AssemblyLineStage;
import java.util.Queue;
import product.utils.FastFoodUtils;

/**
 * This represents an assembly line stage of the factory. Implementations of this class should be thread-safe
 * @author gguzm
 */
public class AssemblyLineStageImpl implements AssemblyLineStage{
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private final Socket socket;
            
    private Queue<Product> products;
    private final Queue<Product> productsNextStage = new LinkedList();

    public AssemblyLineStageImpl(Queue<Product> products) throws IOException {
        socket = new Socket("localhost", 9000);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        
        this.products = products;
    }    
    
    /**
     * Put the specified product to the assembly line to continue in the next
     * stage.
     *
     * @param product
     */
    @Override
    public synchronized void putAfter(Product product) {
        productsNextStage.offer(product);
    }

     /**
     * Takes the next product available from the assembly line.
     *
     * @return
     */
    @Override
    public synchronized Product take() {
        return products.poll();
    }

    /**
     * getProductos
     * @return 
     */
    public synchronized Queue<Product> getProductos() {
        return products;
    }
    
    /**
     * putProductInTheOven
     * @return true: the product could be put in the oven
     * @throws IOException 
     */
    public synchronized boolean putProductInTheOven() throws IOException {
        if (!putProduct(true)) { // Put Oven attempting
            if (!putProduct(false)) { // Put Store attempting
                String serverResponseDetail = input.readUTF();
                System.out.println(serverResponseDetail);
                
                System.out.println("The client is disconnecting...");
                stop(); // stopping the assembly line
                System.out.println("Client disconnected");
                return false;
            }
        }
        products.poll(); // Remove the producto from the queue
        return true;
    }
    
    /***
     * putProduct
     * @param inTheOven
     * @return true: the product could be put
     * @throws IOException 
     */
    public synchronized boolean putProduct(boolean inTheOven) throws IOException {
        String putMessage =inTheOven?FastFoodUtils.PUT_PRODUCT:FastFoodUtils.PUT_PRODUCT_STORE; 
        String refusedMessage =inTheOven?FastFoodUtils.PRODUCT_OVEN_REFUSED:FastFoodUtils.PRODUCT_STORE_REFUSED; 
        
        output.writeUTF(putMessage);
        System.out.println(putMessage +" from Client AssemblyLineStageImpl");
        Product product = products.peek();
        output.writeObject(product);
        output.flush();
        
        String serverResponse = input.readUTF();
        System.out.println(serverResponse);
        
        return !refusedMessage.equals(serverResponse);
    }
   
    public synchronized void stop() throws IOException {
        output.writeUTF(FastFoodUtils.STOP_ASEEMBLY);
        output.flush();
    }
}
