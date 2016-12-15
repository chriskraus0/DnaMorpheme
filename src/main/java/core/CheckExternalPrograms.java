package core;

// Imports.

// Java I/O imports.
import java.io.File;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

// Java utility imports.
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Project-specific imports.
import extProgs.ExtProgType;
import extProgs.ExternalProgram;
import extProgs.Samtools;
import extProgs.Bowtie2;
import extProgs.Cdhit;
import extProgs.Qpms9;
import extProgs.Tomtom;

// Project-specific exceptions.
import core.exceptions.SystemNotSupportedException;
import core.exceptions.VersionNotCompatibleException;

public class CheckExternalPrograms {
		
	// Variables.
	private String configurationPath;
	
	// HashMap to save external programs.
	private Map <ExtProgType, ExternalProgram> extProgMap;
	
	// Constructors.
	
	public CheckExternalPrograms(String configPath) {
		this.configurationPath = configPath;
		this.extProgMap = new HashMap <ExtProgType, ExternalProgram>();
	}
	
	// Methods.
	
	// Getters.
	
	public Map <ExtProgType, ExternalProgram> getExternalProgrammes() {
		return this.extProgMap;
	}
	// End getters.
	
	/**
	 * Test the required external programs for compatibility with this program.
	 * @throws SystemNotSupportedException
	 * @throws VersionNotCompatibleException
	 */
	public void testExtProgs() throws SystemNotSupportedException, VersionNotCompatibleException {
		
		// Test the type of operating system.
		String osName = PhysicalConstants.getOsName();
		
		// System.out.println("System name: " + osName);
		if (!osName.equals("linux"))
			throw new SystemNotSupportedException("The system \"" + osName + "\" is not supported by this programme");
		
		// Test Samtools.
		try {
			boolean samtoolsState = this.testSamtools();
			if (samtoolsState) {
				System.out.println("LOG: Installed samtools version \"" 
						+ this.extProgMap.get(ExtProgType.SAMTOOLS).getSeenVersion()
						+ "\" is compatible with this program.");
			} else {
				throw new VersionNotCompatibleException("ERROR: The current version of Samtools \""
						+ this.extProgMap.get(ExtProgType.SAMTOOLS).getSeenVersion() 
						+ "\" is not compatible with this program.");
			}
		} catch (IOException ie) {
			System.err.println("ERROR: " +ie.getMessage());
			ie.printStackTrace();
		}
		
		// Test Bowtie2.
		try {
			boolean bowtie2State = this.testBowtie2();
			if (bowtie2State) {
				System.out.println("LOG: Installed bowtie2 version \"" 
						+ this.extProgMap.get(ExtProgType.BOWTIE2).getSeenVersion()
						+ "\" is compatible with this program.");
			} else {
				throw new VersionNotCompatibleException("ERROR: The current version of bowtie2 \""
						+ this.extProgMap.get(ExtProgType.BOWTIE2).getSeenVersion() 
						+ "\" is not compatible with this program.");
			}
		} catch (IOException ie) {
			System.err.println("ERROR: " +ie.getMessage());
			ie.printStackTrace();
		}
		
		// Test Cdhit.
		
		try {
			boolean cdhitState = this.testCdhit();
			if (cdhitState) {
				System.out.println("LOG: Installed cdhit version \"" 
						+ this.extProgMap.get(ExtProgType.CDHIT).getSeenVersion()
						+ "\" is compatible with this program.");
			} else {
				throw new VersionNotCompatibleException("ERROR: The current version of cdhit \""
						+ this.extProgMap.get(ExtProgType.CDHIT).getSeenVersion() 
						+ "\" is not compatible with this program.");
			}
		} catch (IOException ie) {
			System.err.println("ERROR: " +ie.getMessage());
			ie.printStackTrace();
		}
		
		// Test Qpms9.
		// Currently qpms9 cannot be tested! No such option included in the current version of qpms9!
		/*try {
			boolean qpms9State = this.testQpms9();
			if (qpms9State) {
				System.out.println("LOG: Installed cdhit version \"" 
						+ this.extProgMap.get(ExtProgType.QPMS9).getSeenVersion()
						+ "\" is compatible with this program.");
			} else {
				throw new VersionNoCompatibleException("ERROR: The current version of cdhit \""
						+ this.extProgMap.get(ExtProgType.QPMS9).getSeenVersion() 
						+ "\" is not compatible with this program.");
			}
		} catch (IOException ie) {
			System.err.println("ERROR: " +ie.getMessage());
			ie.printStackTrace();
		}*/
		
		// Test Tomtom.
		
		try {
			boolean cdhitState = this.testTomtom();
			if (cdhitState) {
				System.out.println("LOG: Installed tomtom version \"" 
						+ this.extProgMap.get(ExtProgType.TOMTOM).getSeenVersion()
						+ "\" is compatible with this program.");
			} else {
				throw new VersionNotCompatibleException("ERROR: The current version of tomtom \""
						+ this.extProgMap.get(ExtProgType.TOMTOM).getSeenVersion() 
						+ "\" is not compatible with this program.");
			}
		} catch (IOException ie) {
			System.err.println("ERROR: " +ie.getMessage());
			ie.printStackTrace();
		}
	}
	
