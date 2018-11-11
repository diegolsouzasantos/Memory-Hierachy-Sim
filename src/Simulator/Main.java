
package Simulator;

import java.util.Scanner;
import java.util.Vector;
import Simulator.Exceptions.IncompatibleCacheSize;
import Simulator.Exceptions.NotACommand;
import Simulator.Exceptions.NotMultiple;
import Simulator.Exceptions.NotPowerOf2;
import Simulator.Exceptions.OutOfOrder;
import Simulator.Exceptions.ParametersInvalid;
import Simulator.IO.FileHandler;


public class Main {

	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws ParametersInvalid {
		
		System.out.println("Enter the name of the file with the commands.");
		String fileName = scan.next();
		if(fileName.equals("c")) {
			fileName = "commands.txt";
		}
		FileHandler file = new FileHandler(fileName);
		Vector<String> commands = file.getLines();
		try {
			Commands.executeCommands(commands);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfOrder e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotACommand e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotMultiple e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotPowerOf2 e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncompatibleCacheSize e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End");
	}
}
