package core;

// Imports.

// Java utility imports.
import java.util.Map;

// Project-specific imports.
import extProgs.ExtProgType;
import extProgs.ExternalProgram;

/**
 * Singleton class to handle information about external programs.
 * @author christopher kraus
 *
 */
public class ListExternalPrograms {

	// Variables.
	
	// Singular "object".
	private static ListExternalPrograms listExternalPrograms = new ListExternalPrograms();
	
	// HashMap to save external programs.
	private static Map <ExtProgType, ExternalProgram> extProgMap;
		
	// Constructors.
	
	/**
	 * Empty Singleton constructor.
	 */
	private ListExternalPrograms () {}
	
	// Methods.
	
	// Setters.
	
	/**
	 * Setter to instantiate the list of external programs.
	 * @param extMap
	 */
	public static void setExternalProgMap (Map <ExtProgType, ExternalProgram> extMap) {
		ListExternalPrograms.extProgMap = extMap;
	}
	
	// Getters.
	
	/**
	 * Method to instantiate a singular instance of this Singleton class.
	 * @return ListExternalPrograms listExternalPrograms
	 */
	public static ListExternalPrograms getInstance () {
		return listExternalPrograms;
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
