package ast;

import pretty.PrettyPrinter;

public interface BValue extends Value {
	public void prettyPrint(PrettyPrinter pp);
}
