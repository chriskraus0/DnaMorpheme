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
	
	private static String bowtie2BuildExe;
	private static String bowtie2BuildPath;
	private static String bowtie2BuildVersion;
	
	private static String tomtomExe;
	private static String tomtomPath;
	private static String tomtomVersion;
	
	private static String workDirPath;
	
	// Constructors.
	private VerifiedExternalPrograms () {}
	
	// Methods.
	
	/**
	 * Create new Singleton Class by calling this method.
	 * @return VerifiedExternalPrograms verifiedExternalPrograms
	 */
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
			case BOWTIE2_BUILD:
				bowtie2BuildExe = exe;
				bowtie2BuildPath = path;
				bowtie2BuildVersion = version;
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
	
	// Samtools getters.
	
	/**
	 * Getter returns the name of the executable for samtools.
	 * @return String exe
	 */
	public static String getSamtoolsExe () {
		return samtoolsExe;
	}
	
	/**
	 * Getter returns the path for samtools.
	 * @return String path
	 */
	public static String getSamtoolsPath () {
		return samtoolsPath;
	}

	/**
	 * Getter returns version of samtools.
	 * @return String version
	 */
	public static String getSamtoolsVersion () {
		return samtoolsVersion;
	}
	
	// Cdhit getters.
	
	/**
	 * Getter returns the name of the executable for cd-hit.
	 * @return String exe
	 */
	public static String getCdhitExe () {
		return cdhitExe;
	}
	
	/**
	 * Getter returns the path for cd-hit.
	 * @return String path
	 */
	public static String getCdhitPath () {
		return cdhitPath;
	}
	
	/**
	 * Getter returns version of cd-hit.
	 * @return String version
	 */
	public static String getCdhitVersion () {
		return cdhitVersion;
	}
	
	// Qpms9 getters.
	
	/**
	 * Getter returns the name of the executable for qPMS9.
	 * @return String exe
	 */
	public static String getQpms9Exe () {
		return qpms9Exe;
	}
	
	/**
	 * Getter returns the path for qPMS9.
	 * @return String path
	 */
	public static String getQpms9Path () {
		return qpms9Path;
	}
	
	/**
	 * Getter returns version of qPMS9.
	 * @return String version
	 */
	public static String getQpms9Version () {
		return qpms9Version;
	}
	
	// Bowtie2 getters.
	
	/**
	 * Getter returns the name of the executable for bowtie2.
	 * @return String exe
	 */
	public static String getBowtie2Exe () {
		return bowtie2Exe;
	}
	
	/**
	 * Getter returns the path for bowtie2.
	 * @return String path
	 */
	public static String getBowtie2Path () {
		return bowtie2Path;
	}
	
	/**
	 * Getter returns version of bowtie2.
	 * @return String version
	 */
	public static String getBowtie2Version () {
		return bowtie2Version;
	}
	
	// Bowtie2-Build getters.
	
	/**
	 * Getter returns the name of the executable for bowtie2-build.
	 * @return String exe
	 */
	public static String getBowtie2BuildExe () {
		return bowtie2BuildExe;
	}
	
	/**
	 * Getter returns the path for bowtie2-build.
	 * @return String path
	 */
	public static String getBowtie2BuildPath () {
		return bowtie2BuildPath;
	}
	
	/**
	 * Getter returns version of bowtie2-build.
	 * @return String version
	 */
	public static String getBowtie2BuildVersion () {
		return bowtie2BuildVersion;
	}
	
	// TomTom getters.
	
	/**
	 * Getter returns the name of the executable for TomTom.
	 * @return String exe
	 */
	public static String getTomtomExe () {
		return tomtomExe;
	}
	
	/**
	 * Getter returns the path for TomTom.
	 * @return String path
	 */
	public static String getTomtomPath () {
		return tomtomPath;
	}
	
	/**
	 * Getter returns version of TomTom.
	 * @return String version
	 */
	public static String getTomtomVersion () {
		return tomtomVersion;
	}
	
	// Working directory getter.
	
	/**
	 * Getter returns the location of the working directory.
	 * @return String workDirPath
	 */
	public static String getWorkingDirPath () {
		return workDirPath;
	}
}
