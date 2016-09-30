package pra;

import java.util.ArrayList;

/**
 * @author Adriano e Gustavo Esta classe é usada para guardar os dados do
 *         arquivo principal
 *
 */
public class Dado implements Comparable {
	private ArrayList<String> leitura;		// Lista contendo todos os items na linha
	private int nrLinha;					// Numero da linha no arquivo

	/**
	 * @return os dados contidos
	 */
	public ArrayList<String> getLeitura() {
		return leitura;
	}

	/**
	 * @return o numero da linha do dado
	 */
	public int getNrLinha() {
		return nrLinha;
	}

	/**
	 * @param dado
	 *            são todos os dados da linha
	 * @param linha
	 *            indica o numero da linha que o dado está
	 */
	Dado(ArrayList<String> dado, int linha) {
		leitura = dado;
		nrLinha = linha;
	}

	@Override
	public int compareTo(Object arg0) {
		Dado outro = (Dado) arg0;
		if (outro == null) {
			return -1;
		}
		if (this.nrLinha >= outro.nrLinha) {
			return 1;
		} else {
			return -1;
		}

	}

}
