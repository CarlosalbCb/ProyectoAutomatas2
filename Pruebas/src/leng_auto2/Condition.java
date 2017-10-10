package leng_auto2;

public class Condition extends Statement
{
	private Statement stmt;
	private Expression exp;
	
	public Condition(Expression exp, Statement stmt) 
	{
		this.stmt = stmt;
		this.exp = exp;
	}

	public Statement getStmt() 
	{
		return stmt;
	}
		
	public Expression getExp()
	{
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
