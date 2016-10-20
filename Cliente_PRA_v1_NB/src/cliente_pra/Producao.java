/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_pra;

import java.util.Collections;
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

    private static final String FIND = "find";
    private static final String EXCLUDE = "delete";
    private static final String INCLUDE = "include";
    private static final String MODIFY = "modify";
    /*private static final String LEFTPAR = "(";
    private static final String RIGHTPAR = ")";*/
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

    private static Producao instance = null;

    private Producao () {
    }

    public static Producao getInstance () {
        if ( instance == null ) {
            instance = new Producao ();
        }
        return instance;
    }

    public String correctMessage ( String message ) {
        String ordem;
        String key;

        String campos[] = message.split ( SEPARATOR );
        ordem = campos[ 0 ];
        key = campos[ 1 ];
        char cod_message = ifCorrect ( ordem );
        if ( cod_message != COD_ERRO ) {
            return messageToSent ( cod_message, key );
        }
        return null;
    }

    /**
     *
     * @param ordem
     * @return
     */
    @SuppressWarnings ( "ConvertToStringSwitch" )
    private char ifCorrect ( String ordem ) {
        if ( ordem.equals ( FIND ) ) {
            return COD_FIND;
        } else if ( ordem.equals ( EXCLUDE ) ) {
            return COD_EXCLUDE;
        } else if ( ordem.equals ( INCLUDE ) ) {
            return COD_INCLUDE;
        } else if ( ordem.equals ( MODIFY ) ) {
            return COD_MODIFY;
        } else {
            return COD_ERRO;
        }
    }

    private String messageToSent ( char ordem, String key ) {
        String message;
        message = ordem + ( SEPARATOR.concat ( key ) );
        return message;
    }

    /**
     *
     * @param msg
     * @return
     */
    public boolean messageToRead ( String msg ) {
        System.out.println ( "PRocessa: " + msg );
        try {
            JSONParser parser = new JSONParser ();
            JSONObject json = ( JSONObject ) parser.parse ( msg );
            
            if ( json.containsKey ( "erro" ) ) {
                // TODO: Error msg
                JOptionPane.showMessageDialog ( null, json.get ( "status" ));
            } else {
                // TODO: ver se e pra ler, editar ou o que a mensagem.
                Resposta rep = new Resposta (json );
            }

        } catch ( ParseException ex ) {
            Logger.getLogger ( Producao.class.getName () ).log ( Level.SEVERE, null, ex );
        }
        return true;
    }

/*    private String parseJSON ( String msg, int level ) {
        StringBuilder sb = new StringBuilder ();
        sb.append ( String.join ( "", Collections.nCopies ( level, "\t" ) ) ).append ( "{\n" );
        level++;
        try {
            JSONParser parser = new JSONParser ();
            JSONObject json = ( JSONObject ) parser.parse ( msg );
            for ( Object key : json.keySet () ) {
                //based on you key types
                String keyStr = ( String ) key;
                Object keyvalue = json.get ( keyStr );

                //Print key and value
                sb.append ( String.join ( "", Collections.nCopies ( level, "\t" ) ) );
                //for nested objects iteration if required
                if ( keyvalue instanceof JSONObject ) {
                    sb.append ( "\"" ).append ( keyStr ).append ( "\":\n" );
                    sb.append ( parseJSON ( ( ( JSONObject ) keyvalue ).toJSONString (), level + 1 ) );
                } else {
                    sb.append ( "\"" ).append ( keyStr ).append ( "\": \"" ).append ( keyvalue ).append ( "\"\n" );
                }

            }
        } catch ( ParseException ex ) {
            Logger.getLogger ( Producao.class.getName () ).log ( Level.SEVERE, null, ex );
        }
        sb.append ( String.join ( "", Collections.nCopies ( level - 1, "\t" ) ) ).append ( "}\n" );
        return new String ( sb );
    }*/

    @SuppressWarnings ( "ConvertToStringSwitch" )
    private boolean commandChoice ( String dados[] ) {
        int action;
        if ( dados[ 1 ].equals ( FIND ) ) {
            action = 1;
        } else if ( dados[ 1 ].equals ( EXCLUDE ) ) {
            action = 2;
        } else if ( dados[ 1 ].equals ( INCLUDE ) ) {
            action = 3;
        } else if ( dados[ 1 ].equals ( MODIFY ) ) {
            action = 4;
        } else {
            return false;
        }
        if ( dados[ 2 ].equals ( OK ) ) {
            criarTela_v1 ( action, 1 );
        } else if ( dados[ 2 ].equals ( ERRO ) ) {
            criarTela_v1 ( action, 0 );
        } else {
            return false;
        }
        return true;
    }

    private void criarTela_v1 ( int action, int ok ) {

    }

    private void criarTela_v2 ( String cabecalho[] ) {

    }

    private void criarTela_v3 ( String informations[] ) {

    }

}
