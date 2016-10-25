/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_pra;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Adriano Zanella Junior
 * @author Gustavo Diel
 */
public class Producao {

    public static final String FIND = "F";
    public static final String EXCLUDE = "D";

    public static final String INCLUDE_REQUEST = "IR";
    public static final String INCLUDE_COMMIT = "IC";

    public static final String MODIFY_REQUEST = "MR";
    public static final String MODIFY_COMMIT = "MC";
    /*
     * private static final String LEFTPAR = "(";
     * private static final String RIGHTPAR = ")";
     */
    private static final String SEPARATOR = ";";

    private static final String ERRO = "Erro";
    private static final String OK = "Ok";
    private static final String COMMAND = "Command";
    private static final String INFO = "Info";
    private static final String CABECALHO = "Cabecalho";

    private static final char COD_FIND = 'F';
    private static final char COD_EXCLUDE = 'E';
    private static final char COD_INCLUDE = 'I';
    private static final char COD_MODIFY = 'M';
    private static final char COD_ERRO = '0';

    // Minhas TAG :v
    private static final String TAG_MODIFICAR = "modify";
    private static final String TAG_DELETAR = "delete";
    private static final String TAG_INSERIR = "insert";
    private static final String TAG_PROCURAR = "find";

    private static Producao instance = null;

    private Producao () {
    }

    public static Producao getInstance () {
        if ( instance == null ) {
            instance = new Producao ();
        }
        return instance;
    }

    /**
     *
     * @param msg
     *
     * @return
     */
    public boolean messageToRead ( String msg ) {
        System.out.println ( "PRocessa: " + msg );
        try {
            JSONParser parser = new JSONParser ();
            JSONObject json = ( JSONObject ) parser.parse ( msg );
            System.out.println ( "Vesh" );
            if ( json.containsKey ( "erro" ) || json.containsKey ( "info" ) ) {
                // TODO: Error msg
                // Done
                JOptionPane.showMessageDialog ( null, json.get ( "status" ) );
            } else {
                // TODO: ver se e pra ler, editar ou o que a mensagem.
                String acao = ( String ) json.get ( "acao" );
                System.out.println ( "IMRPIMEEEEEEE: " + acao );
                switch ( acao ) {
                    case TAG_DELETAR:
                        JOptionPane.showMessageDialog ( null, json.get ( "status" ) );
                        break;
                    case TAG_INSERIR:
                        new Resposta ( json, false, INCLUDE_REQUEST );
                        break;
                    case TAG_MODIFICAR:
                        new Resposta ( json, false, MODIFY_REQUEST );
                        break;
                    case TAG_PROCURAR:
                        new Resposta ( json, true, FIND );
                        break;

                }
            }

        }
        catch ( ParseException ex ) {
            Logger.getLogger ( Producao.class.getName () ).log ( Level.SEVERE, null, ex );
        }
        return true;
    }

    /*
     * private String parseJSON ( String msg, int level ) {
     * StringBuilder sb = new StringBuilder ();
     * sb.append ( String.join ( "", Collections.nCopies ( level, "\t" ) )
     * ).append ( "{\n" );
     * level++;
     * try {
     * JSONParser parser = new JSONParser ();
     * JSONObject json = ( JSONObject ) parser.parse ( msg );
     * for ( Object key : json.keySet () ) {
     * //based on you key types
     * String keyStr = ( String ) key;
     * Object keyvalue = json.get ( keyStr );
     *
     * //Print key and value
     * sb.append ( String.join ( "", Collections.nCopies ( level, "\t" ) ) );
     * //for nested objects iteration if required
     * if ( keyvalue instanceof JSONObject ) {
     * sb.append ( "\"" ).append ( keyStr ).append ( "\":\n" );
     * sb.append ( parseJSON ( ( ( JSONObject ) keyvalue ).toJSONString (),
     * level + 1 ) );
     * } else {
     * sb.append ( "\"" ).append ( keyStr ).append ( "\": \"" ).append (
     * keyvalue ).append ( "\"\n" );
     * }
     *
     * }
     * } catch ( ParseException ex ) {
     * Logger.getLogger ( Producao.class.getName () ).log ( Level.SEVERE, null,
     * ex );
     * }
     * sb.append ( String.join ( "", Collections.nCopies ( level - 1, "\t" ) )
     * ).append ( "}\n" );
     * return new String ( sb );
     * }
     */
}
