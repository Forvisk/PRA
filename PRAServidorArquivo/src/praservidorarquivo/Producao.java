package praservidorarquivo;

import java.util.ArrayList;

/**
 * <h1>Producao</h1>
 * Classe para transportar os dados para a árvore principal
 *
 * @author Adriano
 * @author Gustavo
 */
public class Producao {

    /**
     * Adiciona os valores de uma linha na árvore
     *
     * @param linha   indica a linha no arquivo do dado
     * @param valores contem os dados
     */
    public static void AdicionaNaArvore ( int linha, String[] valores ) {
        ArrayList<String> coisas = new ArrayList<> ();
        for ( int i = 0; i < valores.length; ++i ) {
            coisas.add ( valores[ i ] );
        }
        Dado novoDado = new Dado ( coisas, linha );
        GerenciadorArquivo.getInstance ().getArvore ().add ( novoDado );
    }

}
