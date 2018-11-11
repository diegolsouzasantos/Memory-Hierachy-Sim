package Simulator.Cache;

public class TACacheLineStructure {
	
	private int tag;
	private int[] data;
	private int tagSize;
	private int offsetSize;
	
	/**
	 * Constructor of a Cache line structure containing tag address, array line of data field, and offset size.  
	 * @param Used to look for an associated block on respective cache level.  
	 * @param Integer number of bytes per line of each data line array.
	 */
	public TACacheLineStructure(int tagIndex, int l) {
		this.tag = tagIndex;	
		this.offsetSize = (int) (Math.log10(l)/Math.log10(2));
		this.data = new int[l>>2];
	}
	
	public int[] getData() {
		return this.data;
	}
	public void setData(int data[]) {
		this.data = data;
	}

	public int getTag() {
		return this.tag;
	}
	
	public int getTagSize() {
		return tagSize;
	}
	
	public void setTagSize(int tagSize) {
		this.tagSize = tagSize;
	}
	
	public int getOffsetSize() {
		return offsetSize;
	}
	
	public void setOffsetSize(int offsetSize) {
		this.offsetSize = offsetSize;
	}

	public void setTag(int tag2) {
		this.tag = tag2;
		
	}

}
