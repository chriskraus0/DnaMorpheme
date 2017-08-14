package algorithmLogic;

// Imports.

// Java utility imports.
import java.util.ArrayList;

// Project-specific imports.
import externalStorage.CdHitExtStorage;
import externalStorage.QPMS9ExtStorage;
import externalStorage.SamtoolsExtStorage;
import externalStorage.BAMExtStorage;
import externalStorage.Bowtie2ExtStorage;

/**
 * This class holds ArrayLists of all files which are created through running the program.
 * @author Christopher Kraus
 *
 */

public class DataSet {
	
	// Variables.
	
	// Cd-Hit Clusters.
	private ArrayList<CdHitExtStorage> cdHitClusters;
	
	// qPMS9 files.
	private ArrayList<QPMS9ExtStorage> qPMS9files;
	
	// Samtools SAM files.
	private ArrayList<SamtoolsExtStorage> samtoolsSAMfiles;
	
	// BAM files.
	private ArrayList<BAMExtStorage> bamFiles;
	
	// Uniquely mapped bam files.
	private ArrayList<BAMExtStorage> bamMappedUniqueFiles;
	
	// SAM files created by bowtie2.
	private ArrayList<Bowtie2ExtStorage> bowtie2SAMfiles;
	
	// End variables.
	
	// Constructors.
	public DataSet() {
		
		// Initialize the different ArrayLists.
		this.cdHitClusters = new ArrayList<CdHitExtStorage>();
		this.qPMS9files = new ArrayList<QPMS9ExtStorage>();
		this.samtoolsSAMfiles = new ArrayList<SamtoolsExtStorage>();
		this.bamFiles = new ArrayList<BAMExtStorage>();
		this.bamMappedUniqueFiles = new ArrayList<BAMExtStorage>();
		this.bowtie2SAMfiles = new ArrayList<Bowtie2ExtStorage>();
		
	}
	
	// End constructors.
	
	// Methods.
	
	// Getters.
	public ArrayList<CdHitExtStorage> getCdHitClusters() {
		return this.cdHitClusters;
	}
	
	public ArrayList<QPMS9ExtStorage> getQPMS9files() {
		return this.qPMS9files;
	}
	
	public ArrayList<Bowtie2ExtStorage> getBowtie2SAMfiles() {
		return this.bowtie2SAMfiles;
	}
	public ArrayList<SamtoolsExtStorage> getSamtoolsSAMfiles() {
		return this.samtoolsSAMfiles;
	}
	
	public ArrayList<BAMExtStorage> getBamFiles() {
		return this.bamFiles;
	}
	
	public ArrayList<BAMExtStorage> getBamMappedUniqueFiles() {
		return this.bamMappedUniqueFiles;
	}

	// End getters.
	
	// Setters.
	public void addCdHitCluster(CdHitExtStorage cdHitExtStorage){
		this.cdHitClusters.add(cdHitExtStorage);
	}
	
	public void addQpms9File(QPMS9ExtStorage qPMS9ExtStorage){
		this.qPMS9files.add(qPMS9ExtStorage);
	}
	
	public void addBowtie2SAMfile(Bowtie2ExtStorage bowtie2ExtStorage){
		this.bowtie2SAMfiles.add(bowtie2ExtStorage);
	}
	
	public void addSamtoolsSAMfile(SamtoolsExtStorage samtoolsExtStorage){
		this.samtoolsSAMfiles.add(samtoolsExtStorage);
	}
	
	public void addBamFile(BAMExtStorage bamExtStorage){
		this.bamFiles.add(bamExtStorage);
	}
	
	public void addBamMappedUniqFile(BAMExtStorage bamExtStorage){
		this.bamMappedUniqueFiles.add(bamExtStorage);
	}

	// End setters.

}
