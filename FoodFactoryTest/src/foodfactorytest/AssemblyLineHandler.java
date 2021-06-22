/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodfactorytest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import product.impl.CapacityExceededException;
import product.impl.OvenImpl;
import product.impl.ProductImpl;
import product.impl.StoreImpl;
import product.interfces.Oven;
import product.interfces.Product;
import product.interfces.Store;
import product.utils.FastFoodUtils;

/**
 *
 * @author gguzm
 */
public class AssemblyLineHandler extends Thread {
    private final Socket clientSocket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    private Oven oven = new OvenImpl();
    private Store store = new StoreImpl();
    
//    private final Queue<Oven> ovens = new LinkedList<>();
//    private final Queue<Store> stores = new LinkedList<>();
    
    private Product product;
    
    public AssemblyLineHandler(Socket clientSocket, ObjectInputStream input, ObjectOutputStream output, Oven oven, Store store) throws IOException {
        this.clientSocket = clientSocket;
        this.input = input;
        this.output = output;
        this.oven = oven;
        this.store = store;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                String message = input.readUTF();
                System.out.println(" Server getting message from Client ==> "+ message);
                if (message == null || message.equalsIgnoreCase(FastFoodUtils.STOP_ASEEMBLY)) {
                    break;
                } 
                switch (message) {
                    case FastFoodUtils.PUT_PRODUCT:
                        product = (Product) input.readObject();
                        try {
                            putProduct(product);
                        } catch (CapacityExceededException ex) {
                            output.writeUTF(FastFoodUtils.PRODUCT_OVEN_REFUSED);
                            output.flush();
                        }
                        break;
                    case FastFoodUtils.PUT_PRODUCT_STORE:
                        product = (Product) input.readObject();
                        try {
                            putProductStore(product);
                        } catch (CapacityExceededException ex) {
                            output.writeUTF(FastFoodUtils.PRODUCT_STORE_REFUSED);
                            output.writeUTF("There is no space left in the oven neither in the store");
                            output.flush();
                        }
                        break;
                    case FastFoodUtils.TAKE_PRODUCT:
                        break;
                        
                    default:
                        output.writeUTF("Invalid input message");
                        break;
                }
            } catch (IOException|ClassNotFoundException ex) {
                Logger.getLogger(AssemblyLineHandler.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        try {
            // closing resources
            this.input.close();
            this.output.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(AssemblyLineHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * putProduct
     * @param product
     * @throws IOException 
     */
    private void putProduct(Product product) throws IOException, CapacityExceededException {
        product.setTimePut(LocalDateTime.now());
        oven.put(product);
        output.writeUTF("The product ("+product.getName()+") has been put in the oven");
        output.flush();
    }

    /**
     * putProductStore
     * @param product
     * @throws IOException 
     */
    private void putProductStore(Product product) throws IOException, CapacityExceededException {
        store.put(product);
        output.writeUTF("The product ("+product.getName()+") has been put in the store");
        output.flush();
    }
}
