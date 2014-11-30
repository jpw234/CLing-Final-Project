package ast;

import pretty.PrettyPrinter;

public class Variable extends BValue implements NValue {
	private String name;
	public VariableType type;
	
	public Variable(String n) {
		name = n;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append(name).append(' ');
	}
	
	@Override
	public VariableType inferType(){
		if(type == null){
			System.err.printf("Type inference on undeclared variable '{0}', assuming it is a double.\r\n", name);
			return VariableType.DOUBLE;
		}
		return type;
	}
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Variable)) return false;
		return name.equals(((Variable)o).name);
	}
	
}
