package com.pianorang.money.sprinkle.history;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pianorang.money.sprinkle.Sprinkle;

@Service
public class SprinkleHistoryService {

	@Autowired
	SprinkleHistoryRepository historyRepository;
	
	public void saveHistory(Sprinkle sprinkle) {
		
		Long totalAmount = sprinkle.getAmount();
		int headCount = sprinkle.getHeadCount();
		
		Random random = new Random();
		
		int limit = 70; //최대 금액 퍼센티지
		
		for (int i = 0; i < headCount; i++) {
			
			if(i < (headCount-1)) {				
			
				int percentage = random.nextInt(limit) + 1;
				Long amt = totalAmount * percentage / 100;
				totalAmount = totalAmount - amt;
				
				SprinkleHistory entity = new SprinkleHistory();
				entity.setSprinkleId(sprinkle.getId());
				entity.setAmount(amt);				
				
				historyRepository.save(entity);
			}
		}
		
	}
	
}
