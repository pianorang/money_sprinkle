package com.pianorang.money.sprinkle.history;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SprinkleHistoryDto {
    public SprinkleHistoryDto(Long amount, Long userId) {
        this.amount = amount;
        this.userId = userId;
    }

    private Long amount;
    private Long userId;
}
