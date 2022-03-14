package com.room3.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.ConstructorUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import com.room3.annotations.Entity;
import com.room3.util.ColumnField;
import com.room3.util.Configuration;
import com.room3.util.MetaModel;
import com.room3.util.PrimaryKeyField;

public class DaoImpl {

	



	public <T> int insert(Object o) {
		
		Class<?> clazz = o.getClass();
		MetaModel<T>  mta = new MetaModel(clazz);
		//List<MetaModel<Class<?>>> meta = cfg.getMetaModels();
		PrimaryKeyField pkFields = mta.getPrimaryKey();
		List<ColumnField> columns = mta.getColumns();
		//List<ForeignKey> foreignKeyFields = mta.getForeignKeys();
		int id = 0;
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

//				Annotation[] annotations = f.getDeclaredAnnotations();
//	            for (Annotation annotation : annotations) {
//	                if (annotation instanceof PKey) {
//	                    isSerial = ((PKey) annotation).isSerial();
//	                    // if it is serial, we don't set that number than we need one less ?
//	                }
//	            }
//	            if(isSerial){
//	                totalColumnsQMarks--;
//	            }else {
//				
				
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
			PreparedStatement stmt = con.prepareStatement(sql, 1);
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

			
		
			int i = stmt.executeUpdate();

            if (i > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                while (rs.next()) {
                    return rs.getInt(1);
                }
			
            }
//				System.out.println(idname);
//				int id = rs.getInt(idname);
//				System.out.println(id);
				 // if the insertion is successful, we return here
			
		
			
				
				 // if the insertion is successful, we return here




		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;

	}

	public <T> List<Object> findAll(Object o) throws SQLException, NoSuchMethodException, SecurityException,
			IllegalAccessException, NoSuchFieldException {
		
		Class<?> clazz;
		

		Connection con = Configuration.getConnection();
		List<Object> flubber = new ArrayList<Object>();

		
		clazz = o.getClass();
		MetaModel<T> mta = new MetaModel<T>(clazz);
		PrimaryKeyField pkFields = mta.getPrimaryKey();
		List<ColumnField> columns = mta.getColumns();
		Entity table = clazz.getDeclaredAnnotation(Entity.class);
		StringBuilder far = new StringBuilder();
		Field[] fields = clazz.getDeclaredFields();
		far.append("SELECT * FROM " + table.tableName().toLowerCase());
		String sql = far.toString();
		System.out.println(sql);
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs;
		if ((rs = stmt.executeQuery()) != null) {
			while (rs.next()) {
				Object b = createNewInstance(clazz.getName());
				String idname = pkFields.getName();
				Field field = b.getClass().getDeclaredField(idname);
				field.setAccessible(true);

				for (Field f : fields) {
					String name = f.getName();
					field = null;

					String fieldType = f.getType().getSimpleName();
					try {
						field = b.getClass().getDeclaredField(name);
						field.setAccessible(true);

						switch (fieldType) {

						case "int":
							field.setInt(b, rs.getInt(pkFields.getName()));
							break;
						case "String":

							String uname = rs.getString(f.getName());

							field.set(b, uname);
							break;
						}

					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

//		if ((rs = stmt.executeQuery()) != null) {
//			while (rs.next()) {
//					
//				}
////				clazz.newInstance();
//				
//				flubber.add();
//			}
				}
				flubber.add(b);
			}
		}
		return flubber;
	}
	public <T> Object selectById(Object o, int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, SecurityException {
		
		
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
			System.out.println(sql);
			PreparedStatement stmt = con.prepareStatement(sql);
			
			ResultSet rs;
			if((rs = stmt.executeQuery()) != null) {
				while (rs.next()) {
					Object b =createNewInstance(clazz.getName());
					System.out.println(b);
					String idname= pkFields.getName();
					Field field = b.getClass().getDeclaredField(idname);
					field.setAccessible(true);
					field.setInt(b, id);
					
					for (ColumnField f : columns) {
						String name =f.getName();
						System.out.println(name);
						 field = null;
						 
						 String fieldType = f.getType().getSimpleName();
						try {
				            field = b.getClass().getDeclaredField(name);
				            field.setAccessible(true);
				            System.out.println(field);
				            
				            
				           
								switch (fieldType) {
								
								case "int":
									//field.setInt(b, id);
									break;
								case "String":
									
									String uname = rs.getString(f.getName());
									
									field.set(b, uname);
									break;
								}
								System.out.println(b.toString());
								o=b;
//								case "oolean":
//									
//									break;
//								case "double":
//									
//									break;
//								case "byte":
//									
//									break;
//								case "float":
//									
//									break;
//								case "long":
//									
//									break;
//								case "short":
//									
//									break;
	//
//								}
				            
				            
								    
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
		return o;
		
		
		
		
		
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
		
		
		public <T> void deleteById(Object o, int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, SecurityException {
			
			
			Class<?> clazz = o.getClass();
			MetaModel<T>  mta = new MetaModel(clazz);
			//List<MetaModel<Class<?>>> meta = cfg.getMetaModels();
			PrimaryKeyField pkFields = mta.getPrimaryKey();
			List<ColumnField> columns = mta.getColumns();
			
			try {
				Connection con = Configuration.getConnection();
				
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("delete from ");
				String tableName = clazz.getSimpleName().toLowerCase();
				sqlCommand.append(tableName);
				sqlCommand.append(" where ");
				String idName = pkFields.getColumnName();
				sqlCommand.append(idName);
				sqlCommand.append(" = ");
				sqlCommand.append(id);
				String sql = sqlCommand.toString();
				System.out.println(sql);
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.execute();
		
			}catch(SQLException e){
				e.printStackTrace();
			}
		
	}public void findAllClasses(String packageName) {
		Calculator cal = new Calculator();
		Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
		List<Class<?>> clazzes = reflections.getSubTypesOf(Object.class).stream().collect(Collectors.toList());
		Configuration p = new Configuration();
	
		try (Connection conn = Configuration.getConnection()) {

			p.addAnnotatedClasses(clazzes);

			for (com.room3.util.MetaModel<Class<?>> metamodel : p.getMetaModels()) {

				StringBuilder sb = new StringBuilder();
				String s = "CREATE TABLE IF NOT EXISTS " + metamodel.getSimpleClassName().toLowerCase() + " (";

				sb.append(s);

				PrimaryKeyField pk = metamodel.getPrimaryKey();
				List<ColumnField> columns = metamodel.getColumns();
				// List<ForeignKey> foreignKeyFields = null;

//				try {
//					foreignKeyFields = metamodel.getForeignKeys();
//				} catch (RuntimeException e) {
//					
//				}
				
				sb.append(pk.getColumnName() + " SERIAL PRIMARY KEY");

				for (ColumnField column : columns) {
					String type = cal.getColType(column);
					String here = ", " + column.getColumnName() + " " + type;
					sb.append(here);
				}
				sb.append(")");
				String sql = sb.toString();
				System.out.println(sql);
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}public List<Object> selectAllByValueInColumn(String value, String column, Class o) {
		
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



}
