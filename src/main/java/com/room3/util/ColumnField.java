package com.room3.util;

import java.lang.reflect.Field;

import com.room3.annotations.Column;

public class ColumnField {

		// this class models the column that will be generated in the database
		// we will use Reflection (using classes from the Java.lang.reflect package) to understand the field
		
		private Field field; // from java.lang.reflect
		
		// constructor
		public ColumnField(Field field) {
			
			// check if it has the annotation we're looking for 
			if (field.getAnnotation(Column.class) == null) { // if it's NOT equal to @Column...
				throw new IllegalStateException("Cannot create ColumnField object! Provided field " + getName() + " is not Annotated");
			}
			
			this.field = field;
			
		}
		
		public String getName() {
			return field.getName();
		}
		
		// return the TYPE of the field that's annotated
		public Class<?> getType() {
			
			return field.getType(); // think about how we could this to our advantage when we (as the ORM framework developers
								    // are crafting a way in which we can  set up a way to determine the RDBMS type for the column
		}

		// getColumnName() --> extract the column name that the user sets for that field
		public String getColumnName() {
			return field.getAnnotation(Column.class).columnName(); // extract the columnName() property that the user sets
		}

	}