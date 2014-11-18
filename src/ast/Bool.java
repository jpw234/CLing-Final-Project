package ast;

public class Bool implements BValue {
	private boolean value;
	
	public Bool(boolean val) {
		value = val;
	}

	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append(value ? "true " : "false ");
	}
}
