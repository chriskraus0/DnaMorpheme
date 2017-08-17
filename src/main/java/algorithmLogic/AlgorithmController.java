package algorithmLogic;

// Imports.

// Java utility imports.
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

// Java I/O imports.
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

// Project-specific imports.
import core.common.ModuleType;
import core.CoreController;
import core.ModuleBuilder;
import core.ModuleNode;
import core.ModuleObserver;
import core.PhysicalConstants;
import core.ExtStorageController;

import modules.commands.Commands;
import modules.SamtoolsJobType;
import externalStorage.BAMExtStorage;
import externalStorage.Bowtie2ExtStorage;
import externalStorage.CdHitExtStorage;
import externalStorage.ExtStorage;
import externalStorage.ExtStorageType;
import externalStorage.QPMS9ExtStorage;
import externalStorage.SamtoolsExtStorage;

// Project-specific exceptions
import algorithmLogic.exceptions.NotEnoughIdentityException;

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
	
	// Distance as hamming distance between two motifs.
	// TODO: ATTENTION hard-coded start parameter.
	private int hammingDistance = 3;
	
	// Longest possible predicted consensus of a motif.
	private int longestConsensusSeq;
	
	// THIS IS OBSOLETE AT THE MOMENT.
	// HashMap holding the relevant data; includes an DataSetID for each data set.
	//private HashMap <Integer, DataSet> dataSetMap;
	
	// Variable holding the dataSet.
	private DataSet dataSet;
	
	// Variable which holds the current DataSetID;
	private int currDataSetID;
	
	// Path to the target folder.
	private File absTargetPath;
	
	// Logger.
	private Logger logger;
	
	// Keep a reference to the ModuleBuilder.
	// This factory will be used to create new jobs.
	private ModuleBuilder moduleBuilder;
	
	// Keep a reference of the ModuleObserver.
	private ModuleObserver moduleObserver;
	
	// Keep a reference to the ExtStorageController.
	private ExtStorageController extStorageController;
	
	// End Variables.
	
	// Constructors.
	public AlgorithmController() {
		
		// Initialize the logger.
		this.logger = Logger.getLogger(this.getClass().getName());
		
		// Initialize the dataSetMap.
		//this.dataSetMap = new HashMap <Integer, DataSet>();
		
		// Initialize the DataSet.
		this.dataSet = new DataSet();
		
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
	
	private void setExtStorageController(ExtStorageController eController){
		this.extStorageController = eController;
	}
	
	// End setters.
	
	// Getters.
		
	// End getters.
	
	/**
	 * This method places a new job into the queue of creation and on a new thread.
	 * @param ModuleType mType
	 * @param HashMap<Commands, String> command
	 * @see modules.commands.Commands
	 * @return int moduleID
	 */
	public int orderNewJob (ModuleType mType, HashMap<Commands, String> command, double parameter) {
		
		// Save the moduleID of a new job.
		int moduleID;
		
		switch(mType) {
			case CDHIT_JOB:
				moduleID = this.moduleBuilder.createNewCdHitJob(command, parameter);
				break;
			case QPMS9_JOB:
				moduleID = this.moduleBuilder.createNewQpms9Job(command, parameter);
				break;
			case BOWTIE2_JOB: 
				moduleID = this.moduleBuilder.createNewBowtie2Job(command);
				break;
			case SAMTOOLS_JOB:
				moduleID = this.moduleBuilder.createNewSamtoolsJob(command, parameter);
				break;
			default:
				// An value of "-1" indicates an error.
				moduleID = -1;
		}
		
		return moduleID;
	}
	
	public void notifyAlgorithmController(String extID, ExtStorageType extType) throws NotEnoughIdentityException {
		
		switch (extType) {
			case CDHIT_EXT_STORAGE:
				
				// Save the Cd-Hit cluster as a new DataSet.
				this.dataSet.addCdHitCluster( (CdHitExtStorage) 
						this.extStorageController.getExtStorage(extID) );
				
				// If cluster is too small reduce identity value by 5% and try again.
				if ( ((CdHitExtStorage) this.extStorageController.getExtStorage(extID))
					.areClustersTooSmall() ) {
					
					// Retrieve the name of the saved clustering file.
					File file = ((CdHitExtStorage) this.extStorageController.getExtStorage(extID)).getFile();
					
					// Get last identity value.
					double lastIdentity = ((CdHitExtStorage) this.extStorageController.getExtStorage(extID)).getIdentity();
					
					// Calculate new identity value.
					// TODO: ATTENTION this value is hard coded!
					double identity = lastIdentity - 0.05;
					
					// Check if sufficient identity is still available.
					// Otherwise throw exception and stop the processing (abort).
					if (identity >= 0.6) {
						
						// Parse the Cd-Hit command.
						HashMap<Commands,String> command = this.parseCdhitCommand(
								file, identity);
						
						// Request a new Cd-Hit job.
						int moduleID = this.orderNewJob(ModuleType.CDHIT_JOB, command, identity);
						
						// Prepare the job.
						String cdHitJobNodeName = moduleBuilder.prepareJobs(moduleID);
						
						// Start new thread for "cdHitJobNodeName".
						try {
							moduleBuilder.startJobs(cdHitJobNodeName);
						} catch (InterruptedException intE) {
							this.logger.log(Level.SEVERE, intE.getMessage());
							intE.printStackTrace();
						} catch (Exception e) {
							this.logger.log(Level.SEVERE, e.getMessage());
							e.printStackTrace();
						}
						
						// Write a log.
						this.logger.log(Level.INFO, "Cd-Hit job with an identity of "
								+ identity
								+ " started."
								+ " Job-ID:" + moduleID);
					} else 
						throw new NotEnoughIdentityException("ABBORT: "
								+ "Sequences show too much similarity."
								+ " Cd-Hit clustering yield one cluster with more than 50% identity.");
				} 
				
				// Otherwise call qPMS9 command. 
				else {
					
					// Retrieve the name of the saved clustering file.
					File file = ((CdHitExtStorage) this.extStorageController.getExtStorage(extID)).getFile();
					
					// Parse the qPMS9 command.
					HashMap<Commands,String> command = this.parseQPMS9Command(file, this.hammingDistance);
					
					// Request a new qPMS9 job.
					int moduleID = this.orderNewJob(ModuleType.QPMS9_JOB, command, this.hammingDistance);
					
					// Prepare the job.
					String qPMS9JobNodeName = moduleBuilder.prepareJobs(moduleID);
					
					// Start new thread for "qPMS9JobNodeName".
					try {
						moduleBuilder.startJobs(qPMS9JobNodeName);
					} catch (InterruptedException intE) {
						this.logger.log(Level.SEVERE, intE.getMessage());
						intE.printStackTrace();
					} catch (Exception e) {
						this.logger.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
					}
					
					// Write a log.
					this.logger.log(Level.INFO, "qPMS9 job with an hamming distance of "
							+ this.hammingDistance
							+ " started."
							+ " Job-ID:" + moduleID);
				}
				
				break;
				
			case QPMS9_EXT_STORAGE:
				
				// Save the qPMS9 motifs as a new DataSet.
				this.dataSet.addQpms9File((QPMS9ExtStorage) 
						this.extStorageController.getExtStorage(extID) );
				
				// Get the provided parameter to qPMS9 (hammingDistance).
				double parameter = ((QPMS9ExtStorage) 
						this.extStorageController.getExtStorage(extID) ).getHammingDistance();
				
				// Define the name of the new SAM file.
				String samFileName = ((QPMS9ExtStorage) 
						this.extStorageController.getExtStorage(extID) ).getFile().getAbsolutePath()
						+ ".bowtie2.sam";
				
				File samFile = new File(samFileName);
				
				// Define name for a new fastq file.
				String fastqFileName = ((QPMS9ExtStorage) 
						this.extStorageController.getExtStorage(extID) ).getFile().getAbsolutePath()
						+ ".fastq";
				File fastqFile = new File(fastqFileName);
				
				// Convert qPMS9 motifs to fastq format.
				ArrayList<String> motifs = ((QPMS9ExtStorage) 
						this.extStorageController.getExtStorage(extID) ).getMotifs();
				
				this.convert2fastq(motifs, fastqFileName);
				
				// Parse bowtie2 command.
				
				HashMap<Commands,String> command = this.parseBowtie2Command(fastqFile, samFile);
				
				// Start a new mapping process.
				int moduleID = this.orderNewJob(ModuleType.BOWTIE2_JOB, command, parameter);
				
				// Prepare the job.
				String bowtie2JobNodeName = moduleBuilder.prepareJobs(moduleID);
				
				// Start new thread for "bowti2JobNodeName".
				try {
					moduleBuilder.startJobs(bowtie2JobNodeName);
				} catch (InterruptedException intE) {
					this.logger.log(Level.SEVERE, intE.getMessage());
					intE.printStackTrace();
				} catch (Exception e) {
					this.logger.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
				
				// Write a log.
				this.logger.log(Level.INFO, "bowtie2 job with an hamming distance of "
						+ parameter
						+ " started."
						+ " Job-ID:" + moduleID);
				
				break;
				
			case BOWTIE2_EXT_STORAGE:
				
				// Save the SAM file as a new DataSet.
				this.dataSet.addBowtie2SAMfile((Bowtie2ExtStorage) 
						this.extStorageController.getExtStorage(extID) );
				
				// Get the provided parameter from bowtie2.
				double bowtie2Parameter = ((Bowtie2ExtStorage) 
						this.extStorageController.getExtStorage(extID) ).getParameter();
				
				// Get the file name of the SAM file created by bowtie2.
				File bowtie2SAMfile = ((Bowtie2ExtStorage) 
						this.extStorageController.getExtStorage(extID) ).getFile();
				
				// Define the output file.
				File samtoolsOutputFile = new File(bowtie2SAMfile.getAbsolutePath() + ".bam");
				
				// Convert the resulting SAM to a BAM file.
				HashMap<Commands,String> samtoolsCommand = this.parseSamtoolsSAM2BAM(bowtie2SAMfile, samtoolsOutputFile);
				
				// Start the Samtools job.
				int samtoolsModuleID = this.orderNewJob(ModuleType.SAMTOOLS_JOB, samtoolsCommand, bowtie2Parameter);
				
				// Prepare the job.
				String samtoolsConversionJobNodeName = moduleBuilder.prepareJobs(samtoolsModuleID);
				
				// Start new thread for "samtoolsConversionJobNodeName".
				try {
					moduleBuilder.startJobs(samtoolsConversionJobNodeName);
				} catch (InterruptedException intE) {
					this.logger.log(Level.SEVERE, intE.getMessage());
					intE.printStackTrace();
				} catch (Exception e) {
					this.logger.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
				
				// Write a log.
				this.logger.log(Level.INFO, "Samtools SAM to BAM conversion job started. Hamming distance: "
						+ bowtie2Parameter
						+ " "
						+ " Job-ID:" + samtoolsModuleID);
				
				break;
				
			case SAMTOOLS_EXT_STORAGE:
				
				// Save the SAM file as a new DataSet.
				this.dataSet.addSamtoolsSAMfile((SamtoolsExtStorage) 
						this.extStorageController.getExtStorage(extID) );
				
				// TODO: This a feature currently not used, or required.
				
				break;
				
			case TOMTOM_EXT_STORAGE:
				// TODO: Not implemented yet.
				break;
				
			
		}
	}
	
	public void notifyAlgorithmController(String extID, ExtStorageType extType, SamtoolsJobType samtoosJobType) {
		switch (extType) {
		case BAM_EXT_STORAGE:
			
			// Save the BAM file as a new DataSet.
			this.dataSet.addBamFile((BAMExtStorage) 
					this.extStorageController.getExtStorage(extID) );
			break;
		}
	}
	
	/**
	 * Basic method which runs the algorithm. It takes 2 arguments. 
	 * The fileName defines the name of the fasta file.
	 * The targetPath defines the path where results will be saved.
	 * @param String fileName
	 * @param String targetPath
	 */
	public void runAlgorithm (String fileName, String targetPath) {
		
		/* RUN THE FIRST CLUSTERING. */
		
		// Convert the String of of the fasta file into a proper file path.
		File fastaFile = new File(fileName);
		
		// Convert the path where the results will be saved into a proper path.
		File absTargetPath = new File(targetPath);
		
		// Remember the target path in a object-wide variable.
		this.absTargetPath = absTargetPath;
		
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
		firstClusteringCommand.put(Commands.o, absTargetPath.getAbsolutePath() + PhysicalConstants.getPathSeparator() + "firstClustering0.9");
		
		// Create a new Cd-Hit job.
		int cdHitModuleId = this.orderNewJob(ModuleType.CDHIT_JOB, firstClusteringCommand, this.cdHitIdentity);
		
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
		
		/* END OF THE FIRST CLUSTERING */
		
	}
	
	// Methods to parse commands.
	
	private HashMap<Commands,String> parseCdhitCommand (File file, double identity) {
		
		// Define the command.
		HashMap<Commands, String> command = new HashMap<Commands, String>();
		
		// Retrieve number of cpu cores.
		String cpuNum = Integer.toString(PhysicalConstants.getCpuCoreNum());
		
		// Define the number of usable cpu cores for Cd-Hit.
		command.put(Commands.T, cpuNum);
				
		// Define the absolute path to the fasta file.
		command.put(Commands.i, file.getAbsolutePath());
		
		// Identity to String.
		String ident = Double.toString(identity);
				
		// Define identity parameter.
		command.put(Commands.c,ident);
			
		// Define the output file.
		command.put(Commands.o, this.absTargetPath.getAbsolutePath() 
				+ PhysicalConstants.getPathSeparator() + "cdHitClust" + ident);
		
		return command;
	}
	
	private HashMap<Commands,String> parseQPMS9Command (File file, double hammingDistance) {
		
		// Parse the hamming distance as a String.
		String hamDist = Double.toString(hammingDistance);
		
		// Define the command.
		HashMap<Commands, String> command = new HashMap<Commands, String>();
		
		// TODO: Add parameter for usage of multiple cores.
		
		// Provide the parameter to the file.
		command.put(Commands.fasta, file.getAbsolutePath());
		
		// Define constant length of each identified motif.
		// TODO: ATTENTION hard-coded parameter!
		command.put(Commands.l, "8");
		
		// Define the hammingDistance.
		command.put(Commands.d, hamDist);
		
		// Define the quorum coverage (percentage of minimum occurrences in the quorum).
		// TODO: ATTENTION hard-coded parameter!
		command.put(Commands.q, "100");
		
		// Define the target file.
		command.put(Commands.o, this.absTargetPath.toString() 
				+ PhysicalConstants.getPathSeparator() 
				+ file.getName() + ".qPMS." 
				+ hamDist);
		
		return command;
	}
	
	private HashMap<Commands,String> parseBowtie2Command (File file, File outputFile) {
		
		// Create new command.
		HashMap<Commands, String> command = new HashMap<Commands, String>();
		
		// Define the target path for the input.
		command.put(Commands.reference, file.getAbsolutePath());
		
		// Define the base name for the index.
		command.put(Commands.index_base, file.getName());
		
		// Define the numbers of available CPUs.
		int cpus = PhysicalConstants.getCpuCoreNum();
		command.put(Commands.p, Integer.toString(cpus));
		
		// Call the the base name of the index.
		command.put(Commands.x, file.getName());
		
		// Call the input.
		command.put(Commands.U, file.getAbsolutePath());
		
		// Define the output file.
		command.put(Commands.S, outputFile.getAbsolutePath());
		
		return command;
	}
	
	
	private HashMap<Commands,String> parseSamtoolsSAM2BAM (File samFile, File outputFile) {
	
		// Create a new command.
		HashMap<Commands, String> command = new HashMap<Commands, String>();
						
		// Define the index of the bowtie2 mapping step.
		command.put(Commands.index, samFile.getName());
		
		// Convert the SAM file into a BAM file.
		command.put(Commands.b, "");
		
		// The input is in SAM format.
		command.put(Commands.S, "");
		
		// Get the input from SAM file.
		command.put(Commands.input, samFile.getAbsolutePath());
		
		// Write the output via redirect.
		command.put(Commands.redirect, ">");
		
		// Write the output into this output file.
		command.put(Commands.output, outputFile.getAbsolutePath());
				
		return command;
	}
	
	// TODO: Continue here.
	/*private HashMap<Commands,String> parseSamtoolsSortCommand () {
		
	}*/
	
	// End command parse methods.
	
	// Conversion methods.
	
	private void convert2fastq (ArrayList<String> motifs, String fastqFile) {
		
		// TODO: Create a separate class for this operation with a distinct responsibility.
		
		// Initialize the ArrayList fastqMotifs.
		ArrayList<String> fastqMotifs = new ArrayList<String>();
		
		// Iterate through the found motifs.
		for (String i : motifs) {

			// Add a fastq header.
			String header = '@' + i + "\n";
			fastqMotifs.add(header);
			
			// Add the sequence.
			fastqMotifs.add(i + "\n");
			
			// Add the quality header.
			fastqMotifs.add('+' + "\n");
			
			// Add a pseudo quality string.
			String qualString = "";
			for (int j = 0; j < i.length(); j ++)
				qualString += "I";
			qualString += "\n";
			fastqMotifs.add(qualString);
		}
		
		// Write the fastq file.
		try {
			FileWriter fw = new FileWriter(fastqFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String i : fastqMotifs) {
				bw.write(i);
			}
			bw.close();
			fw.close();
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		}
		
	}
	// End conversion methods.
	
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
		
		// Set ExtStorageController for the AlgorithmController.
		algorithmController.setExtStorageController(moduleBuilder.getJobcontroller().getExtStorageController());
		
		// Get ModuleObserver.
		ModuleObserver moduleObserver = moduleBuilder.getJobcontroller().getModuleObserver();
		
		// Set the ModuleObserver.
		algorithmController.setModuleObserver(moduleObserver);
		
		// Run the algorithm.
		algorithmController.runAlgorithm(fileName, targetPath);
	}
}
