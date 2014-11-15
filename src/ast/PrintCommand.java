package ast;

public class PrintCommand implements Command {
	private Value val;
	
	public PrintCommand(Value v) {
		val = v;
	}
	
	public void prettyPrint(StringBuffer sb) {
		//TODO: Implement this
	}
}
