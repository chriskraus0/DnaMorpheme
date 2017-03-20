package storage;

public class SeqLogo {
	
	// Variables.
	
	private String logoHeader;
		
	// Matrix to save the entropy entries.
	// First dimension index defines the position.
	// Second dimension index defines the entropy values.
	private double[][] logoMatrix;
	
	// Constructors.
	
	public SeqLogo (String header, double[][] matrix) {
		this.logoHeader = header;
		this.logoMatrix = matrix;
	}
	
	// Methods.
	
	// Setters.
	
	// Getters.
	
	public String getLogoHeader () {
		return this.logoHeader;
	}
	
	public double[][] getLogoMatrix () {
		return this.logoMatrix;
	}

}
