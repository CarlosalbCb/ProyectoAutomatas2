package tiposNJ;

public class ExpresionNJ {
	private IdentificadorNJ id1;
	private IdentificadorNJ id2;
	private char Igual= '=';
	private char Suma= '+';
	
	public ExpresionNJ(IdentificadorNJ ID1,char IGUAL1, char IGUAL2,IdentificadorNJ ID2) {
		id1=ID1;
		if(IGUAL1 != '=' || IGUAL2 != '=') {
			System.out.println("Error: Se esperaba un '=='");
		}else {Igual=IGUAL1;}
		id2=ID2;
	}
	public ExpresionNJ(IdentificadorNJ ID1,char SUMA,IdentificadorNJ ID2) {
		id1=ID1;
		if(SUMA != '+') {
			System.out.println("Error: Se esperaba un '+'");
		}else {Suma=SUMA;}
		id2=ID2;
	}
	public ExpresionNJ(IdentificadorNJ id) {
		id1=id;
	}
}
