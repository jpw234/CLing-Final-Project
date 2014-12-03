package ast;

import pretty.PrettyPrinter;

public enum BooleanOpType {
	AND, OR, NOT;
	
	public void prettyPrint(PrettyPrinter pp){
		switch(this){
		case AND:
			pp.sb.append("&& ");
			break;
		case OR:
			pp.sb.append("|| ");
			break;
		case NOT:
			pp.sb.append("!");
			break;
		}
	}
}
