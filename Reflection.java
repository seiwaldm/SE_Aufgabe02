import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflection {

	StringBuilder output = new StringBuilder();

	public void reconstruct(Class c, PrintStream ps) {

		// class modifiers
		output.append(Modifier.toString(c.getModifiers()) + " " // TODO class,
																// interface,
																// abstract
																// class...
				+ c.getSimpleName());

		if (c.getSuperclass() != null) {
			output.append(" extends " + c.getSuperclass()); // unschoen...
		}

		// implemented interfaces
		Class[] interfaces = c.getInterfaces();
		if (interfaces != null) {
			output.append(" implements ");
			for (Class i : interfaces) {
				output.append(i.getSimpleName() + ", ");
			}
			output.deleteCharAt(output.length() - 2);
		}
		
		output.append("{");

		// fields
		Field[] fields = c.getDeclaredFields();
		if (fields != null) {
			output.append("\n\n");
			for (Field f : fields) {
				output.append(Modifier.toString(f.getModifiers()) + " " + f.getName() + "\n");
			}
		}

		// constructors
		Constructor[] constructors = c.getConstructors();
		for (Constructor con : constructors) {
			output.append(
					"\n" + Modifier.toString(con.getModifiers()) + " " + c.getSimpleName() + " ");
		}

		// Methods
		Method[] methods = c.getDeclaredMethods();
		for (Method m : methods) {
			output.append("\n" + Modifier.toString(c.getModifiers()) + " " + m.getName() + "(");

		}
		System.out.println(output);
	}

	public void reconstruct(String fullClassname, PrintStream ps) throws ClassNotFoundException {

	}

}
