import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 * PS Software Engineering WS2015 <br>
 * <br>
 * 
 * This demo class tests and shows the functionality of the Reflection class
 * 
 * @author Markus Seiwald, Kevin Schoergnhofer 
 *
 */

public class Demo {

	/**
	 * tests and shows the functionality of the Reflection class
	 * 
	 * @param args standard parameter
	 *
	 */
	public static void main(String[] args) {
		Reflection demo = new Reflection();
		demo.reconstruct(ArrayList.class, null);
	}

}
