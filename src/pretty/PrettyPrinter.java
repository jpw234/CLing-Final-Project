package pretty;

import java.util.HashMap;
import java.util.Map;

import ast.ArithmeticOp;
import ast.ArithmeticOpType;
import ast.ArithmeticRel;
import ast.ArithmeticRelType;
import ast.Assignment;
import ast.Bool;
import ast.Declaration;
import ast.IfBlock;
import ast.Number;
import ast.PrintCommand;
import ast.Program;
import ast.Variable;
import ast.VariableType;
import ast.WhileBlock;

public class PrettyPrinter {
	public StringBuffer sb;
	public int tabCount;
	public Map<Variable, VariableType> declaredVariables;
	
	public PrettyPrinter(){
		sb = new StringBuffer();
		tabCount = 0;
		declaredVariables = new HashMap<Variable, VariableType>();
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
		ArithmeticOp op = new ArithmeticOp(new Number(53, true), new Number(10, true), ArithmeticOpType.DIVIDE);
		ArithmeticRel r = new ArithmeticRel(ArithmeticRelType.GE, op, new Number(5, true));
		Program then = new Program();
		then.addCommand(new PrintCommand(new Number(1, true)));
		Program els = new Program();
		Program whil = new Program();
		whil.addCommand(new Assignment(new Variable("x"), new ArithmeticOp(new Number(1, true), new Number(5, false), ArithmeticOpType.DIVIDE)));
		whil.addCommand(new Assignment(new Variable("y"), new Number(2, true)));
		WhileBlock wb = new WhileBlock(new Bool(true));
		wb.addBranch(whil);
		els.addCommand(wb);
		IfBlock ifb = new IfBlock(r);
		ifb.addBranch(then);
		Declaration dec = new Declaration(VariableType.INT, new Variable("y"));
		p.addCommand(dec);
		p.addCommand(ifb);
		PrettyPrinter pp = new PrettyPrinter();
		p.prettyPrint(pp);
		System.out.print(pp.sb.toString());
	}

	public void declareVariable(Variable var, VariableType type) {
		declaredVariables.put(var, type);
		type.prettyPrint(this);
		var.prettyPrint(this);
	}
}
