package com.room3.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;

import com.room3.annotations.Entity;
import com.room3.util.ColumnField;
import com.room3.util.Configuration;
import com.room3.util.MetaModel;
import com.room3.util.PrimaryKeyField;

public class Update<T> {
	private Class<?> clazz;
	private boolean isTransaction;
	private List<Boolean> completes;

	Connection con = Configuration.getConnection();
	public List<Object> flubber = new ArrayList<Object>();

//
//
//	public boolean update(Object o) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("UPDATE " + table.tableName() + " SET ");
//		for (Field field : fields) {
//			sql.append(clazz.getDeclaredAnnotation(Column.class) + " = " + field.getName() + " ");
//		}
//		sql.append("WHERE id = " + clazz.getDeclaredAnnotation(Id.class));
//
//		return true;
//	}
//
//	public boolean delete(Object o) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("DELETE FROM " + table.tableName() + " WHERE id = " + clazz.getDeclaredAnnotation(Id.class));
//		return true;
//	}

	public List<Object> findAll(Object o) throws SQLException, NoSuchMethodException, SecurityException,
			IllegalAccessException, NoSuchFieldException {
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

	private void foreach() {
		// TODO Auto-generated method stub

	}

	public Object getGetter(Object o) {
		Method[] methods = o.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (isGetter(method)) {
				try {
					return method.invoke(o);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return methods;
	}

	private static boolean isGetter(Method method) {
		// identify get methods
		if ((method.getName().startsWith("get") || method.getName().startsWith("is")) && method.getParameterCount() == 0
				&& !method.getReturnType().equals(void.class)) {
			return true;
		}
		return false;
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
