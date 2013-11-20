package com.gq2.exceptions;

public class Rating4Exception extends Exception {

    /**
     * Creates new
     * <code>Rating4Exception</code> without detail message.
     */
    public Rating4Exception() {
	this("Rating4Exception");
    }

    /**
     * Constructs an
     * <code>Rating4Exception</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public Rating4Exception(String msg) {
	super(msg);
    }
}
