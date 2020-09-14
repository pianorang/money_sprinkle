package com.pianorang.money.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorDto {
    public ErrorDto(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private int status;
    private String msg;
}
