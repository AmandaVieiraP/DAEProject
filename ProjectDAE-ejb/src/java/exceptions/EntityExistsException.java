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
public class EntityExistsException extends Exception {

    /**
     * Creates a new instance of <code>EntityExistsException</code> without
     * detail message.
     */
    public EntityExistsException() {
    }

    /**
     * Constructs an instance of <code>EntityExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EntityExistsException(String msg) {
        super(msg);
    }
}
