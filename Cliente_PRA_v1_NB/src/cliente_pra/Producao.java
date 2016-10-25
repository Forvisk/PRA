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

    public static final String FIND_REQUEST = "F";
    public static final String EXCLUDE_REQUEST = "D";

    public static final String INCLUDE_REQUEST = "IR";
    public static final String INCLUDE_COMMIT = "IC";

    public static final String MODIFY_REQUEST = "MR";
    public static final String MODIFY_COMMIT = "MC";
    

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
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public boolean messageToRead ( String msg ) {
        try {
            JSONParser parser = new JSONParser ();
            JSONObject json = ( JSONObject ) parser.parse ( msg );
            if ( json.containsKey ( "erro" ) || json.containsKey ( "info" ) ) {
                // TODO: Error msg
                // Done
                JOptionPane.showMessageDialog ( null, json.get ( "status" ) );
            } else {
                // TODO: ver se e pra ler, editar ou o que a mensagem.
                String acao = ( String ) json.get ( "acao" );
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
                        new Resposta ( json, true, FIND_REQUEST );
                        break;

                }
            }

        }
        catch ( ParseException ex ) {
            Logger.getLogger ( Producao.class.getName () ).log ( Level.SEVERE, null, ex );
        }
        return true;
    }

}
