package pra;

import java.util.ArrayList;

public class Producao {
	
	public static void AdicionaNaArvore(int linha, String[] valores){
		ArrayList<String> coisas = new ArrayList<>();
		for (int i = 0; i < valores.length; ++i)
			coisas.add(valores[i]);
		Dado novoDado = new Dado(coisas, linha);
		Main.getArvore().add(novoDado);
	}

}
