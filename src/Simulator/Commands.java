package Simulator;

import java.util.Arrays;
import java.util.Vector;
import Simulator.Cache.*;
import Simulator.Exceptions.*;
import Simulator.IO.Log;
import Simulator.Pointer.Value;
import Simulator.RAM.*;

public class Commands {


	/**
	 * Execute all the commands in the file of commands.
	 * Test if any commands is out of order.
	 * Throw OutOfOrder, NotACommand and ParameterInvalid.
	 * Not finish.
	 * @param commands - Vector of String with all the commands.
	 * @throws NotPowerOf2 
	 * @throws NotMultiple 
	 * @throws NumberFormatException 
	 * @throws ParametersInvalid 
	 * @throws IncompatibleCacheSize 
	 */
	@SuppressWarnings("static-access")
	public static void executeCommands(Vector<String> commands) throws OutOfOrder, NotACommand, NumberFormatException, NotMultiple, NotPowerOf2, ParametersInvalid, IncompatibleCacheSize {
		int []hits = new int [5];
		SACache l1d = null;
		SACache l1i = null;
		SACache l2 = null;
		SACache l3 = null;
		MainMemory mp = null;
		Memory mem = null;
		Processor cp =  null;
		Boolean[] statsCreation = new Boolean[7];
		Arrays.fill(statsCreation, Boolean.FALSE);
		//use Arrays.compare to see if all the elements are true.
		for (String string : commands) {
			/*
			 * finish the execution of the commands
			 * and figure out how return those objs to the main
			 * */
			String str = null;
			try {
				str = string.substring(0,string.indexOf(' '));
			} catch (Exception e) {
				str = string;
			}
			
			/*
			 * Select the command and find the param
			 * */
			switch (str.toLowerCase()) {
			case "cl1d":
				//param c a l
				if(testOrder(statsCreation, 0)) {
					statsCreation[0] = true;
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string.substring(0,string.indexOf(' '));
					string = string.substring(param2.length() + 1);
					String param3 = string;
					l1d = SACache.createSACache(Integer.parseInt(param1),Integer.parseInt(param2), Integer.parseInt(param3));
					Log.write("commmand " + str + " execute with success");
				}else {
					throw new OutOfOrder("command cl1d out of order");
				}
				break;
			case "cl1i":
				//param c a l
				if(testOrder(statsCreation, 1)) {
					statsCreation[1] = true;
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string.substring(0,string.indexOf(' '));
					string = string.substring(param2.length() + 1);
					String param3 = string;
					l1i = SACache.createSACache(Integer.parseInt(param1),Integer.parseInt(param2), Integer.parseInt(param3));
					Log.write("commmand " + str + " execute with success");
				}else {
					throw new OutOfOrder("command cl1i out of order");
				}
				break;
			case "cl2":
				//param c a l
				if(testOrder(statsCreation, 2)) {
					statsCreation[2] = true;
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string.substring(0,string.indexOf(' '));
					string = string.substring(param2.length() + 1);
					String param3 = string;
					l2 = SACache.createSACache(Integer.parseInt(param1),Integer.parseInt(param2), Integer.parseInt(param3));
					Log.write("commmand " + str + " execute with success");
				}else {
					throw new OutOfOrder("command cl2 out of order");
				}
				break;
			case "cl3":
				//param c a l
				if(testOrder(statsCreation, 3)) {
					statsCreation[3] = true;
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string.substring(0,string.indexOf(' '));
					string = string.substring(param2.length() + 1);
					String param3 = string;
					l3 = SACache.createSACache(Integer.parseInt(param1),Integer.parseInt(param2), Integer.parseInt(param3));
					Log.write("commmand " + str + " execute with success");
				}else {
					throw new OutOfOrder("command cl3 out of order");
				}
				break;
			case "cmp":
				//param ramsize vmsize
				if(testOrder(statsCreation, 4)) {
					statsCreation[4] = true;
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string;
					mp = MainMemory.createMainMemory(Integer.parseInt(param1), Integer.parseInt(param2));
					Log.write("commmand " + str + " execute with success");
				}else {
					throw new OutOfOrder("command cmp out of order");
				}
				break;
			case "cmem":
				if(testOrder(statsCreation, 5)) {
					statsCreation[5] = true;
					mem = Memory.createMemory(Cache.createCache(l1d, l1i, l2, l3), mp);
					Log.write("commmand " + str + " execute with success");
				}else {
					throw new OutOfOrder("command cmem out of order");
				}
				break;
			case "cp":
				//param n
				if(testOrder(statsCreation, 6)) {
					statsCreation[6] = true;
					String param1 = string.substring(str.length() + 1);
					cp = Processor.createProcessor(mem, Integer.parseInt(param1));
					Log.write("commmand " + str + " execute with success");
				}else {
					throw new OutOfOrder("command cp out of order");
				}
				break;
			case "ri":
				//param n addr
				//test this later
				if(statsCreation[6]) {
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string;
					//run the command
					Value v = new Value(0);
					//fix this
					int i = cp.getCores()[Integer.parseInt(param1)].getInstruction(Integer.parseInt(param2),v);
					Log.write("commmand " + str + " execute with i = " + i);
					if(i ==-1) {
						hits[0]++;
					}else {
						hits[i]++;
					}
				}else {
					throw new OutOfOrder("creations commands must be execute frist");
				}
				break;
			case "wi":
				//param n addr value
				if(statsCreation[6]) {
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string.substring(0,string.indexOf(' '));
					string = string.substring(param2.length() + 1);
					String param3 = string;
					//run the command
					Value v = new Value(Integer.parseInt(param3));
					int i = cp.getCores()[Integer.parseInt(param1)].setInstruction(cp.getCores()[Integer.parseInt(param1)],Integer.parseInt(param2),v);
					Log.write("commmand " + str + " execute with i = " + i);
					if(i ==-1) {
						hits[0]++;
					}else {
						hits[i]++;
					}
				}else {
					throw new OutOfOrder("creations commands must be execute frist");
				}
				break;
			case "rd":
				//param n addr
				if(statsCreation[6]) {
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string;
					//run the command
					int i = cp.getCores()[Integer.parseInt(param1)].getData(Integer.parseInt(param2),new  Value(0));
					Log.write("commmand " + str + " execute with i = " + i);
					if(i ==-1) {
						hits[0]++;
					}else {
						hits[i]++;
					}
				}else {
					throw new OutOfOrder("creations commands must be execute frist");
				}
				break;
			case "wd":
				//param n addr
				if(statsCreation[6]) {
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string.substring(0,string.indexOf(' '));
					string = string.substring(param2.length() + 1);
					String param3 = string;
					//run the command
					Value v = new Value(Integer.parseInt(param3));
					int i = cp.getCores()[Integer.parseInt(param1)].setData(cp.getCores()[Integer.parseInt(param1)],Integer.parseInt(param2),v);
					Log.write("commmand " + str + " execute with i = " + i);
					if(i ==-1) {
						hits[0]++;
					}else {
						hits[i]++;
					}
				}else {
					throw new OutOfOrder("creations commands must be execute frist");
				}
				break;
			case "asserti":
				//param n addr value level value
				if(statsCreation[6]) {
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string.substring(0,string.indexOf(' '));
					string = string.substring(param2.length() + 1);
					String param3 = string.substring(0,string.indexOf(' '));
					string = string.substring(param3.length() + 1);
					String param4 = string;
					int i = cp.getCores()[Integer.parseInt(param1)].getInstruction(Integer.parseInt(param2),new  Value(Integer.parseInt(param4)));
					Log.write("commmand " + str + " execute with i = " + i);
					if(i == Integer.parseInt(param3)) {
						hits[0]++;
					}else {
						hits[i]++;
					}
					//run the command
				}else {
					throw new OutOfOrder("creations commands must be execute frist");
				}
				break;
			case "assertd":
				//param n addr level value
				if(statsCreation[6]) {
					string = string.substring(str.length() + 1);
					String param1 = string.substring(0,string.indexOf(' '));
					string = string.substring(param1.length() + 1);
					String param2 = string.substring(0,string.indexOf(' '));
					string = string.substring(param2.length() + 1);
					String param3 = string.substring(0,string.indexOf(' '));
					string = string.substring(param3.length() + 1);
					String param4 = string;
					int i = cp.getCores()[Integer.parseInt(param1)].getData(Integer.parseInt(param2),new  Value(Integer.parseInt(param4)));
					Log.write("commmand " + str + " execute with i = " + i);
					if(i == Integer.parseInt(param3)) {
						hits[0]++;
					}else {
						hits[i]++;
					}
					//run the command
				}else {
					throw new OutOfOrder("creations commands must be execute frist");
				}
				break;
			default:
				throw new NotACommand("not a command" +  " var string = " + string + " var str = " + str);
			}
		}
		Log.write("Processor: " + cp.getNcores() + " numero de cores");
		Log.write("RAM: " + cp.getCores()[0].getMM().getRamsize() + " ram size, " + cp.getCores()[0].getMM().getVmsize() + " virtual size");
		Log.write("Cache:");
		Log.write(cp.getCores()[0].getCache().getL1dCache().getCapacity() + "B L1 capacity, " + 
				cp.getCores()[0].getCache().getL1dCache().getAssociativity() + "B L1 associativity, " +
				cp.getCores()[0].getCache().getL1dCache().getLineSize() + "B L1 bytes per line."
				);
		Log.write(cp.getCores()[0].getCache().getL2Cache().getCapacity() + "B L2 capacity, " + 
				cp.getCores()[0].getCache().getL2Cache().getAssociativity() + "B L2 associativity, " +
				cp.getCores()[0].getCache().getL2Cache().getLineSize() + "B L2 bytes per line."
				);
		Log.write(cp.getCores()[0].getCache().getL3Cache().getCapacity() + "B L3 capacity, " + 
				cp.getCores()[0].getCache().getL3Cache().getAssociativity() + "B L3 associativity, " +
				cp.getCores()[0].getCache().getL3Cache().getLineSize() + "B L3 bytes per line."
				);
		Log.write("");
		int count = 0;
		for (int i : hits) {
			Log.write("Hits[" + count + "] = " + i);
			count++;
		}
	}
	
	/**
	 * Test if a commands is out of order.
	 */
	private static boolean testOrder(Boolean[] bool, int pos)  {
		int i = 0;
		for (Boolean boolean1 : bool) {
			if(i < pos) {
				if(!boolean1) {
					return false;
				}
			}else if(i > pos) {
				if(boolean1) {
					return false;
				}
			}
			++i;
		}
		return true;
	}
}
