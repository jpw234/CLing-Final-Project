package parser;

public class VarToken extends Token {
	protected int index;
	
	public VarToken(int n) {
		super(VAR);
		index = n;
	}
	
	public int getIndex() {
		return index;
	}
}
