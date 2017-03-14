package testDrives;

//Imports.

// JUnit testing imports.
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * Test runner for the test class TestPortsPipesInterModuleCommTest.
 * @author christopher
 *
 */

public class TestPortsPipesInterModuleCommRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestPortsPipesInterModuleCommTest.class);
		
		if (result.wasSuccessful()) {
			System.out.println("[TEST]\t\t\tSUCCESS");
		}
	}

}
