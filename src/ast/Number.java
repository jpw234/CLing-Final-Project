package ast;

import pretty.PrettyPrinter;

public class Number implements NValue {
	private double value;
	private boolean isInteger;
	
	public Number(double val, boolean isInt) {
		value = val;
		isInteger = isInt;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		if(isInteger) {
			pp.sb.append((int) value).append(' ');
		}
		else pp.sb.append(value).append(' ');
		
		//I changed this to reflect the addition of isInteger - Jared
	}
	
	@Override
	public VariableType inferType(PrettyPrinter pp) {
		return isInteger ? VariableType.INT : VariableType.DOUBLE;
	}
}
