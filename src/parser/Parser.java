package parser;

import java.io.BufferedReader;
import java.io.IOException;
import ast.*;

public class Parser {
	Tokenizer tk;
	BufferedReader inp;

	public Parser(BufferedReader r) {
		inp = r;
		tk = new Tokenizer(r);
	}
	
	public Program parseProgram() {
		Program out = new Program();
		
		while(tk.hasNext()) {
			out.addCommand(parseCommand());
		}
		
		return out;
	}
	
	public Command parseCommand() {
		Token comm = tk.next();
		switch(comm.getType()){
		case Token.ASSIGN: {
			tk.consume('(');
			Variable var = parseVariable();
			tk.consume(',');
			Value val = parseValue();
			tk.consume(')');
			return new Assignment(var, val);
		}
			
		case Token.PRINT: {
			tk.consume('(');
			Value val = parseValue();
			tk.consume(')');
			return new PrintCommand(val);
		}
			
		case Token.DECLARE: {
			tk.consume('(');
			Token varType = tk.next();
			VariableType t;
			switch(varType.getType()) {
			case Token.INT:
				t = VariableType.INT;
				break;
			case Token.DOUBLE:
				t = VariableType.DOUBLE;
				break;
			case Token.BOOL:
				t = VariableType.BOOL;
				break;
			default:
				return null;
			}
			tk.consume(',');
			Variable var = parseVariable();
			tk.consume(')');
			return new Declaration(t, var);
		}
			
		case Token.IF: {
			tk.consume('(');
			BValue b = parseBValue();
			tk.consume(')');
			//TODO: Parsing the arrow here
			Program ifBranch = parseProgram();
			//TODO: Figure out how to parse the else branch
			tk.consume(')');
			return new IfBlock(b, ifBranch);
		}
			
		case Token.WHILE: {
			tk.consume('(');
			BValue b = parseBValue();
			tk.consume(')');
			//TODO: Parsing the arrow here
			Program loop = parseProgram();
			return new WhileBlock(b, loop);
		}
			
		default:
			return null;
		}
	}
	
	public Value parseValue() {
		Token next = tk.peek();
		
		switch(next.getType()) {
		case Token.VAR: {
			VarToken v = (VarToken) tk.next();
			return new Variable(v.getName());
		}
		case Token.NUM:
		case Token.ADD:
		case Token.SUBTRACT:
		case Token.MULTIPLY:
		case Token.DIVIDE:
		case Token.MOD:
			return parseNValue();
		case Token.AND:
		case Token.OR:
		case Token.NOT:
		case Token.LESS:
		case Token.EQUAL:
		case Token.GREATER:
		case Token.TRUE:
		case Token.FALSE:
			return parseBValue();
		default:
			return null;
		}
	}
	
	public BValue parseBValue() {
		Token next = tk.next();
		BValue ret;
		
		switch(next.getType()) {
		case Token.TRUE:
			ret = new Bool(true);
			break;
		case Token.FALSE:
			ret = new Bool(false);
			break;
		case Token.NOT:
			tk.consume('(');
			BValue operand = parseBValue();
			tk.consume(')');
			ret = new BooleanOp(BooleanOpType.NOT, operand);
			break;
		case Token.LESS: {
			tk.consume('(');
			NValue n1 = parseNValue();
			tk.consume(',');
			NValue n2 = parseNValue();
			tk.consume(')');
			ret = new ArithmeticRel(ArithmeticRelType.LESS, n1, n2);
			break;
		}
		case Token.EQUAL: {
			tk.consume('(');
			NValue n1 = parseNValue();
			tk.consume(',');
			NValue n2 = parseNValue();
			tk.consume(')');
			ret = new ArithmeticRel(ArithmeticRelType.EQUAL, n1, n2);
			break;
		}
		case Token.GREATER: {
			tk.consume('(');
			NValue n1 = parseNValue();
			tk.consume(',');
			NValue n2 = parseNValue();
			tk.consume(')');
			ret = new ArithmeticRel(ArithmeticRelType.GREATER, n1, n2);
			break;
		}
		default:
			ret = null;
		}
		
		if(tk.peek().getType() == Token.AND) {
			tk.next();//discard the and
			return new BooleanOp(BooleanOpType.AND, ret, parseBValue());
		}
		else if(tk.peek().getType() == Token.OR){
			tk.next();//discard the or
			return new BooleanOp(BooleanOpType.OR, ret, parseBValue());
		}
		else return ret;
	}
	
	public NValue parseNValue() {
		Token next = tk.next();
		
		switch(next.getType()) {
		case Token.NUM:
			NumToken val = (NumToken) next;
			if(val.getValue() % 1 == 0) {//it's an integer
				return new ast.Number((int) val.getValue(), true);
			}
			else return new ast.Number(val.getValue(), true);
		case Token.ADD: {
			tk.consume('(');
			NValue n1 = parseNValue();
			tk.consume(',');
			NValue n2 = parseNValue();
			tk.consume(')');
			return new ArithmeticOp(n1, n2, ArithmeticOpType.PLUS);
		}
		case Token.SUBTRACT: {
			tk.consume('(');
			NValue n1 = parseNValue();
			tk.consume(',');
			NValue n2 = parseNValue();
			tk.consume(')');
			return new ArithmeticOp(n1, n2, ArithmeticOpType.MINUS);
		}
		case Token.MULTIPLY: {
			tk.consume('(');
			NValue n1 = parseNValue();
			tk.consume(',');
			NValue n2 = parseNValue();
			tk.consume(')');
			return new ArithmeticOp(n1, n2, ArithmeticOpType.TIMES);
		}
		case Token.DIVIDE: {
			tk.consume('(');
			NValue n1 = parseNValue();
			tk.consume(',');
			NValue n2 = parseNValue();
			tk.consume(')');
			return new ArithmeticOp(n1, n2, ArithmeticOpType.DIVIDE);
		}
		case Token.MOD: {
			tk.consume('(');
			NValue n1 = parseNValue();
			tk.consume(',');
			NValue n2 = parseNValue();
			tk.consume(')');
			return new ArithmeticOp(n1, n2, ArithmeticOpType.MOD);
		}
		default:
			return null;
		}
	}
	
	public Variable parseVariable() {
		VarToken v = (VarToken) tk.next();
		return new Variable(v.getName());
	}
}
