package parser;

public class NumToken extends Token {
	protected double value;
	
	public NumToken(double val) {
		super(NUM);
		value = val;
	}
	
	public double getValue() {
		return value;
	}
}
