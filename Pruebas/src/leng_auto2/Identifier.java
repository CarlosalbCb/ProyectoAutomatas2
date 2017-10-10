package leng_auto2;

public class Identifier extends Expression
{
	private String id;
	
	public Identifier(String id)
	{
		this.id = id;
	}
		
	public String toString()
	{
		return id;	
	}
	
	public String getIdExp()
	{
		return id;
	}
}
