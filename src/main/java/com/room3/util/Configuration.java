package com.room3.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

// pooled connection Connection con = Configuration.getConnection();
public class Configuration {
	private static BasicDataSource ds = new BasicDataSource();
	static {
		ds.setUrl("jdbc:postgresql://team-3-enterprise.cvtq9j4axrge.us-east-1.rds.amazonaws.com:5432/postgres");
		ds.setUsername("postgres");
		ds.setPassword("postgres");
	}
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
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

}
