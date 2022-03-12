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

public class DaoImpl {

	private Configuration conn;
	Configuration cfg = new Configuration();


	public <T> int insert(Object o) {
		
		Class<?> clazz = o.getClass();
		MetaModel<T>  mta = new MetaModel(clazz);
		//List<MetaModel<Class<?>>> meta = cfg.getMetaModels();
		PrimaryKeyField pkFields = mta.getPrimaryKey();
		List<ColumnField> columns = mta.getColumns();
		//List<ForeignKey> foreignKeyFields = mta.getForeignKeys();

		try {
			Connection con = Configuration.getConnection();

			StringBuilder insertCommand = new StringBuilder();
			int totalColumns = columns.size();
			int totalColumnsQMarks = columns.size();
			String tableName = clazz.getSimpleName().toLowerCase();
			insertCommand.append("insert into " + tableName);
			insertCommand.append(" (");
			for (ColumnField f : columns) {
				String columnName = f.getName();

				insertCommand.append(columnName);
				insertCommand.append(totalColumns > 1 ? ", " : ") values(?");

				totalColumns--;
			}

			for (int i = 1; i < totalColumnsQMarks; i++) {
				insertCommand.append(",?");
			}
			insertCommand.append(");");
			String sql = insertCommand.toString();
			System.out.println(sql);
			PreparedStatement stmt = con.prepareStatement(sql);
			int index = 1;

			for (ColumnField f : columns) {
				String name =f.getName();
				System.out.println(name);
				Field field = null;
		        try {
		            field = o.getClass().getDeclaredField(name);
		            System.out.println(field);
		            field.setAccessible(true);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        assert field != null;
				String fieldType = f.getType().getSimpleName();

				
				try {
					switch (fieldType) {
					case "int":
						stmt.setInt(index, (int) field.get(o));
						index++;
						break;
					case "String":
						stmt.setString(index, (String) field.get(o));
						index++;
						break;
					case "boolean":
						stmt.setBoolean(index, (boolean) field.get(o));
						index++;
						break;
					case "double":
						stmt.setDouble(index, (double) field.get(o));
						index++;
						break;
					case "byte":
						stmt.setByte(index, (byte) field.get(o));
						index++;
						break;
					case "float":
						stmt.setFloat(index, (float) field.get(o));
						index++;
						break;
					case "long":
						stmt.setLong(index, (long) field.get(o));
						index++;
						break;
					case "short":
						stmt.setShort(index, (short) field.get(o));
						index++;
						break;

					}

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
			stmt.executeUpdate();
//			ResultSet rs;
//			
//			if ((rs = stmt.executeQuery()) != null) {
//
//				rs.next();			
//				int id = rs.getInt("id");
//				return id; // if the insertion is successful, we return here
//			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;

	}

}
