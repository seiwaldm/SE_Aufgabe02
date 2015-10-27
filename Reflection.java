import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflection {

	StringBuilder stringBuilder = new StringBuilder();

	public void reconstruct(Class c, PrintStream ps) {
		// TODO Auto-generated method stub
		c.getModifiers();
		Method[] methods = c.getDeclaredMethods();
		for (Method m : methods) {
			System.out.println(Modifier.toString(c.getModifiers()) + " " + m.getName()+ "{");

		}
	}

	public void reconstruct(String fullClassname, PrintStream ps) throws ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
