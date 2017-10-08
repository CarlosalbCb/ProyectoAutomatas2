package tiposNJ;

public abstract class TiposNJ extends Object {
	private String texto;
	private int tipo;
	public TiposNJ(String txt,int type){
		texto=txt;
		tipo=type;
	}
	public int getTipo(){
		return tipo;
	}
	public String toString(){
		return texto;
	}
}
