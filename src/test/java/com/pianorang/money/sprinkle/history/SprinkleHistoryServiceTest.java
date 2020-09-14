package com.pianorang.money.sprinkle.history;

import com.pianorang.money.exception.ExpiredTokenException;
import com.pianorang.money.exception.InvalidUserException;
import com.pianorang.money.exception.NoSprinkleMoneyException;
import com.pianorang.money.room.Room;
import com.pianorang.money.sprinkle.Sprinkle;
import com.pianorang.money.sprinkle.SprinkleService;
import com.pianorang.money.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SprinkleHistoryServiceTest {
    @Mock
    private SprinkleHistoryRepository historyRepository;

    @InjectMocks
    private SprinkleHistoryService service;

    @Mock
    private Sprinkle sprinkle;

    @Mock
    private SprinkleHistory sprinkleHistory;

    @BeforeEach()
    public void setup() {
        this.service = new SprinkleHistoryService(historyRepository);
    }

    @Test
    @DisplayName("뿌리기 인원수에 맞게 분배 체크")
    void saveHistory() {
        Sprinkle sprinkle = Sprinkle.builder()
                .room(new Room("ROOM1"))
                .createUser(new User(1L))
                .amount(1000L)
                .headCount(7)
                .token("aaa")
                .tokenCreateDate(LocalDateTime.now())
                .build();

        List<SprinkleHistory> saveHistories = service.saveHistories(sprinkle);

        //생성된 Row Count 와 인원수 비교
        assertThat(saveHistories.size()).isEqualTo(sprinkle.getHeadCount());

        //총금액 비교
        assertThat(saveHistories.stream().mapToLong(i-> i.getAmount()).sum()).isEqualTo(sprinkle.getAmount());

    }

    @Test
    @DisplayName("뿌리기 받기시 이미 받은 사용자인지 체크")
    void receiveMoneyWithInvalidUserException() {
        when(historyRepository.countBySprinkleIdAndReceivedUserId(1L, 1L)).thenReturn(1L);

        org.junit.jupiter.api.Assertions.assertThrows(InvalidUserException.class, () -> {
            service.receiveMoney(1L, 1L);
        });
    }

    @Test
    @DisplayName("뿌리기 받기시 모든 금액이 할당됐을시 체크")
    void receiveMoneyWithNoSprinkleMoneyException() {
        given(historyRepository.findFirstBySprinkleIdAndReceived(1L, false)).willReturn(null);

        org.junit.jupiter.api.Assertions.assertThrows(NoSprinkleMoneyException.class, () -> {
            service.receiveMoney(1L, 1L);
        });
    }
}