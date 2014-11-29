package ast;

import pretty.PrettyPrinter;

public class ArithmeticOp implements NValue {
	private ArithmeticOpType type;
	private NValue lValue;
	private NValue rValue;
	
	public ArithmeticOp(NValue v1, NValue v2, ArithmeticOpType t) {
		type = t;
		lValue = v1;
		rValue = v2;
	}
	
	@Override
	public void prettyPrint(PrettyPrinter pp) {
		lValue.prettyPrint(pp);
		type.prettyPrint(pp);
		rValue.prettyPrint(pp);
	}

	@Override
	public VariableType inferType() {
		return lValue.inferType() == VariableType.DOUBLE || rValue.inferType() == VariableType.DOUBLE ? VariableType.DOUBLE : VariableType.INT;
	}
}