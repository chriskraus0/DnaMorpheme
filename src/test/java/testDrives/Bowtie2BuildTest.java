package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.HashMap;

// JUnit imports.
import junit.framework.TestCase;
import org.junit.Test;

// Project-specific imports.
import core.CoreController;
import core.ModuleBuilder;
import core.ModuleObserver;
import core.PhysicalConstants;
import core.common.ModuleState;

import modules.commands.Commands;

public class Bowtie2BuildTest extends TestCase {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public Bowtie2BuildTest() {
		// Call Logger to get new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Methods.
	
	/**
	 * JUnit testing method.
	 */
	@Test
	public void start() {
		this.testRun();
	}
	
	public void testRun () {
		
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		
		// Create the module builder.
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
			
		// Create new InputReader module.
		HashMap<Commands, String> inputReaderCommand = new HashMap<Commands, String>();
		inputReaderCommand.put(Commands.path, "testFiles" + PhysicalConstants.getPathSeparator() + "testFile5.fasta");
		int inputReaderID = moduleBuilder.createNewInputReader(inputReaderCommand);
		
		// Create new bowtie2 module.
		HashMap<Commands, String> bowtie2Command = new HashMap<Commands, String>();
		bowtie2Command.put(Commands.reference, "testFile" +  PhysicalConstants.getPathSeparator() + "testFile5.fasta");
		bowtie2Command.put(Commands.index_base, "test5");
		bowtie2Command.put(Commands.x, "tmpData/test5");
		bowtie2Command.put(Commands.U, "testFiles/testFile6.fastq");
		bowtie2Command.put(Commands.S, "tmpData" + PhysicalConstants.getPathSeparator() + "testFile6.sam");
		
		int bowtie2JobID = moduleBuilder.createNewQpms9Job(bowtie2Command);
		
		// Prepare module nodes.
		String inputReaderBowtie2JobNodeName = moduleBuilder.prepareJobs(inputReaderID, bowtie2JobID);
		
		// Start new thread for "inputReaderCdHitJobNodeName".
		try {
			moduleBuilder.startJobs(inputReaderBowtie2JobNodeName);
		} catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		// Assert tests.		
		assertEquals(ModuleState.SUCCESS, ModuleBuilder.getModule(bowtie2JobID).getModuleState());
		assertEquals(ModuleState.SUCCESS, ModuleBuilder.getModule(inputReaderID).getModuleState());
	}

}
