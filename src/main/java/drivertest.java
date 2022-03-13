import com.room3.demos.*;
import com.room3.dao.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
	static Update up = new Update();
	static DummyUser durr = new DummyUser();
	public static void main(String[] args) {
	
		
		Object du=  new DummyUser(1,"user","pass");
//		int id = dao.insert(du);
//		System.out.println(id);
		
		try {
			for(Object o : up.findAll(durr)) {
				System.out.println(o);}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	static SelectById sel= new SelectById();
	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, SecurityException {
		Object du=  new DummyUser();
		
		sel.selectById(du, 6);
		
		
//		
		
		//int id = dao.insert(du);
//		System.out.println(id);
//		Create p = new Create();
//		p.findAllClasses("com.room3.demos");


	}
}
