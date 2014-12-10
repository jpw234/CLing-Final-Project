import ast.*;
import parser.*;
import pretty.*;
import java.io.*;

public class ParseTester {
	public static void main(String[] args) {
		
		try {
			InputStream is = new FileInputStream("Test1.txt");
			
			BufferedReader data = new BufferedReader(new InputStreamReader(is, "utf8"));
			
			Parser parse = new Parser(data);
			
			PrettyPrinter pp = new PrettyPrinter();
			
			parse.parseProgram().prettyPrint(pp);
			
			System.out.println(pp.sb.toString());
		}
		catch(FileNotFoundException e) {
			System.out.println("no file");
		}
		catch(UnsupportedEncodingException e) {
			System.out.println("bad encoding");
		}
		
	}
}