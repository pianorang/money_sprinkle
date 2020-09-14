package com.pianorang.money.sprinkle;

import com.pianorang.money.exception.ExpiredTokenException;
import com.pianorang.money.exception.InvalidTokenException;
import com.pianorang.money.room.Room;
import com.pianorang.money.sprinkle.history.SprinkleHistoryRepository;
import com.pianorang.money.sprinkle.history.SprinkleHistoryService;
import com.pianorang.money.user.User;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SprinkleServiceTest {

    @Mock
    SprinkleRepository repository;

    @Mock
    SprinkleHistoryService historyService;

    @InjectMocks
    SprinkleService service;

    @Mock
    private Sprinkle sprinkle;

    @BeforeEach()
    public void setup() {
       this.service = new SprinkleService(repository, historyService);
    }

    @Test
    @DisplayName("뿌리기 토큰 유효시간 체크 (10분)")
    void verifySprinkleToExpiredToken() {
        Sprinkle sprinkle = Sprinkle.builder()
                .room(new Room("ROOM1"))
                .createUser(new User(1L))
                .amount(1000L)
                .headCount(7)
                .token("aaa")
                .tokenCreateDate(LocalDateTime.now().minusMinutes(11))
                .build();

        when(repository.findByRoomIdAndCreateUserIdNotAndToken(sprinkle.getRoom().getId(), sprinkle.getCreateUser().getId(), sprinkle.getToken()))
                .thenReturn(sprinkle);

        org.junit.jupiter.api.Assertions.assertThrows(ExpiredTokenException.class, () -> {
            service.verifySprinkleForReceive(sprinkle.getRoom().getId(), sprinkle.getCreateUser().getId(), sprinkle.getToken());
        });
    }

    @Test
    @DisplayName("뿌리기 조회 토큰 유효시간 체크 7일")
    void verifySprinkleOwnerForView() {
        Sprinkle sprinkle = Sprinkle.builder()
                .room(new Room("ROOM1"))
                .createUser(new User(1L))
                .amount(1000L)
                .headCount(7)
                .token("aaa")
                .tokenCreateDate(LocalDateTime.now().minusDays(11))
                .build();

        when(repository.findByCreateUserIdAndToken(sprinkle.getCreateUser().getId(), sprinkle.getToken()))
                .thenReturn(sprinkle);

        org.junit.jupiter.api.Assertions.assertThrows(ExpiredTokenException.class, () -> {
            service.verifySprinkleOwnerForView(sprinkle.getCreateUser().getId(), sprinkle.getToken());
        });
    }
}
