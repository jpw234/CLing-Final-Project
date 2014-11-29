package ast;


public abstract class BValue implements Value {

	@Override
	public VariableType inferType() {
		return VariableType.BOOL;
	}
}
