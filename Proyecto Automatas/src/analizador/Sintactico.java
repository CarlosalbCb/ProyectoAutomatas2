package analizador;
import tiposNJ.*;
import java.util.Stack;
import java.util.Vector;
import com.jwetherell.algorithms.data_structures.*;
public class Sintactico {
	TreeMap arbol = new TreeMap();
	Stack<TiposNJ> pila = new Stack<TiposNJ>(); //Pila para almacenar las clases de tokens 
	public Sintactico(CreaTokens tokens){
		Vector palabras=tokens.getTokens(); //se almacena cada token en un vector
		OrganizarTokens(palabras); //metodo para asignarle una clase a cada token, donde se le colocará un tipo
		creaArbolSintactico(pila);
	}
	
	public void OrganizarTokens(Vector palabras){
		int contador=0;
		while(palabras.size()>contador){
			switch (palabras.elementAt(contador).toString()){ 
//Toma el valor del token(las palabras en el vector) y lo convierte en un objeto para asignarle un tipo
				case "int":    pila.add(new TipoVarNJ(palabras.elementAt(contador).toString(),2));         contador++;break;
				case "boolean":pila.add(new TipoVarNJ(palabras.elementAt(contador).toString(),2));         contador++;break;
				case "if":     pila.add(new IfNJ(palabras.elementAt(contador).toString(),3));contador++;break;
				case "while":  pila.add(new WhileNJ(palabras.elementAt(contador).toString(),4));contador++;break;
				case "(":      pila.add(new LParenNJ(palabras.elementAt(contador).toString(),7)) ;contador++;break;
				case ")":      pila.add(new RParenNJ(palabras.elementAt(contador).toString(),8)) ;contador++;break;
				case ";":      pila.add(new SemiNJ(palabras.elementAt(contador).toString(),9)) ;contador++;break;
				default:       pila.add(new IdentificadorNJ(palabras.elementAt(contador).toString(),10));contador++;
			}		
		}
//A este punto se tienen todos las las clases de tokens asignadas con su tipo dentro de la pila 
//		
//		TiposNJ o=pila.pop();
//		System.out.print(o.getTipo());
	}
	public void creaArbolSintactico(Stack pila){
		TiposNJ Elemento= (TiposNJ) pila.pop();
		Elemento.getTipo();
	}
	public TreeMap getSintaxTree(){
		return arbol;
	}
}
