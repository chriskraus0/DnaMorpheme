package testDrives;

//Imports.
import java.net.URL;

// Project specific imports.
import core.CoreController;
import core.ListExternalPrograms;
import extProgs.ExternalProgram;
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
public class TestCoreControllerExternalProgrammes {
	
	// Variables.
	
	// Constructors.
	
	// Methods.
	public static void main (String[] args) {
		
		// Determine the location of this particular test class.
		URL location = TestCoreControllerExternalProgrammes.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println("Location of Class \"" + TestCoreControllerExternalProgrammes.class.getName() +"\":");
	    System.out.println(location.getFile());
	    
	    // Determine the location of the working directory after starting the application.
	    System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		TestCoreControllerExternalProgrammes thisJob = new TestCoreControllerExternalProgrammes();
		
		// Run the core components.
		thisJob.run();
	}
	
	public void run() {

	    // Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		ListExternalPrograms externalProgramHandler = CoreController.checkExternalProgrammes();
		
		System.out.println("Entries in the config file:");
		System.out.println(ExtProgType.SAMTOOLS.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.SAMTOOLS).getVersion());
		
		System.out.println(ExtProgType.BOWTIE2.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.BOWTIE2).getVersion());
		
		System.out.println(ExtProgType.CDHIT.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.CDHIT).getVersion());
		
		System.out.println(ExtProgType.QPMS9.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.QPMS9).getVersion());
			
		System.out.println(ExtProgType.TOMTOM.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getPath() + "\n"
				+ "Executable: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getExecutable() + "\n"
				+ "Version: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.TOMTOM).getVersion());
		
		System.out.println(ExtProgType.WORKPATH.toString() + "\n"
				+ "Path: " + ListExternalPrograms.getExternalProgrammes(ExtProgType.WORKPATH).getPath() + "\n");
			
	}
}
