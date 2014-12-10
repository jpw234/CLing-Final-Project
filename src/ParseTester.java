import ast.*;
import parser.*;
import pretty.*;
import java.io.*;

public class ParseTester {
	public static void main(String[] args) {
		
		String[] data = {"if(greater(x,N4))", "(", "assign(y,N5)", "assign(z,N6)", ")", "else", "(",
				"assign(y,N2)", ")"
		};
		
		Parser parse = new Parser(data, null, null, null);
		
		PrettyPrinter pp = new PrettyPrinter();
		
		parse.parseProgram().prettyPrint(pp);
		
		System.out.println(pp.sb.toString());
		
	}
}