package com.pianorang.money.sprinkle.history;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.pianorang.money.exception.InvalidUserException;
import com.pianorang.money.exception.NoSprinkleMoneyException;
import com.pianorang.money.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pianorang.money.sprinkle.Sprinkle;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SprinkleHistoryService {
	private SprinkleHistoryRepository historyRepository;

	public SprinkleHistoryService(SprinkleHistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	public List<SprinkleHistory> saveHistories(Sprinkle sprinkle) {

		List<SprinkleHistory> list = new ArrayList<>();

		Long totalAmount = sprinkle.getAmount(); //뿌릴금액
		int headCount = sprinkle.getHeadCount(); //인원수

		Random random = new Random();
		
		int limit = 45; //최대 금액 퍼센티지
		
		for (int i = 0; i < headCount; i++) {
			
			SprinkleHistory entity = new SprinkleHistory();
			entity.setSprinkle(sprinkle);
			
			if(i < (headCount-1)) {
				int percentage = random.nextInt(limit) + 1;
				Long amt = totalAmount * percentage / 100;
				totalAmount = totalAmount - amt;
				entity.setAmount(amt);
			}
			else {
				entity.setAmount(totalAmount);
			}

			SprinkleHistory history = historyRepository.save(entity);
			list.add(entity);
		}

		return list;
	}
	
	public Long checkReceivedUser(Long sprinkleId, Long userId) {
		return historyRepository.countBySprinkleIdAndReceivedUserId(sprinkleId, userId);
	}

	public SprinkleHistory receiveMoney(Long sprinkleId, Long userId) {

		Long receivedCount = checkReceivedUser(sprinkleId, userId);
		if (receivedCount > 0){
			throw new InvalidUserException();
		}

		//할당안된 뿌리기 머니 1개 가져오기
		SprinkleHistory verifyMoney = historyRepository.findFirstBySprinkleIdAndReceived(sprinkleId, false);

		if(verifyMoney == null){
			throw new NoSprinkleMoneyException();
		}

		verifyMoney.setReceivedUser(new User(userId));
		verifyMoney.setReceivedDate(LocalDateTime.now());
		verifyMoney.setReceived(true);


		return verifyMoney;
	}

	public List<SprinkleHistoryDto> getReceivedList(Long sprinkleId) {
		return historyRepository.getReceivedList(sprinkleId);
	}
}
