package core.common;

// Imports.

// Java I/O imports.
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class CheckExternalProgrammes {
	
	// Constants.
	private static final String SAMTOOLS = "samtools";
	private static final String CDHIT = "cdhit";
	private static final String QPMS9 = "qpms9";
	private static final String BOWTIE2 = "bowtie2";
	private static final String TOMTOM = "tomtom";
	
	// Variables.
	private String configurationPath;
	
	// TODO: change this to an appropriate format.
	private String configEntries;
	
	public CheckExternalProgrammes(String configPath) {
		this.configurationPath = configPath;
		// Read configuration file.
		this.readConfig(this.configurationPath);
		
	}
	
	private void readConfig(String cPath) { // TODO: FileNotFoundException, IOException 
		//TODO: read Configuration for all programs.
		
		try {
			File configFile = new File (cPath);
			FileReader fReader = new FileReader(configFile);
			
			BufferedReader bReader = new BufferedReader(fReader);
			
			String line = null;
			
			while ((line = bReader.readLine()) != null) {
				this.configEntries += line;
			} 
		} catch (Exception e) {
			//TODO: continue here
		}
	}

}
