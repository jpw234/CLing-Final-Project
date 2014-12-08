import ast.*;
import parser.*;
import pretty.*;
import java.io.*;

public class ParseTester {
	public static void main(String[] args) {
		
		try {
			BufferedReader data = new BufferedReader(new FileReader("Test1.txt"));
			
			Parser parse = new Parser(data);
			
			PrettyPrinter pp = new PrettyPrinter();
			
			parse.parseProgram().prettyPrint(pp);
			
			System.out.println(pp.sb.toString());
		}
		catch(FileNotFoundException e) {
			System.out.println("no file");
		}
		
	}
}