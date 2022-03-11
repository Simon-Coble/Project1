package com.room3.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.room3.annotations.*;
import org.apache.commons.dbcp2.BasicDataSource;

// pooled connection Connection con = Configuration.getConnection();
public class Configuration {
	private static BasicDataSource ds = new BasicDataSource();
	static {
		ds.setUrl("jdbc:postgresql://team-3-enterprise.cvtq9j4axrge.us-east-1.rds.amazonaws.com:5432/postgres");
		ds.setUsername("postgres");
		ds.setPassword("postgres");
	}
	public static Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Configuration() {}
	
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
