package analizador;

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreaTokens {
	private Vector Tokens;
	
	public CreaTokens(String Texto_A_Separar){
		if(revisaAlfabeto(Texto_A_Separar)){ //Se llama a revisaAlfabeto para notificar si existen caracteres invalidos en el alfabeto del codigo
			//si el alfabeto es correcto se procede a crear tokens
			Tokens = new Vector(); //Vector para almacenar cada token encontrado
			StringTokenizer tokens = new StringTokenizer(Texto_A_Separar," ;={}()<>",true);
			while(tokens.hasMoreTokens())
				 Tokens.addElement(tokens.nextToken().toString());  //agrega el token al vector de tokens
			int contador=0;
			while(Tokens.size() > contador){
				if(Tokens.elementAt(contador).toString().contains(" ")){
					Tokens.removeElementAt(contador);
					contador--; //se reduce el contador para revisar la misma posicion al haberse recorrido los elementos del vector
				}
				contador++;
			}
		}
		else{
			System.out.println("Existen caracteres invalidos en el código");	
		}
	}
	
	public void ImprimeTokens(){
		int contador=0;
		while(Tokens.size()!=contador){
			System.out.println(Tokens.elementAt(contador));
			contador++;
		}
	}
	public boolean revisaAlfabeto(String texto){
		boolean correcto=true;
		String REG_EXP = "[\\d]+[a-zA-Z]+|\\¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|\\%+|\\&+|\\+|\\’+|\\¡+|\\~+|\\[+|\\]+|\\^+";		
		Pattern pattern = Pattern.compile(REG_EXP);
		Matcher matcher = pattern.matcher(texto);
		if(matcher.find()){
			correcto=false;
			System.out.println(matcher.toString());
		}
		return correcto;
	}
	public Vector getTokens(){
		return Tokens;
	}
}
