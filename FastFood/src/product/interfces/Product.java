package product.interfces;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Implementations of this class should take care of overriding the necessary methods of the Object class to allow
 * for the use of Collections in the different implementations of Oven and Store.
 * This interface is not required to be implemented for this exercise.
 */
public interface Product {

    /**
     * The name of thr product
     * @return 
     */
    String getName();
    
    /**
     * The size that this product physically occupies in cm2
     *
     * @return
     */
    double size();

    /**
     * This is the duration that this product should be cooked for.
     *
     * @return
     */
    Duration cookTime();
    
    LocalDateTime getTimePut();

    void setTimePut(LocalDateTime timePut);


    @Override
    public boolean equals(Object o);

    @Override
    public int hashCode();
    
    
}
