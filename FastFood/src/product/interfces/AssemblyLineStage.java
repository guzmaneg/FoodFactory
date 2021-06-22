/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product.interfces;

/*
* This represents an assembly line stage of the factory. Implementations of this class should be thread-safe
*/
public interface AssemblyLineStage {

    /**
     * Put the specified product to the assembly line to continue in the next
     * stage.
     *
     * @param product
     */
    void putAfter(Product product);

    /**
     * Takes the next product available from the assembly line.
     *
     * @return
     */
    Product take();
}
