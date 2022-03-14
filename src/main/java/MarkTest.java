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
			Field[] fields = o.getClass().getDeclaredFields();
			System.out.println(o.getClass().getSimpleName());
			for (Field field : fields) {
				field.setAccessible(true);
				System.out.print(field.get(o) + " ");
			}
			System.out.println();
		}
	}
}
