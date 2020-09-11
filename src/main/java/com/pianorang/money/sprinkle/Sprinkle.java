package com.pianorang.money.sprinkle;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.pianorang.money.room.Room;
import com.pianorang.money.user.User;

@Entity
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

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	

	public LocalDateTime getTokenCreateDate() {
		return tokenCreateDate;
	}

	public void setTokenCreateDate(LocalDateTime tokenCreateDate) {
		this.tokenCreateDate = tokenCreateDate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	
	
	
	
	
}
