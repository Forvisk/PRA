/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_pra;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Adriano Zanella Junior
 * @author Gustavo Diel
 */
class Cliente {
    
    private static Socket socket;
    private String username;
    private String password;
    
    private static Cliente instancia = null;
    private static Producao producao;
    
    Cliente(){
        producao = new Producao();
        inicio();
    }
    
    public static Cliente getInstance(){
        if( instancia == null)
            instancia = new Cliente();
        return instancia;
    }
    
    private void inicio(){
        String serverAddress;
        serverAddress = JOptionPane.showInputDialog( "IP servidor:");
        
        try {
            socket = new Socket ( serverAddress, 62469);
            try {
                while (true){
                    enviar();
                }
            } finally{
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviar() {
        PrintWriter output;
        try {
            output = new PrintWriter( socket.getOutputStream(), true);
            String message;
            message = JOptionPane.showInputDialog("Insira a mensagem:");
            message = producao.correctMessage( message);
            
            if( !message.equals( "ERRO")){
                output.println( message);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Socket getSocket(){
        return socket;
    }
    
    public String getUsername(){
        return username;
    }
    
}
