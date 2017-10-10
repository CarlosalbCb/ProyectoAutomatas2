package leng_auto2;

public class Plus extends Expression
{
	private Identifier id1;
	private Identifier id2;
	
	public Plus(Identifier id1, Identifier id2)
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
	
	public String exec()
	{
		return "i_load_"+id1+"\n"+
			   "i_load_"+id2+"\n"+
			   "i_add"+"\n";
	}
}
