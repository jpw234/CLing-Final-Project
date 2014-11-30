package ast;

import pretty.PrettyPrinter;

public abstract class BValue implements Value {

	@Override
	public VariableType inferType(PrettyPrinter pp) {
		return VariableType.BOOL;
	}
}
