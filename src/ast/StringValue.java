package ast;

import pretty.PrettyPrinter;

public class StringValue implements Value {
	private String val;
	
	public StringValue(String v) {
		val = v;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append(" \"");
		pp.sb.append(val);
		pp.sb.append("\" ");
	}
	
	public VariableType inferType(PrettyPrinter pp) {
		return VariableType.STRING;
	}
}
