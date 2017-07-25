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
import java.util.HashMap;

/**
 * This class parses the SAM format and saves the results as SAMentries.
 * @see externalStorage.SAMEntry
 * @author Christopher Kraus
 *
 */

public class SamtoolsFile {

	// Variables.
	
	// SAM file.
	private File samFile;
	
	// SAM entries.
	private HashMap <Integer, SAMEntry> samEntries;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public SamtoolsFile(File file) {
		
		// Initialize the SAM file.
		this.samFile = file;
		
		// Generate new logger for this class.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Methods.
	
	public FileState parseSAM() {
		
		try {
			
			// Initialize the file readers.
			FileReader fr = new FileReader(this.samFile);
			BufferedReader br = new BufferedReader (fr);
			
			// Read first line;
			String line = br.readLine();
			
			// Read and parse all following lines;
			while (null != line) {
				// Split the SAM file into 12 fields.
				String[] fields = line.split("\\t");
				
				// Save the fields in a SAM entry object.
				SAMEntry samEntry = new SAMEntry(fields);
				
				// Save the new entry in the HashMap. 
				// Use its start position as key.
				this.samEntries.put(Integer.parseInt(fields[3]), samEntry);
				
				// Read next line.
				line = br.readLine();
			}
			
			// Close readers.
			br.close();
			fr.close();
			
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return FileState.AVAILABLE;
	}
}
