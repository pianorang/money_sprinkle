package com.pianorang.money.room;

import com.pianorang.money.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pianorang.money.user.User;

@Service
public class RoomService {

	private RoomRepository roomRepository;

	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public User verifyUser(String roomId, Long userId) {
		User user = roomRepository.verifyUser(roomId, userId);
		if (user == null){
			throw new InvalidUserException();
		}

		return user;
	}
}
