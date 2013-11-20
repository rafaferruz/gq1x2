package com.gq2.exceptions;

public class TeamException extends Exception {

    /**
     * Creates new
     * <code>Rating4Exception</code> without detail message.
     */
    public TeamException() {
	this("EquException");
    }

    /**
     * Constructs an
     * <code>Rating4Exception</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public TeamException(String msg) {
	super(msg);
    }
}
