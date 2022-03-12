package com.room3.dao;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
import org.apache.commons.beanutils.ConstructorUtils;

public class SelectById {

	
	public <T> void selectById(Object o, int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		
	
	Class<?> clazz = o.getClass();
	MetaModel<T>  mta = new MetaModel(clazz);
	//List<MetaModel<Class<?>>> meta = cfg.getMetaModels();
	PrimaryKeyField pkFields = mta.getPrimaryKey();
	List<ColumnField> columns = mta.getColumns();
	
	try {
		Connection con = Configuration.getConnection();
		
		StringBuilder sqlCommand = new StringBuilder();
		sqlCommand.append("select * from ");
		String tableName = clazz.getSimpleName().toLowerCase();
		sqlCommand.append(tableName);
		sqlCommand.append(" where ");
		String idName = pkFields.getColumnName();
		sqlCommand.append(idName);
		sqlCommand.append(" = ");
		sqlCommand.append(id);
		String sql = sqlCommand.toString();
		PreparedStatement stmt = con.prepareStatement(sql);
		
		ResultSet rs;
		if((rs = stmt.executeQuery()) != null) {
			while (rs.next()) {
				Object b =createNewInstance(clazz.getName());
				System.out.println(b);
				for (ColumnField f : columns) {
					String name =f.getName();
					System.out.println(name);
					Field field = null;
					 String fieldType = f.getType().getSimpleName();
					try {
			            field = b.getClass().getDeclaredField(name);
			            System.out.println(field);
			            field.setAccessible(true);
			            
			           
							switch (fieldType) {
							case "int":
								field.setInt(b, id);
								break;
							case "String":
								field.set(b, name);
								break;
							}
							System.out.println(b.toString());
//							case "oolean":
//								
//								break;
//							case "double":
//								
//								break;
//							case "byte":
//								
//								break;
//							case "float":
//								
//								break;
//							case "long":
//								
//								break;
//							case "short":
//								
//								break;
//
//							}
			            
			            
							    
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			}
		}
		
		}
		
		
	
	
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
}
	private Object createNewInstance(String clazzName) {
		  Class<?> beanClass = null;
		  Object beanInstance = null;
		  try {
		    beanClass = getClass().getClassLoader().loadClass(clazzName);
		    beanInstance = ConstructorUtils.invokeConstructor(beanClass, null);
		  } catch (Exception e) {
		    System.out.println("Error during creating class" + clazzName);
		  }
		  return beanInstance;
		}
	
	
}
