/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodfactorytest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import product.impl.OvenImpl;
import product.impl.StoreImpl;
import product.interfces.Store;

/**
 *
 * @author gguzm
 */
public class FoodFactoryServer {
    private static ServerSocket serverSocket;

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
        new FoodFactoryServer().startServer();
     }

     /**
      * startServer
      */
    public void startServer() {
        // For the moment it has only one oven and one store. The final implementatio should be a multiples ovens and store as well
        OvenImpl oven = new OvenImpl(); 
        Store store = new StoreImpl();

        Runnable serverTask;
        
        serverTask = () -> {
            try {
                ServerSocket serverSocket1 = new ServerSocket(9000);
                System.out.println("Waiting for clients to connect...");
                while (true) {
                    Socket clientSocket = serverSocket1.accept();
                    System.out.println("Assembly Line connected.");
                    ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
                    new AssemblyLineHandler(clientSocket, input, output, oven, store).start();
                }
            }catch (IOException e) {
                System.err.println("Unable to process client request ==> "+e);
            }
        };
        
        productsCookingControl(serverTask, oven);
    }

    /**
     * It starts a new thread for the products cooking control
     * @param serverTask
     * @param oven 
     */
    private void productsCookingControl(Runnable serverTask, OvenImpl oven) {
        // New thead for the cooking control of the oven
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
        
        while (true) {            
            oven.getProducts().forEach((product) -> {
                System.out.println("There are "+oven.getProducts().size()+" products in the oven");
                // Checking if the product is cooked
                long cookSecondsElapsed = LocalDateTime.now().toEpochSecond(ZoneOffset.ofTotalSeconds(0)) - 
                        product.getTimePut().toEpochSecond(ZoneOffset.ofTotalSeconds(0));
                
                long productCookTime = product.cookTime().getSeconds();
                
                // If the the duration of the product is reacehed then romeve the product from the oven
                if (cookSecondsElapsed >= productCookTime) {
                    oven.getProducts().remove(product);
                    System.out.println("The product "+product.getName()+" has been removed from the oven");
                }
            });
        }
    }
}
