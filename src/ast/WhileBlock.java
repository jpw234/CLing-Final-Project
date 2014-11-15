package ast;

public class WhileBlock implements Command {
	
	private BValue condition;
	private Program content;

	public WhileBlock(BValue cond, Program cont) {
		condition = cond;
		content = cont;
	}
	
	public void prettyPrint(StringBuffer sb) {
		//TODO: Implement this
	}
}
