import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * PS Software Engineering WS2015 <br>
 * <br>
 * 
 * This demo class tests and shows the functionality of the Reflection class
 * 
 * @author Markus Seiwald, Kevin Schoergnhofer
 */

public class DemoReflection {

	/**
	 * tests and shows the functionality of the Reflection class
	 * 
	 * @param args
	 *            standard parameter
	 */
	public static void main(String[] args) {
		String className = "RPN";
		Class input;
		FileOutputStream output;
		PrintStream ps;

		try {
			input = Class.forName(className);
			output = new FileOutputStream(input.getSimpleName() + "Dummy.java");
			ps = new PrintStream(output);
			Reflection demo = new Reflection();
			demo.reconstruct(className, ps);
			ps.close();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
