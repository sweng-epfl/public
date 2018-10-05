package ch.epfl.sweng.testing.part1;

abstract public class Currency {
	private int value;
	private int expirationYear;
	
	public Currency(int value, int expirationYear) {
		this.value = value;
		this.expirationYear = expirationYear;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getExpiration() {
		return expirationYear;
	}
}
