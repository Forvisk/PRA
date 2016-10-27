package praservidorarquivo;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import org.json.simple.JSONObject;

/**
 * <h1>Gerenciador Arquivo</h1>
 * Classe feita para preparar e executar a indexação
 * dos arquivos requisitados.
 *
 * @author usuario
 */
public class GerenciadorArquivo {

    private static final String TAG_MODIFICAR = "modify";
    private static final String TAG_DELETAR = "delete";
    private static final String TAG_INSERIR = "insert";
    private static final String TAG_PROCURAR = "find";

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

    HashMap<Socket, Integer> dadosEmEdicao;

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
        dadosEmEdicao = new HashMap<> ();
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
     * @param _indice é o indice da linha
     *
     * @return JSON de resposta ao cliente
     */
    public String getDadoAtIndexAsString ( final int _indice ) {

        Dado dado = getDadoAtIndex ( _indice );

        if ( dado == null || dado.isDeletado () ) {
            return String.format ( "{\"status\":\"Dado não existe no indice especificado: %d\",\"erro\": 1, \"info\": \"" + TAG_PROCURAR + "\"}", _indice );
        }

        StringBuilder sb = new StringBuilder ();
        sb.append ( "{\"indice\": " );
        sb.append ( _indice );
        sb.append ( ", \"status\": {" );
        sb.append ( Utilitarios.dadoToJSONString ( dado ) );
        sb.append ( "}, \"acao\": \"" + TAG_PROCURAR + "\"}" );

        String out = new String ( sb );
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

        if ( dado == null || dado.isDeletado () ) {
            return String.format ( "{\"status\":\"Dado não existe no indice especificado: %d\",\"erro\": 1, \"info\": \"" + TAG_PROCURAR + "\"}", _indice );
        }

        if ( dado.taEditando () ) {
            return "{\"status\": \"Dado esta sendo editado!\", \"erro\": 2, \"info\": \"" + TAG_DELETAR + "\"}";
        }

        dado.setDeletado ( true );
        return String.format ( "{\"status\": \"Dado %d deletado\", \"info\": \"" + TAG_DELETAR + "\"}", _indice );
    }

    /**
     *
     * @param valores
     *
     * @return
     */
    public String insertDado ( final String[] valores ) {
        JSONObject json = Utilitarios.stringToJSON ( valores[ 1 ] );
        JSONObject status = ( JSONObject ) json.get ( "status" );

        String[] listao = new String[ getArvore ().first ().getDadosDaLinha ().size () ];

        for ( Object key : status.keySet () ) {
            //based on you key types
            String keyStr = ( String ) key;
            Object keyValue = status.get ( keyStr );
            int indx = cabecalho.indexOf ( keyStr );
            listao[ indx ] = keyValue.toString ();
        }

        Producao.AdicionaNaArvore ( getArvore ().size (), listao );

        return String.format ( "{\"status\": \"Dado inserido, Indice: %d\", \"info\": \"%s\"}", getArvore ().size (), TAG_INSERIR );
    }

    /**
     *
     * @param valores
     *
     * @return
     */
    public String modificaDado ( final String[] valores ) {

        JSONObject json = Utilitarios.stringToJSON ( valores[ 0 ] );
        JSONObject status = ( JSONObject ) json.get ( "status" );

        int indice = Integer.parseInt ( ( String ) json.get ( "indice" ) );

        Dado dado = getDadoAtIndex ( indice );

        String[] listao = new String[ dado.getDadosDaLinha ().size () ];

        for ( Object key : status.keySet () ) {
            //based on you key types
            String keyStr = ( String ) key;
            Object keyValue = status.get ( keyStr );
            int indx = cabecalho.indexOf ( keyStr );
            listao[ indx ] = keyValue.toString ();
        }

        ArrayList<String> lista = new ArrayList<> ();
        lista.addAll ( Arrays.asList ( listao ) );
        dado.setValores ( lista );

        dado.setEstadoEditando ( false );
        return String.format ( "{\"status\": \"Dado modificado\", \"info\": \"" + TAG_MODIFICAR + "\"}" );
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
        synchronized ( getArvore () ) {
            for ( Dado d : getArvore () ) {
                if ( d.getNumeroLinha () == index ) {
                    ret = d;
                }
            }
        }
        return ret;
    }

    public String requestInsercao ( String[] _args ) {
        StringBuilder sb = new StringBuilder ();

        sb.append ( "{\"status\": {" );
        for ( String s : cabecalho ) {
            sb.append ( "\"" );
            sb.append ( s );
            sb.append ( "\": \"\"," );
        }
        sb.append ( "}, \"acao\": \"" + TAG_INSERIR + "\"}" );

        String out = new String ( sb );
        JanelaServidor.getInstance ().AddMensagemLog ( out, -1 );

        return out;
    }

    public String requestModifica ( int _indice ) {
        Dado dado = getDadoAtIndex ( _indice );

        if ( dado == null || dado.isDeletado () ) {
            return String.format ( "{\"status\":\"Dado não existe no indice especificado: %d\",\"erro\": 1, \"info\": \"" + TAG_PROCURAR + "\"}", _indice );
        }

        if ( dado.taEditando () ) {
            return "{\"status\": \"Dado está sendo editado!\", \"erro\": 2, \"info\": \"" + TAG_MODIFICAR + "\"}";
        }

        dado.setEstadoEditando ( true );

        StringBuilder sb = new StringBuilder ();
        sb.append ( "{\"indice\": " );
        sb.append ( _indice );
        sb.append ( ", \"status\": {" );
        sb.append ( Utilitarios.dadoToJSONString ( dado ) );
        sb.append ( "}, \"acao\": \"" + TAG_MODIFICAR + "\"}" );

        String out = new String ( sb );
        JanelaServidor.getInstance ().AddMensagemLog ( out, -1 );

        return out;
    }

}
