/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_pra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private String serverAddress;
    
    private static Cliente instancia = null;
    private static Producao producao;
    
    private static ClienteFramer frame;
    
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
        serverAddress = JOptionPane.showInputDialog( "IP servidor:");

        try {
            socket = new Socket(serverAddress, 62469);
            frame = new ClienteFramer();
            try {
                
                new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            receber();
                        }
                    }
                }.start();
                
            } finally {
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void enviar( String message) {
        PrintWriter output;
        try {
            output = new PrintWriter( socket.getOutputStream(), true);
            message = producao.correctMessage( message);
            
            if( !message.equals( "ERRO")){
                output.println( message);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void receber() {
        try {
            BufferedReader input = new BufferedReader( new InputStreamReader( socket.getInputStream()));
            String answer = input.readLine();
            System.out.println(answer);
            if( producao.messageToRead(answer)){
                System.out.println("OK");
            }else{
                System.out.println("NO OK");
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Socket getSocket(){
        return socket;
    }
    
    public String getIpServer(){
        return serverAddress;
    }
    
    public String getUsername(){
        return username;
    }
    

    
}
