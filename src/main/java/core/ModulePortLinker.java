package core;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.TreeMap;

// Project specific imports.
import core.common.PipeType;

// Project specific exceptions.
import core.exceptions.OccupiedException;
import core.exceptions.PipeTypeNotSupportedException;
import core.exceptions.PipeNotFoundException;

/**
 * Singleton which links all input and output ports of all available classes.
 * @author christopher
 *
 */
public class ModulePortLinker {
	
	// Variables.
	
	private static int inputPortIDs = 0;
	private static Map <Integer, InputPort> inputPorts = new TreeMap <Integer, InputPort>();
	
	private static int outputPortIDs = 0;
	private static Map <Integer, OutputPort> outputPorts = new TreeMap <Integer, OutputPort>();
	
	
	private static ModulePortLinker modulePortLinker = new ModulePortLinker();
	
	// Constructors.
	private ModulePortLinker () {}
	
	// Create instance.
	public static ModulePortLinker getInstance () {
		return modulePortLinker;
	}
	
	
	public static int requestNewInputPortID (int moduleID) {
		inputPortIDs ++;
		InputPort inPort = new InputPort (inputPortIDs, moduleID);
		inputPorts.put(inputPortIDs, inPort);
		return inputPortIDs;
	}
	
	public static int requestNewOutputPortID (int moduleID) {
		outputPortIDs ++;
		OutputPort outPort = new OutputPort (outputPortIDs, moduleID);
		outputPorts.put(outputPortIDs, outPort);
		return outputPortIDs;
	}
	
	// Getters.
	
	public static InputPort getInputPort (int inPortID) {
		return inputPorts.get(inPortID);
	}
	
	public static OutputPort getOutputPort (int outPortID) {
		return outputPorts.get(outPortID);
	}
	
	// End getters.
	
	public static void removeInputPort (int inPortID) {
		try {
			inputPorts.get(inPortID).removePipe();
		} catch (PipeNotFoundException ne) {
			System.err.println(ne.getMessage());
			ne.printStackTrace();
		}
		inputPorts.remove(inPortID);
	}
	
	public static void removeOutputPort (int outPortID) {
		try {
			outputPorts.get(outPortID).removePipe();
		} catch (PipeNotFoundException ne) {
			System.err.println(ne.getMessage());
			ne.printStackTrace();
		}
		outputPorts.remove(outPortID);
	}
	
	public static void linkPorts (int outPortID, int inPortID, PipeType pType) {
		try {
			outputPorts.get(outPortID).connectPorts(outputPorts.get(outPortID).getPipe(), inputPorts.get(inPortID));
		} catch (PipeTypeNotSupportedException pe) { 
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		}catch (OccupiedException oe) {
			System.err.println(oe.getMessage());
			oe.printStackTrace();
		} 
	}
}
