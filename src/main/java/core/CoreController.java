package core;

import core.exceptions.SystemNotSupportedException;
import core.exceptions.VersionNotCompatibleException;

/**
 * Singleton which creates all major core components.
 * @author christopher
 *
 */
public class CoreController {
	
	// Variables.
	private static CoreController coreController = new CoreController();
	
	// Constructor.
	private CoreController () {}
	
	// Methods.
	
	/**
	 * Always return the very same instance. ("Singleton" design pattern).
	 * @return CoreConstroller coreController
	 */
	public static CoreController getInstance() {
		return coreController;
	}
	
	/**
	 * Static method to check required external programs. Return after successful
	 * tests the ExternalProgramHandler.
	 * @return ExternalProgramHandler externalProgramHandler (Singleton)
	 */
	public static ListExternalPrograms checkExternalProgrammes() {
		
		// Retrieve all physical constants.
		retrievePhysicalConstants();
		
		// Check the external programs.
		CheckExternalPrograms chExProg = new CheckExternalPrograms("config/config.txt");
		chExProg.readConfig();
		try {
			chExProg.testExtProgs();
		} catch (SystemNotSupportedException se) {
			System.err.println(se.getMessage());
			se.printStackTrace();
		} catch (VersionNotCompatibleException ve) {
			System.err.println(ve.getMessage());
			ve.printStackTrace();
		}
		
		// Generate ExternalProgramHandler 		
		ListExternalPrograms externalProgramHandler = generateExternalProgramHandler();
		ListExternalPrograms.setExternalProgMap(chExProg.getExternalProgrammes());
		return externalProgramHandler;
		
	}
	
	/**
	 * Generate GenerateExternalProgramHandler ("Singleton" design pattern).
	 * @return GenerateExernalProgramHandler externalProgramHandler
	 */
	private static ListExternalPrograms generateExternalProgramHandler () {
		return ListExternalPrograms.getInstance();
	}
	
	/**
	 * Retrieve physical constants and generate the Singleton PhysicalConstants to hold parameters.
	 * @return PhysicalConstants
	 */
	private static void retrievePhysicalConstants() {
		PhysicalConstants.getInstance();
		PhysicalConstants.testOS();
		PhysicalConstants.testCPUcores();
		PhysicalConstants.testFreeRAM();
	}
	
	/**
	 * Generate ModuleBuilder.
	 * @return ModuleBuilder moduleBuilder.
	 */
	public static ModuleBuilder generateModuleBuilder() {
		JobController jobController = new JobController();
		ModuleBuilder moduleBuilder = new ModuleBuilder(jobController);
		return moduleBuilder;
	}

}
