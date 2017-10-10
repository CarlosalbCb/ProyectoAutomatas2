package leng_auto2;

public class Assign extends Statement
{
	private Identifier id;
	private Expression exp;
	
	public Assign(Identifier id, Expression exp)
	{
		this.id = id;
		this.exp = exp;
	}

	public Identifier getId() {
		return id;
	}

	public Expression getExp() {
		return exp;
	}
	
	public Equal getEqualExp() 
	{
		return (Equal) exp;
	}
	
	public Plus getPlusExp() 
	{
		return (Plus) exp;
	}
	
	public Identifier getIdExp() 
	{
		return (Identifier) exp;
	}
}
