package core;

// Imports.

// Java utility imports.
import java.util.TreeMap;

import core.common.Module;
import core.common.ModuleBuilderInterface;
import core.common.ModuleState;
import core.common.ModuleType;
// DnaMorpheme specific imports.
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
	
	private TreeMap <String, Module> moduleMap;
	
	// Constructors.
	
	public ModuleBuilder () {
		super();
		this.moduleMap = new TreeMap<String, Module> ();
	}


	// End constructors.
	
	// Methods.
	/**
	 * Return a newly generated moduleID for a new module.
	 * @return String moduleID
	 */
	private String generateNewModuleID () {
		String newID = "";
		// TODO: Create unique hash-key generator;
		return newID;
	}
	
	
	public void createNewCdHitJob() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.CDHIT_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create CdHitJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewQpms9Job() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.QPMS9_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create QPMS9Job class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewBowtie2Job() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.BOWTIE2_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create QPMS9Job class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewSamtoolsJob() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.SAMTOOLS_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create SamtoolsJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewTomtomJob() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.TOMTOM_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create TomTomJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewSequenceLogoJob() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.SEQ_LOGO_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create SeqLogoJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewPmsConsensusJob() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.PMS_CONSENSUS_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create PMSConsensusJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewNucFreqJob() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.NUCFREQ_JOB;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create NucFreqJob class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	public void createNewExternalCommandHandler() {
		String moduleID = this.generateNewModuleID();
		String storageID = this.requestStorage();
		ModuleType mType = ModuleType.EXTERNAL_COMMAND;
		
		// Add module with the ID "moduleID" to the new module TreeMap. TODO: Is there a reason to use TreeMap instead of HashMap?
		// TODO: Create ExternalCommand class.
		//this.moduleMap.put(moduleID, new CdHitJob(moduleID, storageID, mType));
		// TODO: implement "connectModuleObserver" method
		//this.connectModuleObserver(this.moduleMap.get(moduleID);
	}
	
	@Override
	public Module createNewModule(String moduleID, String storageID, ModuleType mType) {
		
		Module newModule = new CdHitJob(moduleID, storageID, mType);
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
	public String requestStorage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyModule(String moduleD) {
		// TODO Auto-generated method stub
		
	}
	
	// End methods.
}
