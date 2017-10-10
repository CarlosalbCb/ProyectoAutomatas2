package leng_auto2;

public class Equal extends Expression
{
	private Identifier id1;
	private Identifier id2;
	
	public Equal(Identifier id1, Identifier id2)
	{
		this.id1 = id1;
		this.id2 = id2;
	}

	public Identifier getId1() {
		return id1;
	}

	public Identifier getId2() {
		return id2;
	}
}
