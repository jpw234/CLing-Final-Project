package parser;

public class ErrorToken extends Token {
	protected String value;

	public ErrorToken(String value) {
		super(ERROR);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "[error] " + value;
	}
}
