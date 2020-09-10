package com.pianorang.money.sprinkle;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
