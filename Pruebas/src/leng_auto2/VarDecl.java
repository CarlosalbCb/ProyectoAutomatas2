package leng_auto2;

public class VarDecl
{
	private Type type;
	private Identifier id;
	
	public VarDecl(Type type, Identifier id)
	{
		this.type = type;
		this.id = id;
	}

	public Type getType() {
		return type;
	}
	
	public Identifier getId() {
		return id;
	}
}
