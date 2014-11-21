package parser;

public class VarToken extends Token {
	protected String name;
	
	public VarToken(String n) {
		super(VAR);
		name = n;
	}
	
	public String getName() {
		return name;
	}
}
