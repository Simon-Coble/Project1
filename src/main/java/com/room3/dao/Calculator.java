package com.room3.dao;

import com.room3.annotations.Column;
import com.room3.util.ColumnField;

public class Calculator {

	public String getColType(ColumnField column) {
		
		String t = column.getType().getSimpleName();
		
		if (t.equals("String")) {
			return "VARCHAR(50) ";
		} else if (t.equals("int")) {
			return "Integer ";
		}
	
		
		return null;
	}
}
