package com.room3.demos;

import java.util.Objects;

import com.room3.annotations.Column;
import com.room3.annotations.Entity;
import com.room3.annotations.Id;

@Entity(tableName = "DummyUser")
public class DummyUser {
	
	@Id(columnName = "user_id")
	private int user_id;
	@Column(columnName = "username")
	private String username;
	@Column(columnName = "passd")
	private String passd;
	public DummyUser(int user_id, String username, String passd) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.passd = passd;
	}
	public DummyUser(String username, String passd) {
		super();
		this.username = username;
		this.passd = passd;
	}
	
	public DummyUser() {
		super();
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return passd;
	}
	public void setPass(String pass) {
		this.passd = passd;
	}
	@Override
	public int hashCode() {
		return Objects.hash(passd, user_id, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DummyUser other = (DummyUser) obj;
		return Objects.equals(passd, other.passd) && user_id == other.user_id && Objects.equals(username, other.username);
	}
	
	
	
	
}

