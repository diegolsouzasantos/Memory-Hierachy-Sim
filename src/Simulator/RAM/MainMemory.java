package Simulator.RAM;
import java.util.Arrays;

import Simulator.Cache.Line;
import Simulator.Pointer.Value;

//finish
public class MainMemory {
	private int ramsize;
	private int vmsize;
	// int change to Line
	private int[] mainmem;
	private int totalSize;

	/**
	 * Create a object of the class MainMemory.
	 * @param ramsize - integer that represent the size of a RAM.
	 * @param vimsize - integer that represent the size of a Virtual Memory.
	 * @return return a object of the class MainMemory.
	 */
	public static MainMemory createMainMemory(int ramsize, int vmsize) {
		return new MainMemory(ramsize,vmsize);
	}
	
	// this is function below
	//finish
	/**
	 * Search the address in the main memory array and set the object value with the value found in the address.
	 * @param mem - object of the MainMemory where the search will occur.
	 * @param address - integer that represents the address to be search in the main memory.
	 * @param value - object of the class value that simulate the behavior of a pointer in c++;
	 * @return return -1 if the address is out of range and 4 if the address is valid and was properly read. 
	 */
	public static int getMainMemoryData(MainMemory mem, int address, Value value) { 
		return mem.getMMData(address, value);
	}
	
	/**
	 * Writes the value of the object value in the address of the main memory.
	 * @param mem - object of the MainMemory where the value will be written.
	 * @param address - integer that represents the address in the main memory.
	 * @param value - object of the class Value that contains the value to be write in the main memory.
	 * @return return -1 if the address is out of range and 4 if the address is valid and the value was properly written.
	 */
	public static int setMainMemoryData(MainMemory mem, int address, Value value) {
		return mem.setMMData(address, value);
	}
	
	
	//finish
	/**
	 * Construct a object of the class MainMemory.
	 * @param ramsize - integer that represent the size of a RAM.
	 * @param vimsize - integer that represent the size of a Virtual Memory.
	 */
	public MainMemory(int ramsize, int vmsize) {
		this.setRamsize(ramsize);
		this.setVmsize(vmsize);
		this.totalSize = (this.ramsize + this.vmsize)/4;
		this.mainmem = new int[this.totalSize];
	}
	
	//not sure if it works
	//finish
	/**
	 * Search the address in the main memory array and set the object value with the value found in the address.
	 * @param address - integer that represents the address to be search in the main memory.
	 * @param value - object of the class value that simulate the behavior of a pointer in c++;
	 * @return return -1 if the address is out of range and 4 if the address is valid and was properly read. 
	 */
	public int getMMData(int address, Value value) {
		if(address/4 < this.totalSize) {
			value.setValue(mainmem[address/4]);
			return 4;
		}
		return -1;
	}
	
	// also this, is function below
	
	//finish
	//check if return type is void or integer
	/**
	 * Writes the value of the object value in the address of the main memory.
	 * @param address - integer that represents the address in the main memory.
	 * @param value - object of the class Value that contains the value to be write in the main memory.
	 * @return return -1 if the address is out of range and 4 if the address is valid and the value was properly written.
	 */
	public int setMMData(int address, Value value) {
		if(address/4 < this.totalSize) {
			mainmem[address/4] = value.getValue();
			return 4;
		}
		return -1;
	}
	//address 0
	public Line getLine(int address, int lineSize) {
		int v[] = Arrays.copyOfRange(this.mainmem, address/4,address/4 + lineSize);
		return new Line(v);
	}
	public int getRamsize() {
		return ramsize;
	}

	public void setRamsize(int ramsize) {
		this.ramsize = ramsize;
	}

	public int getVmsize() {
		return vmsize;
	}

	public void setVmsize(int vmsize) {
		this.vmsize = vmsize;
	}
	
	public int[] getMainmem() {
		return mainmem;
	}

	public void setMainmem(int[] mainmem) {
		this.mainmem = mainmem;
	}
}
