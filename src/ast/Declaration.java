package ast;

import pretty.PrettyPrinter;

public class Declaration implements Command {
	
	private Variable var;
	private VariableType type;
	
	public Declaration(VariableType t, Variable v) {
		type = t;
		var = v;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		//TODO: Implement this
	}
}
