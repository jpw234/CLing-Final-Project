package ast;

public class ArithmeticRel implements BValue {
	private ArithmeticRelType type;
	private NValue lValue;
	private NValue rValue;
	
	public ArithmeticRel(ArithmeticRelType t, NValue v1, NValue v2) {
		type = t;
		lValue = v1;
		rValue = v2;
	}
	
	public void prettyPrint(StringBuffer sb) {
		//TODO: Implement this
		//NOTE: I'm not yet sure if we're doing <= and >= as their own ArithmeticRelTypes, or as combinations
		// 		of <, > and = yet. If that will change your implementation, wait on it a bit.
	}
	
}
