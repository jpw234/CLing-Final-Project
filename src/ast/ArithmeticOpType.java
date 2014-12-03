package ast;

import pretty.PrettyPrinter;

public enum ArithmeticOpType {
	PLUS, MINUS, TIMES, DIVIDE, MOD;
		
	public void prettyPrint(PrettyPrinter pp){
		switch(this){
		case PLUS:
			pp.sb.append("+ ");
			break;
		case MINUS:
			pp.sb.append("- ");
			break;
		case TIMES:
			pp.sb.append("* ");
			break;
		case DIVIDE:
			pp.sb.append("/ ");
			break;
		case MOD:
			pp.sb.append("% ");
			break;
		}
	}
}

