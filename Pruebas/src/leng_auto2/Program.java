package leng_auto2;

import java.util.ArrayList;

public class Program 
{
	private ArrayList <VarDecl> decl;
	private ArrayList <Statement> stmt;
	
	public Program(ArrayList <VarDecl> decl, ArrayList <Statement> stmt)
	{
		this.decl = decl;
		this.stmt = stmt;
	}

	public ArrayList<VarDecl> getDecl() {
		return decl;
	}

	public ArrayList<Statement> getStmt() {
		return stmt;
	}
}
