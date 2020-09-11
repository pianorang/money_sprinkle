package com.pianorang.money.sprinkle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SprinkleRepository extends JpaRepository<Sprinkle, Long> {
	@Query("SELECT s FROM Sprinkle s WHERE s.room.id =:roomId AND s.createUser.id <>:userId AND s.token =:token")
    @Transactional(readOnly = true)
    Sprinkle findByToken(@Param("roomId") String roomId, @Param("userId") Long userId, @Param("token") String token);
}
