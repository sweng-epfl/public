package ch.epfl.sweng.coding_style.exercise;

import java.io.IOException;

/**
 * Main class running the client.
 * 
 * Request the Recaman's sequence up to the n-th term to the server. Expects to
 * receive this number n as an argument when launching the program.
 * 
 * @author John Doe
 */
public class MainClient {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("The program " + "needs a number as argument");
		}
		int n = checkPositive(Integer.parseInt(args[0]));
		Client client = new Client();
		System.out.println("Requesting Recaman sequence for " + n);
		String sequence = client.requestRecamanSequence(n);
		System.out.println("The sequence is : " + sequence);
	}

	/**
	 * Checks that a number is strictly greater than 0
	 * 
	 * @param n the parameter that needs to be positive
	 * @throws IllegalArgumentException if n is negative or equal to 0
	 */
	private static int checkPositive(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Recaman sequence " + "is undefined for negative values");
		}
		return n;
	}
}
