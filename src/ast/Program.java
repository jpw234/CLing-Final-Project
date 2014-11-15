package ast;

import java.util.ArrayList;

public class Program implements Node {
	
	private ArrayList<Command> children;
	
	public Program() {
		children = new ArrayList<Command>();
	}
	
	public Program(ArrayList<Command> list) {
		children = list;
	}
	
	public void addCommand(Command next) {
		children.add(next);
	}
	
	public ArrayList<Command> getChildren() {
		return children;
	}
	
	public void prettyPrint(StringBuffer sb) {
		//TODO: Implement this
	}
}
