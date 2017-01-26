package extProgs;

public class Workpath implements ExternalProgram {

	// Constants.
	
	private final ExtProgType EXT_PROG_TYPE; 
	
	
	// Path to executable.
	private final String PATH;
	
	// Name of executable.
	private final String EXECUTABLE;
	
	// HashMap of used flags.
	// Maybe in the future.

	private final String VERSION;

	// Variables.
	
	// Tested version for bowtie2.
	private String seenVersion;
	
	// Constructors.
	
	/**
	 * Constructor takes 3 constants.
	 * @param path
	 * @param exe
	 * @param version
	 */
	public Workpath ( String path, String exe, String version) {
		this.PATH = path;
		this.EXECUTABLE = exe;
		this.EXT_PROG_TYPE = ExtProgType.WORKPATH;
		this.VERSION = version;
		this.seenVersion="";
	}
	
	// Methods.
	
	// Setters.
	@Override
	public void setSeenVersion(String version) {
		this.seenVersion = version;
		
	}
	
	// Getters.
	@Override
	public ExtProgType getProgType() {
		return this.EXT_PROG_TYPE;
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

	@Override
	public String getSeenVersion() {
		return this.seenVersion;
	}
}
