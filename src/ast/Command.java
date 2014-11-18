package ast;

import pretty.PrettyPrinter;

public interface Command extends Node {
	public void prettyPrint(PrettyPrinter pp);
}
