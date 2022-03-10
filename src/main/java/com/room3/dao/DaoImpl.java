package com.room3.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.room3.util.ColumnField;
import com.room3.util.Configuration;
import com.room3.util.ForeignKey;
import com.room3.util.MetaModel;
import com.room3.util.PrimaryKeyField;


public class DaoImpl {

	private Class<?> clazz;
    private Configuration conn;
    Configuration cfg = new Configuration();
	
    List<MetaModel<Class<?>>>  meta = cfg.getMetaModels();
    List<PrimaryKeyField> pkFields = (List<PrimaryKeyField>) ((MetaModel<Class<?>>) meta).getPrimaryKey();
    //List<ColumnField> columns = ((MetaModel<Class<?>>) meta).getColumns();
	List<ForeignKey> foreignKeyFields = ((MetaModel<Class<?>>) meta).getForeignKeys();
    String[] columns = { "tom","bob","loser","rabbit"};
    
    public int insert(Class<?> clazz) {
    	try {
			Connection con = Configuration.getConnection();
			
			 StringBuilder insertCommand = new StringBuilder();
		        int totalColumns = columns.length;
		        int totalColumnsQMarks = columns.length;
		         String tableName = clazz.getSimpleName().toLowerCase();
		        insertCommand.append("insert into "+tableName); 
		        for(String f: columns) {
		        	String columnName = f;//.getName();  
		        	
		        	
		        	 	insertCommand.append(columnName);
		                insertCommand.append(totalColumns > 1 ? ", " : ") values(?");
		            
		            totalColumns--;
		        }
		        	
		        	
		        for(int i = 1; i < totalColumnsQMarks; i++){
		            insertCommand.append(",?");
		        }
		       insertCommand.append(");");
		        
		    
		        
		        
		        
		        
		        
		        
		        
		        
		        
				
				
					
					
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setDouble(1, a.getBalance()); // how do we extract the balance from the Account Obj that's being passed?
					stmt.setInt(2, a.getAccOwner());
					stmt.setBoolean(3, a.isActive());
					
					ResultSet rs;
					
					if ((rs = stmt.executeQuery()) != null) {

						rs.next();			
						int id = rs.getInt("id");
						return id; // if the insertion is successful, we return here
					}
				
				
			

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	
	
	return -1;
	
    }
	
	
}
