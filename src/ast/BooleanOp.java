package ast;

public class BooleanOp implements BValue {
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
	
	public void prettyPrint(StringBuffer sb) {
		//TODO: Implement this
	}
}
