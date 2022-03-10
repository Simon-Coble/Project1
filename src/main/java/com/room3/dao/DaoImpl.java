package com.room3.dao;

import java.util.List;

import com.room3.annotations.Entity;
import com.room3.util.Configuration;

public class DaoImpl {

	private Class<?> clazz;
    private Configuration conn;
    private boolean isTransaction;
    private List<Boolean> completes;
	
    
    public boolean insert(Object o) {
    	
    
	Entity table = clazz.getDeclaredAnnotation(Entity.class);
	
	String sql = "insert into" +table.tableName();
	
	
	
	
	return true;
	
    }
	
	
}
