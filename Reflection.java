import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflection {

	StringBuilder output = new StringBuilder();

	public void reconstruct(Class c, PrintStream ps) {

		output.delete(0, output.length());

		// package:
		output.append("package " + c.getPackage().getName() + ";\n\n");

		// TODO imports:
		// ??????? -> get lost at compile-time

		// class modifiers & name
		output.append(Modifier.toString(c.getModifiers()) + " ");
		if (c.isInterface() == true)
			output.append("interface ");
		else
			output.append("class ");
		output.append(c.getSimpleName() + " ");

		// extended abstract class
		if (c.getSuperclass() != null) {
			if (Modifier.isAbstract(c.getSuperclass().getModifiers())) {
				output.append("extends " + c.getSuperclass().getSimpleName() + " ");
			}
		}

		// implemented interfaces
		Class[] interfaces = c.getInterfaces();
		if (interfaces.length != 0) {
			output.append("implements ");
			for (Class i : interfaces) {
				output.append(i.getSimpleName() + ", ");
			}
			output.deleteCharAt(output.length() - 2);
		}

		output.append("{\n\n");

		// fields
		Field[] fields = c.getDeclaredFields();
		if (fields != null) {
			for (Field f : fields) {
				// TODO schaut anders aus, als in der Vorlage... :(
				output.append("\t" + Modifier.toString(f.getModifiers()) + " " + f.getType() + " "
						+ f.getName() + "\n");
			}
		}

		output.append("\n");

		// constructors
		Constructor[] constructors = c.getConstructors();
		for (Constructor con : constructors) {
			output.append("\t" + Modifier.toString(con.getModifiers()) + " " + c.getSimpleName());
			Class[] para = con.getParameterTypes();
			printParameters(para);
		}

		output.append("\n");

		// methods
		Method[] methods = c.getDeclaredMethods();
		for (Method m : methods) {
			output.append("\t" + Modifier.toString(c.getModifiers()) + " " + m.getReturnType() + " "
					+ m.getName()); // TODO some return-types fuck around...
			Class[] para = m.getParameterTypes();
			printParameters(para);

		}

		output.append("\n}");

		System.out.println(output);
	}

	public void reconstruct(String fullClassname, PrintStream ps) throws ClassNotFoundException {

	}

	// helper method to print parameters of constructors and methods:
	private void printParameters(Class[] para) {
		output.append("(");
		int counter = 0;
		for (Class p : para) {
			output.append(p.getSimpleName() + " param" + counter + ", ");
			counter++;
		}
		if (counter != 0)
			output.delete(output.length() - 2, output.length());
		output.append(")\n");
	}

}
