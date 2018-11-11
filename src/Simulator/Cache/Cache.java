package Simulator.Cache;
import Simulator.Exceptions.IncompatibleCacheSize;
import Simulator.Exceptions.NotMultiple;
import Simulator.Exceptions.NotPowerOf2;
import Simulator.Pointer.Value;
import Simulator.RAM.MainMemory;

public class Cache {
	private SACache l1d;
	private SACache l1i;
	private SACache l2;
	private SACache l3;
	
	/**
	 * Creates a object of the class Cache.
	 * @param l1d - object of the class SACache that represent the cache L1 of data.
	 * @param l1i - object of the class SACache that represent the cache L1 of instruction.
	 * @param l2 - object of the class SACache that represent the cache L2 of data and instruction.
	 * @param l3 - object of the class SACache that represent the shared cache L3 of data and instruction.
	 * @return a object of the class Cache.
	 * @throws IncompatibleCacheSize 
	 */
	public static Cache createCache(SACache l1d, SACache l1i, SACache l2, SACache l3) throws IncompatibleCacheSize {
		return new Cache(l1d, l1i, l2, l3);
	}

	public static void fetchCacheData(Cache c, MainMemory mmem, int address, int value) {
		c.fetchCData(mmem, address);
	}

	public static void fetchCacheInstruction(Cache c, MainMemory mmem, int address, int value){
		c.fetchCInstruction(mmem, address);
	}
		
	public static int getCacheData(Cache c, MainMemory mmem, int address, Value value) {
		return c.getCData(c,mmem, address, value);
	}
	
	public static int getCacheInstruction(Cache c, MainMemory mmem, int address, Value value) {
		return c.getCInstruction(c,mmem, address, value);
	}


	
	/**
	 * Copy the existent object and generate a new object with all the structure of the caches levels equals besides the L3 that is the same.
	 * @param c - object of the class Cache.
	 * @return return the copy of the object c with the same L3. 
	 * @throws NotMultiple
	 * @throws NotPowerOf2
	 * @throws IncompatibleCacheSize
	 */
	public static Cache duplicateCache(Cache c) throws NotMultiple, NotPowerOf2, IncompatibleCacheSize {
		Cache newcache = createCache(
				SACache.duplicateSACache(c.getL1dCache()),
				SACache.duplicateSACache(c.getL1iCache()),
				SACache.duplicateSACache(c.getL2Cache()),
				c.getL3Cache());
		return newcache;
	}
	
	//check if Saude.

	/**
	 * Construct a object of the class Cache.
	 * @param l1d - object of the class SACache that represent the cache L1 of data.
	 * @param l1i - object of the class SACache that represent the cache L1 of instruction.
	 * @param l2 - object of the class SACache that represent the cache L2 of data and instruction.
	 * @param l3 - object of the class SACache that represent the shared cache L3 of data and instruction.
	 * @throws IncompatibleCacheSize 
	 */
	public Cache(SACache l1d, SACache l1i, SACache l2, SACache l3) throws IncompatibleCacheSize {
		if((l1d.getLineSize() <= l2.getLineSize() || l1i.getLineSize() <= l2.getLineSize()) && l2.getLineSize() < l3.getLineSize()) {
			this.l1d = l1d;
			this.l1i = l1i;
			this.l2 = l2;
			this.l3 = l3;
		}else {
			throw new IncompatibleCacheSize("one of the caches does no respect the size rules");
		}
	}
	
	public void fetchCData(MainMemory mmem, int address) {
		int l1dAddress = address & (-(SACache.getSACacheLineSize(this.l1d)));
	    int l2Address = address & (-(SACache.getSACacheLineSize(this.l2)));
	    int l3Address = address & (-(SACache.getSACacheLineSize(this.l3)));
	    
	    
	    Line l1dline = mmem.getLine(l1dAddress, SACache.getSACacheLineSize(this.l1d));
	    Line l2line = mmem.getLine(l2Address, SACache.getSACacheLineSize(this.l2));
	    Line l3line = mmem.getLine(l3Address, SACache.getSACacheLineSize(this.l3));
	    
	    SACache.setSACacheLine(this.l1d, address, l1dline);
	    SACache.setSACacheLine(this.l2, address, l2line);
	    SACache.setSACacheLine(this.l3, address, l3line);
	}
	
	
	public void fetchCInstruction(MainMemory mmem, int address){
	    int l1iAddress = address & (-(SACache.getSACacheLineSize(this.l1i)));
	    int l2Address = address & (-(SACache.getSACacheLineSize(this.l2)));
	    int l3Address = address & (-(SACache.getSACacheLineSize(this.l3)));
	    
	    Line l1dline = mmem.getLine(l1iAddress, SACache.getSACacheLineSize(this.l1i));
	    Line l2line = mmem.getLine(l2Address, SACache.getSACacheLineSize(this.l2));
	    Line l3line = mmem.getLine(l3Address, SACache.getSACacheLineSize(this.l3));
	    
	    SACache.setSACacheLine(this.l1i, address, l1dline);
	    SACache.setSACacheLine(this.l2, address, l2line);
	    SACache.setSACacheLine(this.l3, address, l3line);
	}
	
