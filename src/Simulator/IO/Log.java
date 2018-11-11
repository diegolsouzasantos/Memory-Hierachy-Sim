package Simulator.IO;

import java.util.Vector;
/**
 * Class to generate the final report.
 */
public class Log {
	
	/*
	 * fileName - a constant with the name of the report file.
	 * */
	private static final String fileName = "log.txt";
	/*
	 * 
	 * */
	private static final FileHandler fileHandler = new FileHandler(fileName);
	
	public Log() {
	}
	
	public static void write(String string) {
		Vector<String> str = new Vector<String>();
		str.add(string);
		fileHandler.setLines(str);
	}
	
	public static void write(Vector<String> string) {
		fileHandler.setLines(string);
	}
}
