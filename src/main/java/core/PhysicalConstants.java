package core;

import java.io.File;

/**
 * This Singleton class saves all necessary physical and OS-based information.
 * @author christopher
 *
 */
public class PhysicalConstants {
	
	// Constants.
	
	// Variables.
	
	private static PhysicalConstants physicalConstants = new PhysicalConstants();
	
	// Name of the system.
	private static String osName;
	
	// Number of physical CPU cores.
	private static int coreNum;
	
	// Size of the free memory in the Java Virtual Machine (JVM).
	private static long freeRamSize;
	
	// OS specific new line symbol.
	private static String newLine = System.lineSeparator();
	
	// OS specific path separator.
	private static String pathSeparator = File.separator;
	
	// Constructors.
	
	/**
	 * Empty constructor.
	 */
	private PhysicalConstants () {}
	
	// Methods.
	
	/**
	 * Always return the very same instance. ("Singleton" design pattern).
	 * @return PhysicalConstants physicalConstants
	 */
	public static PhysicalConstants getInstance() {
		return physicalConstants;
	}
	
	/**
	 * Loads the name of the operating system.
	 */
	public static void testOS () {
		osName = System.getProperty("os.name").toLowerCase();
	}
	
	/**
	 * Loads the number of available CPU cores.
	 */
	public static void testCPUcores () {
		coreNum = Runtime.getRuntime().availableProcessors();
	}
	
	/**
	 * Loads the size of available Memory.
	 */
	public static void testFreeRAM() {
		freeRamSize = Runtime.getRuntime().freeMemory();
	}
	
	// Getters.
	
	/**
	 * Get the name of the operation system (OS). 
	 * @return String osName
	 */
	public static String getOsName() {
		return osName;
	}
	
	/**
	 * Get the number of available cpu cores.
	 * @return int coreNum
	 */
	public static int getCpuCoreNum() {
		return coreNum;
	}
	
	/**
	 * Get the size of free available memory in the Java Virtual Machine (JVM).
	 * @return long freeRamSize
	 */
	public static long getFreeMem() {
		return freeRamSize;
	}
	
	/**
	 * Get the OS specific new line symbol.
	 * @return String newLine
	 */
	public static String getNewLine () {
		return newLine;
	}
	
	/**
	 * Get the OS specific path separator string.
	 * @return String pathSeparator
	 */
	public static String getPathSeparator() {
		return pathSeparator;
	}

}
