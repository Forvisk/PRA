/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_pra;

import java.net.Socket;

/**
 *
 * @author Adriano Zanella Junior
 * @author Gustavo Diel
 */
public class Producao {
    private static final String FIND = "find";
    private static final String LEFTPAR = "(";
    private static final String RIGHTPAR = ")";
    private static final String EXCLUDE = "delete";
    private static final String SEPARATOR = ";";
    
    private Socket socket;
    
    private static Producao instance = null;
    
    Producao(){
    }
    
    public static Producao getInstance(){
        if( instance == null)
            instance = new Producao();
        return instance;
    }
    
    
    
    public String correctMessage( String message){
        String ordem = "";
        String key = "";
        
        String campos[] = message.split(SEPARATOR);
        ordem = campos[ 0];
        key = campos[ 1];
        if( ifCorrect( ordem))
            return messageToSent( ordem, key);
        return null;
    }
    
    /**
     *
     * @param ordem
     * @return
     */
    private boolean ifCorrect( String ordem){
        return ordem.equals(FIND) || ordem.equals(EXCLUDE);
    }
    private String messageToSent( String ordem, String key){
        String message = ordem.concat(SEPARATOR.concat(key));
        return message;
    }   
}
