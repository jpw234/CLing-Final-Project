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
			case Token.DOUBLE:
				t = VariableType.DOUBLE;
			case Token.BOOL:
				t = VariableType.BOOL;
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
		case Token.NUM: {
			return parseNValue();
		}
		case Token.
		}
	}
}
