package pretty;

import ast.ArithmeticOp;
import ast.ArithmeticOpType;
import ast.ArithmeticRel;
import ast.ArithmeticRelType;
import ast.Assignment;
import ast.Bool;
import ast.IfBlock;
import ast.NValue;
import ast.Number;
import ast.PrintCommand;
import ast.Program;
import ast.Variable;
import ast.WhileBlock;

public class PrettyPrinter {
	public StringBuffer sb;
	public int tabCount;
	
	public PrettyPrinter(){
		sb = new StringBuffer();
		tabCount = 0;
	}
	
	public void endLine(){
		sb.append("\r\n");
		for(int i = 0; i < tabCount; i++) sb.append('\t');
	}
	
	public void closeParen(){
		sb.setCharAt(sb.length() - 1, ')');
		sb.append(' ');
	}
	
	public void semicolonEndline(){
		sb.setCharAt(sb.length() - 1, ';');
		endLine();
	}
	
	
	public static void main(String... args){
		Program p = new Program();
		ArithmeticOp op = new ArithmeticOp(new Number(53), new Number(10), ArithmeticOpType.DIVIDE);
		ArithmeticRel r = new ArithmeticRel(ArithmeticRelType.GE, op, new Number(5));
		Program then = new Program();
		then.addCommand(new PrintCommand(new Number(1)));
		Program els = new Program();
		Program whil = new Program();
		whil.addCommand(new Assignment(new Variable("x"), new Number(5)));
		WhileBlock wb = new WhileBlock(new Bool(true), whil);
		els.addCommand(wb);
		IfBlock ifb = new IfBlock(r, then);
		ifb.addElse(els);
		p.addCommand(ifb);
		PrettyPrinter pp = new PrettyPrinter();
		p.prettyPrint(pp);
		System.out.print(pp.sb.toString());
	}
}
