package Simulator.Cache;

import Simulator.Exceptions.NotMultiple;
import Simulator.Exceptions.NotPowerOf2;
import Simulator.Pointer.*;

public class SACache {
	
	private int totalCapacity;
	private int associativity;
	private int bytesPerLine;
	private int nsets;
	private TACache sets[];
	
	public SACache(int c, int a, int l) throws NotMultiple, NotPowerOf2 {
		if(isPowerOf2(c) && isPowerOf2(l)) {
			if(c % (l*a) == 0 && c > (l*a)) {
				this.totalCapacity = c;
				this.associativity = a;
				this.bytesPerLine = l;
				this.nsets = c / (a*l);
				this.sets = new TACache[this.nsets];
				for(int i = 0; i < this.nsets; i++) {
					TACache tac = TACache.createTACache(a*l, l);
					this.sets[i] = tac;
				}
			} else {
				throw new NotMultiple("c not multiple of l*a");
			}
		}else {
			throw new NotPowerOf2("c, a or l in not a power of 2");
		}
	}

	public static SACache createSACache(int c, int a, int l) throws NotMultiple, NotPowerOf2 {
		return new SACache(c, a, l);
	}
	
	public static boolean getSACacheData(SACache sac, int address, Value value) {
		return sac.getSACData(address, value);
	}	
	
	public static boolean setSACacheData(SACache sac, int address, int value) {
		return sac.setSACData(sac, address, value);
	}
	
	public static void setSACacheLine(SACache sac, int address, Line line) {
		sac.setSACLine(address, line);
	}
	
	public static SACache duplicateSACache(SACache sac) throws NotMultiple, NotPowerOf2 {
		return sac.duplicateSAC(sac);
	}

	public static int getSACacheCapacity(SACache sac) {
		return sac.getCapacity();
	}
	
	public static int getSACacheLineSize(SACache sac) {
		return sac.getLineSize();
	}

	public static int getSACacheAssociativity(SACache sac) {
		return sac.getAssociativity();
	}
	
	
	//finish
	/**
	 * Return if had found the value in the address. 
	 * @param address
	 * @param value
	 * @return true if found the value(hit) and false if didn't (miss).
	 */
	public boolean getSACData(int address, Value value) {
		int offsetSize = (int)(Math.log10(this.bytesPerLine)/Math.log10(2));
		//check with Saude
		int lookup = address >> offsetSize;
		lookup = lookup & ((this.bytesPerLine/this.associativity) - 1);
		//can pass tac as null, handle in TACache,ask saude
		return TACache.getTACacheData(this.sets[lookup], address, value);
	}
	//finish
	public boolean setSACData(SACache sac, int address, int value) {
		int offsetSize = (int)(Math.log10(this.bytesPerLine)/Math.log10(2));
		//check with Saude
		int lookup = address >> offsetSize;
		lookup = lookup & ((this.bytesPerLine/this.associativity) - 1);
		return TACache.setTACacheData(this.sets[lookup], address, value);
	}
	//finish
	public void setSACLine(int address, Line line) {
		int offsetSize = (int)(Math.log10(this.bytesPerLine)/Math.log10(2));
		//check with Saude
		int lookup = address >> offsetSize;
		lookup = lookup & ((this.bytesPerLine/this.associativity) - 1);
		TACache.setTACacheLine(this.sets[lookup], address, line);
	}
	//finish
	public SACache duplicateSAC(SACache sac) throws NotMultiple, NotPowerOf2 {
		return new SACache(this.totalCapacity, this.associativity, this.bytesPerLine);
	}
	public int getCapacity() {
		return this.totalCapacity;
	}
	
	public int getLineSize() {
		return this.bytesPerLine;
	}
	
	public int getAssociativity() {
		return this.associativity;
	}
	
	public TACache[] getSets() {
		return sets;
	}

	public void setSets(TACache[] sets) {
		this.sets = sets;
	}
	/**
	 * Test if a number is a power of 2.
	 * @param number - the number to be tested.
	 * @return true if the number is a power of 2 and false if not.
	 */
	public boolean isPowerOf2(int number) {
		if((number & (number -1)) ==0){
			return true;
		}
		return false;
	}
	
}
