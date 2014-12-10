package ast;

import pretty.PrettyPrinter;

public class ReturnStatement implements Command {
	
	private Value val;
	
	public ReturnStatement(Value v) {
		val = v;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append("return ");
		val.prettyPrint(pp);
		pp.semicolonEndline();
	}
	
}
