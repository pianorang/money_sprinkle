package com.pianorang.money.room;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.pianorang.money.user.User;

@Entity
public class Room {
	
	@Id	
	private String id;
	
	@OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
	private List<User> userList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	
	
}