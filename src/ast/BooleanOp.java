package ast;

import pretty.PrettyPrinter;

public class BooleanOp extends BValue {
	private BooleanOpType type;
	private BValue lValue;
	private BValue rValue;
	
	public BooleanOp(BooleanOpType t, BValue v) {
		type = t;
		lValue = v;
		rValue = null;
	}
	
	public BooleanOp(BooleanOpType t, BValue v1, BValue v2) {
		type = t;
		lValue = v1;
		rValue = v2;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		lValue.prettyPrint(pp);
		type.prettyPrint(pp);
		rValue.prettyPrint(pp);
	}
}
