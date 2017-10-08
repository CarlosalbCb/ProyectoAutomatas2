package analizador;
import java.io.*;

public class Lexico {
	private String texto="";
	private CreaTokens tokens;
 public Lexico(){
	 //Doc almacena la cadena de codigo ingresada en el archivo prueba.txt
	try {
		File doc = new File("src\\analizador\\prueba.txt");
		FileReader fr = new FileReader(doc);
		BufferedReader br = new BufferedReader(fr);
		String linea;
        try {
        	//Ciclo para recorrer el archivo y guardar cada linea en la variable texto
			while((linea=br.readLine())!=null){ 
				texto=texto+(linea+" ");
			}
			//System.out.print(texto);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (FileNotFoundException e) {
		System.out.println("Archivo no encontrado");
		e.printStackTrace();
	}
	//Separa el codigo en tokens de Tokens
	try{
		tokens= new CreaTokens(texto);
		tokens.ImprimeTokens();
	}catch(NullPointerException e){
		System.out.println("Programa detenido");
	}
 }
 public CreaTokens getTokens(){
	 return tokens;
 }
 
}
