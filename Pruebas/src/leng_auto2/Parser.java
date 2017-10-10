package leng_auto2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

public class Parser
{
	private final int intt = 1, booleann = 2, idd = 3, semi = 4, asig = 5, eq = 6, sum = 7, iff = 8, parea = 9, parec = 10, whilee = 11;
	private Scanner scanner;
	private Panel panel;
	private Program prog;
	private Hashtable <String, String> symboltable;
	private ArrayList <String> bytecode;
	private Stack <Integer> startIf;
	private Stack <Integer> endIf;
	private Stack <Integer> startWhile;
	private Stack <Integer> cmpWhile;
	private Stack <Integer> endWhile;
	private int tkncode;
	private String token;
	private boolean error;
		
	public Parser(Scanner s, Panel p)
	{
		scanner = s;
		panel   = p;
		prog = null;
		symboltable = new Hashtable <String, String>(); //Tabla de símbolos (ST).
		bytecode = new ArrayList <String>(); //Bytecode.
		startIf = new Stack<Integer>(); //Guarda la posición donde empiezan los If.
		endIf = new Stack<Integer>(); //Guarda la posición donde terminan los If.
		startWhile = new Stack<Integer>(); //Guarda la posición donde empiezan los While.
		cmpWhile = new Stack <Integer>(); //Guarda la posición de la comparación While.
		endWhile = new Stack<Integer>(); //Guarda la posición donde terminan los While.
		tkncode = scanner.getTkncode(); //Obtiene el primer tkncode.
		error = tkncode == -1 ? true : false; //Si el primer token es inválido, entonces hay error.
	}
	
	public void program() //Inicia el Parser.
	{
		ArrayList <VarDecl> decl = new ArrayList <VarDecl>(); //Arreglo de declaraciones.
		ArrayList <Statement> stmt = new ArrayList <Statement>(); //Arreglo de estatutos.
		
		do
			decl.add(varDeclaration());
		while (tkncode == intt || tkncode == booleann);
		
		do
			stmt.add(statement());
		while (tkncode == idd || tkncode == iff || tkncode == whilee);

		if(!error)
		{
			prog = new Program(decl, stmt);
			generateBytecode(prog);
			
			//Imprimir el bytecode.
			int cont = 0;
			for (String code : bytecode)
				panel.appendConsola(++cont+" "+code+"\n");
			
			//Imprimir las pilas para verificar que se haya generado todo el código intermedio.
			/*panel.appendConsola("\nSTART IF\n");
			while(!startIf.isEmpty())
				panel.appendConsola(startIf.pop()+"\n");
			
			panel.appendConsola("END IF\n");
			while(!endIf.isEmpty())
				panel.appendConsola(endIf.pop()+"\n");
			
			panel.appendConsola("START WHILE\n");
			while(!startWhile.isEmpty())
				panel.appendConsola(startWhile.pop()+"\n");	
			
			panel.appendConsola("CMP WHILE\n");
			while(!cmpWhile.isEmpty())
				panel.appendConsola(cmpWhile.pop()+"\n");
			
			panel.appendConsola("END WHILE\n");
			while(!endWhile.isEmpty())
				panel.appendConsola(endWhile.pop()+"\n");*/
			
		}			
	}

	private void generateBytecode(Program p)
	{	
		Iterator<Statement> it2 = p.getStmt().iterator();
		
		while(it2.hasNext())
			raiz(it2.next());
	}
	
	private void raiz(Statement s)
	{
		if(s instanceof Assign)
			 buildAssign(((Assign) s).getId(), ((Assign) s).getExp());
		 else if(s instanceof Condition)
		 {				 
			nodoExp(((Condition) s).getExp());
			bytecode.add("ifne"+" <numero>");
			startIf.add(bytecode.size());
			nodoStmt(((Condition) s).getStmt());
			endIf.add((bytecode.size()+1));
			buildCondition();
		 } 
		 else if(s instanceof Whilee)
		 {
			startWhile.add((bytecode.size()+1));
			nodoExp(((Whilee) s).getExp());
			bytecode.add("ifne"+" <numero>");
			cmpWhile.add(bytecode.size());
			nodoStmt(((Whilee) s).getStmt());
			bytecode.add("jmp"+" <numero>");
			endWhile.add(bytecode.size());
			buildWhile();
		}
	}
	
	private void buildWhile() 
	{
		bytecode.set(cmpWhile.pop()-1, "ifne "+(endWhile.peek()+1));
		bytecode.set(endWhile.pop()-1, "jmp "+startWhile.pop());
	}

