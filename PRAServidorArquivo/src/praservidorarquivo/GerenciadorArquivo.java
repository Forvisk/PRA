package praservidorarquivo;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * <h1>Gerenciador Arquivo</h1>
 * Classe feita para preparar e executar a indexação
 * dos arquivos requisitados.
 *
 * @author usuario
 */
public class GerenciadorArquivo {

    /**
     * Árvore que conterá os dados, sendo automaticamente balanceada
     * gracas ao {@link TreeSet}. Cada nó da arvore é um {@link Dado}
     */
    private TreeSet<Dado> arvorePrincipal;

    /**
     * Cabecalho do arquivo a ser processado.
     * <p>
     * Exemplo: Nome, id, email, idade, etc.
     */
    private ArrayList<String> cabecalho;

    /**
     * Quantidade máxima de caracteres que um determinado campo pode
     * ter. Trabalha em conjunto do {@link GerenciadorArquivo#cabecalho}
     * <p>
     * Exemplo: se cabecalho[0] = "Nome", e valoresMaiores[0] = 10 então o
     * máximo de caracteres que todos os nomes poderão ter é 10
     */
    private int[] valoresMaiores;

    /**
     * Quantidade de dados que o arquivo possui.
     * <p>
     * Por exemplo, possui 3 dados: Nome, idade e ID.
     */
    private int numeroDados = 0;				// Quantidade de dados

    /**
     * Instancia atual e única da classe.
     */
    private static GerenciadorArquivo instance = null;

    /**
     * Arquivo que contem todos os dados
     */
    private static final String ARQUIVO1 = "Sample - Superstore Sales.csv";

    /**
     * Arquivo que contem outros dados
     */
    private static final String ARQUIVO2 = "returned.csv";

    /**
     * Arquivo que contem os usuários
     */
    private static final String ARQUIVO3 = "users.csv";

    /**
     * Método usado para retornar a instancia unica e atual da classe.
     *
     * @return é a instancia da classe.
     */
    public static GerenciadorArquivo getInstance () {
        if ( instance == null ) {
            instance = new GerenciadorArquivo ();
        }
        return instance;
    }

    /**
     * Construtor, onde serão alocados a {@link TreeSet} e o {@link ArrayList}
     */
    private GerenciadorArquivo () {
        arvorePrincipal = new TreeSet<> ();
        cabecalho = new ArrayList<> ();
    }

    /**
     * Configura e executa todas as classes e métodos necessários
     * para ler e processar os arquivos para guardar eles num arquivo indentado
     * junto de um arquivo de indice.
     */
    public void IniciaGerenciador () {
        JanelaServidor.getInstance ().AddMensagemLog ( "Criando indices", -1 );

        Leitor lt = new Leitor ();
        lt.LeEProcessa ( ARQUIVO1 );

        Escritor that = new Escritor ();
        that.escreveIndice ();
        that.escreveDados ();

        JanelaServidor.getInstance ().AddMensagemLog ( "Indice criado", -1 );
    }

    /**
     * Retorn a variavel {@link GerenciadorArquivo#cabecalho}.
     *
     * @return o cabeçalho
     */
    public ArrayList<String> getCabecalho () {
        return cabecalho;
    }

    /**
     * Retorn o {#link TreeSet} de
     * {@link Dado}, {@link GerenciadorArquivo#arvorePrincipal}.
     *
     * @return a árvore principal contendo todos os dados
     */
    public TreeSet<Dado> getArvore () {
        return arvorePrincipal;
    }

    /**
     * Retorn o vetor {@link GerenciadorArquivo#valoresMaiores}.
     *
     * @return a lista contendo todos as quantidades de caracteres dos items do
     *         arquivo
     */
    public int[] getValoresMaiores () {
        return valoresMaiores;
    }

    /**
     * Retorn o inteiro {@link GerenciadorArquivo#numeroDados}.
     *
     * @return o numero de items do arquivo
     */
    public int getNumeroDados () {
        return numeroDados;
    }

    /**
     * Atualiza o vetor {@link GerenciadorArquivo#valoresMaiores}.
     *
     * @param novosValores novo vetor
     */
    public void setValoresMaiores ( int[] novosValores ) {
        valoresMaiores = novosValores;
    }

    /**
     * Atualiza a variavel {@link GerenciadorArquivo#numeroDados}
     *
     * @param n atualiza o numero de dados que o arquivo de entrada possui por
     *          instancia
     */
    public void setNumeroDados ( int n ) {
        numeroDados = n;
    }

    /**
     * Retorna o conteúdo da linha em um determinado indice.
     *
     * @param arg é o indice da linha
     *
     * @return o conteudo da linha
     */
    String FindIndex ( int arg ) {
        return "{\"titulo\": \"Estamos procurando a linha " + arg + "\","
                + "\"coisa\": \"Happy Face\"}";
    }

}
