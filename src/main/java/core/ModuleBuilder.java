package core;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashMap;

// Project specific imports.
import core.common.Module;
import core.common.ModuleBuilderInterface;
import core.common.ModuleState;
import core.common.ModuleType;
import core.ExtStorageController;

// Module imports.
import modules.CdHitJob;
import modules.QPMS9Job;
import modules.Bowtie2Job;
import modules.SamtoolsJob;
import modules.DummyJob;
import modules.commands.Commands;


// Test module imports.
import testModules.InputTest;
import testModules.TestTransferModule;
import testModules.TestOutput;

/**
 * ModuleBuilder: The actual factory which creates new modules.
 * @author christopher
 *
 */

public class ModuleBuilder implements ModuleBuilderInterface {
	
	// Variables.
	
	// Variable holds the JobController.
	private JobController jobController;
		
	// Keep an instance independent sorted map of all modules and IDs.
	private static Map <Integer, Module> moduleMap;
	
	// Keep an map of all running connected modules.
	private static Map <Integer, Thread> modulesThreadMap;
	
	// Keep count of all module objects created.
	private static int moduleCount;
	
	// Constructors.
	
	public ModuleBuilder (JobController jController) {
		super();
		this.jobController = jController;
		ModuleBuilder.moduleMap = new TreeMap<Integer, Module> ();
		ModuleBuilder.moduleCount = 0;
		
		// Get a single instance for ModulePortLinker.
		ModulePortLinker.getInstance();
		
		// Create a dummy module with the ID "-1" for all modules which
		// do not need to transfer data via pipes.
		this.createNewDummyJob();
		ModuleBuilder.moduleMap.get(-1).setModuleState(ModuleState.UNDEFINED);
	}


	// End constructors.
	
	// Methods.
	
	/**
	 * Create a new thread for a module and save it, start and put it to sleep (100 ms).
	 * Take the moduleID of this module and the connected module ID.
	 * @param int moduleID
	 * @param int connectedModuleID
	 * @return String nodeName
	 */
	public String prepareJobs (int producerID, int consumerID) {
		String nodeName = this.jobController.addNewModuleNode(producerID, consumerID);
		this.jobController.connect(nodeName);
		return nodeName;
	}
	
	/**
	 * Create a new thread for a module and save it, start and put it to sleep (100 ms).
	 * Take the moduleID of this module but without connecting to any other module.
	 * @param int moduleID
	 * @param int connectedModuleID
	 * @return String nodeName
	 */
	public String prepareJobs (int moduleID) {
		String nodeName = this.jobController.addNewModuleNode(moduleID);
		// TODO delete this:
		//this.jobController.connect(nodeName);
		return nodeName;
	}
	
	/**
	 * Method to start one thread per module.
	 * @param moduleNodeName
	 * @throws InterruptedException
	 */
	public void startJobs (String moduleNodeName) throws InterruptedException {
		this.jobController.startJob(moduleNodeName);
	}
	
	/**
	 * Return a newly generated moduleID for a new module.
	 * @return String moduleID
	 */
	private static int generateNewModuleID () {
		ModuleBuilder.moduleCount ++;
		return ModuleBuilder.moduleCount;
	}
	
	/**
	 * Returns a Set of the current ModuleIDs.
	 * @return Set moduleIDs
	 */
	public static Set<Integer> getModuleIDs() {
		return ModuleBuilder.moduleMap.keySet();
	}
	
	/**
	 * Returns the Thread of the module connected to this one.
	 * @param int moduleID
	 * @return Thread of connected module.
	 */
	public static Thread getRunningThread(int moduleID) {
		return ModuleBuilder.modulesThreadMap.get(moduleID);
	}
	
	/**
	 * Returns a specific Module.
	 * @param moduleID
	 * @return Module
	 */
	public static Module getModule(int moduleID) {
		return ModuleBuilder.moduleMap.get(moduleID);
	}
	
