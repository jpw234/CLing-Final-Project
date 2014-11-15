package ast;

public class IfBlock implements Command {
	
	private BValue condition;
	private Program branch1;
	private Program branch2;
	private boolean hasElse = false;

	public IfBlock(BValue cond, Program b1) {
		condition = cond;
		branch1 = b1;
	}
	
	public void addElse(Program b2) {
		branch2 = b2;
		hasElse = true;
	}
	
	public void prettyPrint(StringBuffer sb) {
		//TODO: Implement this
	}
}
