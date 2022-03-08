package com.room3;

import java.sql.Connection;
import java.sql.SQLException;

import com.room3.util.Configuration;

public class Driver {

	public static void main(String[] args) throws SQLException {
		Connection con = Configuration.getConnection();

	}

}
