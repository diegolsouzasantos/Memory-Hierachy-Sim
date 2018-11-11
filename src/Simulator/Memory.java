package Simulator;
import Simulator.Cache.Cache;
import Simulator.Exceptions.IncompatibleCacheSize;
import Simulator.Exceptions.NotMultiple;
import Simulator.Exceptions.NotPowerOf2;
import Simulator.Pointer.Value;
import Simulator.RAM.MainMemory;

public class Memory {
	private Cache c;
	private MainMemory mainmem;
		
	public static Memory createMemory(Cache c, MainMemory mainmem) {
		return new Memory(c,mainmem);
	}
	
	public static int getData(Memory mem, int address, Value value) {
		return mem.getData(address, value);
	}
	
	//not sure if it works
	public static int getInstruction(Memory mem, int address, Value value) {
		return mem.getInstruction(address, value);
	}
	
	public static int setData(Memory mem, int address, Value value) {
		return mem.setData(address, value);
	}
	
	public Memory(Cache c, MainMemory mainmem) {
		this.setCache(c);
		this.setMM(mainmem);
	}
	//this is function below
	
	public int getData(int address, Value value) {
		int flag = Cache.getCacheData(this.c, this.mainmem, address, value);
		if(flag != 4) {
			return flag;
		}else {
			return MainMemory.getMainMemoryData(this.mainmem, address, value);
		}
	}
	
	public int getInstruction(int address, Value value) {
		int flag = Cache.getCacheInstruction(this.c, this.mainmem, address, value);
		if(flag != 4) {
			return flag;
		}else {
			return MainMemory.getMainMemoryData(this.mainmem, address, value);
		}
	}
	public int setData(int address, Value value) {
		int flag = Cache.setCacheData(this.c,this.mainmem, address, value.getValue());
		if(flag > 0) {
			if(flag > 0) {
				if(flag == 1) {
					return 1;
				}
				if(flag == 2) {
					return 2;
				}
				if(flag == 3) {
					return 3;
				}
				if(MainMemory.setMainMemoryData(this.mainmem, address, value) == 4) {
					return 4;
				}
			}
		}
		return -1;
	}
	
	//this is function below
	public int setInstruction(Memory mem, int address, Value value) {
		int flag = Cache.setCacheInstruction(this.c, this.mainmem, address, value.getValue());
		if(flag > 0) {
			if(flag == 1) {
				return 1;
			}
			if(flag == 2) {
				return 2;
			}
			if(flag == 3) {
				return 3;
			}
			else if(MainMemory.setMainMemoryData(this.mainmem, address, value) == 4) {
				return 4;
			}
		}
		return -1;
	}
	
	public static Memory duplicateMemory(Memory mem) throws NotMultiple, NotPowerOf2, IncompatibleCacheSize {
		Memory newmem = createMemory(Cache.duplicateCache(mem.getCache()), mem.getMM());
		return newmem;
	}
	
	public Cache getCache() {
		return c;
	}

	private void setCache(Cache c) {
		this.c = c;
	}

	public MainMemory getMM() {
		return mainmem;
	}

	private void setMM(MainMemory mainmem) {
		this.mainmem = mainmem;
	}

}
