package Simulator.IO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Vector;

/**
 * Class responsible for reading of a file and writing in a file 
 * */
public class FileHandler {
	
	private File file;
	/**
	 * Construct the fileHandler
	 * @param pathname- string with the name or path and name to the file. 
	 */
	public FileHandler(String pathname) {
		this.file = new File(pathname);
	}
	
	/**
	 * Method responsible for reading the file and returning all the lines of the file.
	 * @return return all the lines in the format of Vector<String>
	 */
	public Vector<String> getLines(){
		try(BufferedReader reader = Files.newBufferedReader(this.file.toPath())){
			Vector<String> lines = new Vector<String>();
			String line =  null;
			while((line = reader.readLine()) != null) {
				lines.add(line);
			}
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Method responsible for writing the lines in the file.
	 * @param lines, Vector<String> with all the lines that will be written in the file.
	 * @return return true if no problem occurs and false if some problem occur.
	 */
	public boolean setLines(Vector<String> lines) {
		try(BufferedWriter writer = Files.newBufferedWriter(this.file.toPath(),StandardOpenOption.APPEND)) {
			for (String string : lines) {
				writer.write(string);
				writer.newLine();
			}
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}
}
