package analizador;

import java.io.FileNotFoundException;

public class Principal {
	public static void main(String[] args) {
			Lexico AnalizadorLexico = new Lexico();
			Sintactico AnalizadorSintactico=new Sintactico(AnalizadorLexico.getTokens());
	}

}
