package com.room3.dao;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.room3.util.ColumnField;
import com.room3.util.Configuration;
import com.room3.util.ForeignKey;
import com.room3.util.MetaModel;
import com.room3.util.PrimaryKeyField;
public class SelectById {

	
	public <T> void selectById(Object o, int id) {
		
	
	Class<?> clazz = o.getClass();
	MetaModel<T>  mta = new MetaModel(clazz);
	//List<MetaModel<Class<?>>> meta = cfg.getMetaModels();
	PrimaryKeyField pkFields = mta.getPrimaryKey();
	List<ColumnField> columns = mta.getColumns();
	
	try {
		Connection con = Configuration.getConnection();
		
		
	
	
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
