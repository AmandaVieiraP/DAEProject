/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Amanda
 */
public class EntityDoesNotExistsException extends Exception {

    /**
     * Creates a new instance of <code>EntityDoesNotExistsException</code>
     * without detail message.
     */
    public EntityDoesNotExistsException() {
    }

    /**
     * Constructs an instance of <code>EntityDoesNotExistsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public EntityDoesNotExistsException(String msg) {
        super(msg);
    }
}
