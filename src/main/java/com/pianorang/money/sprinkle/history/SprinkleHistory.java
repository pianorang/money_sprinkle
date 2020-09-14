package com.pianorang.money.sprinkle.history;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.swing.Spring;

import com.pianorang.money.sprinkle.Sprinkle;
import com.pianorang.money.user.User;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Getter @Setter
@ToString
@Builder
@AllArgsConstructor
public class SprinkleHistory {
	public SprinkleHistory() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(targetEntity = Sprinkle.class, fetch = FetchType.LAZY)
	private Sprinkle sprinkle;
	
	private Long amount;

	private boolean received;

	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User receivedUser;
	
	private LocalDateTime receivedDate;
	
}
