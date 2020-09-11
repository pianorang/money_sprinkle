package com.pianorang.money.sprinkle.history;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.swing.Spring;

import com.pianorang.money.sprinkle.Sprinkle;
import com.pianorang.money.user.User;

@Entity
public class SprinkleHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(targetEntity = Sprinkle.class, fetch = FetchType.LAZY)
	private Sprinkle sprinkle;
	
	private Long amount;	
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User receivedUser;
	
	private LocalDateTime receivedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public Sprinkle getSprinkle() {
		return sprinkle;
	}

	public void setSprinkle(Sprinkle sprinkle) {
		this.sprinkle = sprinkle;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public User getReceivedUser() {
		return receivedUser;
	}

	public void setReceivedUser(User receivedUser) {
		this.receivedUser = receivedUser;
	}

	public LocalDateTime getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDateTime receivedDate) {
		this.receivedDate = receivedDate;
	}

	
	
	
	
	
}
