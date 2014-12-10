package ast;

import pretty.PrettyPrinter;

public enum VariableType {
	INT, DOUBLE, BOOL, STRING;//More to come...

	public void prettyPrint(PrettyPrinter pp){
		switch(this) {
		case INT:
			pp.sb.append("int ");
			break;
		case DOUBLE:
			pp.sb.append("double ");
			break;
		case BOOL:
			pp.sb.append("boolean ");
			break;
		case STRING:
			pp.sb.append("string ");
			break;
		}
	}
}
