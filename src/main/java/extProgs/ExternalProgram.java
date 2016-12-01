package extProgs;

public interface ExternalProgram {
	
	/**
	 * Get the type of external program
	 * @return ExtProgType extProgType
	 * @see extProgs.ExtProgType
	 */
	public ExtProgType getProgType();
	
	/**
	 * Get the path of the external program.
	 * @return String path
	 */
	public String getPath();
	
	/**
	 * Get the name of the external program.
	 * @return String executableName
	 */
	public String getExecutable();
	
	/**
	 * Get verion of the program.
	 * @return String version.
	 */
	public String getVersion();
	
}
