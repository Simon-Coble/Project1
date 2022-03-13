package com.room3;

import java.sql.SQLException;

import com.room3.dao.DaoImpl;
import com.room3.dao.Update;
import com.room3.demos.DummyUser;

public class SimonDriver {
	static DaoImpl dao = new DaoImpl();
	static Update up = new Update();
	static DummyUser durr = new DummyUser();
	public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
		
		try {
			for(Object o : up.findAll(durr)) {
				System.out.println(o);}
		} catch (NoSuchMethodException | SecurityException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
