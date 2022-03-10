
public class drivertest {
	static String[] columns = { "tom","bob","loser","rabbit"};
	public static void main(String[] args) {
		StringBuilder insertCommand = new StringBuilder();
        int totalColumns = columns.length;
        int totalColumnsQMarks = columns.length;
         //String tableName = clazz.getSimpleName().toLowerCase();
        insertCommand.append("insert into table ");//+tableName); 
        insertCommand.append("(");
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
	System.out.println(insertCommand);
	}

}
