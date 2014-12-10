package parser;

public class StringToken extends Token{
	protected int index;
	
	public StringToken(int val) {
		super(STR);
		index = val;
	}
	
	public double getIndex() {
		return index;
	}
}
