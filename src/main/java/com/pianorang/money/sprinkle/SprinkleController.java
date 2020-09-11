package com.pianorang.money.sprinkle;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pianorang.money.room.Room;
import com.pianorang.money.room.RoomService;
import com.pianorang.money.sprinkle.history.SprinkleHistory;
import com.pianorang.money.sprinkle.history.SprinkleHistoryRepository;
import com.pianorang.money.sprinkle.history.SprinkleHistoryService;
import com.pianorang.money.user.User;
import com.pianorang.money.util.Util;


@RestController
@RequestMapping("/api")
public class SprinkleController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SprinkleService sprinkleService;
	
	@Autowired
	SprinkleHistoryService sprinkleHistoryService;
	
	@Autowired
	RoomService roomService;
	
	@PostMapping("sprinkle/send")
	public Map<String, String> sprinkle(@RequestHeader("X-USER-ID") Long userId, @RequestHeader("X-ROOM-ID") String roomId, @RequestBody Sprinkle sprinkle)  {		
		
		Map<String, String> result = new HashMap<String, String>();
		
		//roomId, userId 검증
		User user = roomService.verifyUser(roomId, userId);
		
		if(user != null) {
			String token = Util.generateToken();
			
			sprinkle.setCreateUser(user);
			sprinkle.setRoom(new Room(roomId));
			sprinkle.setToken(token);
			sprinkle.setTokenCreateDate(LocalDateTime.now());
			
			Sprinkle dbSprinkle = sprinkleService.save(sprinkle);		
			
			sprinkleHistoryService.saveHistory(dbSprinkle);
			
			result.put("status", "ok");
			result.put("token", token);
			
		}
		else {
			result.put("status", "no");
			result.put("msg", "파라미터가 유효하지 않습니다.");
		}
		
		return result;
	}
	
	
	@PostMapping("sprinkle/receive")
	public Map<String, String> moneyReceive(@RequestHeader("X-USER-ID") Long userId, @RequestHeader("X-ROOM-ID") String roomId, @RequestBody String token)  {		
		
		Map<String, String> result = new HashMap<String, String>();
		
		//roomId, userId 검증
		User user = roomService.verifyUser(roomId, userId);
		
		if(user != null) {
			Sprinkle sprinkle = sprinkleService.verifySprinkle(roomId, userId, token);
			
			if(sprinkle != null) {
				SprinkleHistory sprinkleHistory = sprinkleHistoryService.getVerifyMoney();
				
				if(sprinkleHistory != null) {
					
					sprinkleHistory.setReceivedUser(new User(userId));
					sprinkleHistory.setReceivedDate(LocalDateTime.now());
					
					result.put("status", "ok");
					result.put("token", token);
				}
			}
		}
		
		
		
		
		
		
		return result;
	}
	
	
	@ExceptionHandler()  
	public Map<String, String> nfeHandler(Exception e){
		
		logger.debug(e.getMessage());
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("status", "no");
		result.put("msg", "파라미터가 유효하지 않습니다.");
		
		return result;
	}  


	
}
