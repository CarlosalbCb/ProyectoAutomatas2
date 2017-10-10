package tiposNJ;

public class EstatutoNJ{
	private IdentificadorNJ id;
	private char Igual;
	private ExpresionNJ expr;
	private SemiNJ semi;
	private IfNJ IFNJ;
	private LParenNJ lpar;
	private RParenNJ rpar;
	private EstatutoNJ St;
	private WhileNJ wh;
	public EstatutoNJ(IdentificadorNJ ID,char IGUAL,ExpresionNJ EXPR, SemiNJ SEMI) {
		id=ID;
		if(IGUAL != '=') {
			System.out.println("Error: Se esperaba un '='");
		}else {Igual=IGUAL;}
		expr=EXPR;
		semi=SEMI;
	}
	public EstatutoNJ(IfNJ IF,LParenNJ LPAR, ExpresionNJ EXPR, RParenNJ RPAR, EstatutoNJ Stat) {
		IFNJ=IF;
		lpar=LPAR;
		expr=EXPR;
		rpar=RPAR;
		St=Stat;
	}
	public EstatutoNJ(WhileNJ WH,LParenNJ LPAR, ExpresionNJ EXPR, RParenNJ RPAR, EstatutoNJ Stat) {
		wh=WH;
		lpar=LPAR;
		expr=EXPR;
		rpar=RPAR;
		St=Stat;
	}

}
