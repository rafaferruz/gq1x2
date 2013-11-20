package com.gq2.exceptions;

public class ResultException extends Exception {

    /**
     * Creates new
     * <code>ResultException</code> without detail message.
     */
    public ResultException() {
	this("ResultException");
    }

    /**
     * Constructs an
     * <code>ResultException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ResultException(String msg) {
	super(msg);
    }
}
