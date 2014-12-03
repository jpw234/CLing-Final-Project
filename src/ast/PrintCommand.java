package ast;

import pretty.PrettyPrinter;

public class PrintCommand implements Command {
	private Value val;
	
	public PrintCommand(Value v) {
		val = v;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append("System.out.println(");
		val.prettyPrint(pp);
		pp.closeParen();
		pp.semicolonEndline();
	}
}
