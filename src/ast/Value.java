package ast;

import pretty.PrettyPrinter;

public interface Value extends Node {
	public void prettyPrint(PrettyPrinter pp);
	public VariableType inferType(PrettyPrinter pp);
}
