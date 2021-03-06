package ast;

import pretty.PrettyPrinter;

public class IfBlock implements Command, Block {
	
	private BValue condition;
	private Program branch1;
	//private Program branch2;
	//private boolean hasElse = false;

	public IfBlock(BValue cond) {
		condition = cond;
	}
	
	public void addBranch(Program p) {
		branch1 = p;
	}
	
	/*public void addElse(Program b2) {
		branch2 = b2;
		hasElse = true;
	}*/
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append("if (");
		condition.prettyPrint(pp);
		pp.closeParen();
		branch1.prettyPrint(pp);
		/*if(hasElse){
			pp.sb.append("else ");
			branch2.prettyPrint(pp);
		}*/
		pp.endLine();
	}
}
