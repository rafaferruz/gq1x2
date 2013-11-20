package com.gq2.exceptions;

public class CtoException extends Exception {

    /**
     * Creates new
     * <code>Rating4Exception</code> without detail message.
     */
    public CtoException() {
	this("CtoException");
    }

    /**
     * Constructs an
     * <code>CtoException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CtoException(String msg) {
	super(msg);
    }
}