	public int getCData(Cache c,MainMemory mmem, int address, Value value) {
		int flag = -1;
		if(SACache.getSACacheData(this.l1d, address, value)) {
			flag = 1;
		}else if(SACache.getSACacheData(this.l2, address, value)) {
			flag = 2;
		}else if(SACache.getSACacheData(this.l3, address, value)) {
			flag = 3;
		}
		if(flag < 0){
			flag = MainMemory.getMainMemoryData(mmem, address,value);	
	    }
		
		if(flag > 1)
		{
			Cache.fetchCacheData(c, mmem, address, value.getValue());
		}
		return flag;
//
//		if(SACache.getSACacheData(this.l1d, address, value)) {
//			return 1;
//		}
//		if(SACache.getSACacheData(this.l2, address, value)) {
//			return 2;
//		}
//		if(SACache.getSACacheData(this.l3, address, value)) {
//			return 3;
//		}
//		this.fetchCData(mmem, address);
//		return 4;
	}

	public int getCInstruction(Cache c,MainMemory mmem, int address, Value value) {
		int flag = -1;
		if(SACache.getSACacheData(this.l1i, address, value)) {
			flag = 1;
		}else if(SACache.getSACacheData(this.l2, address, value)) {
			flag = 2;
		}else if(SACache.getSACacheData(this.l3, address, value)) {
			flag = 3;
		}
		if(flag < 0){
			flag = MainMemory.getMainMemoryData(mmem, address,value);	
	    }
		
		if(flag > 1)
		{
			Cache.fetchCacheInstruction(c, mmem, address, value.getValue());
		}
		return flag;
//		if(SACache.getSACacheData(this.l1i, address, value)) {
//			return 1;
//		}else if(SACache.getSACacheData(this.l2, address, value)) {
//			return 2;
//		}else if(SACache.getSACacheData(this.l3, address, value)) {
//			return 3;
//		}
//		this.fetchCInstruction(mmem, address);
//		return 4;
	}
	
	public static int setCacheData(Cache c, MainMemory mmem, int address, int value) {
		int flag = -1;
		if(SACache.setSACacheData(c.getL1dCache(), address, value)) {
			flag = 1;
		}else if(SACache.setSACacheData(c.getL2Cache(), address, value)) {
			flag = 2;
		}else if(SACache.setSACacheData(c.getL3Cache(), address, value)) {
			flag = 3;
		}
		if(flag < 0){
			flag = MainMemory.setMainMemoryData(mmem, address,new Value(value));	
	    }
		
		if(flag > 1)
		{
			Cache.fetchCacheData(c, mmem, address, value);
		}
		if(flag > 0) {
			SACache.setSACacheData(c.getL1dCache(), address, value);
		    SACache.setSACacheData(c.getL2Cache(), address, value);
		    SACache.setSACacheData(c.getL3Cache(), address, value);
			MainMemory.setMainMemoryData(mmem, address, new Value(value));
	    }
		return flag;
	}
	
	public static int setCacheInstruction(Cache c, MainMemory mmem, int address, int value){
		int flag = -1;
		if(SACache.setSACacheData(c.getL1iCache(), address, value)) {
			flag = 1;
		}else if(SACache.setSACacheData(c.getL2Cache(), address, value)) {
			flag = 2;
		}else if(SACache.setSACacheData(c.getL3Cache(), address, value)) {
			flag = 3;
		}
		if(flag < 0){
			flag = MainMemory.setMainMemoryData(mmem, address,new Value(value));	
	    }
		
		if(flag > 1)
		{
			Cache.fetchCacheInstruction(c, mmem, address, value);
		}
		if(flag > 0) {
			SACache.setSACacheData(c.getL1iCache(), address, value);
		    SACache.setSACacheData(c.getL2Cache(), address, value);
		    SACache.setSACacheData(c.getL3Cache(), address, value);
			MainMemory.setMainMemoryData(mmem, address, new Value(value));
	    }
		return flag;
	}
	
	public SACache getL1dCache() {
		return this.l1d;
	}
	
	public SACache getL1iCache() {
		return this.l1i;
	}	
	public SACache getL2Cache() {
		return this.l2;
	}

	public SACache getL3Cache() {
		return l3;
	}

}
