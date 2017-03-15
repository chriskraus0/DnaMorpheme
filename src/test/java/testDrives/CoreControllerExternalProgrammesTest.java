package testDrives;

//Imports.

// Java imports.
import java.net.URL;

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit testing imports.
import org.junit.Test;
import junit.framework.TestCase;

//Project specific imports.
import core.CoreController;
import core.ListExternalPrograms;
import extProgs.ExtProgType;

/**
* Test drive class to test core classes such as 
* core.Controller, core.ModuleBuilder, core.common.CommandFailedException, core.CheckExternalProgrammes.
* This test drive iterates over 3 independently created instances of the Module CdHitJob.
* @see core.Controller
* @see core.Controller#checkExternalProgrammes()
* @see core.ModuleBuilder
* @see core.ModuleBuilder#createNewCdHitJob()
* @see core.Module
* @see modules.CdHitJob
* @author christopher
*
*/

public class CoreControllerExternalProgrammesTest extends TestCase {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public CoreControllerExternalProgrammesTest () {
		
		// Call Logger factory to get new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Test function.
	@Test
	public void testFunction () {
		// Determine the location of this particular test class.
		URL location = CoreControllerExternalProgrammesTest.class.getProtectionDomain().getCodeSource().getLocation();
		this.logger.log(Level.INFO, "Location of Class \"" + CoreControllerExternalProgrammesTest.class.getName() +"\":\n"
				+ location.getFile());
	    
	    // Determine the location of the working directory after starting the application.
	    this.logger.log(Level.INFO, "Working Directory = " +
	              System.getProperty("user.dir"));
	    CoreControllerExternalProgrammesTest thisJob = new CoreControllerExternalProgrammesTest();
		
		// Run the core components.
		thisJob.start();
	}
	
	public void start() {

	    // Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		
		this.logger.log(Level.INFO, "Entries in the config file:\n\n"
				+ ExtProgType.SAMTOOLS.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getVersion() + "\n\n"
				
				+ ExtProgType.BOWTIE2.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getVersion() + "\n\n"
		
				+ ExtProgType.CDHIT.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getVersion() + "\n\n" 

				+ ExtProgType.QPMS9.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getVersion() + "\n\n"
			
				+ ExtProgType.TOMTOM.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getVersion() + "\n\n"
		
				+ ExtProgType.WORKPATH.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.WORKPATH).getPath() + "\n");
		
		// Testing.
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getPath());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getExecutable());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getVersion());
		
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getPath());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getExecutable());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getVersion());

		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getPath());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getExecutable());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getVersion());

		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getPath());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getExecutable());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getVersion());
	
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getPath());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getExecutable());
		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getVersion());

		assertNotNull (ListExternalPrograms.getExternalProgrammes(ExtProgType.WORKPATH).getPath());
	}

}
