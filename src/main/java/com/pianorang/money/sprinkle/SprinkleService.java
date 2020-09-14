package com.pianorang.money.sprinkle;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import com.pianorang.money.exception.ExpiredTokenException;
import com.pianorang.money.exception.InvalidTokenException;
import com.pianorang.money.sprinkle.history.SprinkleHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.pianorang.money.sprinkle.history.SprinkleHistory;
import com.pianorang.money.sprinkle.history.SprinkleHistoryRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SprinkleService {
	private SprinkleRepository sprinkleRepository;

	private SprinkleHistoryService sprinkleHistoryService;

	public SprinkleService(SprinkleRepository sprinkleRepository, SprinkleHistoryService sprinkleHistoryService) {
		this.sprinkleRepository = sprinkleRepository;
		this.sprinkleHistoryService = sprinkleHistoryService;
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Sprinkle save(Sprinkle sprinkle) {
		Sprinkle dbSprinkle = sprinkleRepository.save(sprinkle);
		sprinkleHistoryService.saveHistories(dbSprinkle);

		return dbSprinkle;
	}
	
	public Sprinkle verifySprinkleForReceive(String roomId, Long userId, String token) {

		Sprinkle sprinkle = sprinkleRepository.findByRoomIdAndCreateUserIdNotAndToken(roomId, userId, token);
		if (sprinkle != null){
			//토큰 유효시간 체크: 10분
			LocalDateTime expiredTime = sprinkle.getTokenCreateDate().plusMinutes(10);

			if(expiredTime.isBefore(LocalDateTime.now())) {
				throw new ExpiredTokenException();
			}
		}
		else{
			throw new InvalidTokenException();
		}

		return sprinkle;
	}

	public Sprinkle verifySprinkleOwnerForView(Long userId, String token) {

		Sprinkle sprinkle = sprinkleRepository.findByCreateUserIdAndToken(userId, token);

		if (sprinkle != null){
			//토큰 유효시간 체크: 7일
			LocalDateTime expiredTime = sprinkle.getTokenCreateDate().plusDays(7);

			if(expiredTime.isBefore(LocalDateTime.now())) {
				throw new ExpiredTokenException();
			}
		}
		else{
			throw new InvalidTokenException();
		}

		return sprinkle;

	}

}
