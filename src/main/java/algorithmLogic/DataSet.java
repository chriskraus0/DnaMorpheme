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
	
	// Uniquely mapped bam files:
	private ArrayList<BAMExtStorage> bamMappedUniqueFiles;
	
	// End variables.
	
	// Constructors.
	public DataSet() {
		// TODO: empty stub
	}
	
	// End constructors.
	
	// Methods.
	/*
	// Getters.
	public ArrayList<CdHitExtStorage> getCdHitClusters() {
		// TODO: empty stub.
		return null;
	}
	
	public ArrayList<QPMS9ExtStorage> getQPMS9files() {
		// TODO: empty stub.
		return null;
	}
	
	public ArrayList<Bowtie2ExtStorage> getBowtie2SAMfiles() {
		// TODO: empty stub.
		return null;
	}
	public ArrayList<SamtoolsExtStorage> getSamtoolsSAMfiles() {
		// TODO: empty stub.
		return null;
	}
	
	public ArrayList<BAMExtStorage> getBamFiles() {
		// TODO: empty stub.
		return null;
	}
	
	public ArrayList<BAMExtStorage> getBamMappedUniqueFiles() {
		// TODO: empty stub.
		return null;
	}

	// End getters.
	
	// Setters.
	public void addCdHitCluster(CdHitExtStorage){
		// TODO: empty stub.
	}
	
	public void addQpms9File(QPMS9ExtStorage){
		// TODO: empty stub.
	}
	
	public void addBowtie2SAMfile(Bowtie2ExtStorage){
		// TODO: empty stub.
	}
	
	public void addSamtoolsSAMfile(SamtoolsExtStorage){
		// TODO: empty stub.
	}
	
	public void addBamFile(BAMExtStorage){
		// TODO: empty stub.
	}
	
	public void addBamMappedUniqFile(BAMExtStorage){
		// TODO: empty stub.
	}
	*/
	// End setters.

}
