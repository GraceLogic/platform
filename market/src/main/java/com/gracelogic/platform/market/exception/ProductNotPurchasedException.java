package com.gracelogic.platform.market.exception;

public class ProductNotPurchasedException extends Exception {

    private String message;

    public ProductNotPurchasedException(String message) {
        super(message);
        this.message = message;
    }

    public ProductNotPurchasedException() {
        super("");
    }

    public String getMessage() {
        return message;
    }
}
