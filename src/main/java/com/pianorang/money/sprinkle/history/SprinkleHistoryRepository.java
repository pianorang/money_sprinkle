package com.pianorang.money.sprinkle.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pianorang.money.sprinkle.Sprinkle;

import java.util.List;


public interface SprinkleHistoryRepository extends JpaRepository<SprinkleHistory, Long> {

	@Query("SELECT s FROM SprinkleHistory s WHERE s.sprinkle.id =:sprinkleId AND s.receivedUser is null")
	@Transactional(readOnly = true)
	SprinkleHistory findVerifyMoney(@Param("sprinkleId") Long sprinkleId);

	@Transactional(readOnly = true)
	SprinkleHistory findFirstBySprinkleIdAndReceived(Long sprinkleId, boolean received);

	@Transactional(readOnly = true)
	Long countBySprinkleIdAndReceivedUserId(Long sprinkleId, Long userId);

	@Query("SELECT new com.pianorang.money.sprinkle.history.SprinkleHistoryDto(s.amount, s.receivedUser.id) FROM SprinkleHistory s WHERE s.sprinkle.id =:sprinkleId AND s.received=true")
	@Transactional(readOnly = true)
	List<SprinkleHistoryDto> getReceivedList(@Param("sprinkleId") Long sprinkleId);
	
}
