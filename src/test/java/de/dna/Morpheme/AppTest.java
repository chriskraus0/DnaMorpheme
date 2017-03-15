package de.dna.Morpheme;

// Imports.

// Project-specific imports.
import testDrives.CoreControllerExternalProgrammesTest;
import testDrives.FirstExternalCommandTest;
import testDrives.TestPortsPipesInterModuleCommTest;

// JUnit imports.
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
    	TestSuite suite = new TestSuite (AppTest.class);
    	
        /*return new TestSuite( 
        		AppTest.class, 
        		CoreControllerExternalProgrammesTest.class, 
        		FirstExternalCommandTest.class, 
        		TestPortsPipesInterModuleCommTest.class );
        */
    	
    	return suite;
    }

    /**
     * Rigorous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
