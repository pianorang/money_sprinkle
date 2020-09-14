package com.pianorang.money.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super("유효하지 않은 사용자 입니다.");
    }
}
