package core;

// Imports.

// Java utility imports.
import java.util.Map;

// Project-specific imports.
import extProgs.ExtProgType;
import extProgs.ExternalProgram;

/**
 * Singular class to handle information about external programs.
 * @author christopher
 *
 */
public class ExternalProgramHandler {

	// Variables.
	
	// Singular "object".
	private static ExternalProgramHandler externalProgramHandler = new ExternalProgramHandler();
	
	// HashMap to save external programs.
	private static Map <ExtProgType, ExternalProgram> extProgMap;
		
	// Constructors.
	
	public ExternalProgramHandler () {}
	
	// Methods.
	
	// Setters.
	
	public static void setExternalProgMap (Map <ExtProgType, ExternalProgram> extMap) {
		ExternalProgramHandler.extProgMap = extMap;
	}
	
	// Getters.
	
	public static ExternalProgramHandler getInstance () {
		return externalProgramHandler;
	}
	
	/**
	 * Returns information for a specific external program.
	 * @param ExtProgType extProgType
	 * @return ExternalProgram
	 */
	public static ExternalProgram getExternalProgrammes(ExtProgType extProgType) {
		return extProgMap.get(extProgType);
	}
}
