package com.room3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.room3.annotations.Column;
import com.room3.annotations.Entity;
import com.room3.util.Configuration;
import com.room3.util.MetaModel;

public class checkOrCreate {

	public Object selectAllByValueInColumn(String value, String column, Object o) {
				
		Class<?> demo = o.getClass();
		
		
		String tableName = o.getClass().getSimpleName().toLowerCase();
		
		try(Connection conn = Configuration.getConnection()) {
			
			
			String sql = "SELECT * FROM " + tableName + " WHERE " + column + " = " + value;
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
			}
			
			
		} catch (SQLException e) {
			
		}
		
	
		
		
		return null;
		
		
		
	}
	
}
