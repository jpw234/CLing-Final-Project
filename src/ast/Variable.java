package ast;

import pretty.PrettyPrinter;

public class Variable implements NValue, BValue {
	private String name;
	
	public Variable(String n) {
		name = n;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append(name).append(' ');
	}
}
