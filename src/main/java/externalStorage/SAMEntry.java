package externalStorage;

/**
 * This class saves the 11 fields of a SAM file as variables.
 * @author Christopher Kraus
 *
 */

public class SAMEntry {
	
	// Variables.
	
	// Query Name.
	private String qName;
	
	// Flag.
	private int flag;
	
	// Reference Name.
	private String rName;
	
	// Mapping position on reference.
	private int pos;
	
	// Mapping quality.
	private int mapQ;
	
	// CIGAR quality string.
	private String cigar;
	
	// String name of next mapping target.
	private String rNext;
	
	// Position of next mapping target.
	private int pNext;
	
	// Template length.
	private int tLen;
	
	// Sequence.
	private String seq;
	
	// Phred64 based quality string.
	private String qual;
	
	// Length of the query.
	private int entryLength;
	
	// End Variables.
	
	// Constructors.
	public SAMEntry () {
		
	}
	
	public SAMEntry (String [] fields) {
		
		// Initialize the 11 different SAM fields.
		this.qName = fields[0];
		this.flag = Integer.parseInt(fields[1]);
		this.rName = fields[2];
		this.pos = Integer.parseInt(fields[3]);
		this.mapQ = Integer.parseInt(fields[4]);
		this.cigar = fields[5];
		this.rNext = fields[6];
		this.pNext = Integer.parseInt(fields[7]);
		this.tLen = Integer.parseInt(fields[8]);
		this.seq = fields[9];
		this.qual = fields[10];
		this.entryLength = this.seq.length();
		
	}
	
	// End Constructors.
	
	// Methods.
	
	// Getters.
	
	public String getQname() {
		return this.qName;
	}
	
	public int getFlag() {
		return this.flag;
	}
	
	public String getRname() {
		return this.rName;
	}
	
	public int getPos() {
		return this.pos;
	}
	
	public int getMapQ() {
		return this.mapQ;
	}
	
	public String getCigar() {
		return this.cigar;
	}
	
	public String getRnext() {
		return this.rNext;
	}
	
	public int getPnext() {
		return this.pNext;
	}
	
	public int getTlen() {
		return this.tLen;
	}
	
	public String getSeq() {
		return this.seq;
	}
	
	public String getQual() {
		return this.qual;
	}
	
	public int getEntryLength() {
		return this.entryLength;
	}
	
	// End getters.
	
	// Setters.
	
	public void setQname(String qName) {
		this.qName = qName;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public void setRname(String rName) {
		this.rName = rName;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public void setMapQ(int mapQ) {
		this.mapQ = mapQ;
	}
	
	public void cigar(String cigar) {
		this.cigar = cigar;
	}
	
	public void setRnext(String rNext) {
		this.rNext = rNext;
	}
	
	public void setPnext(int pNext) {
		this.pNext = pNext;
	}
	
	public void setTlen(int tLen) {
		this.tLen = tLen;
	}
	
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public void setQual(String qual) {
		this.qual = qual;
	}
	
	public void setEntryLength(int entryLength) {
		this.entryLength = entryLength;
	}
	
	// End Setters.

}
