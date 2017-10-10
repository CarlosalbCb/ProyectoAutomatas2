package tiposNJ;

public class IgualNJ{
	private IdentificadorNJ id1;
	private IdentificadorNJ id2;
	private char Igual= '=';
	public IgualNJ(IdentificadorNJ ID1,char IGUAL1, char IGUAL2,IdentificadorNJ ID2){
		id1=ID1;
		if(IGUAL1 != '=' || IGUAL2 != '=') {
			System.out.println("Error: Se esperaba un '=='");
		}else {Igual=IGUAL1;}
		id2=ID2;
	}
}
