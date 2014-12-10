import ast.*;
import java.util.HashMap;
import parser.*;
import pretty.*;
import java.io.*;

public class ParseTester {
	public static void main(String[] args) {
		
		String[] data = { "if(less(V1,N1))", "(", "print(S1)", ")", "else", "(", 
				"return(multiply(D1,D2))", ")", "print(S2)"
		};
		
		HashMap<Integer, Integer> i = new HashMap<Integer, Integer>();
		i.put(1, 1);
		i.put(2, 2);
		
		HashMap<Integer, Double> d = new HashMap<Integer, Double>();
		d.put(1, 3.0);
		d.put(2, 4.0);
		
		HashMap<Integer, String> s = new HashMap<Integer, String>();
		s.put(1, "hi");
		s.put(2, "bye");
		
		Parser parse = new Parser(data, i, d, s);
		
		PrettyPrinter pp = new PrettyPrinter();
		
		parse.parseProgram().prettyPrint(pp);
		
		System.out.println(pp.sb.toString());
		
	}
}