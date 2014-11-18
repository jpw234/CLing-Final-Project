package ast;

import pretty.PrettyPrinter;

public interface Node {
	public void prettyPrint(PrettyPrinter sb);
}
