package com.pianorang.money.sprinkle;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.LongStream;

import com.pianorang.money.exception.InvalidParameterException;
import com.pianorang.money.exception.InvalidUserException;
import com.pianorang.money.sprinkle.history.SprinkleHistoryDto;
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

	private SprinkleService sprinkleService;

	private SprinkleHistoryService sprinkleHistoryService;

	private RoomService roomService;

	public SprinkleController(SprinkleService sprinkleService, SprinkleHistoryService sprinkleHistoryService, RoomService roomService) {
		this.sprinkleService = sprinkleService;
		this.sprinkleHistoryService = sprinkleHistoryService;
		this.roomService = roomService;
	}

	@PostMapping("sprinkle/send")
	public Map<String, Object> sprinkleMoney(@RequestHeader("X-USER-ID") Long userId, @RequestHeader("X-ROOM-ID") String roomId, @RequestBody Sprinkle sprinkle)  {

		Map<String, Object> result = new HashMap<String, Object>();
		
		//roomId, userId 검증
			User user = roomService.verifyUser(roomId, userId);

			if(user!= null && sprinkle.getAmount() != null && sprinkle.getAmount() > 0
					&& sprinkle.getHeadCount() > 0 ) {
				String token = Util.generateToken();

				sprinkle.setCreateUser(user);
				sprinkle.setRoom(new Room(roomId));
				sprinkle.setToken(token);
				sprinkle.setTokenCreateDate(LocalDateTime.now());

				sprinkleService.save(sprinkle);

				result.put("token", token);
			
		}
		else {
			throw new InvalidParameterException();
		}
		
		return result;
	}
	
	
	@PostMapping("sprinkle/receive")
	public SprinkleReceiveDto moneyReceive(@RequestHeader("X-USER-ID") Long userId,
											@RequestHeader("X-ROOM-ID") String roomId,
											@RequestBody HashMap<String, Object> param)  {

		String token = "";
		if (param.containsKey("token") && param.get("token") != null){
			token = param.get("token").toString();
		}
		else{
			throw new InvalidParameterException();
		}

		//roomId, userId 검증
		roomService.verifyUser(roomId, userId);
		Sprinkle sprinkle = sprinkleService.verifySprinkleForReceive(roomId, userId, token);

		//뿌리기 머니 받기
		SprinkleHistory sprinkleHistory = sprinkleHistoryService.receiveMoney(sprinkle.getId(), userId);

		return new SprinkleReceiveDto(sprinkleHistory.getAmount());
	}

	@GetMapping("sprinkle")
	public SprinkleDto view(@RequestHeader("X-USER-ID") Long userId,
									@RequestHeader("X-ROOM-ID") String roomId,
									@RequestParam String token)  {

		//뿌린 사람인지 체크
		Sprinkle sprinkle = sprinkleService.verifySprinkleOwnerForView(userId, token);

		//받은 사용자 리스트
		List<SprinkleHistoryDto> history = sprinkleHistoryService.getReceivedList(sprinkle.getId());

		//받아간금액 총액
		Long totalReceivedAmount = history.stream().mapToLong(i-> i.getAmount()).sum();

		return new SprinkleDto(
				sprinkle.getTokenCreateDate(),
				sprinkle.getAmount(),
				totalReceivedAmount,
				history
		);
	}
}
