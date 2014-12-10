package ast;

import pretty.PrettyPrinter;

public class ElseBlock implements Command, Block {
	
	private Program branch;
	
	public ElseBlock() {
	}
	
	public void addBranch(Program p) {
		branch = p;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append("else ");
		branch.prettyPrint(pp);
		pp.endLine();
	}

}
