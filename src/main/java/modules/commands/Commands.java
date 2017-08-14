package modules.commands;

public enum Commands {
	// Cdhit options.
	T, c, i,
	// Qpms9 options.
	l, d, q, 
	// General options.
	fasta, input, output, path,
	// Bowtie2-Build options.
	reference, index_base,
	// Bowtie2 options.
	x, U, m1, m2,
	// Samtools options.
	b, sort, view, F, index, faidx, tview, p,
	redirect,
	// Shared options between bowtie2 and samtools.
	S,
	// Shared options between cdhit and samtools.
	o,
	// Dummy command for dummy module.
	dummy
}
