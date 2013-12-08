package com.gq2.exceptions;

public class ExampleException extends Exception {

    /**
     * Creates new
     * <code>Rating4Exception</code> without detail message.
     */
    public ExampleException() {
	this("ClasifException");
    }

    /**
     * Constructs an
     * <code>ExampleException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ExampleException(String msg) {
	super(msg);
    }
}
