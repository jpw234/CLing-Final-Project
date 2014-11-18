package ast;

public class Number implements NValue {
	private double value;
	
	public Number(double val) {
		value = val;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append(value).append(' ');
	}
}
