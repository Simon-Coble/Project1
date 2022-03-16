package com.room3.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;

import com.room3.annotations.Column;
import com.room3.annotations.Entity;
import com.room3.util.ColumnField;
import com.room3.util.Configuration;
import com.room3.util.MetaModel;
import com.room3.util.PrimaryKeyField;

public class CheckOrCreate {

	public List<Object> selectAllByValueInColumn(String value, String column, Class o) {
		
		MetaModel<Class> mta = new MetaModel(o);
		PrimaryKeyField pkField = mta.getPrimaryKey();
		List<ColumnField> columns = mta.getColumns();
		String idname = pkField.getName();
		List<Object> stuff = new ArrayList<Object>();
		try (Connection con = Configuration.getConnection()) {

			String sql = "SELECT * FROM " + o.getSimpleName().toLowerCase() + " WHERE " + column + " = " + "'" + value + "'";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Object b = createNewInstance(o.getName());
				Field[] fields = b.getClass().getDeclaredFields();
				
				for (Field field : fields) {
				field.setAccessible(true);
					try {
						if (field.getName().equals(pkField.getName())) {
							field.set(b, rs.getInt(pkField.getColumnName()));
							int pg = rs.getInt(pkField.getColumnName());

						} else {
							for (ColumnField columnfield : columns) {
								if (field.getName().equals(columnfield.getName())) {
								
									if (field.getType().getSimpleName().equals("String")) {
									field.set(b, rs.getString(columnfield.getName()));
									} else if (field.getType().getSimpleName().equals("int")) {
										field.set(b, rs.getInt(columnfield.getName()));
									}
								}
							}
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						System.out.println("log this 1");
					}
				} stuff.add(b);
				
			}
		} catch (SQLException e) {
			System.out.println("log this");
		}
		
		return stuff;
	}
	
	public Object updateSingle(Object o) {
		
		MetaModel<Class> mta = new MetaModel(o.getClass());
		PrimaryKeyField pkField = mta.getPrimaryKey();
		List<ColumnField> columns = mta.getColumns();
	
		String update = "UPDATE " + o.getClass().getSimpleName().toLowerCase() + " SET ";
		StringBuilder sb = new StringBuilder();
		sb.append(update);
		String id = "" , columnValue; 
		try (Connection con = Configuration.getConnection()) {
			
			for (Field field: o.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				
				try {
					if (field.getName().equals(pkField.getName())) {
						id = " WHERE " + pkField.getColumnName() + " = " + field.get(o);
						System.out.println(id);

					} else {
						for (int i = 0; i < columns.size(); i++ ) {
							
							if (field.getName().equals(columns.get(i).getName())) {
								if (i < columns.size() - 1) {
								columnValue = columns.get(i).getColumnName() + " = '" + field.get(o) + "', ";
								sb.append(columnValue);
								} else {
									columnValue = columns.get(i).getColumnName() + " = '" + field.get(o) + "'";
									sb.append(columnValue);
								}
							}
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("log this 1");
				}
			} sb.append(id + " RETURNING *");
			PreparedStatement stmt = con.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {

				Object b = createNewInstance(o.getClass().getName());
				Field[] fields = b.getClass().getDeclaredFields();
				
				for (Field field : fields) {
				field.setAccessible(true);
					try {
						if (field.getName().equals(pkField.getName())) {
							field.set(b, rs.getInt(pkField.getColumnName()));
							int pg = rs.getInt(pkField.getColumnName());

						} else {
							for (ColumnField columnfield : columns) {
								if (field.getName().equals(columnfield.getName())) {
									field.set(b, rs.getString(columnfield.getName()));
									
								}
							}
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						System.out.println("log this 1");
					}
				} return b;
				
			}
			
			
			System.out.println(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	

	private Object createNewInstance(String clazzName) {

		try {
			Class<?> beanClass = getClass().getClassLoader().loadClass(clazzName);
			Object beanInstance = ConstructorUtils.invokeConstructor(beanClass, null);
			return beanInstance;
		} catch (Exception e) {
			System.out.println("Error during creating class" + clazzName);
		}
		return null;
	}

}
