package externalStorage;

// Imports.

// Java I/O imports.

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class creates objects which hold the path to a (text) file containing
 * the predicted quorum planted motifs (qPM) from the quorum planted motif search (qPMS)
 * by the program qPMS9.
 * @author Christopher Kraus
 *
 */
public class QPMS9File {

	// Variables.
	
	// Save the file containing the quorum planted motifs (qPM).
	private File file;
	
	// Logger.
	private Logger logger;
	
	// Number of predicted qPMs.
	private int numberQPMs;
	
	// Current State of the file.
	private FileState fileState;
	
	// Constructors.
	
	public QPMS9File (File file) {
		
		// Save the file during object construction.
		this.file = file;
		
		// Generate a new logger for this class.
		this.logger = Logger.getLogger(this.getClass().getName());
		
		// Assess the number of qPMs.
		this.fileState = this.countQuorumPlantedMotifs();
		
		// Log the success of creating the file.
		this.logger.log(Level.INFO, "File: \"" + this.file.getAbsolutePath()
				+ "\" has state: " + this.fileState.toString());
		
		// Log the number of found qPMs.
		this.logger.log(Level.INFO, "File: \"" + this.file.getAbsolutePath()
				+ " \" contains " + this.numberQPMs + " predicted quorum planted motifs");
	}
	
	// Methods.
	
	private FileState countQuorumPlantedMotifs() {
			
		// Counter to count the lines.
		int counter = 0;
		
		try {
			
			
			// Initialize the readers to read the file.
			FileReader fr = new FileReader(this.file);
			BufferedReader br = new BufferedReader (fr);
			
			// Read the first line into "line".
			String line = br.readLine();
			
			// Read all remaining lines.
			while (null != line ) {
				
				// Increment counter for each line.
				counter ++;
				
				// Read next line.
				line = br.readLine();
			}
			
			// Close the readers.
			br.close();
			fr.close();
			
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
		}
		
		this.numberQPMs = counter;
		return FileState.AVAILABLE;
	}
	
	// Getters.
	
	/**
	 * Getter returns the number of predicted quorum planted motifs
	 * found by the qPMS9 program.
	 * @return int numberQPMs
	 */
	int getNumberOfQPMs() {
		return this.numberQPMs;
	}
	
	/**
	 * Getter returns a File object of the saved qPMS9 file.
	 * @return File qPMS9file
	 * @see java.io.File
	 */
	File getQPMSFile() {
		return this.file;
	}
	
	/**
	 * Getter returns the current state of the qPM file.
	 * @return FileState state
	 * @see externalStorage.FileState
	 */
	FileState getQPMSFileState() {
		return this.fileState;
	}
	
	// End Getters.
	
}