	private void buildCondition() 
	{
		bytecode.set(startIf.pop()-1, "ifne "+endIf.pop());
	}

	private int nodoExp(Expression exp) 
	{
		if(exp instanceof Equal)
		{
			 bytecode.add("i_load_"+((Equal) exp).getId1());
			 bytecode.add("i_load_"+((Equal) exp).getId2());
			 return 1;
		}
		else if(exp instanceof Identifier)
		{
			bytecode.add("i_load_"+((Identifier) exp).getIdExp());
			return 0;
		}
		
		return 0;
	}
	
	static int aux = 0;
	
	private void nodoStmt(Statement s) 
	{
		if(s instanceof Assign)
			buildAssign(((Assign) s).getId(), ((Assign) s).getExp());
		else if(s instanceof Condition)
		{
			nodoExp(((Condition) s).getExp());
			bytecode.add("ifne"+" <numero>");
			startIf.add(bytecode.size());
			nodoStmt(((Condition) s).getStmt());
			endIf.add((bytecode.size()+1));
			buildCondition();
		}
		else if(s instanceof Whilee)
		{
			aux = nodoExp(((Whilee) s).getExp());
			startWhile.add((bytecode.size()-aux));
			bytecode.add("ifne"+" <numero>");
			cmpWhile.add(bytecode.size());
			nodoStmt(((Whilee) s).getStmt());
			bytecode.add("jmp"+" <numero>");
			endWhile.add(bytecode.size());
			buildWhile();
		}
	}
	
	private void buildAssign(Identifier id, Expression exp) 
	{
		if(exp instanceof Equal)
		{
			bytecode.add("i_load_"+((Equal) exp).getId1());
			bytecode.add("i_load_"+((Equal) exp).getId2());
			bytecode.add("ifne "+(bytecode.size()+5));
			bytecode.add("i_const_1");
			bytecode.add("i_store_"+id);
			bytecode.add("goto "+(bytecode.size()+4));
			bytecode.add("i_const_0");
			bytecode.add("i_store_"+id);
		}
		else if(exp instanceof Plus)
		{
			bytecode.add("i_load_"+((Plus) exp).getId1());
			bytecode.add("i_load_"+((Plus) exp).getId2());
			bytecode.add("i_add");
			bytecode.add( "i_store_"+id);
		}
		else if(exp instanceof Identifier)
		{
			bytecode.add("i_load_"+((Identifier) exp).getIdExp());
			bytecode.add("i_store_"+id);
		}
	}
	
	private void varDuplicate(VarDecl vd)//Verifica que no existan declaraciones de variables duplicadas.  
	{
		try 
		{
			if(symboltable.containsKey(vd.getId().toString())) //Busca en la ST, si la varDecl está dada de alta. 
			{
				error = true;
				panel.appendConsola(vd.getId().toString()+" -> Variable duplicada.\n");
			}
			else //Si la varDecl no se encuentra en la ST la damos de alta. 
				symboltable.put(vd.getId().toString(), vd.getType().toString());	
		}
		catch (Exception e) {}
	}
	
	private void checkIdentifier(Identifier id) //Verifica que un identificador esté previamente declarado.
	{
		try 
		{
			if(!symboltable.containsKey(id.toString())) //Busca si la variable está declarada.
			{
				error = true;
				panel.appendConsola(id.toString()+" -> Variable no declarada.\n");
			}
		} 
		catch (Exception e) {}
	}	 
		
