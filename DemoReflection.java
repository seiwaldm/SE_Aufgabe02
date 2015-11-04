import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

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
		Class input;
		FileOutputStream output;
		PrintStream ps;

		try {
			input = ArrayList.class;
			output = new FileOutputStream(input.getSimpleName() + "Dummy.java");
			ps = new PrintStream(output);
			Reflection demo = new Reflection();
			demo.reconstruct(input, ps);
			ps.close();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
