package extProgs;

public class Bowtie2 implements ExternalProgram {
	
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
	
	// Tested version for bowtie2.
	private String seenVersion;
	
	// Constructors.
	
	/**
	 * Constructor takes 3 constants.
	 * @param path
	 * @param exe
	 * @param version
	 */
	public Bowtie2 ( String path, String exe, String version) {
		this.PATH = path;
		this.EXECUTABLE = exe;
		this.EXT_PPROG_TYPE = ExtProgType.BOWTIE2;
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

	@Override
	public String getSeenVersion() {
		return this.seenVersion;
	}
	
}
