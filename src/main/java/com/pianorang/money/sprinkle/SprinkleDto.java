package com.pianorang.money.sprinkle;

import com.pianorang.money.sprinkle.history.SprinkleHistoryDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SprinkleDto {
    public SprinkleDto(LocalDateTime sprinkleDate, Long sprinkleAmount, Long receivedAmount, List<SprinkleHistoryDto> receivedList) {
        this.sprinkleDate = sprinkleDate;
        this.sprinkleAmount = sprinkleAmount;
        this.receivedAmount = receivedAmount;
        this.receivedList = receivedList;
    }

    private LocalDateTime sprinkleDate;
    private Long sprinkleAmount;
    private Long receivedAmount;
    private List<SprinkleHistoryDto> receivedList = new ArrayList<>();


}
