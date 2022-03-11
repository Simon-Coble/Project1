package com.room3.dao;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import com.room3.util.ColumnField;
import com.room3.util.ForeignKey;
import com.room3.util.MetaModel;
import com.room3.util.PrimaryKeyField;
import com.room3.util.Configuration;

@SuppressWarnings("deprecation")

public class Create {


	public List<Class<?>> findAllClasses(String packageName) {
		Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
		List<Class<?>> clazzes = reflections.getSubTypesOf(Object.class).stream().collect(Collectors.toList());

		Configuration p = new Configuration();
		try (Connection conn = Configuration.getConnection()) {

			p.addAnnotatedClasses(clazzes);

			for (com.room3.util.MetaModel<Class<?>>  metamodel : p.getMetaModels()) {

				System.out.printf("Printing MetaModel for class: %s\n", metamodel.getClass());
				
				
				
				PrimaryKeyField pk = metamodel.getPrimaryKey();
				List<ColumnField> columns = metamodel.getColumns();
				List<ForeignKey> foreignKeyFields = null;

				try {
					foreignKeyFields = metamodel.getForeignKeys();
				} catch (RuntimeException e) {
					// System.out.println("no fk found");
				}

				System.out.printf(
						"\t Found a primary key field named %s, of type %s, which maps to the column with name: %s\n",
						pk.getName(), pk.getType(), pk.getColumnName());

				for (ColumnField column : columns) {
					System.out.printf(
							"\t Found a column field named %s, of type %s, which maps to the column with name: %s\n",
							column.getName(), column.getType().getSimpleName(), column.getColumnName());
				}
				if (foreignKeyFields == null) {

				} else {
					for (ForeignKey foreignKey : foreignKeyFields) {
						System.out.printf(
								"\t Found a foreign key field named %s, of type %s, which maps to the column with name: %s\n",
								foreignKey.getName(), foreignKey.getType().getSimpleName(), foreignKey.getColumnName());
					}
					System.out.println("");
				}
			}

		}

	}

}
