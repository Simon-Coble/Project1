package com.room3.util;

import java.sql.Connection;
import java.sql.SQLException;

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
	
	private Configuration() {}
}
