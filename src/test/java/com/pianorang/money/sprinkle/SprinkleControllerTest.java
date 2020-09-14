package com.pianorang.money.sprinkle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pianorang.money.room.Room;
import com.pianorang.money.room.RoomRepository;
import com.pianorang.money.room.RoomService;
import com.pianorang.money.sprinkle.history.SprinkleHistory;
import com.pianorang.money.sprinkle.history.SprinkleHistoryDto;
import com.pianorang.money.sprinkle.history.SprinkleHistoryRepository;
import com.pianorang.money.sprinkle.history.SprinkleHistoryService;
import com.pianorang.money.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@WebMvcTest(SprinkleController.class)
class SprinkleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SprinkleService sprinkleService;

    @MockBean
    private SprinkleHistoryService sprinkleHistoryService;

    @MockBean
    private RoomService roomService;

    @Test
    @DisplayName("뿌리기 API")
    void sprinkleMoney() throws Exception {

        User user = new User(1L);
        given(roomService.verifyUser("ROOM1", 1L)).willReturn(user);

        Map<String, Object> param = new HashMap<>();
        param.put("amount", 10000);
        param.put("headCount", 5);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sprinkle/send")
            .header("X-USER-ID", 1)
            .header("X-ROOM-ID", "ROOM1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(param))
        ).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("token").isNotEmpty());
    }

    @Test
    @DisplayName("받기 API")
    void moneyReceive() throws Exception {
        User user = new User(1L);
        given(roomService.verifyUser("ROOM1", 1L)).willReturn(user);

        Sprinkle sprinkle = Sprinkle.builder()
                .id(1L)
                .room(new Room("ROOM1"))
                .createUser(new User(1L))
                .amount(10000L)
                .headCount(7)
                .token("aaa")
                .tokenCreateDate(LocalDateTime.now())
                .build();
        given(sprinkleService.verifySprinkleForReceive("ROOM1", 1L, "aaa")).willReturn(sprinkle);

        SprinkleHistory history = SprinkleHistory.builder()
                .id(1L)
                .amount(3000L)
                .received(true)
                .receivedDate(LocalDateTime.now())
                .receivedUser(user)
                .sprinkle(sprinkle)
                .build();
        given(sprinkleHistoryService.receiveMoney(sprinkle.getId(), sprinkle.getCreateUser().getId())).willReturn(history);


        Map<String, Object> param = new HashMap<>();
        param.put("token", "aaa");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sprinkle/receive")
                .header("X-USER-ID", 1)
                .header("X-ROOM-ID", "ROOM1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(param))
        ).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
         .andExpect(MockMvcResultMatchers.jsonPath("amount").value(history.getAmount()));

    }

    @Test
    @DisplayName("조회 API")
    void view() throws Exception {

        Sprinkle sprinkle = Sprinkle.builder()
                .id(1L)
                .room(new Room("ROOM1"))
                .createUser(new User(1L))
                .amount(10000L)
                .headCount(7)
                .token("aaa")
                .tokenCreateDate(LocalDateTime.now())
                .build();
        given(sprinkleService.verifySprinkleOwnerForView(sprinkle.getCreateUser().getId(), sprinkle.getToken())).willReturn(sprinkle);

        List<SprinkleHistoryDto> listHistory = new ArrayList<>();
        SprinkleHistoryDto history1 = new SprinkleHistoryDto(3000L,2L);
        SprinkleHistoryDto history2 = new SprinkleHistoryDto(1500L,3L);
        SprinkleHistoryDto history3 = new SprinkleHistoryDto(4500L,4L);

        listHistory.add(history1);
        listHistory.add(history2);
        listHistory.add(history3);

        given(sprinkleHistoryService.getReceivedList(sprinkle.getId())).willReturn(listHistory);


        Map<String, Object> param = new HashMap<>();
        param.put("token", "aaa");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sprinkle")
                .header("X-USER-ID", 1)
                .header("X-ROOM-ID", "ROOM1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", "aaa")
        ).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
         .andExpect(MockMvcResultMatchers.jsonPath("receivedAmount").value(9000L))
         .andExpect(MockMvcResultMatchers.jsonPath("receivedList").isArray());
    }
}