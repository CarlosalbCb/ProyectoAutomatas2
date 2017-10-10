package leng_auto2;

public class Scanner 
{
	private char [] caracteres;
	private int indice;
	private String token;

	public Scanner(Panel p)
	{
		caracteres = p.getCodFte().trim().toCharArray();
		indice = 0;
		token = null;
	}

	public int getTkncode() 
	{		
		while(indice < caracteres.length)
		{					
			token = "";
			
			if(Character.isLetter(caracteres[indice]))
			{
				token += caracteres[indice++]; 
				
				if(indice < caracteres.length) 
				{ 
					while(Character.isLetterOrDigit(caracteres[indice]))
					{
						token += caracteres[indice++]; 
						if(indice == caracteres.length)
							break;
					}
					
					switch (token) 
					{
						case "int":
							return 1;
						case "boolean":
							return 2;
						case "if":
							return 8;
						case "while":
							return 11;
						default:
							return 3;
					}
				}
				else
					return 3;					
			}	
		
			if(Character.isWhitespace(caracteres[indice]))
			{
				indice++;
				
				if(indice < caracteres.length)
					while(Character.isWhitespace(caracteres[indice]))
						indice++;	
				
				continue;
			}
			
			switch (caracteres[indice])
			{
				case '+':
					token += caracteres[indice++];
					return 7;
				case '(':
					token += caracteres[indice++];
					return 9;
				case ')':
					token += caracteres[indice++];
					return 10;
				case ';':
					token += caracteres[indice++];
					return 4;
				case '=':
					token += caracteres[indice++];
					
					if (indice < caracteres.length)
					{
						if(caracteres[indice] == '=')
						{
							token += caracteres[indice++];
							return 6;
						}
						else 
							return 5;
					}
					else 
						return 5;
				default:
					break;
			}
			
			//Se encontró un error de léxico. El scanner regresa -1 para indicarlo.
			token += caracteres[indice];
			return -1;
		}	
		
		//Se terminó de procesar todo el código fuente. El scanner regresa 0 para indicarlo.
		return 0;
	}

	public String getToken()
	{
		return token;
	}
}
