package praservidorarquivo;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * <h1>Utilitarios</h1>
 * Classe feita para conter algumas funções de utilidades diversas para
 * facilitar o acesso.
 *
 * @author Gustavo
 */
public class Utilitarios {

    /**
     * Informa a quantidade de digitos que um inteiro possui
     *
     * @param i inteiro diferente de 0
     *
     * @return quantidade de digitos do numero i
     */
    public static int getNumberOfDigits ( int i ) {
        if ( i > 0 ) {
            return ( int ) ( Math.log10 ( i ) + 1 );
        }
        if ( i < 0 ) {
            return ( int ) ( Math.log10 ( -i ) + 1 );
        }
        return 0;

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

    /**
     * Transofmra um {@link Dado} em um JSONObject para podermos mandar para o
     * cliente.
     *
     * @param dado Dado a ser transformado
     *
     * @return string em formato JSON
     */
    public static String dadoToJSONString ( Dado dado ) {
        StringBuilder sb = new StringBuilder ();
        int i = 0;
        for ( String cab : GerenciadorArquivo.getInstance ().getCabecalho () ) {
            sb.append ( String.format ( "\"%s\": \"%s\",", cab, dado.getDadosDaLinha ().get ( i++ ).replace ( "\"", "\\\"" ) ) );
        }
        return new String ( sb );
    }

    /**
     * Transforma uma string, como por exemplo:
     * <p>
     * {@code {"status": 1, "mensagem": "que coisa mais linda"}}
     * <p>
     * em um {@link JSONObject} para podermos percorrer e encontrar as
     * informações facilmente.
     * O {@link JSONObject} funciona como um {@link java.util.Map}
     *
     * @param stringAProcessar string a ser processada.
     *
     * @return JSONObject que contem todos as informações
     */
    public static JSONObject stringToJSON ( String stringAProcessar ) {
        try {
            JSONParser parser = new JSONParser ();
            JSONObject json = ( JSONObject ) parser.parse ( stringAProcessar );
            return json;
        }
        catch ( ParseException ex ) {
            Logger.getLogger ( Utilitarios.class.getName () ).log ( Level.SEVERE, null, ex );
        }
        return new JSONObject ();
    }

}