	private void checkVarTypes(Statement stmt) //Realiza el chequeo de tipos de variables.
	{
		String id, id1exp, id2exp;
		
		try 
		{
			if(stmt instanceof Assign) //Cuando es una asignación.
			{
				if(((Assign) stmt).getExp() instanceof Identifier && symboltable.containsKey(((Assign) stmt).getIdExp().toString())) //id = id1exp.
				{
					id = ((Assign) stmt).getId().toString();
					id1exp = ((Assign) stmt).getIdExp().toString();
					
					if(!symboltable.get(id).equals(symboltable.get(id1exp)))
					{
						error = true;
						panel.appendConsola(id+" y "+id1exp+" -> Tipos incompatibles.\n");
					}
				}
				else if(((Assign) stmt).getExp() instanceof Equal && symboltable.containsKey(((Assign) stmt).getEqualExp().getId1().toString()) && symboltable.containsKey(((Assign) stmt).getEqualExp().getId2().toString())) //id = id1exp == id2exp.  
				{
					id = ((Assign) stmt).getId().toString();
					id1exp = ((Assign) stmt).getEqualExp().getId1().toString();
					id2exp = ((Assign) stmt).getEqualExp().getId2().toString();
					
					if(!(symboltable.get(id).equals("boolean") && symboltable.get(id1exp).equals(symboltable.get(id2exp))))
					{
						error = true;
						panel.appendConsola(id+", "+id1exp+" y "+id2exp+" -> Tipos incompatibles.\n");
					}		
				}
				else if(((Assign) stmt).getExp() instanceof Plus && symboltable.containsKey(((Assign) stmt).getPlusExp().getId1().toString()) && symboltable.containsKey(((Assign) stmt).getPlusExp().getId2().toString())) //id = id1exp + id2exp. 
				{
					id = ((Assign) stmt).getId().toString();
					id1exp = ((Assign) stmt).getPlusExp().getId1().toString();
					id2exp = ((Assign) stmt).getPlusExp().getId2().toString();
					
					if(!(symboltable.get(id).equals("int") && symboltable.get(id1exp).equals("int") && symboltable.get(id2exp).equals("int")))
					{
						error = true;
						panel.appendConsola(id+", "+id1exp+" y "+id2exp+" -> Tipos incompatibles.\n");
					}		
				}
			}
			else if(stmt instanceof Condition) //Cuando es un if.
			{
				if(((Condition) stmt).getExp() instanceof Equal && symboltable.containsKey(((Condition) stmt).getEqualExp().getId1().toString()) && symboltable.containsKey(((Condition) stmt).getEqualExp().getId2().toString())) //id1exp == id2exp.
				{
					id1exp = ((Condition) stmt).getEqualExp().getId1().toString();
					id2exp = ((Condition) stmt).getEqualExp().getId2().toString();
					
					if(!symboltable.get(id1exp).equals(symboltable.get(id2exp)))
					{
						error = true;
						panel.appendConsola(id1exp+" y "+id2exp+" -> Tipos incompatibles.\n");
					}	
				 
				}
				else if(((Condition) stmt).getExp() instanceof Plus && symboltable.containsKey(((Condition) stmt).getPlusExp().getId1().toString()) && symboltable.containsKey(((Condition) stmt).getPlusExp().getId2().toString())) //id1exp + id2exp.
				{
					id1exp = ((Condition) stmt).getPlusExp().getId1().toString();
					id2exp = ((Condition) stmt).getPlusExp().getId2().toString();
					
					if(!symboltable.get(id1exp).equals(symboltable.get(id2exp)))
					{
						error = true;
						panel.appendConsola(id1exp+" y "+id2exp+" -> Tipos incompatibles.\n");
					}		
				}
				else if(((Condition) stmt).getExp() instanceof Identifier) //id.
				{
					id = ((Condition) stmt).getIdExp().toString();
					
					if(!symboltable.get(id).equals("boolean"))
					{
						error = true;
						panel.appendConsola(id+" -> Error de tipo.\n");
					}
				}
			}
			else if(stmt instanceof Whilee) //Cuando es un ciclo while.
			{
				if(((Whilee) stmt).getExp() instanceof Equal && symboltable.containsKey(((Whilee) stmt).getEqualExp().getId1().toString()) && symboltable.containsKey(((Whilee) stmt).getEqualExp().getId2().toString())) //id1exp == id2exp.
				{
					id1exp = ((Whilee) stmt).getEqualExp().getId1().toString();
					id2exp = ((Whilee) stmt).getEqualExp().getId2().toString();
					
					if(!symboltable.get(id1exp).equals(symboltable.get(id2exp)))
					{
						error = true;
						panel.appendConsola(id1exp+" y "+id2exp+" -> Tipos incompatibles.\n");
					}		
				}
				else if(((Whilee) stmt).getExp() instanceof Plus && symboltable.containsKey(((Whilee) stmt).getPlusExp().getId1().toString()) && symboltable.containsKey(((Whilee) stmt).getPlusExp().getId2().toString())) //id1exp + id2exp.
				{
					id1exp = ((Whilee) stmt).getPlusExp().getId1().toString();
					id2exp = ((Whilee) stmt).getPlusExp().getId2().toString();
					
					if(!symboltable.get(id1exp).equals(symboltable.get(id2exp)))
					{
						error = true;
						panel.appendConsola(id1exp+" y "+id2exp+" -> Tipos incompatibles.\n");
					}		
				}
				else if(((Whilee) stmt).getExp() instanceof Identifier) //id.
				{
					id = ((Whilee) stmt).getIdExp().toString();
					
					if(!symboltable.get(id).equals("boolean"))
					{
						error = true;
						panel.appendConsola(id+" -> Error de tipo.\n");
					}
				}
			}
		} 
		catch (Exception e) {}
	}
	
