package parser;

public class DoubleToken extends Token {
	protected int index;
	
	public DoubleToken(int n) {
		super(DOUB);
		index = n;
	}
	
	public int getIndex() {
		return index;
	}
}
