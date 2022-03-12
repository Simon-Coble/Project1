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
	

		Object du=  new DummyUser(2,"user","pass");
		int id = dao.insert(du);
		System.out.println(id);

		Create p = new Create();
		p.findAllClasses("com.room3.demos");
		
	}

}