	private VarDecl varDeclaration() //Declaraciones.
	{
		VarDecl decl = null;
		Type type = null;
		Identifier id = null;
		
		switch (tkncode) 
		{
			case intt:
				eat(intt); 
				type = new Type(token);
				id = identifier();
				decl = new VarDecl(type, id);
				varDuplicate(decl);
				eat(semi);
				break;
			case booleann:
				eat(booleann); 
				type = new Type(token);
				id = identifier(); 
				decl = new VarDecl(type, id);
				varDuplicate(decl);
				eat(semi);
				break;
			default: 
				error = true;
				panel.appendConsola("Se esperaba una declaración de variable: int id / boolean id.\n");
				break;
		}
		
		return decl;
	}

	private Statement statement() //Estatutos.
	{	
		Statement stmt = null;
		Expression exp = null;
		Identifier id = null;
		
		switch (tkncode) 
		{
			case idd:
				eat(idd);
				id = new Identifier(token);
				checkIdentifier(id);
				eat(asig);
				exp = expression();
				eat(semi);
				Assign assign = new Assign(id, exp);
				checkVarTypes(assign);
				return assign;
			case iff:
				eat(iff); 
				eat(parea);
				exp = expression(); 
				eat(parec); 
				stmt = statement();
				Condition condition = new Condition(exp, stmt); 
				checkVarTypes(condition);
				return condition;
			case whilee:
				eat(whilee); 
				eat(parea); 
				exp = expression(); 
				eat(parec);
				stmt = statement();
				Whilee whilee = new Whilee(exp, stmt);
				checkVarTypes(whilee);
				return whilee;
			default:
				error = true;
				panel.appendConsola("Se esperaba una instrucción: id = id / id = id == id / id = id + id.\n");
				break;				
		}
		
		return stmt;
	}

	private Identifier identifier() //Identificadores.
	{
		Identifier id = null;
				
		switch (tkncode) 
		{
			case idd:
				eat(idd);
				id = new Identifier(token);
				break;
			default:
				panel.appendConsola("Se esperaba un identificador.\n");
				error = true;
				break;
		}
		
		return id;
	}

	private Expression expression() //Expresión.
	{		
		Expression exp = null;
		Identifier id1 = null;
		Identifier id2 = null;
		
		switch (tkncode) 
		{
			case idd:
				eat(idd);
				id1 = new Identifier(token);
				checkIdentifier(id1);
				if (tkncode == eq)
				{
					eat(eq);
					eat(idd);
					if(tkncode <=0 && error)
						break;
					id2 = new Identifier(token);
					checkIdentifier(id2);
					return new Equal(id1, id2);
				}
				else if (tkncode == sum)
				{
					eat(sum); 
					eat(idd);
					if(tkncode <=0 && error) 
						break;
					id2 = new Identifier(token);
					checkIdentifier(id2);
					return new Plus(id1, id2);
				}
				else
					return id1;
			default:
				error = true;
				panel.appendConsola("Se esperaba una expresión: id == id / id + id / id.\n");
				break;
		}
		
		return exp;	
	}
	
	private void advance() //Obtiene el siguiente tkncode.
	{
		tkncode = scanner.getTkncode(); 
	}
	
	private void getToken() //Obtiene el token.
	{
		token = scanner.getToken();
	}
	
	private void eat(int t) //Revisa si hay un error de sintáxis.  
	{
		if (t == tkncode)
		{
			getToken();
			advance();
		}
		else
		{
			switch (t) 
			{
				case semi:
					panel.appendConsola("Se esperaba un punto y coma.\n");
					break;
				case asig:
					panel.appendConsola("Se esperaba un igual.\n");
					break;
				case idd:
					panel.appendConsola("Se esperaba un identificador.\n");
					break;
				case parea:
					panel.appendConsola("Se esperaba un paréntesis de apertura.\n");
					break;
				case parec:
					panel.appendConsola("Se esperaba un paréntesis de cierre.\n");
					break;
				default:
					break;
			}	
			
			error = true;
		}
	}
}
