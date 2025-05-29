package com.example.library;

public class MaxLimitReachedException extends Exception {
    public MaxLimitReachedException(String message) {
        super(message);
    }
}