package com.pianorang.money.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException() {
        super("유효하지 않은 파라미터 입니다.");
    }
}
