package ast;

import pretty.PrettyPrinter;

public enum ArithmeticRelType {
	LESS, GREATER, EQUAL, NE, GE, LE;
	
	public void prettyPrint(PrettyPrinter pp){
		switch(this){
		case LESS:
			pp.sb.append("< ");
			break;
		case GREATER:
			pp.sb.append("> ");
			break;
		case EQUAL:
			pp.sb.append("== ");
			break;
		case NE:
			pp.sb.append("!= ");
			break;
		case GE:
			pp.sb.append(">= ");
			break;
		case LE:
			pp.sb.append("<= ");
			break;
		}
	}
}
