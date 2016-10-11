/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praservidorarquivo;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author usuario
 */
public class PRAServidorArquivo {

    private static TreeSet<Dado> arvorePrincipal;	// Árvore que conterá os dados
    private static ArrayList<String> cabecalho;		// Cabecalho do arquivo a ser processado
    private static int[] valoresMaiores;			// Vetor contendo os maiores tamanhos de cada item do arquivo
    private static int numeroDados = 0;				// Quantidade de dados

    /**
     * Funcao principal
     *
     * @param args
     */
    public static void main(String[] args) {

        arvorePrincipal = new TreeSet<>();
        cabecalho = new ArrayList<>();

        final String ARQUIVO1 = "Sample - Superstore Sales.csv";
        final String ARQUIVO2 = "returned.csv";
        final String ARQUIVO3 = "users.csv";

        Leitor lt = new Leitor();
        lt.LeEProcessa(ARQUIVO1);

        Escritor that = new Escritor();
        that.escreveIndice();
        that.escreveDados();

    }

    /**
     * @return o cabeçalho
     */
    public static ArrayList<String> getCabecalho() {
        return cabecalho;
    }

    /**
     * @return a árvore principal contendo todos os dados
     */
    public static TreeSet<Dado> getArvore() {
        return arvorePrincipal;
    }

    /**
     * @return a lista contendo todos as quantidades de caracteres dos items do
     * arquivo
     */
    public static int[] getValoresMaiores() {
        return valoresMaiores;
    }

    /**
     * @return o numero de items do arquivo
     */
    public static int getNumeroDados() {
        return numeroDados;
    }

    /**
     * @param novosValores atualiza os valores
     */
    public static void setValoresMaiores(int[] novosValores) {
        valoresMaiores = novosValores;
    }

    /**
     * @param n atualiza o numero de dados que o arquivo de entrada possui por
     * instancia
	 *
     */
    public static void setNumeroDados(int n) {
        numeroDados = n;
    }

}
