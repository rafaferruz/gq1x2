package com.gq2.exceptions;

public class ClasifException extends Exception {

    /**
     * Creates new
     * <code>Rating4Exception</code> without detail message.
     */
    public ClasifException() {
	this("ClasifException");
    }

    /**
     * Constructs an
     * <code>ClasifException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ClasifException(String msg) {
	super(msg);
    }
}
