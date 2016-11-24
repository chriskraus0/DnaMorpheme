package core;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.HashMap;

// Project specific imports.
import core.common.ModuleState;

/**
 * This class creates nodes for each interacting thread pair.
 * @author christopher
 *
 */
public class ModuleNode {
	
	// Variables.
	
	// Integer holding the module ID of the first module.
	int module1ID;
	
	// Variable holding the ModuleState of the first module.
	ModuleState module1State;
	
	// Integer holding the module ID of the second module.
	int module2ID;
	
	// Variable holding the ModuleState of the second module.
	ModuleState module2State;
	
	// Constructors.
	public ModuleNode (int moduleID) {
		this.module1ID = moduleID;
		this.module1State = ModuleBuilder.getModule(moduleID).getModuleState();
	}
	
	public ModuleNode (int module1ID, int module2ID) {
		this.module1ID = module1ID;
		this.module1State = ModuleBuilder.getModule(module1ID).getModuleState();
		this.module2ID = module2ID;
		this.module2State = ModuleBuilder.getModule(module2ID).getModuleState();
	}

	// End constructors.
	
	// Methods.
	
	// Setters.
	public void updateModuleState(int moduleID) throws Exception {
		if (this.module1ID == moduleID) 
			this.module1State = ModuleBuilder.getModule(moduleID).getModuleState();
		
		if (this.module2ID == moduleID) 
			this.module2State = ModuleBuilder.getModule(moduleID).getModuleState();
		
		if (this.module1ID != moduleID && this.module2ID != moduleID) 
			throw new Exception("Cannot update node for module with ID:\""
					+ moduleID + "\". No such module registered");
	}
	// End setters.
	
	// Getters.
	public ModuleState getModuleState(int moduleID) throws Exception {
		if (this.module1ID == moduleID) 
			return this.module1State;
		
		if (this.module2ID == moduleID) 
			return this.module2State;
		
		if (this.module1ID != moduleID && this.module2ID != moduleID) 
			throw new Exception("Cannot retrieve node for module with ID:\""
				+ moduleID + "\". No such module registered");
		
		return module1State;
	}
	// End getters.
	
	
	// End methods.
}
