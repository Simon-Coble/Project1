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
			System.out.println(sql);

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
									field.set(b, rs.getString(columnfield.getName()));
									
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
