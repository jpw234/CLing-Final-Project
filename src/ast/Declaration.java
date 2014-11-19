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
		switch(type) {
		case INT:
			pp.sb.append("int ");
		case DOUBLE:
			pp.sb.append("double ");
		case BOOL:
			pp.sb.append("boolean ");
		default:
			//uh-oh
		}
		
		var.prettyPrint(pp);
		pp.semicolonEndline();
	}
}
