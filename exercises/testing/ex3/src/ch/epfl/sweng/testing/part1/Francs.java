package ch.epfl.sweng.testing.part1;

public class Francs extends Currency {
	private String authority;

	public Francs(int value, int expirationYear, String authority) {
		super(value, expirationYear);
		this.authority = authority;
	}
	
	public String getAuthority() {
		return authority;
	}
}
