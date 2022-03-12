import com.room3.demos.*;
import com.room3.dao.*;
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
public class drivertest {
	static DaoImpl dao = new DaoImpl();
	public static void main(String[] args) {
	
		
		Object du=  new DummyUser(5,"user","pass");
		Object d2=  new DummyUser(6,"ueer","prass");
		
		//int id = dao.insert(du);
		int id2 = dao.insert(d2);
		System.out.println(id2);
		
		
		
	}

}
