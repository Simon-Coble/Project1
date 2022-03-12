package com.room3.demos;

import java.util.Objects;

import com.room3.annotations.Column;
import com.room3.annotations.Entity;
import com.room3.annotations.Id;
@Entity(tableName = "OtherDummy")
public class OtherDummy {

	@Id(columnName = "user_id")
	private int user_id;
	@Column(columnName = "username")
	private String username;
	@Column(columnName = "passd")
	private String passd;
	public OtherDummy(int user_id, String username, String passd) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.passd = passd;
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
	public String getPassd() {
		return passd;
	}
	public void setPassd(String passd) {
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
		OtherDummy other = (OtherDummy) obj;
		return Objects.equals(passd, other.passd) && user_id == other.user_id
				&& Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "OtherDummy [user_id=" + user_id + ", username=" + username + ", passd=" + passd + "]";
	}
	
	
}
