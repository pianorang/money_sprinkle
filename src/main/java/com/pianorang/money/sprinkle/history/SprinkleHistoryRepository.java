package com.pianorang.money.sprinkle.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pianorang.money.sprinkle.Sprinkle;


public interface SprinkleHistoryRepository extends JpaRepository<SprinkleHistory, Long> {

	@Query("SELECT s FROM SprinkleHistory s WHERE s.sprinkle.id =:sprinkleId AND s.user is null")
	@Transactional(readOnly = true)
	SprinkleHistory findVerifyMoney();
	
}
