package praservidorarquivo;

import java.util.Collections;

/**
 * <h1>Utilitarios</h1>
 * Classe feita para conter algumas funções de utilidades diversas para
 * facilitar o acesso.
 *
 * @author Gustavo
 */
public class Utilitarios {

    /**
     * @param i inteiro diferente de 0
     *
     * @return quantidade de digitos do numero i
     */
    public static int getNumberOfDigits ( int i ) {
        if ( i > 0 ) {
            return ( int ) ( Math.log10 ( i ) + 1 );
        } else if ( i < 0 ) {
            return ( int ) ( Math.log10 ( -i ) + 1 );
        } else {
            return 0;
        }
    }

    /**
     * Repete uma determinada {@link String} n vezes.
     *
     * @param str String a ser repetida
     * @param n   quantidade de vezes a repetir a String
     *
     * @return a String repetida
     */
    public static String repeatString ( String str, int n ) {
        return String.join ( "", Collections.nCopies ( n, str ) );
    }

}
