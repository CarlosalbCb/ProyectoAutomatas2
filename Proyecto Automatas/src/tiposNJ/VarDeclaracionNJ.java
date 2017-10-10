package tiposNJ;

public class VarDeclaracionNJ {
	private int varInt;
	private boolean varBool;
	IdentificadorNJ id;
	SemiNJ semi;
	
	public VarDeclaracionNJ(int var, IdentificadorNJ ID, SemiNJ SEMI) {
		varInt=var;
		id=ID;
		semi=SEMI;
	}
	public VarDeclaracionNJ(boolean var, IdentificadorNJ ID, SemiNJ SEMI) {
		varBool=var;
		id=ID;
		semi=SEMI;
	}
	
}
