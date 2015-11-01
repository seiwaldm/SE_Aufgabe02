import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import java.util.Stack;

/**
 * PS Software Engineering WS2015 <br>
 * <br>
 * 
 * This class provides methods to reconstruct the source skeleton of a Java applications .class-file using Java Reflection
 * 
 * @author Markus Seiwald, Kevin Schoergnhofer 
 *
 */
 
public class Reflection {

	StringBuilder output1 = new StringBuilder();
	StringBuilder output2 = new StringBuilder();
	Hashtable<String, String> usedPackages = new Hashtable<>();

	/**
	 * reconstructs the source skeleton of the given class
	 * 
	 * @param c the class which should get reconstructed
	 * @param ps the printstream ... (TODO: fuer was braucht man den ps ueberhaupt?)
	 *
	 */
	public void reconstruct(Class c, PrintStream ps) {

		// package:
		if (c.getPackage() != null)
			output1.append("package " + c.getPackage().getName() + ";\n\n");

		// class modifiers & name
		output2.append(Modifier.toString(c.getModifiers()) + " ");
		if (c.isInterface())
			output2.append("interface ");
		else
			output2.append("class ");
		output2.append(c.getSimpleName() + " ");

		// extended abstract class
		if (Modifier.isAbstract(c.getSuperclass().getModifiers())) {
			output2.append("extends " + c.getSuperclass().getSimpleName() + " ");
			usedPackages(c.getSuperclass());
		}

		// implemented interfaces
		Class[] interfaces = c.getInterfaces();
		if (interfaces.length != 0) {
			output2.append("implements ");
			for (Class i : interfaces) {
				output2.append(i.getSimpleName() + ", ");
				usedPackages(i);
			}
			output2.deleteCharAt(output2.length() - 2);
		}

		output2.append("{\n\n");

		// fields
		Field[] fields = c.getDeclaredFields();
		if (fields.length != 0) {
			for (Field f : fields) {
				output2.append("\t" + Modifier.toString(f.getModifiers()) + " "
						+ f.getType().getSimpleName() + " " + f.getName() + " = "
						+ setValue(f.getType().getSimpleName()) + ";\n");
				usedPackages(f.getType());
			}
		}

		output2.append("\n");

		// constructors
		Constructor[] constructors = c.getConstructors();
		for (Constructor con : constructors) {
			output2.append("\t" + Modifier.toString(con.getModifiers()) + " " + c.getSimpleName());
			Class[] para = con.getParameterTypes();
			printParameters(para);
			output2.append("{\n\t\tSystem.out.println(\"constructor\");\n\t}\n\n");
		}

		output2.append("\n");

		// methods
		Method[] methods = c.getDeclaredMethods();
		for (Method m : methods) {
			output2.append("\t" + Modifier.toString(m.getModifiers()) + " "
					+ m.getReturnType().getSimpleName() + " " + m.getName());
			usedPackages(m.getReturnType());
			Class[] para = m.getParameterTypes();
			printParameters(para);
			Class[] exceptions = m.getExceptionTypes();
			if (exceptions.length != 0) {
				output2.append("throws ");
				for (Class e : exceptions) {
					output2.append(e.getSimpleName() + ", ");
					usedPackages(e);
				}
				output2.deleteCharAt(output2.length() - 2);
			}
			output2.append("{\n\t\tSystem.out.println(\"" + m.getName() + "\");\n\t}\n\n");
			if (m.getReturnType() != void.class) {
				output2.delete(output2.length() - 5, output2.length() - 1);
				output2.append(
						"\t\treturn " + setValue(m.getReturnType().getSimpleName()) + ";\n\t}\n\n");
			}
		}

		output2.append("}");

		// imports:
		Stack<String> imported = new Stack<String>();
		imported.addAll(usedPackages.values());
		while (!imported.isEmpty()) {
			String usedPackage = imported.pop();
			if (!usedPackage.contains(c.getPackage().getName()) && !usedPackage.contains("void")
					&& !usedPackage.contains("java.lang"))
				output1.append("import " + usedPackage + ";\n");
		}

		output1.append("\n");

		// merge outputs:
		String output = output1.toString() + output2.toString();
		System.out.println(output);
	}

	/**
	 * reconstructs the source skeleton of the given class
	 * 
	 * @param fullClassname the class name of the class which should get reconstructed
	 * @param ps the printstream ... (TODO: fuer was braucht man den ps ueberhaupt?)
	 * @throws ClassNotFoundException if the class corresponding to the given class name was not found
	 *
	 */
	public void reconstruct(String fullClassname, PrintStream ps) throws ClassNotFoundException {
		Class c = Class.forName(fullClassname);
		reconstruct(c, ps);
	}

	// helper method to print parameters of constructors and methods:
	private void printParameters(Class[] para) {
		output2.append("(");
		int counter = 0;
		for (Class p : para) {
			output2.append(p.getSimpleName() + " param" + counter + ", ");
			usedPackages(p);
			counter++;
		}
		if (counter != 0)
			output2.delete(output2.length() - 2, output2.length());
		output2.append(") ");
	}

	// helper method to set default value of fields and return value of methods:
	private String setValue(String s) {
		switch (s) {
		case "boolean":
			return "false";
		case "char":
			return "'u0000'";
		case "double":
			return "0.0";
		case "float":
			return "0.0";
		case "int":
			return "0";
		case "long":
			return "0";
		case "short":
			return "0";
		case "byte":
			return "0";
		default:
			return "null";
		}
	}

	// helper method to store packages of used classes:
	private void usedPackages(Class c) {
		String name = c.getSimpleName();
		if (name.charAt(name.length() - 1) == ']')
			name = name.substring(0, name.length() - 2);
		if (name != "boolean" && name != "char" && name != "double" && name != "float"
				&& name != "long" && name != "short" && name != "byte" && name != "int")
			usedPackages.put(name, c.getCanonicalName());
	}
}
