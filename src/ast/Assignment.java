package ast;

public class Assignment implements Command {

	private Variable var;
	private Value val;
	
	public Assignment(Variable v1, Value v2) {
		var = v1;
		val = v2;
	}
	
	public void prettyPrint(StringBuffer sb) {
		//TODO: Implement this
	}
}
