/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */
package com.gq2.exception;

/**
 * This application exception indicates that a book has not been found.
 */
public class GeneralException extends Exception {

    public GeneralException() {
    }

    public GeneralException(String msg) {
	super(msg);
    }
}
