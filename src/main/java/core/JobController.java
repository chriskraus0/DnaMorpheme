package core;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

// Project-specific imports.
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
 * @author christopher
 *
 */
public class JobController {
	
	// Constants.
	private final ModuleObserverInterface moduleObserver;
	
	// Variables.
	
	// HashMap save all moduleNodes.
	// TODO: What to use? Faster HashMap or sorted TreeMap?
	Map <String, ModuleNode> moduleNodeMap;
	
	// Constructors.
	public JobController () {
		this.moduleObserver = new ModuleObserver();
		this.moduleNodeMap = new HashMap <String, ModuleNode>();
		
	}
	
	// Methods.
	
	// Getters.
	
	public ModuleNode getModuleNode (String moduleNodeName) {
		return this.moduleNodeMap.get(moduleNodeName);
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
	
	public void connect(String moduleNodeName) {
		
		try {
			this.moduleNodeMap.get(moduleNodeName).connect();
		} catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		} catch (OccupiedException oe) {
			System.err.println(oe.getMessage());
			oe.printStackTrace();
		}
		
		// Start new threads for producer and consumer of the node.
		try {
			this.startJob(moduleNodeName);
		} catch (InterruptedException intE) {
			System.err.println(intE.getMessage());
			intE.printStackTrace();
		}
		
	}
	
	/**
	 * Create a new thread for the Producer and for the Consumer.
	 * @param String moduleNodeName
	 */
	private void startJob(String moduleNodeName) throws InterruptedException {
		
			// Start new thread for the Producer.
			Thread newProducerThread = new Thread(ModuleBuilder.getModule(
					this.moduleNodeMap.get(moduleNodeName).getProducerID()
					));
			
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
			
			// Start new thread for the Producer.
			Thread newConsumerThread = new Thread(ModuleBuilder.getModule(
					this.moduleNodeMap.get(moduleNodeName).getConsumerID()
					));
			
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
	
	public void updateModuleNodes () {
		Iterator <Map.Entry<String, ModuleNode>> nodeIt = this.moduleNodeMap.entrySet().iterator();
		while (nodeIt.hasNext()) {
			
			Map.Entry<String, ModuleNode> nodeEntry = nodeIt.next();
			nodeEntry.getValue().notifyModuleObserver();
			
		}
		
	}

}