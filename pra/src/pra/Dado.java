package pra;

import java.util.ArrayList;

public class Dado implements Comparable {
	ArrayList<String> leitura;
	int nrLinha;

	Dado(ArrayList<String> dado, int linha) {
		leitura = dado;
		nrLinha = linha;
	}

	@Override
	public int compareTo(Object arg0) {
		Dado outro = (Dado)arg0;
		if (outro == null){
			return -1;
		}
		if (this.nrLinha >= outro.nrLinha) {
			return 1;
		} else {
			return -1;
		}

	}

}
