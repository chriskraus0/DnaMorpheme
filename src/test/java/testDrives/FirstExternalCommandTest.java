package testDrives;

// Imports.

// JUnit imports.
import static org.junit.Assert.assertEquals;
import org.junit.Test;

// Project-specific imports.
import core.CoreController;
import core.ListExternalPrograms;
import extProgs.ExtProgType;

public class FirstExternalCommandTest {
	// Variables.
	private ListExternalPrograms externalProgramHandler;
	

	// Constructors.
	
	// JUnit Test.
	@Test
	public void testExtJob () {
		
	    // Determine the location of the working directory after starting the application.
	    System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
	    TestFirstExternalCommand thisJob = new TestFirstExternalCommand();
		
		// Run the core components.
		thisJob.run();
		
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
	
	public void run() {

	    // Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		this.externalProgramHandler = CoreController.checkExternalProgrammes();
		
		// Create a storage in the provided working directory path.
		
		
	}
}
