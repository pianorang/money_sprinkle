package com.pianorang.money.sprinkle;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SprinkleReceiveDto {
    public SprinkleReceiveDto(Long amount) {
        this.amount = amount;
    }

    private Long amount;
}
