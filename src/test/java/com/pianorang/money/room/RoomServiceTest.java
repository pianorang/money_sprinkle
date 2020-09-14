package com.pianorang.money.room;


import com.pianorang.money.exception.InvalidUserException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    RoomRepository repository;

    @InjectMocks
    RoomService service;

    @BeforeEach()
    public void setup() {
        this.service = new RoomService(repository);
    }

    @Test
    @DisplayName("대화방에 있는 사용자인지 체크")
    void verifyUser() {
        when(repository.verifyUser("ROOM1", 1L))
                .thenReturn(null);

        org.junit.jupiter.api.Assertions.assertThrows(InvalidUserException.class, () -> {
            service.verifyUser("ROOM1", 1L);
        });
    }
}