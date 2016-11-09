package core;

// Imports.

// Java I/O imports.
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class CheckExternalProgrammes {
	
	// Constants.
	private final String SAMTOOLS = "samtools";
	private final String CDHIT = "cdhit";
	private final String QPMS9 = "qpms9";
	private final String BOWTIE2 = "bowtie2";
	private final String TOMTOM = "tomtom";
	
	// Variables.
	private String configurationPath;
	
	// TODO: change this to an appropriate format.
	private String configEntries;
	
	public CheckExternalProgrammes(String configPath) {
		this.configurationPath = configPath;
		// Read configuration file.
		this.readConfig(this.configurationPath);
		
		// TODO: Parse the read configuration entries.
	}
	
	private void readConfig(String cPath) { 
		
		try {
			File configFile = new File (cPath);
			
			// Test for existing configuration file.
			if (!configFile.exists()) {
				String message = "Error: Configuration file \"config.txt\" not found in path \"" + cPath + "\""; 
				throw new FileNotFoundException(message);
			}
			
			FileReader fReader = new FileReader(configFile);
			
			BufferedReader bReader = new BufferedReader(fReader);
			
			String line = null;
			
			while ((line = bReader.readLine()) != null) {
				this.configEntries += line;
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

}