	/**
	 * Test the current version of tomtom and check whether it is compatible
	 * with the one provided by the configuration file.
	 * @return boolean samtoolsState
	 * @throws IOException
	 */
	private boolean testTomtom() throws IOException {
		boolean isCompatible = false;
		
		String exe = this.extProgMap.get(ExtProgType.TOMTOM).getExecutable();
		String path = this.extProgMap.get(ExtProgType.TOMTOM).getPath();
		String[] command = new String[2];
		command[0]=path + "/" + exe;
		command[1]="--version"; 
		
		// Define pattern for the expected qpms9 version.
		Pattern bowtie2Exp = Pattern.compile(this.extProgMap.get(ExtProgType.TOMTOM).getVersion());
		
		// Define pattern for bowtie2 version.
		Pattern bowtie2Pat = Pattern.compile("\\A(\\S.+)");
		
		// Define local variable line to save read lines.
	    String line;
	    
	    // Start an external process with the pre-defined command array.
	    Process process = Runtime.getRuntime().exec(command);
	    
	    // Read the STDIN from the unix process.
	    Reader r = new InputStreamReader(process.getInputStream());
	    
	    // Read line by line using the BufferedReader.
	    BufferedReader in = new BufferedReader(r);
	    while((line = in.readLine()) != null) {
	    	
	    	// Check each line whether we find the line with the version.
	    	Matcher samtoolsMatcher = bowtie2Pat.matcher(line);
	    	
	    	// If the current version was found then check whether the expected and the observed
	    	// version are compatible. Return true if this is the case.
	    	if (samtoolsMatcher.find()) {
	    		this.extProgMap.get(ExtProgType.TOMTOM).setSeenVersion(samtoolsMatcher.group(1));
	    		Matcher equalPat = bowtie2Exp.matcher(this.extProgMap.get(ExtProgType.TOMTOM).getSeenVersion());
	    		if (equalPat.find()) {
	    			isCompatible = true;
	    		}
	    	}
	    }
	    in.close();
	    
	    return isCompatible;
	}
	
