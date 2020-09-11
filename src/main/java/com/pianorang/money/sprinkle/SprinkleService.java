package com.pianorang.money.sprinkle;

import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.pianorang.money.sprinkle.history.SprinkleHistory;
import com.pianorang.money.sprinkle.history.SprinkleHistoryRepository;

@Service
public class SprinkleService {
	
	@Autowired
	SprinkleRepository sprinkleRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Sprinkle save(Sprinkle sprinkle) {
		
		return sprinkleRepository.save(sprinkle);
	}
	
	public Sprinkle verifySprinkle(String roomId, Long userId,  String token) {
		
		Sprinkle sprinkle = sprinkleRepository.findByToken(roomId, userId, token);		
		
		//토큰 유효시간 체크: 10분
		LocalDateTime expiredTime = sprinkle.getTokenCreateDate().plusMinutes(10);
		
		if(expiredTime.isBefore(LocalDateTime.now())) {
			return sprinkle;
		}
		else {
			return null;	
		}
		 
	}
	
	 
}
