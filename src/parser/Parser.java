package parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import ast.*;
import java.util.HashMap;
import java.util.LinkedList;

/* STUFF TO ASK JANE ABOUT:
 * Representations of if and while
 * Representation of Declare syntax (is that int(x), double(x), bool(x), string(x)?) - DONE
 * return statement? Add that to grammar? - DONE
 * Strings
 * What are the characters that are outputted for "and" and "or" and "not" so i can add them to tokenizer - DONE
 * how will the passing of variable/string/numbers affect this parser? - this affects point, number/hundred/thousand/etc.
 */

//FILES TO UPDATE ON LATEST PATCH: ast/VariableType, Parser, Token, Tokenizer, BooleanOp

/* Input assumed to be an array of strings, 3 hashmaps for ints/doubles/strings
 * elements are either lambda calc outputs or open/close parens for block structure
 * 
 */


public class Parser {
	Tokenizer tk;
	BufferedReader inp;
	
	String[] lines;
	HashMap<Integer, Integer> ints;
	HashMap<Integer, Double> doubles;
	HashMap<Integer, String> strings;
	
	LinkedList<Program> blocks = new LinkedList<Program>();
	
	
	/*public Parser(BufferedReader r) {
		inp = r;
		tk = new Tokenizer(r);
	}*/
	
	public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

	public Parser(String[] l, HashMap<Integer, Integer> i, HashMap<Integer, Double> d, HashMap<Integer, String> s) {
		lines = l;
		ints = i;
		doubles = d;
		strings = s;
	}
	
	public Program parseProgram() {
		Program out = new Program();
		
		for(int a = 0; a < lines.length; a++) {
			if(lines[a].equals("(")) {
				blocks.push(new Program());
			}
			else if(lines[a].equals(")")) {
				Program data = blocks.pop();
				if(blocks.peek() == null) out.addBranchToBlock(data);
				else blocks.peek().addBranchToBlock(data);
			}
			else {
				BufferedReader br = new BufferedReader(new StringReader(convertFromUTF8(lines[a])));
				tk = new Tokenizer(br);
				
				if(blocks.peek() == null) out.addCommand(parseCommand());
				else blocks.peek().addCommand(parseCommand());
			}
		}
		
		return out;
	}
	
	public Command parseCommand() {
		Token comm = tk.next();
		switch(comm.getType()){
		case Token.ASSIGN: {
			tk.next(); //LParen
			Variable var = parseVariable();
			tk.next(); //Comma
			Value val = parseValue();
			tk.next(); //RParen
			return new Assignment(var, val);
		}
			
		case Token.PRINT: {
			tk.next(); //This should be an LParen
			Value val = parseValue();
			tk.next(); //This should be an RParen
			return new PrintCommand(val);
		}
		
		case Token.INT: {
			tk.next(); //LParen
			Variable var = parseVariable();
			tk.next(); //RParen
			return new Declaration(VariableType.INT, var);
		}

		case Token.DOUBLE: {
			tk.next(); //LParen
			Variable var = parseVariable();
			tk.next(); //RParen
			return new Declaration(VariableType.DOUBLE, var);
		}
		
		case Token.BOOL: {
			tk.next(); //LParen
			Variable var = parseVariable();
			tk.next(); //RParen
			return new Declaration(VariableType.BOOL, var);
		}
		
		case Token.STRING: {
			tk.next(); //LParen
			Variable var = parseVariable();
			tk.next(); //RParen
			return new Declaration(VariableType.STRING, var);
		}
		
		case Token.RETURN: {
			tk.next(); //LParen
			Value val = parseValue();
			tk.next(); //RParen
			return new ReturnStatement(val);
		}
			
		case Token.IF: {
			tk.next(); //LParen
			BValue b = parseBValue();
			tk.next(); //RParen
			return new IfBlock(b);
		}
		
		case Token.ELSE: {
			return new ElseBlock();
		}
			
		case Token.WHILE: {
			tk.next(); //LParen
			BValue b = parseBValue();
			tk.next(); //RParen
			return new WhileBlock(b);
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
		case Token.VAR: 
			ret = new Variable(((VarToken) next).getName());
		case Token.NOT:
			tk.next(); //LBracket
			BValue operand = parseBValue();
			tk.next(); //RBracket
			ret = new BooleanOp(BooleanOpType.NOT, operand);
			break;
		case Token.LESS: {
			tk.next(); //LParen
			NValue n1 = parseNValue();
			tk.next(); //Comma
			NValue n2 = parseNValue();
			tk.next(); //RParen
			ret = new ArithmeticRel(ArithmeticRelType.LESS, n1, n2);
			break;
		}
		case Token.EQUAL: {
			tk.next(); //LParen
			NValue n1 = parseNValue();
			tk.next(); //Comma
			NValue n2 = parseNValue();
			tk.next(); //RParen
			ret = new ArithmeticRel(ArithmeticRelType.EQUAL, n1, n2);
			break;
		}
		case Token.GREATER: {
			tk.next(); //LParen
			NValue n1 = parseNValue();
			tk.next(); //Comma
			NValue n2 = parseNValue();
			tk.next(); //RParen
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
		case Token.VAR: {
			VarToken var = (VarToken) next;
			return new Variable(var.getName());
		}
		case Token.ADD: {
			tk.next(); //LParen
			NValue n1 = parseNValue();
			tk.next(); //Comma
			NValue n2 = parseNValue();
			tk.next(); //RParen
			return new ArithmeticOp(n1, n2, ArithmeticOpType.PLUS);
		}
		case Token.SUBTRACT: {
			tk.next(); //LParen
			NValue n1 = parseNValue();
			tk.next(); //Comma
			NValue n2 = parseNValue();
			tk.next(); //RParen
			return new ArithmeticOp(n1, n2, ArithmeticOpType.MINUS);
		}
		case Token.MULTIPLY: {
			tk.next(); //LParen
			NValue n1 = parseNValue();
			tk.next(); //Comma
			NValue n2 = parseNValue();
			tk.next(); //RParen
			return new ArithmeticOp(n1, n2, ArithmeticOpType.TIMES);
		}
		case Token.DIVIDE: {
			tk.next(); //LParen
			NValue n1 = parseNValue();
			tk.next(); //Comma
			NValue n2 = parseNValue();
			tk.next(); //RParen
			return new ArithmeticOp(n1, n2, ArithmeticOpType.DIVIDE);
		}
		case Token.MOD: {
			tk.next(); //LParen
			NValue n1 = parseNValue();
			tk.next(); //Comma
			NValue n2 = parseNValue();
			tk.next(); //RParen
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
