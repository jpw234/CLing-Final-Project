package ast;

import pretty.PrettyPrinter;

public interface NValue extends Value {
	public void prettyPrint(PrettyPrinter pp);
}
