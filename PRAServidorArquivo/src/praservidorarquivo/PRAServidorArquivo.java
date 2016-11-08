package praservidorarquivo;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>PRAServidorArquivo</h1>
 * É a classe main que está encarregada
 * de iniciar todas as outras subclasses.
 * <p>
 * Serve também para declarar algumas constantes e preencher o dicionário
 * de funções que o cliente poderá executar
 * <p>
 * <h2>Design Patterns neste projeto:</h2>
 * <h3>Singleton</h3>
 * <h3>Flags</h3>
 * <h3>Factory Method</h3>
 * <h3>Asynchrnous Method Invocation</h3>
 * <h3>Command</h3>
 *
 * @author Gustavo
 * @author Adriano
 */
class PRAServidorArquivo {

    public static final String FIND = "F";
    public static final String EXCLUDE = "D";

    public static final String INCLUDE_REQUEST = "IR";
    public static final String INCLUDE_COMMIT = "IC";

    public static final String MODIFY_REQUEST = "MR";
    public static final String MODIFY_COMMIT = "MC";

    /**
     * Porta em que o servidor será executado
     * <p>
     * De preferência, usar porta 60000 para cima
     */
    public static final int PORTA = 2300;

    /**
     * {@link Map} que será usado para guardar todos os métodos que
     * o cliente poderá executar, usando como hash uma {@link String}.
     */
    public static Map<String, IMetodo> mapMetodos;

    /**
     * Funcao para carregar todo o aplicativo. Inicia a {@link JanelaServidor},
     * o
     * {@link GerenciadorArquivo} e {@link GerenciadorServidor}.
     *
     * @param args lista de {@link String} que receberá caso iniciar por linha
     *             de comando.
     */
    public static void main ( String[] args ) {

        JanelaServidor.getInstance ();

        PopulateMetodos ();

        GerenciadorArquivo ga = GerenciadorArquivo.getInstance ();
        ga.IniciaGerenciador ();
        GerenciadorServidor gs = GerenciadorServidor.getInstance ( PORTA );
    }

    /**
     * Cria todos os métodos que o cliente poderá mandar para o servidor.
     * Definido como um {@link Map} de {@link String} e {@link IMetodo}.
     */
    private static void PopulateMetodos () {
        mapMetodos = new HashMap<> ();   // Alocamos o mapa.

        /*
         * Atribuimos uma string a um retorno. No caso, uma função IMetodo.
         * FIND encontra um indice
         * O indice esta em args[1]
         */
        mapMetodos.put ( FIND, ( IMetodo ) ( String[] args ) -> {
            return GerenciadorArquivo.getInstance ().getDadoAtIndexAsString (
                    Integer.parseInt ( args[ 1 ] )
            );
        } );

        /*
         * EXCLUDE deleta um indice ( não permanentemente )
         * O indice esta em args[1]
         */
        mapMetodos.put ( EXCLUDE, ( IMetodo ) ( String[] args ) -> {
            return GerenciadorArquivo.getInstance ().delDado (
                    Integer.parseInt ( args[ 1 ] )
            );
        } );

        /*
         * INCLUDE_REQUEST faz um pedido para iniciar a inclusão de um dado
         * O servidor deve retornar uma string contendo todos os campos que o
         * cliente deve preencher
         */
        mapMetodos.put ( INCLUDE_REQUEST, ( IMetodo ) ( String[] args ) -> {
            return GerenciadorArquivo.getInstance ().requestInsercao (
                    args
            );
        } );

        /*
         * MODIFY_REQUEST é para quando o cliente pede para editar um dado
         * O servidor deve entregar o dado para o cliente e travar o dado para
         * edições
         */
        mapMetodos.put ( MODIFY_REQUEST, ( IMetodo ) ( String[] args ) -> {
            return GerenciadorArquivo.getInstance ().requestModifica (
                    Integer.parseInt ( args[ 1 ] )
            );
        } );

        /*
         * INCLUDE_COMMIT o cliente comita ( finaliza ) a inclusao do dado.
         * O servidor deve incluir o dado e retornar seu indice
         *
         * TODO: Precisamos fazer a checagem dos dados!!!!
         */
        mapMetodos.put ( INCLUDE_COMMIT, ( IMetodo ) ( String[] args ) -> {
            return GerenciadorArquivo.getInstance ().insertDado (
                    args
            );
        } );

        /*
         * MODIFY_COMMIT o cliente comita ( finaliza ) a edição de algum dado
         * O servidor deve destravar o dado e realizar as alterações
         */
        mapMetodos.put ( MODIFY_COMMIT, ( IMetodo ) ( String[] args ) -> {
            String[] valores = new String[ args.length - 1 ];
            for ( int i = 1; i < args.length; i++ ) {
                valores[ i - 1 ] = args[ i ];
            }
            return GerenciadorArquivo.getInstance ().modificaDado (
                    valores
            );
        } );

    }

}
