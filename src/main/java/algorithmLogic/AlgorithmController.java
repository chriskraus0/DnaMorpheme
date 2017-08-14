package algorithmLogic;

// Imports.

// Java utility imports.
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

// Java I/O imports.
import java.io.File;

// Project-specific imports.
import core.common.ModuleType;
import core.CoreController;
import core.ModuleBuilder;
import core.ModuleNode;
import core.ModuleObserver;
import core.PhysicalConstants;

import modules.commands.Commands;

import externalStorage.ExtStorage;
import externalStorage.ExtStorageType;

/**
 * This central controller executes the logic of the algorithm.
 * @author Christopher Kraus
 *
 */

public class AlgorithmController {

	// Variables.
	
	// Algorithm relevant parameters.
	
	// Percentage of all sequences per cluster.
	private double cdHitClusterCoverage;
	
	// Identity threshold for Cd-Hit.
	private double cdHitIdentity;
	
	// Distance as Hamming distance between two motifs.
	private int hammingDistance;
	
	// Longest possible predicted consensus of a motif.
	private int longestConsensusSeq;
	
	// HashMap holding the relevant data; includes an DataSetID for each data set.
	private HashMap <Integer, DataSet> dataSetMap;
	
	// Variable which holds the current DataSetID;
	private int currDataSetID;
	
	// Logger.
	private Logger logger;
	
	// Keep a reference to the ModuleBuilder.
	// This factory will be used to create new jobs.
	private ModuleBuilder moduleBuilder;
	
	// Keep a reference of the ModuleObserver.
	private ModuleObserver moduleObserver;
	
	// End Variables.
	
	// Constructors.
	public AlgorithmController() {
		
		// Initialize the logger.
		this.logger = Logger.getLogger(this.getClass().getName());
		
		// Initialize the dataSetMap.
		this.dataSetMap = new HashMap <Integer, DataSet>();
		
		// Initialize the current data set ID.
		this.currDataSetID = 1;
		
	}
	
	// End constructors.
	
	// Methods.
	
	// Setters.
	/**
	 * Setter sets the moduleBuilder.
	 * @param ModuleBuilder mBuilder
	 */
	private void setModuleBuilder(ModuleBuilder mBuilder) {

		// Initialize the factory to build more modules.
		this.moduleBuilder = mBuilder;
	}
	
	private void setModuleObserver(ModuleObserver mObserver) {
		this.moduleObserver = mObserver;
	}
	// End setters.
	
	// Getters.
	
	private void setNewDataSet(ExtStorage extStorage, ExtStorageType extType) {
		DataSet newDataSet = new DataSet();
		// TODO: Add routines in DataSet to save this new information
		// this.dataSetMap.put(this.currDataSetID, value);
	}
	
	// End getters.
	
	/**
	 * This method places a new job into the queue of creation and on a new thread.
	 * @param ModuleType mType
	 * @param HashMap<Commands, String> command
	 * @see modules.commands.Commands
	 * @return int moduleID
	 */
	public int orderNewJob (ModuleType mType, HashMap<Commands, String> command) {
		
		// Save the moduleID of a new job.
		int moduleID;
		
		switch(mType) {
			case CDHIT_JOB:
				moduleID = this.moduleBuilder.createNewCdHitJob(command);
				break;
			case QPMS9_JOB:
				moduleID = this.moduleBuilder.createNewQpms9Job(command);
				break;
			case BOWTIE2_JOB: 
				moduleID = this.moduleBuilder.createNewBowtie2Job(command);
				break;
			case SAMTOOLS_JOB:
				moduleID = this.moduleBuilder.createInputTestJob(command);
				break;
			default:
				// An value of "-1" indicates an error.
				moduleID = -1;
		}
		
		return moduleID;
	}
	
	public void notifyAlgorithmController(int moduleID) {
		// TODO: empty stub		
	}
	
	/**
	 * Basic method which runs the algorithm. It takes 2 arguments. 
	 * The fileName defines the name of the fasta file.
	 * The targetPath defines the path where results will be saved.
	 * @param String fileName
	 * @param String targetPath
	 */
	public void runAlgorithm (String fileName, String targetPath) {
		
		// Convert the String of of the fasta file into a proper file path.
		File fastaFile = new File(fileName);
		
		// Convert the path where the results will be saved into a proper path.
		File absTargetPath = new File(targetPath);
		
		// Determine the coverage and number of clusters.
		
		// Define the clusting command for Cd-Hit:
		HashMap<Commands, String> firstClusteringCommand = new HashMap<Commands, String>();
		
		// Retrieve number of cpu cores.
		String cpuNum = Integer.toString(PhysicalConstants.getCpuCoreNum());
		
		// Define the number of usable cpu cores for Cd-Hit.
		firstClusteringCommand.put(Commands.T, cpuNum);
		
		// Define the absolute path to the fasta file.
		firstClusteringCommand.put(Commands.i, fastaFile.getAbsolutePath());
		
		// Define first Cd-Hit sequence identity threshold.
		this.cdHitIdentity = 0.9;	
		firstClusteringCommand.put(Commands.c, Double.toString(this.cdHitIdentity));
		
		// Define the output file.
		firstClusteringCommand.put(Commands.o, absTargetPath.getAbsolutePath());
		
		// Create a new Cd-Hit job.
		int cdHitModuleId = this.orderNewJob(ModuleType.CDHIT_JOB, firstClusteringCommand);
		
		// Start the Cd-Hit job.
		String firstCdHitJob = this.moduleBuilder.prepareJobs(cdHitModuleId);
		try {
			moduleBuilder.startJobs(firstCdHitJob);
		} catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * main
	 * @param String[] args
	 */
	public static void main (String[] args) {
		
		// File name of the sequence fasta file.
		String fileName = args[0];
		
		// Target path.
		String targetPath = args[1];
		
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		
		// Create new AlgorithmController.
		AlgorithmController algorithmController = new AlgorithmController();
		
		// Create the module builder.
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder(algorithmController);
		
		// Set the ModuleBuilder for AlgorithmController.
		algorithmController.setModuleBuilder(moduleBuilder);
		
		// Get ModuleObserver.
		ModuleObserver moduleObserver = moduleBuilder.getJobcontroller().getModuleObserver();
		
		// Set the ModuleObserver.
		algorithmController.setModuleObserver(moduleObserver);
		
		// Run the algorithm.
		algorithmController.runAlgorithm(fileName, targetPath);
	}
}
