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
 *
 * @author Gustavo
 * @author Adriano
 */
class PRAServidorArquivo {

    /**
     * Porta em que o servidor será executado
     * <p>
     * De preferência, usar porta 60000 para cima
     */
    public static final int PORTA = 62469;

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

        // Atribuimos uma string a um retorno. No caso, uma função IMetodo.
        // Find encontra um indice
        // O indice esta em args[1]
        mapMetodos.put ( "find", ( IMetodo ) ( String[] args )
                -> GerenciadorArquivo.getInstance ().FindIndex (
                        Integer.parseInt ( args[ 1 ] )
                )
        );
    }

}
