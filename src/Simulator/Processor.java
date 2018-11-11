package Simulator;

import Simulator.Exceptions.IncompatibleCacheSize;
import Simulator.Exceptions.NotMultiple;
import Simulator.Exceptions.NotPowerOf2;
/**
 * Class that represent a processor.
 */
public class Processor {
	/*
	 * Array with the memory of the cores
	 * */
	private Memory[] cores;
	/*
	 * Number of cores
	 * */
	private int ncores;
	
	/**
	 * Construct a processor.
	 * @param Obj of the class Memory that represent the hierarchy of memory.
	 * @param integer that represent the number of cores in the processor.
	 * @throws IncompatibleCacheSize 
	 */
	public Processor(Memory memory, int ncores) throws NotMultiple, NotPowerOf2, IncompatibleCacheSize {
		this.setNcores(ncores);
		this.cores = new Memory[ncores];
		this.cores[0] = memory;
		for(int i = 1; i < ncores; i++) {
			this.cores[i] = Memory.duplicateMemory(memory);
		}
	}
	
	/**
	 * Calls the constructor of the class Processor and return a obj.	
	 * @param memory - obj of the class Memory that represent the hierarchy of memory.
	 * @param ncores - integer that represent the number of cores in the processor.
	 * @return obj of the Processor class.
	 * @throws IncompatibleCacheSize 
	 */
	public static Processor createProcessor(Memory memory, int ncores) throws NotMultiple, NotPowerOf2, IncompatibleCacheSize {
		return new Processor(memory, ncores);
	}
	
	/**
	 * Return the number of cores in the processor.
	 * @return the number of cores
	 */
	public int getNcores() {
		return ncores;
	}

	/**
	 * Set the number of cores in the processor.
	 * @param ncores - integer that represent the number of cores.
	 */
	public void setNcores(int ncores) {
		this.ncores = ncores;
	}
	
	/**
	 * Return the all the cores.
	 * @return Array of Memory.
	 */
	public Memory[] getCores() {
		return this.cores;
	}
}
