package ast;

import pretty.PrettyPrinter;

public class WhileBlock implements Command, Block {
	
	private BValue condition;
	private Program content;

	public WhileBlock(BValue cond) {
		condition = cond;
	}
	
	public void addBranch(Program p) {
		content = p;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append("while (");
		condition.prettyPrint(pp);
		pp.closeParen();
		content.prettyPrint(pp);
		pp.endLine();
	}
}
