package core;

// Imports.

// Java utility imports.
import java.util.TreeMap;
import java.util.Set;

// Project specific imports.
import core.common.Module;
import core.common.ModuleBuilderInterface;
import core.common.ModuleState;
import core.common.ModuleType;

import modules.CdHitJob;


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
	private static TreeMap <Integer, Module> moduleMap;
	
	// Keep count of all module objects created.
	private static int moduleCount;
	
	// Constructors.
	
	public ModuleBuilder () {
		super();
		ModuleBuilder.moduleMap = new TreeMap<Integer, Module> ();
		ModuleBuilder.moduleCount = 0;
		
		// Get a single instance for ModulePortLinker.
		ModulePortLinker.getInstance();
	}


	// End constructors.
	
	// Methods.
	/**
	 * Return a newly generated moduleID for a new module.
	 * @param Module module
	 * @return String moduleID
	 */
	private static int generateNewModuleID () {
		ModuleBuilder.moduleCount ++;
		// TODO: Create unique hash-key generator;
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
	 * Returns a specific Module.
	 * @param moduleID
	 * @return Module
	 */
	public static Module getModule(int moduleID) {
		return ModuleBuilder.moduleMap.get(moduleID);
	}
	
	public int createNewInputReader() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.INPUT_READER;
		
		Module newInputReader = this.createNewModule(moduleID, storageID, mType);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newInputReader.getModuleID(), newInputReader);
		
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(ModuleBuilder.moduleMap.get(moduleID));
		
		return moduleID;
	}
	
	public int createNewCdHitJob() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.CDHIT_JOB;
		
		Module newCdHitJob = this.createNewModule(moduleID, storageID, mType);
		
		// Add module with the "moduleID" and module to the new module TreeMap.
		ModuleBuilder.moduleMap.put(newCdHitJob.getModuleID(), newCdHitJob);
		
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(ModuleBuilder.moduleMap.get(moduleID));
		
		return moduleID;
	}
	
	public int createNewQpms9Job() {
		int moduleID = ModuleBuilder.generateNewModuleID();
		int storageID = this.requestStorage();
		ModuleType mType = ModuleType.QPMS9_JOB;
		
		Module newQpms9Job = this.createNewModule(moduleID, storageID, mType);
		
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
	public Module createNewModule(int moduleID, int storageID, ModuleType mType) {
		
		Module newModule = null;
		
		switch (mType) {
			case CDHIT_JOB:
				newModule = new CdHitJob (moduleID, storageID, mType, 
						ModulePortLinker.requestNewInputPortID(moduleID), 
						ModulePortLinker.requestNewOutputPortID(moduleID));
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
