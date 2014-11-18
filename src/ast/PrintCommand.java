package ast;

public class PrintCommand extends Command {
	private Value val;
	
	public PrintCommand(Value v) {
		val = v;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append("System.out.println(");
		val.prettyPrint(pp);
		pp.closeParen();
		super.prettyPrint(pp);
	}
}
