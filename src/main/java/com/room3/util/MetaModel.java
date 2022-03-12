package com.room3.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.room3.annotations.Column;
import com.room3.annotations.Entity;
import com.room3.annotations.Id;
import com.room3.annotations.JoinCol;
import com.room3.util.ColumnField;
import com.room3.util.ForeignKey;
import com.room3.util.MetaModel;
import com.room3.util.PrimaryKeyField;

public class MetaModel<T> {

	private Class<?> clazz;
	private PrimaryKeyField primaryKeyField;
	private List<ColumnField> columnFields;
	private List<ForeignKey> foreignKeyFields;

	// create a method in which we pass a class through and generate a meta model OF
	// the class
	public static MetaModel<Class<?>> of(Class<?> clazz) {
		// check that the class we're attempting to transpose is annotated with @Entity
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create MetaModel object from this class! Provided class "
					+ clazz.getName() + " is not annotated with @Entity");
		}
		// if it IS annotated with @Entity, generate a MetaModel object of it.
		return new MetaModel<Class<?>>(clazz);
	}

	// constructor to build a metamodel
	public MetaModel(Class<?> clazz) {
		this.clazz = clazz; // since we've set the class as equal to the original class, we still intel on
							// its fields
		this.columnFields = new LinkedList<ColumnField>();
		this.foreignKeyFields = new LinkedList<ForeignKey>();
	}

	//  
	public List<ColumnField> getColumns() {

		Field[] fields = clazz.getDeclaredFields();

		// check if each field has a @Column annotation
		// if it does, add it to the metamodel's columnField's list
		for (Field field : fields) {

			// the column reference variable will NOT be null if the field is indeed
			// annotated with @Column
			Column column = field.getAnnotation(Column.class);
				
			if (column != null) {
				// if the column is indeed marked with @Colum, instantiate a new ColumnField
				// object with its data
				column.columnName();
				columnFields.add(new ColumnField(field));
				// now that we've transposed the field to a column Field object, we can capture
				// data like Type, columnName, etc...
			}
		}
		if (columnFields.isEmpty()) {
			throw new RuntimeException("No columns found in: " + clazz.getName());
		}
		return columnFields;
	}

	// getPrimaryKey() - return a PrimaryKeyField or throw a runtime exception if we
	// can't find one
	public PrimaryKeyField getPrimaryKey() {

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			Id primaryKey = field.getAnnotation(Id.class);

			if (primaryKey != null) { // we se the primaryKeyField of this MetaModel to the field we find 
				this.primaryKeyField = new PrimaryKeyField(field);
				return new PrimaryKeyField(field); // if there is a PK found, we end the method here and return it
			}
		}

		throw new RuntimeException("Did not find a field annotated with @Id in " + clazz.getName());
	}

	// getForeignKey - returns a list of foreignKeyFields
	public List<ForeignKey> getForeignKeys() {

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			JoinCol foreignKey = field.getAnnotation(JoinCol.class);

			if (foreignKey != null) {
				foreignKeyFields.add(new ForeignKey(field));
			}
		}
		if (foreignKeyFields.isEmpty()) {
			throw new RuntimeException("No foreign keys found in: " + clazz.getName());
		}
		return foreignKeyFields;
	}
	
	public String getSimpleClassName() {
		return clazz.getSimpleName();
	}
	
	public String getClassName() {
		return clazz.getName(); // reutrns  the package of where the class came from as well
	} 

	
}
