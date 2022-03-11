package com.room3.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;

import com.room3.annotations.Column;
import com.room3.annotations.Entity;
import com.room3.annotations.Id;
import com.room3.util.Configuration;

public class Update {
	private Class<?> clazz;
    private boolean isTransaction;
    private List<Boolean> completes;
    Field[] fields = clazz.getDeclaredFields();
    Connection con = Configuration.getConnection();
    Entity table = clazz.getDeclaredAnnotation(Entity.class);
    
    public boolean update(Object o) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("UPDATE " + table.tableName() + " SET ");
    	for (Field field :fields)
    	{
    		sql.append(clazz.getDeclaredAnnotation(Column.class) + " = " + field.getName() + " ");
    	}
    	sql.append( "WHERE id = " + clazz.getDeclaredAnnotation(Id.class));
    	
    	return true;
    }
    
    public boolean delete(Object o) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("DELETE FROM " + table.tableName() + " WHERE id = " + clazz.getDeclaredAnnotation(Id.class));
    	return true;
    }
}
