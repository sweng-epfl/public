package ch.epfl.sweng.testing.part1;

public class Euros extends Currency {
	private int creationYear;
	
	public Euros(int value, int expirationYear, int creationYear) {
		super(value, expirationYear);
		this.creationYear = creationYear;
	}
	
	public int getCreation() {
		return creationYear;
	}
}
