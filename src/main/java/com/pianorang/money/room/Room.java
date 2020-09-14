package com.pianorang.money.room;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.pianorang.money.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Room {

	public Room(String id) {		
		this.id = id;
	}

	@Id
	private String id;
	
	private String name;
	
	@ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)	
	private List<User> userList;
	
}
