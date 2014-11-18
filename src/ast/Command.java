package ast;

public abstract class Command implements Node {
	public void prettyPrint(PrettyPrinter pp){
		pp.sb.setCharAt(pp.sb.length() - 1, ';');
		pp.endLine();
	};
}
