package com.room3.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.room3.annotations.*;
import org.apache.commons.dbcp2.BasicDataSource;

// pooled connection Connection con = Configuration.getConnection();
public class Configuration {
	private static BasicDataSource ds = new BasicDataSource();
	private Properties prop = new Properties();
	
	public Configuration(String file) {
		try  {
			prop.load(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ds.setUrl(prop.getProperty("url"));
		ds.setUsername(prop.getProperty("username"));
		ds.setPassword(prop.getProperty("password"));
	}
	
	public static Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Configuration() {
		
	}
private List<MetaModel<Class<?>>> metaModelList; // for example, this is a placeholder for metamodels of SuperVillain, SuperPrison, Crime
	
	// constructor - addAnnotatedClass
	public Configuration addAnnotatedClasses(List<Class<?>> annotatedClasses) {
		
		// if the metaModelList is null, geenrate a new LinkedList
		if(metaModelList == null) {
			this.metaModelList = new LinkedList<MetaModel<Class<?>>>();
		}
		
		// iterate through the list of classes passed through
		for (Class clazz : annotatedClasses) {
			
			// call the of() method from the MetaModel class in order to generate a MetaModel obj of each class in the list
			this.metaModelList.add(MetaModel.of(clazz)); // this method procies a metamodel object because it calls the constructor
		}
		return this;
	}
	
	// how do we get all the meta models to process and build tables from ?
	public List<MetaModel<Class<?>>> getMetaModels() {
		// if this list is empty return emptyList(), otherwise return the list
		return (List<MetaModel<Class<?>>>) ((metaModelList == null) ? Collections.emptyList() : metaModelList);
	}
	public static MetaModel<Class<?>> of(Class<?> clazz) {
		// check that the class we're attempting to transpose is annotated with @Entity
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create MetaModel objkect from this class! Provided class "
					+ clazz.getName() + " is not annotated with @Entity");
		}
		// if it IS annotated with @Entity, generate a MetaModel object of it.
		return new MetaModel<Class<?>>(clazz);
	}

}
