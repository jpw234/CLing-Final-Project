package parser;

public class Token {
	/* Tokens:
	 * assign, print, declare, return, if, else, while
	 * multiply, divide, add, subtract, mod
	 * greater, less, equal
	 * or(the upside-down carat), and(the carat), not, implies(the arrow)
	 * negative, point
	 * hundred, thousand, million, billion, number
	 * int, double, bool, string
	 * constant, variable, True, False
	 * lparen, rparen, lbracket, rbracket
	 */
	
	//Commands between 0 and 10
	public static final int ASSIGN = 0;
	public static final int PRINT = 1;
	public static final int DECLARE = 2;
	public static final int RETURN = 3;
	//Gap between control flow and other
	public static final int IF = 6;
	public static final int ELSE = 7;
	public static final int WHILE = 8;
	
	//Arithmetic operations between 11 and 20
	public static final int ADD = 11;
	public static final int SUBTRACT = 12;
	//Gap between multiplicative and additive
	public static final int MULTIPLY = 16;
	public static final int DIVIDE = 17;
	public static final int MOD = 18;
	
	//Arithmetic relations between 21 and 25
	public static final int GREATER = 21;
	public static final int LESS = 22;
	public static final int EQUAL = 23;
	
	//Boolean relations between 26 and 30
	public static final int OR = 26;
	public static final int AND = 27;
	public static final int NOT = 28;
	public static final int IMPLIES = 29;
	
	//Numeric modifiers between 31 and 40
	public static final int NEG = 31;
	public static final int POINT = 32;
	public static final int NUMBER = 33;
	//Gap for magnitude orders
	public static final int HUNDRED = 36;
	public static final int THOUSAND = 37;
	public static final int MILLION = 38;
	public static final int BILLION = 39;
	
	//Types between 41 and 50
	public static final int INT = 41;
	public static final int DOUBLE = 42;
	public static final int BOOL = 43;
	public static final int STRING = 44;
	
	//Literals between 51 and 60
	public static final int NUM = 51;
	public static final int VAR = 52;
	public static final int TRUE = 53;
	public static final int FALSE = 54;
	
	//Syntactic markers between 61 and 70
	public static final int LPAREN = 61;
	public static final int RPAREN = 62;
	public static final int LBRACKET = 63;
	public static final int RBRACKET = 64;
	public static final int COMMA = 65;
	
	//Error code
	public static final int ERROR = 500;
	
	protected final int type;
	
	public Token(int t) {
		type = t;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean isNum() {
		return type == NUM;
	}
	
	public boolean isVar() {
		return type == VAR;
	}
}
