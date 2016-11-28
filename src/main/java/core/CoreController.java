package core;

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
	
	public static void checkExternalProgrammes(String path) {
		CheckExternalProgrammes chExProg = new CheckExternalProgrammes(path);
		chExProg.readConfig();
	}
		
	public static ModuleBuilder generateModuleBuilder() {
		JobController jobController = new JobController();
		ModuleBuilder moduleBuilder = new ModuleBuilder(jobController);
		return moduleBuilder;
	}

}
