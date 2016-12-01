package extProgs;

public class Cdhit implements ExternalProgram {

	// Constants.
	
	private final ExtProgType EXT_PPROG_TYPE; 
	
	
	// Path to executable.
	private final String PATH;
	
	// Name of executable.
	private final String EXECUTABLE;
	
	// HashMap of used flags.
	// Maybe in the future.

	private final String VERSION;
	

	// Variables.
	
	// Constructors.
	/**
	 * Constructor takes 3 constants.
	 * @param path
	 * @param exe
	 * @param version
	 */
	public Cdhit ( String path, String exe, String version) {
		this.PATH = path;
		this.EXECUTABLE = exe;
		this.EXT_PPROG_TYPE = ExtProgType.CDHIT;
		this.VERSION = version;
	}
	
	// Methods.
	
	// Setters.
		
	// Getters.
	
	@Override
	public ExtProgType getProgType() {
		return this.EXT_PPROG_TYPE;
	}
	
	@Override
	public String getPath() {
		return this.PATH;
	}
	
	@Override
	public String getExecutable() {
		return this.EXECUTABLE;
	}

	@Override
	public String getVersion() {
		return this.VERSION;
	}

}
