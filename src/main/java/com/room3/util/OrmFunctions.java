package com.room3.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrmFunctions {
	Connection conn = Configuration.getConnection();
	public boolean transaction;
	private List<Boolean> finish;
	
	public void begin() {
		transaction = true;
		finish = new ArrayList<>();
	}
	public void commit() throws Exception{
		if (finish.contains(false)) {
            throw new Exception();
        } else {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	public void rollback() {
		try {
			conn.rollback();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void close() {
	      try {
	            if (conn != null) {
	                conn.close();
	            }
	            transaction = false;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
}
