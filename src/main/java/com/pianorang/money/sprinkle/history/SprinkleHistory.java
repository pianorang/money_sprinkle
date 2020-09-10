package com.pianorang.money.sprinkle.history;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.swing.Spring;

import com.pianorang.money.sprinkle.Sprinkle;

@Entity
public class SprinkleHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity = Sprinkle.class, fetch = FetchType.LAZY)
	private Long sprinkleId;
	
	private Long amount;
	
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSprinkleId() {
		return sprinkleId;
	}

	public void setSprinkleId(Long sprinkleId) {
		this.sprinkleId = sprinkleId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
