package core;

// Imports.

// Java I/O imports.
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

// Java utility imports.
import java.util.Map;
import java.util.HashMap;

// Project-specific imports.
import extProgs.ExtProgType;
import extProgs.ExternalProgram;
import extProgs.Samtools;
import extProgs.Bowtie2;
import extProgs.Cdhit;
import extProgs.Qpms9;
import extProgs.Tomtom;

public class CheckExternalProgrammes {
		
	// Variables.
	private String configurationPath;
	
	// HashMap to save external programs.
	private Map <ExtProgType, ExternalProgram> extProgMap;
	
	// Constructors.
	
	public CheckExternalProgrammes(String configPath) {
		this.configurationPath = configPath;
		this.extProgMap = new HashMap <ExtProgType, ExternalProgram>();
	}
	
	// Methods.
	
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
