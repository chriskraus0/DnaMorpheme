package core;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Set;

// Project specific imports.
import core.common.Module;
import core.common.ModuleBuilderInterface;
import core.common.ModuleState;
import core.common.ModuleType;

import modules.CdHitJob;
import modules.QPMS9Job;


/**
 * The actual factory which creates new modules.
 * @author christopher
 *
 */

public class ModuleBuilder implements ModuleBuilderInterface {
	// Enumeration.
	
	private ModuleState mState;
	
	// Variables.
	
	// Keep an instance independent sorted map of all modules and IDs.
	private static Map <Integer, Module> moduleMap;
	
	// Keep an map of all running connected modules.
	private static Map <Integer, Thread> modulesThreadMap;
	
	// Keep count of all module objects created.
	private static int moduleCount;
	
	// Constructors.
	
	public ModuleBuilder () {
		super();
		ModuleBuilder.moduleMap = new TreeMap<Integer, Module> ();
		ModuleBuilder.modulesThreadMap = new HashMap<Integer, Thread>();
		ModuleBuilder.moduleCount = 0;
		
		// Get a single instance for ModulePortLinker.
		ModulePortLinker.getInstance();
	}


	// End constructors.
	
	// Methods.
	
	/**
	 * Create a new thread for a module and save it, start and put it to sleep (100 ms).
	 * Take the moduleID of this module and the connected module ID.
	 * @param int moduleID
	 * @param int connectedModuleID
	 */
	public void startJob (int moduleID) throws InterruptedException {
		Thread newModuleThread = new Thread(ModuleBuilder.getModule(moduleID));
		
		// Use thread specific names for debugging.
		newModuleThread.setName(ModuleBuilder.getModule(moduleID).getModuleType().toString() 
				+ ":" + ModuleBuilder.getModule(moduleID).getModuleID());
		ModuleBuilder.modulesThreadMap.put(moduleID, newModuleThread);
		
		// Start new Thread.
		newModuleThread.start();
		
		// Sleep for 100 milliseconds.
		Thread.sleep(100);
	}
	
	/**
	 * Return a newly generated moduleID for a new module.
	 * @param Module module
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
	 * Returns the moduleID of the module connected to this one.
	 * @param int moduleID
	 * @return int moduleID of connected module.
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
	
	public int createNewInputReader(String command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.INPUT_READER;
		
		Module newInputReader = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newInputReader.getModuleID(), newInputReader);
		
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(ModuleBuilder.moduleMap.get(moduleID));
		
		return moduleID;
	}
	
	public int createNewCdHitJob(String command) {
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
	
	public int createNewQpms9Job(String command) {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.QPMS9_JOB;
		
		Module newQpms9Job = this.createNewModule(moduleID, storageID, mType, command);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newQpms9Job.getModuleID(), newQpms9Job);
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create QPMS9Job class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
		
		return moduleID;
	}
	
	public void createNewBowtie2Job() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.BOWTIE2_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create QPMS9Job class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewSamtoolsJob() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.SAMTOOLS_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create SamtoolsJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
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
	
	@Override
	public Module createNewModule(int moduleID, int storageID, ModuleType mType, String command) {
		
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
	
	// End methods.
}
