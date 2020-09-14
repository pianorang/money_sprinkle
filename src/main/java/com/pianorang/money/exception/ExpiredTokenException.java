package com.pianorang.money.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("액세스 토큰이 만료 되었습니다.");
    }
}
