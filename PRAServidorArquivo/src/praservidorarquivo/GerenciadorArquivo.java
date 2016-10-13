package praservidorarquivo;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final TreeSet<Dado> arvorePrincipal;

    /**
     * Cabecalho do arquivo a ser processado.
     * <p>
     * Exemplo: Nome, id, email, idade, etc.
     */
    private final ArrayList<String> cabecalho;

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
    private int numeroDados = 0;

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
    public void setValoresMaiores ( final int[] novosValores ) {
        valoresMaiores = novosValores;
    }

    /**
     * Atualiza a variavel {@link GerenciadorArquivo#numeroDados}
     *
     * @param n atualiza o numero de dados que o arquivo de entrada possui por
     *          instancia
     */
    public void setNumeroDados ( final int n ) {
        numeroDados = n;
    }

    /**
     * Retorna o conteúdo da linha em um determinado indice.
     *
     * @param arg é o indice da linha
     *
     * @return JSON de resposta ao cliente
     */
    String getDadoAtIndexAsString ( final int _indice ) {

        Dado dado = getDadoAtIndex ( _indice );

        if ( dado == null ) {
            return String.format ( "{\"status\": \"Dado não existe no indice especificado: %d\", \"erro\": 1}", _indice );
        }

        StringBuilder sb = new StringBuilder ();

        sb.append ( "{\"indice\": " );
        sb.append ( _indice );
        sb.append ( ", \"dados\": [" );

        dado.getDadosDaLinha ().stream ().forEach ( ( s ) -> {
            sb.append ( String.format ( "\"%s\"", s ) );
        } );

        sb.append ( "]}" );

        String out = new String ( sb );
        System.out.println ( out );
        JanelaServidor.getInstance ().AddMensagemLog ( out, -1 );

        return out;
    }

    /**
     * Deleta um dado ( não permanentemente )
     *
     * @param _indice indice do dado
     *
     * @return JSON de resposta ao cliente
     */
    public String delDado ( final int _indice ) {
        Dado dado = getDadoAtIndex ( _indice );

        if ( dado == null ) {
            return String.format ( "{\"status\": \"Dado não existe no indice especificado: %d\", \"erro\": 1}", _indice );
        }

        dado.setDeletado ( true );

        return String.format ( "{\"status\": \"Dado %d deletado\"}", _indice );
    }

    /**
     *
     * @param valores
     *
     * @return
     */
    public String insertDado ( final String[] valores ) {
        Producao.AdicionaNaArvore ( getArvore ().size (), valores );
        return String.format ( "{\"status\": \"Dado inserido\"}" );
    }

    /**
     *
     * @param _indice
     * @param valores
     *
     * @return
     */
    public String modificaDado ( final int _indice, final String[] valores ) {
        
        Dado dado = getDadoAtIndex ( _indice );

        if ( dado == null ) {
            return String.format ( "{\"status\": \"Dado não existe no indice especificado: %d\", \"erro\": 1}", _indice );
        }
        
        ArrayList<String> lista = new ArrayList<>();
        
        lista.addAll ( Arrays.asList ( valores ) );
        
        dado.setValores ( lista );
        
        return String.format ( "{\"status\": \"Dado inserido\"}" );
    }

    /**
     * Dado um indice, procura a linha deste dado.
     *
     * @param index indice da linha a ser procurada
     *
     * @return Dado encontrado, null se não existir
     */
    private Dado getDadoAtIndex ( final int index ) {
        Dado ret = null;
        for ( Dado d : getArvore () ) {
            if ( d.getNumeroLinha () == index && d.isDeletado () == false ) {
                ret = d;
            }
        }
        return ret;
    }

}
