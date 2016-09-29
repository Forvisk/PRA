package pra;

import java.util.ArrayList;
import java.util.TreeSet;

public class Main {
	
	private static TreeSet<Dado> arvorePrincipal;
	private static ArrayList<String> cabecalho;
	private static int[] valoresMaiores;
	private static int numeroDados = 0;

	public static void main(String[] args) {
		
		arvorePrincipal = new TreeSet<>();
		cabecalho = new ArrayList<>();
		
		final String ARQUIVO1 = "Sample - Superstore Sales.csv";
		final String ARQUIVO2 = "returned.csv";
		final String ARQUIVO3 = "users.csv";
		
		
		Leitor lt = new Leitor();
		lt.LeEProcessa(ARQUIVO1);
		
		/*for (Dado i : arvorePrincipal){
			System.out.println(i.nrLinha);
			System.out.println(i.leitura);
		}*/
		
		Escritor that = new Escritor();
		that.escreveIndice();
		that.escreveDados();

	}
	
	public static ArrayList<String> getCabecalho(){
		return cabecalho;
	}
	
	public static TreeSet<Dado> getArvore(){
		return arvorePrincipal;
	}
	
	public static int[] getValoresMaiores(){
		return valoresMaiores;
	}
	
	public static int getNumeroDados(){
		return numeroDados;
	}
	
	public static void setValoresMaiores(int[] novosValores){
		valoresMaiores = novosValores;
	}
	
	public static void setNumeroDados(int n){
		numeroDados = n;
	}

}