	/* Currently qpms9 cannot be tested! No such option included in the current version of qpms9!
	/**
	 * Test the current version of qpms9 and check whether it is compatible
	 * with the one provided by the configuration file.
	 * @return boolean samtoolsState
	 * @throws IOException
	 /
	private boolean testQpms9() throws IOException {
		boolean correctVersion = false;
		
		String exe = this.extProgMap.get(ExtProgType.QPMS9).getExecutable();
		String path = this.extProgMap.get(ExtProgType.QPMS9).getPath();
		String[] command = new String[2];
		command[0]=path + "/" + exe;
		command[1]="--version"; 
		
		// Define pattern for the expected qpms9 version.
		Pattern bowtie2Exp = Pattern.compile(this.extProgMap.get(ExtProgType.QPMS9).getVersion());
		
		// Define pattern for bowtie2 version.
		Pattern bowtie2Pat = Pattern.compile("\\A/usr/bin/bowtie2-align-s version (.+)");
		
		// Define local variable line to save read lines.
	    String line;
	    
	    // Start an external process with the pre-defined command array.
	    Process process = Runtime.getRuntime().exec(command);
	    
	    // Read the STDIN from the unix process.
	    Reader r = new InputStreamReader(process.getInputStream());
	    
	    // Read line by line using the BufferedReader.
	    BufferedReader in = new BufferedReader(r);
	    while((line = in.readLine()) != null) {
	    	
	    	// Check each line whether we find the line with the version.
	    	Matcher samtoolsMatcher = bowtie2Pat.matcher(line);
	    	
	    	// If the current version was found then check whether the expected and the observed
	    	// version are compatible. Return true if this is the case.
	    	if (samtoolsMatcher.find()) {
	    		this.extProgMap.get(ExtProgType.QPMS9).setSeenVersion(samtoolsMatcher.group(1));
	    		Matcher equalPat = bowtie2Exp.matcher(this.extProgMap.get(ExtProgType.QPMS9).getSeenVersion());
	    		if (equalPat.find()) {
	    			correctVersion = true;
	    		}
	    	}
	    }
	    in.close();
	    
	    return correctVersion;
	}
	*/
	
	/**
	 * Test the current version of cdhit and check whether it is compatible
	 * with the one provided by the configuration file.
	 * @return boolean samtoolsState
	 * @throws IOException
	 */
	private boolean testCdhit() throws IOException {
		boolean isCompatible = false;
		
		String exe = this.extProgMap.get(ExtProgType.CDHIT).getExecutable();
		String path = this.extProgMap.get(ExtProgType.CDHIT).getPath();
		String[] command = new String[2];
		command[0]=path + "/" + exe;
		command[1]="2>&1"; 
		
		// Define pattern for the expected cdhit version.
		Pattern bowtie2Exp = Pattern.compile(this.extProgMap.get(ExtProgType.CDHIT).getVersion());
		
		// Define pattern for bowtie2 version.
		Pattern bowtie2Pat = Pattern.compile("\\A\\t\\t====== CD-HIT version (\\S+)");
		
		// Define local variable line to save read lines.
	    String line;
	    
	    // Start an external process with the pre-defined command array.
	    Process process = Runtime.getRuntime().exec(command);
	    
	    // Read the STDIN from the unix process.
	    Reader r = new InputStreamReader(process.getInputStream());
	    
	    // Read line by line using the BufferedReader.
	    BufferedReader in = new BufferedReader(r);
	    while((line = in.readLine()) != null) {
	    	
	    	// Check each line whether we find the line with the version.
	    	Matcher samtoolsMatcher = bowtie2Pat.matcher(line);
	    	
	    	// If the current version was found then check whether the expected and the observed
	    	// version are compatible. Return true if this is the case.
	    	if (samtoolsMatcher.find()) {
	    		this.extProgMap.get(ExtProgType.CDHIT).setSeenVersion(samtoolsMatcher.group(1));
	    		Matcher equalPat = bowtie2Exp.matcher(this.extProgMap.get(ExtProgType.CDHIT).getSeenVersion());
	    		if (equalPat.find()) {
	    			isCompatible = true;
	    			break;
	    		}
	    	}
	    }
	    in.close();
	    
	    return isCompatible;
	}
	
