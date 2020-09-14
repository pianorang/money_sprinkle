package com.pianorang.money.exception;

public class NoSprinkleMoneyException extends RuntimeException {
    public NoSprinkleMoneyException() {
        super("뿌리기 금액이 모두 소진 되었습니다.");
    }
}
