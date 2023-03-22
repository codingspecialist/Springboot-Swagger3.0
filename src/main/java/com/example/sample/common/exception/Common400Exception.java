package com.example.sample.common.exception;

// 적합하지 않거나(illegal) 적절하지 못한(inappropriate) 인자
public class Common400Exception extends IllegalArgumentException {
    public Common400Exception(String message) {
        super(message);
    }
}
