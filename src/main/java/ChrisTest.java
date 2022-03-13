import com.room3.demos.*;
import com.room3.dao.*;
import java.lang.reflect.InvocationTargetException;

import com.room3.dao.DaoImpl;
import com.room3.dao.SelectById;
import com.room3.dao.Update;
import com.room3.demos.DummyUser;
public class ChrisTest {
  
//  int id = dao.insert(du);
//  System.out.println(id);

  
public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, SecurityException {
	SelectById sel= new SelectById();
	DaoImpl dao = new DaoImpl();
    Update up = new Update();
    DummyUser durr = new DummyUser();
    Object du=  new DummyUser("user","pass");
	



//

  int id = dao.insert(du);
  System.out.println(id);
//  Create p = new Create();
//  p.findAllClasses("com.room3.demos");
}
}
