package core;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

// Project-specific imports.
import algorithmLogic.AlgorithmController;
import core.common.ModuleState;
import core.common.ModuleObserverInterface;
import core.exceptions.NeitherConsumerProducerException;
import core.exceptions.OccupiedException;
import core.exceptions.PipeTypeNotSupportedException;

/**
 * This controller class checks the STATES of each modules and resolves inter-module dependencies of threads.
 * The aim of this class is to provide queues for several threads to keep track when which
 * thread-thread pairs are finished and what is the status of each job.
 * This class acts as a controller in the MVC (Model-View-Controller) pattern.
 * @see Model-View-Controller pattern.
 * @author Christopher Kraus
 *
 */
public class JobController {
	
	// Constants.
	private final ModuleObserverInterface moduleObserver;
	
	// Variables.
	

	// Variable holds the ExtStorageController.
	private ExtStorageController extStorageController;
	
	// Variable holds the AlgorithmController.
	private AlgorithmController algorithmController;
	
	// HashMap to save all moduleNodes.
	private Map <String, ModuleNode> moduleNodeMap;
	
	// HashMap to save all threads.
	private Map <Integer, Thread> threadMap;
	
	// Constructors.
	public JobController (ExtStorageController extScontroller, AlgorithmController aController) {
		this.extStorageController = extScontroller;
		this.algorithmController = aController;
		this.moduleObserver = new ModuleObserver(
				this.extStorageController, this.algorithmController);
		this.moduleNodeMap = new HashMap <String, ModuleNode>();
		this.threadMap = new HashMap<Integer, Thread>();
		
	}
	
	// Methods.
	
	// Getters.
	
	/**
	 * Getter to get the ModuleObserver instance.
	 * @return ModuleObserver moduleObserver
	 */
	public ModuleObserver getModuleObserver() {
		return (ModuleObserver) this.moduleObserver;
	}
	
	/**
	 * Getter to get the ModuleNode.
	 * @param moduleNodeName
	 * @return ModuleNode moduleNode
	 */
	public ModuleNode getModuleNode (String moduleNodeName) {
		return this.moduleNodeMap.get(moduleNodeName);
	}
	
	/**
	 * Getter which returns the ExtStorageController.
	 * @return ExtStorageController extStorageController
	 */
	public ExtStorageController getExtStorageController() {
		return this.extStorageController;
	}
	
	// End getters.
	
	public String addNewModuleNode(ModuleNode mNode) {
		
		this.moduleNodeMap.put(mNode.getModuleNodeName(), mNode);
		return mNode.getModuleNodeName();
		
	}
	
	public String addNewModuleNode (int producerID, int consumerID) {
		
		ModuleNode newNode = new ModuleNode (producerID, consumerID, (ModuleObserver) this.moduleObserver);
		String moduleNodeName = newNode.getModuleNodeName();
		this.moduleNodeMap.put(moduleNodeName, newNode);
		
		// Familiarize the module objects with their ModuleNodeNames.
		ModuleBuilder.getModule(consumerID).setConsumerModuleNodeName(moduleNodeName);
		ModuleBuilder.getModule(producerID).setProducerModuleNodeName(moduleNodeName);
		
		
		return newNode.getModuleNodeName();
		
	}
	
	public String addNewModuleNode (int moduleID) {
		
		ModuleNode newNode = new ModuleNode (moduleID, (ModuleObserver) this.moduleObserver);
		String moduleNodeName = newNode.getModuleNodeName();
		this.moduleNodeMap.put(moduleNodeName, newNode);
		
		// Familiarize the module objects with their ModuleNodeNames.
		ModuleBuilder.getModule(moduleID).setConsumerModuleNodeName(moduleNodeName);
		ModuleBuilder.getModule(moduleID).setProducerModuleNodeName(moduleNodeName);
		
		return newNode.getModuleNodeName();
	}
	
	public void connect(String moduleNodeName) {
		
		// Create ports and connect them by a new pipe.
		try {
			this.moduleNodeMap.get(moduleNodeName).connect();
		} catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		} catch (OccupiedException oe) {
			System.err.println(oe.getMessage());
			oe.printStackTrace();
		}
		
		// Give each producer and consumer the moduleNode.
		
		// Set up producer.
		ModuleBuilder.getModule(
				this.moduleNodeMap.get(moduleNodeName).getProducerID()
				).setSuperModuleNode(this.moduleNodeMap.get(moduleNodeName));
		
		// Set up consumer.
		ModuleBuilder.getModule(
				this.moduleNodeMap.get(moduleNodeName).getConsumerID()
				).setSuperModuleNode(this.moduleNodeMap.get(moduleNodeName));
		
	}
	
	/**
	 * Create a new thread for the Producer and for the Consumer.
	 * @param String moduleNodeName
	 */
	public void startJob(String moduleNodeName) throws InterruptedException {
			
			// Check whether producer already has a thread.
		
			if (ModuleBuilder.getModule(
					this.moduleNodeMap.get(moduleNodeName).getProducerID()
					).getModuleState().equals(ModuleState.READY)) {
				
						
				// Start new thread for the Producer.
				Thread newProducerThread = new Thread(ModuleBuilder.getModule(
						this.moduleNodeMap.get(moduleNodeName).getProducerID()
						));
				
				// Change module state to "STARTING".
				ModuleBuilder.getModule(
						this.moduleNodeMap.get(moduleNodeName).getProducerID()
						).setModuleState(ModuleState.STARTING);
				
				// Register new thread.
				this.threadMap.put(this.moduleNodeMap.get(moduleNodeName).getProducerID(), newProducerThread);
				
				// Use specific name for the Producer.
				newProducerThread.setName("Node " + moduleNodeName + " "
						+ ModuleBuilder.getModule(
								this.moduleNodeMap.get(moduleNodeName).getProducerID()
							).getModuleType().toString() 
						+ ":ProducerThread:" + this.moduleNodeMap.get(moduleNodeName).getProducerID());
				
				// Start new Thread.
				newProducerThread.start();
				
				// Sleep for 100 milliseconds.
				Thread.sleep(100);
			}
			
			// Check whether consumer already has a thread.
		
			if (ModuleBuilder.getModule(
					this.moduleNodeMap.get(moduleNodeName).getConsumerID()
					).getModuleState().equals(ModuleState.READY)) {
					
				
				// Start new thread for the Producer.
				Thread newConsumerThread = new Thread(ModuleBuilder.getModule(
						this.moduleNodeMap.get(moduleNodeName).getConsumerID()
						));
				
				// Change module state to "STARTING".
				ModuleBuilder.getModule(
						this.moduleNodeMap.get(moduleNodeName).getConsumerID()
						).setModuleState(ModuleState.STARTING);
				
				// Use specific name for the Producer.
				newConsumerThread.setName("Node " + moduleNodeName + " "
						+ ModuleBuilder.getModule(
								this.moduleNodeMap.get(moduleNodeName).getConsumerID()
							).getModuleType().toString() 
						+ ":ConsumerThread:" + this.moduleNodeMap.get(moduleNodeName).getProducerID());
				
				// Start new Thread.
				newConsumerThread.start();
				
				// Sleep for 100 milliseconds.
				Thread.sleep(100);
			}
		
	}
	
	public void updateModuleNodes () {
		Iterator <Map.Entry<String, ModuleNode>> nodeIt = this.moduleNodeMap.entrySet().iterator();
		while (nodeIt.hasNext()) {
			
			Map.Entry<String, ModuleNode> nodeEntry = nodeIt.next();
			nodeEntry.getValue().notifyModuleObserver();
			
		}
		
	}

}
