package Simulator;

/**
 * Class use to test other class 
 */
public class Test {
	 
	/**
	 * Do anything. 
	 */
	public static void Test1() {
		int n = 2;
		for(int i = 0; i < 30; ++i) {
			if((n & (n - 1)) == 0) {
				System.out.println((int)(Math.log10(n)/Math.log10(2)));
			}else{
				System.out.println("fuck off");
			}
			n =n * 2;
		}
		
	}
}