	public void createNewDummyJob() {
		int moduleID = -1;
		int storageID = -1;
		ModuleType mType = ModuleType.DUMMY;
		HashMap<Commands, String> command = new HashMap<Commands, String>();
		command.put(Commands.dummy, "dummy");
		
		Module newDummyJob = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newDummyJob.getModuleID(), newDummyJob);
	}
	
	public int createNewInputReader(HashMap<Commands, String> command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.INPUT_READER;
		
		Module newInputReader = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newInputReader.getModuleID(), newInputReader);
		
		// TODO: implement "connectModuleObserver" method.
		//this.connectModuleObserver(ModuleBuilder.moduleMap.get(moduleID));
		
		return moduleID;
	}
	
	/**
	 * This method creates a Cd-Hit job which will be executed on a new thread.
	 * @param HashMap<Commands, String> command
	 * @see modules.command.Commands
	 * @return int moduleID
	 */
	public int createNewCdHitJob(HashMap<Commands, String> command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.CDHIT_JOB;
		
		Module newCdHitJob = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newCdHitJob.getModuleID(), newCdHitJob);
				
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(ModuleBuilder.moduleMap.get(moduleID));
		
		return moduleID;
	}
	
	public int createNewQpms9Job(HashMap<Commands, String> command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.QPMS9_JOB;
		
		Module newQpms9Job = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newQpms9Job.getModuleID(), newQpms9Job);
		
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
		
		return moduleID;
	}
	
	public int createNewBowtie2Job(HashMap<Commands, String> command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.BOWTIE2_JOB;
		
		Module newBowtie2Job = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newBowtie2Job.getModuleID(), newBowtie2Job);
		
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
		return moduleID;
	}
	
	public int createNewSamtoolsJob(HashMap<Commands, String> command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.SAMTOOLS_JOB;
		
		Module samtoolsJob = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(samtoolsJob.getModuleID(), samtoolsJob);
		
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
		
		return moduleID;
	}
	
	public void createNewTomtomJob() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.TOMTOM_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create TomTomJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewSequenceLogoJob() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.SEQ_LOGO_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create SeqLogoJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewPmsConsensusJob() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.PMS_CONSENSUS_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create PMSConsensusJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewNucFreqJob() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.NUCFREQ_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create NucFreqJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewExternalCommandHandler() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.EXTERNAL_COMMAND;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create ExternalCommand class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public int createInputTestJob(HashMap<Commands, String> command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.INPUT_TEST;
		
		Module newInputTest = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newInputTest.getModuleID(), newInputTest);
				
		return moduleID;
	}
	
	public int createTestTransferJob(HashMap<Commands, String> command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.TEST_TRANSFER;
		
		Module newTestransfer = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newTestransfer.getModuleID(), newTestransfer);
				
		return moduleID;
	}
	
	public int createTestoutputJob(HashMap<Commands, String> command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.TEST_OUTPUT;
		
		Module newTestoutput = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newTestoutput.getModuleID(), newTestoutput);
				
		return moduleID;
	}
	
	@Override
	public Module createNewModule(int moduleID, int storageID, ModuleType mType, HashMap<Commands, String> command) {
		
		Module newModule = null;
		
		switch (mType) {
			case CDHIT_JOB:
				newModule = new CdHitJob (moduleID, storageID, mType, 
						ModulePortLinker.requestNewInputPortID(moduleID), 
						ModulePortLinker.requestNewOutputPortID(moduleID),
						command);
				break;
			case INPUT_READER:
				newModule = new InputReader (moduleID, storageID, mType, 
						ModulePortLinker.requestNewInputPortID(moduleID), 
						ModulePortLinker.requestNewOutputPortID(moduleID),
						command);
				break;
			case QPMS9_JOB:
				newModule = new QPMS9Job (moduleID, storageID, mType, 
						ModulePortLinker.requestNewInputPortID(moduleID), 
						ModulePortLinker.requestNewOutputPortID(moduleID),
						command);
				break;
			case BOWTIE2_JOB:
				newModule = new Bowtie2Job (moduleID, storageID, mType, 
						ModulePortLinker.requestNewInputPortID(moduleID), 
						ModulePortLinker.requestNewOutputPortID(moduleID),
						command);
				break;
			case SAMTOOLS_JOB:
				newModule = new SamtoolsJob (moduleID, storageID, mType, 
						ModulePortLinker.requestNewInputPortID(moduleID), 
						ModulePortLinker.requestNewOutputPortID(moduleID),
						command);
				break;
			case INPUT_TEST:
				newModule = new InputTest (moduleID, storageID, mType,
						ModulePortLinker.requestNewInputPortID(moduleID),
						ModulePortLinker.requestNewOutputPortID(moduleID),
						command);
				break;
			case TEST_TRANSFER:
				newModule = new TestTransferModule (moduleID, storageID, mType,
						ModulePortLinker.requestNewInputPortID(moduleID),
						ModulePortLinker.requestNewOutputPortID(moduleID),
						command);
				break;
			case TEST_OUTPUT:
				newModule = new TestOutput (moduleID, storageID, mType,
						ModulePortLinker.requestNewInputPortID(moduleID),
						ModulePortLinker.requestNewOutputPortID(moduleID),
						command);
				break;
			case DUMMY:
				newModule = new DummyJob (moduleID, storageID, mType, -1, -1, command);
			case UNDEFINED:
				break;
			default:
				break;
		}
		return newModule;
		
	}

	@Override
	public void createNewInputPort(String moduleID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createNewOutputPort(String moduleID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int requestStorage() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void destroyModule(String moduleD) {
		// TODO Auto-generated method stub
		
	}
	
	
	// Getters. 
	
	/**
	 * Getter which returns the JobController.
	 * @return JobController jobController
	 */
	public JobController getJobcontroller() {
		return this.jobController;
	}
	
	// End getters.
	
	// End methods.
}
