package parser;

public class NumToken extends Token {
	protected int index;
	
	public NumToken(int val) {
		super(NUM);
		index = val;
	}
	
	public int getIndex() {
		return index;
	}
}