	/**
	 * Test the current version of bowtie2 and check whether it is compatible
	 * with the one provided by the configuration file.
	 * @return boolean samtoolsState
	 * @throws IOException
	 */
	private boolean testBowtie2() throws IOException {
		boolean isCompatible = false;
		
		String exe = this.extProgMap.get(ExtProgType.BOWTIE2).getExecutable();
		String path = this.extProgMap.get(ExtProgType.BOWTIE2).getPath();
		String[] command = new String[2];
		command[0]=path + "/" + exe;
		command[1]="--version"; 
		
		// Define pattern for the expected bowtie2 version.
		Pattern bowtie2Exp = Pattern.compile(this.extProgMap.get(ExtProgType.BOWTIE2).getVersion());
		
		// Define pattern for bowtie2 version.
		Pattern bowtie2Pat = Pattern.compile("\\A/usr/bin/bowtie2-align-s version (\\S+)");
		
		// Define local variable line to save read lines.
	    String line;
	    
	    // Start an external process with the pre-defined command array.
	    Process process = Runtime.getRuntime().exec(command);
	    
	    // Read the STDIN from the unix process.
	    Reader r = new InputStreamReader(process.getInputStream());
	    
	    // Read line by line using the BufferedReader.
	    BufferedReader in = new BufferedReader(r);
	    while((line = in.readLine()) != null) {
	    	
	    	// Check each line whether we find the line with the version.
	    	Matcher samtoolsMatcher = bowtie2Pat.matcher(line);
	    	
	    	// If the current version was found then check whether the expected and the observed
	    	// version are compatible. Return true if this is the case.
	    	if (samtoolsMatcher.find()) {
	    		this.extProgMap.get(ExtProgType.BOWTIE2).setSeenVersion(samtoolsMatcher.group(1));
	    		Matcher equalPat = bowtie2Exp.matcher(this.extProgMap.get(ExtProgType.BOWTIE2).getSeenVersion());
	    		if (equalPat.find()) {
	    			isCompatible = true;
	    		}
	    	}
	    }
	    in.close();
	    
	    return isCompatible;
	}
	
	/**
	 * Test the current version of samtools and check whether it is compatible
	 * with the one provided by the configuration file.
	 * @return boolean samtoolsState
	 * @throws IOException
	 */
	private boolean testSamtools() throws IOException {
		boolean isCompatible = false;
		
		String samExe = this.extProgMap.get(ExtProgType.SAMTOOLS).getExecutable();
		String samPath = this.extProgMap.get(ExtProgType.SAMTOOLS).getPath();
		String[] command = new String[2];
		command[0]=samPath + "/" + samExe;
		command[1]="--version"; 
		
		// Define pattern for the expected samtool version.
		Pattern samtoolsExp = Pattern.compile(this.extProgMap.get(ExtProgType.SAMTOOLS).getVersion());
		
		// Define pattern for samtools version.
		Pattern samtoolsPat = Pattern.compile("\\Asamtools (\\S+)");
		
		// Define local variable line to save read lines.
	    String line;
	    
	    // Start an external process with the pre-defined command array.
	    Process process = Runtime.getRuntime().exec(command);
	    
	    // Read the STDIN from the unix process.
	    Reader r = new InputStreamReader(process.getInputStream());
	    
	    // Read line by line using the BufferedReader.
	    BufferedReader in = new BufferedReader(r);
	    while((line = in.readLine()) != null) {
	    	
	    	// Check each line whether we find the line with the version.
	    	Matcher samtoolsMatcher = samtoolsPat.matcher(line);
	    	
	    	// If the current version was found then check whether the expected and the observed
	    	// version are compatible. Return true if this is the case.
	    	if (samtoolsMatcher.find()) {
	    		this.extProgMap.get(ExtProgType.SAMTOOLS).setSeenVersion(samtoolsMatcher.group(1));
	    		Matcher equalPat = samtoolsExp.matcher(this.extProgMap.get(ExtProgType.SAMTOOLS).getSeenVersion());
	    		if (equalPat.find()) {
	    			isCompatible = true;
	    		}
	    	}
	    }
	    in.close();
	    
	    return isCompatible;
	}
	
