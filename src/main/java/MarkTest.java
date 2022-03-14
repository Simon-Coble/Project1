import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.room3.dao.CheckOrCreate;
import com.room3.dao.SelectById;
import com.room3.demos.DummyUser;

public class MarkTest {
	static SelectById sel = new SelectById();

	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException, NoSuchFieldException, SecurityException {

		CheckOrCreate lo = new CheckOrCreate();
		List<Object> op = new ArrayList<Object>();
		op = lo.selectAllByValueInColumn("chris", "username", DummyUser.class);

		for (Object o : op) {
			
			for (Field field : o.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				System.out.print(field.get(o) + " ");
			}
			System.out.println();
		}
		
		DummyUser kol = new DummyUser(1, "fgdfgd", "dgfgdg");
		kol = (DummyUser) lo.updateSingle(kol);
		//kol = (DummyUser) lo.updateSingle(kol);
		System.out.println(kol.getPass());
	}
}
