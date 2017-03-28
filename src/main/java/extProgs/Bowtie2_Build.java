package extProgs;

public class Bowtie2_Build implements ExternalProgram {

	// Constants.
	
	private final ExtProgType EXT_PROG_TYPE;
	
	// Path to executable.
	private final String PATH;
	
	// Name of executable.
	private final String EXECUTABLE;
	
	// Version of the executable.
	private final String VERSION;
	
	// Variables
	
	// Tested version for bowtie2-build.
	private String seenVersion;
	
	// Constructors.
	
	/**
	 * Constructor takes 3 arguments.
	 * @param path
	 * @param exe
	 * @param version
	 */
	public Bowtie2_Build (String path, String exe, String version) {
		this.PATH = path;
		this.EXECUTABLE = exe;
		this.EXT_PROG_TYPE = ExtProgType.BOWTIE2_BUILD;
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
	public String getSeenVersion() {
		return this.seenVersion;
	}

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

}
