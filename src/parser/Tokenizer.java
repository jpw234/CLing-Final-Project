package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Tokenizer implements Iterator<Token> {
	/**
	 * The state of a Tokenizer is {@code NOT_READY} if the next token has not been processed.
	 */
	public static final int NOT_READY = 0;
	/**
	 * The state of a Tokenizer is {@code READY} if the next token has been processed.
	 */
	public static final int READY = 1;

	protected BufferedReader br;
	protected StringBuffer buf;
	protected int state;
	protected Token curTok;
	
	public Tokenizer(Reader r) {
		this.br = new BufferedReader(r);
		this.buf = new StringBuffer();
	}
	
	@Override
	public boolean hasNext() {
		if (state == NOT_READY) {
			try {
				lexOneToken();
			}
			catch (NoSuchElementException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Token next() {
		Token tok = peek();
		state = NOT_READY;
		return tok;
	}
	
	/**
	 * Return the next token in the program without consuming the token.
	 * @return
	 */
	public Token peek() {
		if (state == NOT_READY)
			lexOneToken();
		return curTok;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Close the reader opened by this tokenizer.
	 */
	public void close() {
		try {
			br.close();
		}
		catch (IOException e) {
			System.out.println("IOException:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected void lexOneToken() {
		char c;
		if (buf.length() == 1)
			c = buf.charAt(0);
		else
			c = nextChar();

		//consume whitespaces
		while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
			c = nextChar();
		}

		resetBuffer(c);

		if (c == '[')
			setNextToken(Token.LBRACKET);
		else if (c == ']')
			setNextToken(Token.RBRACKET);
		else if (c == '(')
			setNextToken(Token.LPAREN);
		else if (c == ')')
			setNextToken(Token.RPAREN);
		else if (c == ',')
			setNextToken(Token.COMMA);
		//TODO: Add handlers for or, and, and implies (the unique characters for them)
		else if (Character.isJavaIdentifierStart(c))
			lexIdentifier();
		else if (Character.isDigit(c))
			lexNum();
		else
			unexpected();
	}
	
	protected void lexIdentifier() {
		int c;
		for (c = nextChar(false);
				c != -1 && Character.isJavaIdentifierPart(c);
				c = nextChar(false))
			buf.append((char)c);

		String id = buf.toString();
		if (id.equals("assign"))
			setNextToken(Token.ASSIGN);
		else if(id.equals("print"))
			setNextToken(Token.PRINT);
		else if(id.equals("declare"))
			setNextToken(Token.DECLARE);
		else if(id.equals("return"))
			setNextToken(Token.RETURN);
		else if(id.equals("if"))
			setNextToken(Token.IF);
		else if(id.equals("else"))
			setNextToken(Token.ELSE);
		else if(id.equals("while"))
			setNextToken(Token.WHILE);
		else if(id.equals("add"))
			setNextToken(Token.ADD);
		else if(id.equals("subtract"))
			setNextToken(Token.SUBTRACT);
		else if(id.equals("multiply"))
			setNextToken(Token.MULTIPLY);
		else if(id.equals("divide"))
			setNextToken(Token.DIVIDE);
		else if(id.equals("mod"))
			setNextToken(Token.MOD);
		else if(id.equals("greater"))
			setNextToken(Token.GREATER);
		else if(id.equals("less"))
			setNextToken(Token.LESS);
		else if(id.equals("equal"))
			setNextToken(Token.EQUAL);
		else if(id.equals("negative"))
			setNextToken(Token.NEG);
		else if(id.equals("point"))
			setNextToken(Token.POINT);
		else if(id.equals("hundred"))
			setNextToken(Token.HUNDRED);
		else if(id.equals("thousand"))
			setNextToken(Token.THOUSAND);
		else if(id.equals("million"))
			setNextToken(Token.MILLION);
		else if(id.equals("billion"))
			setNextToken(Token.BILLION);
		else if(id.equals("number"))
			setNextToken(Token.NUMBER);
		else if(id.equals("int"))
			setNextToken(Token.INT);
		else if(id.equals("double"))
			setNextToken(Token.DOUBLE);
		else if(id.equals("bool"))
			setNextToken(Token.BOOL);
		else if(id.equals("True"))
			setNextToken(Token.TRUE);
		else if(id.equals("False"))
			setNextToken(Token.FALSE);
		else if(id.equals("not"))
			setNextToken(Token.NOT);
		else if(id.charAt(0) == 'N' && id.length() > 1) {//then it's a number
			try {
				String numString = id.substring(1);
				int val = Integer.parseInt(numString);
				curTok = new NumToken(val);
				state = READY;
				buf = new StringBuffer();
			}
			catch (NumberFormatException e) {
				unexpected();
			}
		}
		else if(id.length() == 1) {//then it's a variable
			curTok = new VarToken(id);
			state = READY;
			buf = new StringBuffer();
		}
		else
			unexpected();

		if (c != -1)
			buf.append((char)c);
	}

	protected void lexNum() {
		int c;
		for (c = nextChar(false);
				c != -1 && Character.isJavaIdentifierPart(c);
				c = nextChar(false))
			buf.append((char)c);

		try {
			String num = buf.toString();
			int val = Integer.parseInt(num);
			curTok = new NumToken(val);
			state = READY;
			buf = new StringBuffer();
			if (c != -1)
				buf.append((char)c);
		}
		catch (NumberFormatException e) {
			unexpected();
		}
	}

	/**
	 * Read the next character from the reader, treating EOF as an error.
	 * If successful, append the character to the buffer.
	 * @return The next character
	 * @throws NoSuchElementException if EOF is encountered
	 */
	protected char nextChar() {
		char c = (char)nextChar(true);
		buf.append(c);
		return c;
	}

	/**
	 * Read the next character from the reader.
	 * If isEOFerror, treat EOF as an error.
	 * If successful, append the character to the buffer.
	 * @param isEOFerror
	 * @return The integer representation of the next character
	 * @throws NoSuchElementException if EOF is encountered and isEOFerror is true
	 */
	protected int nextChar(boolean isEOFerror) {
		try {
			int c = br.read();
			if (isEOFerror && c == -1)
				throw new NoSuchElementException();
			return c;
		}
		catch (IOException e) {
			System.out.println("IOException:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	protected void setNextToken(int tokenID) {
		curTok = new Token(tokenID);
		state = READY;
		buf = new StringBuffer();
	}

	protected void setNextToken(int tokenID, char c) {
		setNextToken(tokenID);
		buf.append(c);
	}

	protected void resetBuffer(char c) {
		buf = new StringBuffer();
		buf.append(c);
	}

	/**
	 * Read the next character and determine whether it is the expected character.
	 * If not, the current buffer is an error token.
	 * @param expected The expected next character
	 * @return true if the next character is as expected
	 */
	protected boolean consume(char expected) {
		int c = nextChar();
		if (c != expected) {
			unexpected();
			return false;
		}
		return true;
	}

	/**
	 * Make the current buffer an error token.
	 */
	protected void unexpected() {
		curTok = new ErrorToken(buf.toString());
		state = READY;
		buf = new StringBuffer();
	}
}
