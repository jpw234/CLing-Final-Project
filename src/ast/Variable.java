package ast;

public class Variable implements NValue, BValue {
	private String name;
	
	public Variable(String n) {
		name = n;
	}
	
	public void prettyPrint(StringBuffer sb) {
		//TODO: Implement this
	}
}
