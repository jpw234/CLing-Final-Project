package ast;

import pretty.PrettyPrinter;

public class ArithmeticRel implements BValue {
	private ArithmeticRelType type;
	private NValue lValue;
	private NValue rValue;
	
	public ArithmeticRel(ArithmeticRelType t, NValue v1, NValue v2) {
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
