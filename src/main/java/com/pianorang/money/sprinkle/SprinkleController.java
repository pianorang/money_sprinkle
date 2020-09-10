package com.pianorang.money.sprinkle;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pianorang.money.sprinkle.history.SprinkleHistoryRepository;
import com.pianorang.money.sprinkle.history.SprinkleHistoryService;
import com.pianorang.money.util.Util;

@RestController
@RequestMapping("/api")
public class SprinkleController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SprinkleService sprinkleService;
	
	@Autowired
	SprinkleHistoryService sprinkleHistoryService;
	
	@GetMapping("sprinkle/send")
	public String sprinkle(@RequestHeader("X-USER-ID") Long xUserId, @RequestHeader("X-ROOM-ID") String xRoomId, Sprinkle sprinkle)  {		
		
		//roomId, userId 검증
			
		
		sprinkle.setCreateUserId(xUserId);
		sprinkle.setRoomId(xRoomId);
		
		logger.info(sprinkle.toString());
		
		Sprinkle dbSprinkle = sprinkleService.save(sprinkle);
		
		sprinkleHistoryService.saveHistory(dbSprinkle);
		
		
		
		
		
		String token = Util.generateToken();
		
		
		
		return token;
	}
}
