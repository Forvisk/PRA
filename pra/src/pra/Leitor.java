package pra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Leitor {
	
	
	Leitor(){
		
	}

	TreeSet<Dado> LeEProcessa(String arqv){
		BufferedReader leitor = null;
		try {
			leitor = new BufferedReader(new FileReader(arqv) );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String cLine;
		int linha_atual = 0;
		int[] maiores = null;
		
		try {
			String linha = leitor.readLine();
			String[] nomeCabecalhos = SeparaLinha(linha);
			maiores = new int[nomeCabecalhos.length + 1];

			
			while ((cLine = leitor.readLine()) != null) {
				linha_atual++;
				String[] dados = SeparaLinha(cLine);
				Producao.AdicionaNaArvore(linha_atual, dados);
				int i = 0;
				
				for (String string : dados) {
					if (string.length() > maiores[i]){
						maiores[i] = string.length();
					}
					i++;
				}
			}
			int num = 0;
			for (String i : nomeCabecalhos){
				Main.getCabecalho().add(i);
				if (i.length() > maiores[num]){
					maiores[num] = i.length();
				}
				num++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maiores[maiores.length - 1] = Utilitarios.getNumberOfDigits(linha_atual);

		Main.setValoresMaiores(maiores);
		return null;		
	}
	
	String[] SeparaLinha(String s){
		final char ASPAS = '"', SEPARADOR = ';';
		boolean podeSeparar = true;
		ArrayList<String> res = new ArrayList<>();
		String temp = "";
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if (c == ASPAS){
				podeSeparar = !podeSeparar;
			}
			if (c == SEPARADOR && podeSeparar){
				res.add(temp);
				temp = "";
			} else {
				temp = temp + c;
			}
		}
		String[] arr = new String[res.size()];
		arr = res.toArray(arr);
		return arr;
	}
}
