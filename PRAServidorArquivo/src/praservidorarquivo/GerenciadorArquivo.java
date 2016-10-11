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
public class GerenciadorArquivo {

    private TreeSet<Dado> arvorePrincipal;	// Árvore que conterá os dados
    private ArrayList<String> cabecalho;		// Cabecalho do arquivo a ser processado
    private int[] valoresMaiores;			// Vetor contendo os maiores tamanhos de cada item do arquivo
    private int numeroDados = 0;				// Quantidade de dados

    private static GerenciadorArquivo instance = null;

    private static final String ARQUIVO1 = "Sample - Superstore Sales.csv";
    private static final String ARQUIVO2 = "returned.csv";
    private static final String ARQUIVO3 = "users.csv";

    public static GerenciadorArquivo getInstance() {
        if (instance == null) {
            instance = new GerenciadorArquivo();
        }
        return instance;
    }

    /**
     * Funcao principal
     *
     * @param args
     */
    private GerenciadorArquivo() {
        arvorePrincipal = new TreeSet<>();
        cabecalho = new ArrayList<>();
    }

    public void IniciaGerenciador() {
        JanelaServidor.getInstance().AddMensagemLog("Criando indices", -1);

        Leitor lt = new Leitor();
        lt.LeEProcessa(ARQUIVO1);

        Escritor that = new Escritor();
        that.escreveIndice();
        that.escreveDados();

        JanelaServidor.getInstance().AddMensagemLog("Indice criado", -1);
    }

    /**
     * @return o cabeçalho
     */
    public ArrayList<String> getCabecalho() {
        return cabecalho;
    }

    /**
     * @return a árvore principal contendo todos os dados
     */
    public TreeSet<Dado> getArvore() {
        return arvorePrincipal;
    }

    /**
     * @return a lista contendo todos as quantidades de caracteres dos items do
     * arquivo
     */
    public int[] getValoresMaiores() {
        return valoresMaiores;
    }

    /**
     * @return o numero de items do arquivo
     */
    public int getNumeroDados() {
        return numeroDados;
    }

    /**
     * @param novosValores atualiza os valores
     */
    public void setValoresMaiores(int[] novosValores) {
        valoresMaiores = novosValores;
    }

    /**
     * @param n atualiza o numero de dados que o arquivo de entrada possui por
     * instancia
     *
     */
    public void setNumeroDados(int n) {
        numeroDados = n;
    }

    String FindIndex(String arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
