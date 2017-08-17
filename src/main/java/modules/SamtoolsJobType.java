package modules;

public enum SamtoolsJobType {
	// Parse "SAM to BAM conversion" command.
	SAM_2_BAM,
	// Parse "remove non-mapping reads from BAM" command.
	REMOVE_READS_BAM,
	// Parse the "sort BAM file" command.
	SORT_BAM,
	// Parse the "index BAM file" command.
	INDEX_BAM,
	// Parse the "index reference" command.
	INDEX_FASTA,
	// Parse the "show tview for specific region and convert to text" command.
	TVIEW_BAM
}
