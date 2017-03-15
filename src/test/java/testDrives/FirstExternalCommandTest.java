package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit imports.
import org.junit.Test;
import junit.framework.TestCase;

// Project-specific imports.
import core.CoreController;
import core.ListExternalPrograms;
import extProgs.ExtProgType;

/**
 * External command test to see whether all external programs are present
 * in the correct version and work properly.
 * @author christopher
 *
 */

public class FirstExternalCommandTest extends TestCase {
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	
	public FirstExternalCommandTest () {
		this.logger = Logger.getLogger(FirstExternalCommandTest.class.getName());
	}
	
	// JUnit Test.
	@Test
	public void testExtJob () {
		
	    // Determine the location of the working directory after starting the application.
	    this.logger.log(Level.INFO, "Working Directory = " +
	              System.getProperty("user.dir"));
	    FirstExternalCommandTest thisJob = new FirstExternalCommandTest();
		
		// Run the core components.
		thisJob.start();
		
		// Assert the external programs.
		// SAMTOOLS.
		assertEquals(ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getExecutable(), "samtools");
		
		// CDHIT.
		assertEquals(ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getExecutable(), "cdhit");
		
		// BOWTIE2.
		assertEquals(ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getExecutable(), "bowtie2");

		// TOMTOM.
		assertEquals(ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getExecutable(), "tomtom");
		
	}
	
	public void start() {

	    // Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		
		// Create a storage in the provided working directory path.
		
		
	}
}
