package core;

import core.exceptions.SystemNotSupportedException;
import core.exceptions.VersionNoCompatibleException;

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
	public static CoreController getInstance() {
		return coreController;
	}
	
	public static ExternalProgramHandler checkExternalProgrammes() {
		CheckExternalProgrammes chExProg = new CheckExternalProgrammes("config/config.txt");
		chExProg.readConfig();
		try {
			chExProg.testExtProgs();
		} catch (SystemNotSupportedException se) {
			System.err.println(se.getMessage());
			se.printStackTrace();
		} catch (VersionNoCompatibleException ve) {
			System.err.println(ve.getMessage());
			ve.printStackTrace();
		}
		
		ExternalProgramHandler externalProgramHandler = generateExternalProgramHandler();
		ExternalProgramHandler.setExternalProgMap(chExProg.getExternalProgrammes());
		return externalProgramHandler;
		
	}
	
	private static ExternalProgramHandler generateExternalProgramHandler () {
		return ExternalProgramHandler.getInstance();
	}
	
	public static ModuleBuilder generateModuleBuilder() {
		JobController jobController = new JobController();
		ModuleBuilder moduleBuilder = new ModuleBuilder(jobController);
		return moduleBuilder;
	}

}