	/**
	 * Reads configuration file and keeps track of paths for required programs.
	 * @param String cPath
	 */
	public void readConfig() { 
		
		try {
			File configFile = new File (this.configurationPath);
			
			// Test for existing configuration file.
			if (!configFile.exists()) {
				String message = "Error: Configuration file \"config.txt\" not found in path \"" + this.configurationPath + "\""; 
				throw new FileNotFoundException(message);
			}
			
			FileReader fReader = new FileReader(configFile);
			
			BufferedReader bReader = new BufferedReader(fReader);
			
			String line = null;
			
			while ((line = bReader.readLine()) != null) {
				
				// Save each entry from the config file.
				// Entries should have the format:
				// "TYPE:/path/bin:exename:version"
				// e.g.
				// "SAMTOOLS:/usr/local/bin:samtools:1.3.1"
				String[] configEntry = line.split(":");
				
				ExtProgType programType = ExtProgType.UNDEFINED;
				
				programType = this.identifyExtProgType (configEntry[0]);
				
				switch (programType) {
					case SAMTOOLS:
						ExternalProgram newSamtools = new Samtools (
								configEntry[1], configEntry[2], configEntry[3]);
						this.extProgMap.put(ExtProgType.SAMTOOLS, newSamtools);
						break;
					case BOWTIE2:
						ExternalProgram newBowtie2 = new Bowtie2 (
								configEntry[1], configEntry[2], configEntry[3]);
						this.extProgMap.put(ExtProgType.BOWTIE2, newBowtie2);
						break;
					case CDHIT:
						ExternalProgram newCdhit = new Cdhit (
								configEntry[1], configEntry[2], configEntry[3]);
						this.extProgMap.put(ExtProgType.CDHIT, newCdhit);
						break;
					case QPMS9:
						ExternalProgram newQpms9 = new Qpms9 (
								configEntry[1], configEntry[2], configEntry[3]);
						this.extProgMap.put(ExtProgType.QPMS9, newQpms9);
						break;
					case TOMTOM:
						ExternalProgram newTomtom = new Tomtom (
								configEntry[1], configEntry[2], configEntry[3]);
						this.extProgMap.put(ExtProgType.TOMTOM, newTomtom);
						break;
					case UNDEFINED:
						System.out.println("WARN: Entry \"" + line + "\" unknown");
						break;
					default:
						System.out.println("WARN: \"" + programType + "\" unknown.");
						break;
				}
			} 
			
			// Close the bReader.
			bReader.close();
		} 
		
		// Catch "write-read privileges" error for the configuration file.
		catch (SecurityException es) {
			System.err.println(es.getMessage());
			es.printStackTrace();
		}
		
		// Catch IOException for the bReader.
		catch (IOException ei) {
			System.err.println(ei.getMessage());
			ei.printStackTrace();
		}
		
		// Catch unspecified exception.
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Private method for identification of external programs.
	 * @param String input
	 * @return ExtProgType
	 */
	private ExtProgType identifyExtProgType (String input) {

		// Identify any of the following known external programs.
		if (input.equals(ExtProgType.SAMTOOLS.toString()))
			return  ExtProgType.SAMTOOLS; 
		else if (input.equals(ExtProgType.BOWTIE2.toString()))
			return  ExtProgType.BOWTIE2;
		else if (input.equals(ExtProgType.CDHIT.toString()))
			return ExtProgType.CDHIT;
		else if (input.equals(ExtProgType.QPMS9.toString()))
			return ExtProgType.QPMS9;
		else if (input.equals(ExtProgType.TOMTOM.toString()))
			return ExtProgType.TOMTOM;
		
		// If it is nothing from above return "UNDEFINED".
		return ExtProgType.UNDEFINED;
	}
	
	// TODO: Include private method which validates the entries of the config file.

}
