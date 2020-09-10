package com.pianorang.money.sprinkle;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sprinkle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String roomId;
	
	private Long createUserId;
	
	private Long amount;
	
	private int headCount;
	
	private String token;
	
	private LocalDateTime expirationDate;

	public Long getId() {
		return id;
	}
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
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

	@Override
	public String toString() {
		return "Sprinkle [id=" + id + ", roomId=" + roomId + ", createUserId=" + createUserId + ", amount=" + amount
				+ ", headCount=" + headCount + ", token=" + token + ", expirationDate=" + expirationDate
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
