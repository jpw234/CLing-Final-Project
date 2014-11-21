package parser;

public class NumToken extends Token {
	protected int value;
	
	public NumToken(int val) {
		super(NUM);
		value = val;
	}
	
	public int getValue() {
		return value;
	}
}
