package com.pianorang.money.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pianorang.money.user.User;

public interface RoomRepository extends JpaRepository<Room, String> {
	@Query("SELECT u FROM Room r INNER JOIN r.userList u WHERE r.id =:roomId AND u.id =:userId ")
    @Transactional(readOnly = true)
    User verifyUser(@Param("roomId") String roomId, @Param("userId") Long userId);
}
