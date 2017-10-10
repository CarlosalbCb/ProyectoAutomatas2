package leng_auto2;

public class Whilee extends Statement
{
	private Expression exp;
	private Statement stmt;
	
	public Whilee(Expression exp, Statement stmt) 
	{
		this.exp = exp;
		this.stmt = stmt;
	}

	public Expression getExp() {
		return exp;
	}

	public Statement getStmt() {
		return stmt;
	}
	
	public Equal getEqualExp() 
	{
		return (Equal) getExp();
	}
	
	public Plus getPlusExp() 
	{
		return (Plus) getExp();
	}
	
	public Identifier getIdExp() 
	{
		return (Identifier) getExp();
	}
}
