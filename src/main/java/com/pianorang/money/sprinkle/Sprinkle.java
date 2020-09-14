package com.pianorang.money.sprinkle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.pianorang.money.room.Room;
import com.pianorang.money.sprinkle.history.SprinkleHistory;
import com.pianorang.money.user.User;
import lombok.*;

@Entity
@Getter @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sprinkle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
	private Room room;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User createUser;
	
	private Long amount;
	
	private int headCount;
	
	private String token;
	
	private LocalDateTime tokenCreateDate;

	@OneToMany(targetEntity = SprinkleHistory.class, fetch = FetchType.LAZY, mappedBy = "sprinkle")
	private List<SprinkleHistory> receiveList = new ArrayList<>();

}
