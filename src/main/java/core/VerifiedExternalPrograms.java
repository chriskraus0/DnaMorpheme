package core;


// Imports.

// Project-specific imports.
import extProgs.ExtProgType;

/**
 * Singleton class which provides a resource of all tested external programs.
 * @author Christopher Kraus
 *
 */

public class VerifiedExternalPrograms {
	
	// Variables.
	
	private static VerifiedExternalPrograms verifiedExternalPrograms = new VerifiedExternalPrograms();
	
	private static String samtoolsExe;
	private static String samtoolsPath;
	private static String samtoolsVersion;
	
	private static String cdhitExe;
	private static String cdhitPath;
	private static String cdhitVersion;
	
	private static String qpms9Exe;
	private static String qpms9Path;
	private static String qpms9Version;
	
	private static String bowtie2Exe;
	private static String bowtie2Path;
	private static String bowtie2Version;
	
	private static String tomtomExe;
	private static String tomtomPath;
	private static String tomtomVersion;
	
	private static String workDirPath;
	
	// Constructors.
	private VerifiedExternalPrograms () {}
	
	// Methods.
	
	public static VerifiedExternalPrograms getInstance() {
		return verifiedExternalPrograms;
	}

	// Setters.
	
	/**
	 * Set the parameters for the different external programs.
	 * @param ExtProgType type
	 * @param String exe; name of the executable
	 * @param String path; path to the executable
	 * @param String version; version of the program
	 */
	public static void setParam (ExtProgType type, String exe, String path, String version) {
		switch (type) {
			case SAMTOOLS:
				samtoolsExe = exe;
				samtoolsPath = path;
				samtoolsVersion = version;
				break;
			case CDHIT:
				cdhitExe = exe;
				cdhitPath = path;
				cdhitVersion = version;
				break;
			case QPMS9:
				qpms9Exe = exe;
				qpms9Path= path;
				qpms9Version = version;
				break;
			case BOWTIE2:
				bowtie2Exe = exe;
				bowtie2Path = path;
				bowtie2Version = version;
				break;
			case TOMTOM:
				tomtomExe = exe;
				tomtomPath = path;
				tomtomVersion = version;
				break;
			case WORKPATH:
				workDirPath = path;
				break;
			case UNDEFINED:
				break;
			default:
					break;
		}
	}
	
	// Getters.
	public static String getSamtoolsExe () {
		return samtoolsExe;
	}
	
	public static String getSamtoolsPath () {
		return samtoolsPath;
	}

	public static String getSamtoolsVersion () {
		return samtoolsVersion;
	}
	
	public static String getCdhitExe () {
		return cdhitExe;
	}
	
	public static String getCdhitPath () {
		return cdhitPath;
	}
	
	public static String getCdhitVersion () {
		return cdhitVersion;
	}
	
	public static String getQpms9Exe () {
		return qpms9Exe;
	}
	
	public static String getQpms9Path () {
		return qpms9Path;
	}
	public static String getQpms9Version () {
		return qpms9Version;
	}
	
	public static String getbowtie2Exe () {
		return bowtie2Exe;
	}
	
	public static String getbowtie2Path () {
		return bowtie2Path;
	}
	
	public static String getbowtie2Version () {
		return bowtie2Version;
	}
	
	public static String gettomtomExe () {
		return tomtomExe;
	}
	
	public static String gettomtomPath () {
		return tomtomPath;
	}
	
	public static String gettomtomVersion () {
		return tomtomVersion;
	}
	
	public static String getWorkingDirPath () {
		return workDirPath;
	}
}
