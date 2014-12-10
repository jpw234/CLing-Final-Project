package ast;

import java.util.ArrayList;

import pretty.PrettyPrinter;

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
	
	public void addBranchToBlock(Program p) {
		if(children.get(children.size() - 1) instanceof Block) {
			((Block) children.get(children.size() - 1)).addBranch(p);
		}
		else throw new RuntimeException("tried to add branch to something that isn't a block");
	}
	
	public ArrayList<Command> getChildren() {
		return children;
	}
	
	public void prettyPrint(PrettyPrinter pp) {
		pp.sb.append("{ ");
		pp.tabCount++;
		pp.endLine();
		for(Command c: children) c.prettyPrint(pp);
		pp.sb.deleteCharAt(pp.sb.length() - 1); // delete extra tab
		pp.tabCount--;
		pp.sb.append("} ");
	}
}
