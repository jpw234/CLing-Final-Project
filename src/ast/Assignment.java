package ast;

import pretty.PrettyPrinter;

public class Assignment implements Command {

	private Variable var;
	private Value val;
	
	public Assignment(Variable v1, Value v2) {
		var = v1;
		val = v2;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		if(pp.declaredVariables.containsKey(var)){
			var.prettyPrint(pp);
		}else{
			pp.declareVariable(var, val.inferType(pp));
		}
		pp.sb.append("= ");
		val.prettyPrint(pp);
		pp.semicolonEndline();
	}
}
