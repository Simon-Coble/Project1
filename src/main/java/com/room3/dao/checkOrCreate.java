package com.room3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.room3.annotations.Column;
import com.room3.annotations.Entity;
import com.room3.util.Configuration;
import com.room3.util.MetaModel;

public class checkOrCreate {

	public Object selectAllByValueInColumn(String value, Column column, Entity table) {
		
		String columnName = column.columnName(); 
		String tableName = table.tableName();
		
		Connection conn = Configuration.getConnection();
		
		
		String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = " + value;
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		
		Class<?> clazz = table.getClass();
		MetaModel<?> metamodel = MetaModel.of(clazz);
		
		Object obj = table.getClass();
		
		
		clazz.c
		
		
		if (rs.next()) {
			
		}
		return metamodel;
		
		
	}
	
}
