package com.pianorang.money.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pianorang.money.user.User;

@Service
public class RoomService {
	
	@Autowired
	RoomRepository roomRepository;
	
	public User verifyUser(String roomId, Long userId) {
		
		return roomRepository.verifyUser(roomId, userId);
	}
}
