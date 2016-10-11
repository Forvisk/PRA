/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praservidorarquivo;

import java.util.ArrayList;

/**
 * @author Adriano e Gustavo
 * Classe para transportar os dados para a árvore principal
 *
 */
public class Producao {

	/**
	 * @param linha indica a linha no arquivo do dado
	 * @param valores contem os dados
	 */
	public static void AdicionaNaArvore(int linha, String[] valores) {
		ArrayList<String> coisas = new ArrayList<>();
		for (int i = 0; i < valores.length; ++i)
			coisas.add(valores[i]);
		Dado novoDado = new Dado(coisas, linha);
		GerenciadorArquivo.getInstance().getArvore().add(novoDado);
	}

}