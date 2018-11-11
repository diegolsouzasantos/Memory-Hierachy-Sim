package Simulator.Cache;

import java.util.Arrays;
import Simulator.Exceptions.NotMultiple;
import Simulator.Exceptions.NotPowerOf2;
import Simulator.Pointer.Value;

public class TACache {
	
	private int totalCapacity;
	private int bytesPerLine;
	private TACacheLineStructure lines[];
	private int numberOfLines;
	private int[] counter;
	private int wi = 0;
	
	/**
	 * Constructor of a Fully Associative Cache.
	 * @param c - Integer representing the total capacity in bytes of the respective cache data structure. 
	 * @param l - Integer number of bytes per line of each data line array.
	 */
	public TACache(int c, int l) throws NotMultiple, NotPowerOf2 {
		if(isPowerOf2(c) && isPowerOf2(l)) {
			if(c % l == 0 && c > l) {//test this catch all the numbers
				this.totalCapacity = c;
				this.bytesPerLine = l;
				this.numberOfLines = c/l;
				this.lines = new TACacheLineStructure[this.numberOfLines];
				for(int i  = 0 ; i < this.numberOfLines; ++i) {
					this.lines[i] = new TACacheLineStructure(0, this.bytesPerLine);
				}
				
			} else {
				throw new NotMultiple("c not multiple of l");
			}
		} else {
			throw new NotPowerOf2("c or l is not a power of 2");
		}
	}
	
	/**
	 * Calls the constructor of the class TACache and return a object.	
	 * @param c - Integer representing the total capacity in bytes of the respective cache data structure. 
	 * @param l - Integer number of bytes per line of each data line array.
	 * @return Object of the TACache class.
	 */
	public static TACache createTACache(int c , int l) throws NotMultiple, NotPowerOf2 {
		return new TACache(c,l);
	}
	
	/**
	 * Access getter of a cache data line value.	
	 * @param tac - Object of type TACache selected to obtain data line value.
	 * @param address - Integer containing tag value and offset number of a cache data line position.
	 * @param value - Pointer data type defined in order to obtain values inside methods via reference.
	 * @return Result from cache data value getter.
	 */
	public static boolean getTACacheData(TACache tac, int address, Value value) {
		return tac.getTACData(address, value);
	}
	
	/**
	 * Access setter of a cache data line.
	 * @param tac - Object of type TACache selected to obtain data line value.
	 * @param address - Integer containing tag value and offset number of a cache data line position.
	 * @param value - Pointer data type defined in order to obtain values inside methods via reference. 
	 * @return Result from cache data value getter.
	 */
	public static void setTACacheLine(TACache tac, int address, Line line) {
		tac.setTACLine(address, line);
	}
	
	/**
	 * Access setter of a cache data line value.
	 * @param tac -  Object of type TACache selected to obtain data line value.	
	 * @param adrress - Integer containing tag value and offset number of a cache data line position.
	 * @param value - Pointer data type defined in order to obtain values inside methods via reference.
	 * @return Result from cache data value setter.
	 */
	public static boolean setTACacheData(TACache tac, int address, int value) {
		return tac.setTACData(address, value);
	}
	
	/**
	 * Returns the value found in the addressed data line position. 
	 * @param address - Integer containing tag value and offset number of a cache data line position.
	 * @param value - Pointer data type representing integer data values reference. 
	 * @return true if found the value (hit) and false if didn't (miss).
	 */
	public boolean getTACData(int address, Value value) {
		int offsetSize = this.lines[0].getOffsetSize();
		int offset = address & (this.bytesPerLine - 1);
		int tag = address >> offsetSize;	
				
		for(int i = 0; i < this.getNumberOfLines(); i++) {
			if((this.lines[i].getTag() ^ tag) == 0) {
				value.setValue(this.lines[i].getData()[offset>>2]);
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns if the desired value has been set in the addressed data line position. 
	 * @param address - Integer containing tag value and offset number of a cache data line position.
	 * @param value - Pointer data type representing integer data values reference. 
	 * @return true if new value was set (hit) and false if didn't (miss).
	 */
	public boolean setTACData(int address, int value) {
		int offsetSize = this.lines[0].getOffsetSize();
		int offset = address & (this.bytesPerLine - 1);
		int tag = address >> offsetSize;
		for(int i = 0; i < this.getNumberOfLines(); i++) {
			if((this.lines[i].getTag() ^ tag) == 0) {
				if(this.lines[i].getData()[offset>>2] == 0) {
					this.lines[i].getData()[offset>>2] = value;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Return if the data line has been set in the addressed data line position. 
	 * @param tag - Integer containing tag value of a cache data line position.
	 * @param line - Pointer data type representing a cache data line structure reference. 
	 */
	public void setTACLine(int address, Line line) {
		int lwi = -1;
		int tag =  address / this.bytesPerLine;
		for(int i = 0; i < this.getNumberOfLines(); i++) {
			if((this.lines[i].getTag() ^ tag) == 0) {
				lwi = i;
			}
		}
		if(lwi< 0) {
			lwi = this.wi;
			this.wi = (this.wi +1)  % this.numberOfLines;
		}
		this.lines[lwi].setData(Arrays.copyOf(line.getLine(), this.bytesPerLine>>2));
		this.lines[lwi].setTag(tag);
		
	}

	public static int getTACacheLineSize(TACache tac) {
		return tac.getLineSize();
	}
	
	public static int getTACacheCapacity(TACache tac) {
		return tac.getCapacity();
	}
	
	public int getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	
	public int getCapacity() {
		return this.totalCapacity;
	}
	
	public int getLineSize() {
		return this.bytesPerLine;
	}

	public int[] getCounter() {
		return counter;
	}

	public void setCounter(int[] counter) {
		this.counter = counter;
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

	