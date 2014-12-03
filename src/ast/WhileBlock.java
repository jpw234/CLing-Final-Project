package ast;

import pretty.PrettyPrinter;

public class WhileBlock implements Command {
	
	private BValue condition;
	private Program content;

	public WhileBlock(BValue cond, Program cont) {
		condition = cond;
		content = cont;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append("while (");
		condition.prettyPrint(pp);
		pp.closeParen();
		content.prettyPrint(pp);
		pp.endLine();
	}
}
