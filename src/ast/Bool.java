package ast;

import pretty.PrettyPrinter;

public class Bool extends BValue {
	private boolean value;
	
	public Bool(boolean val) {
		value = val;
	}

	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append(value ? "true " : "false ");
	}
}
