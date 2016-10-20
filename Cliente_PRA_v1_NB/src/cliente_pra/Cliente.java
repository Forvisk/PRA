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

    private static ClienteFramer frame;

    private Cliente () {
    }

    public static Cliente getInstance () {
        if ( instancia == null ) {
            instancia = new Cliente ();
        }
        return instancia;
    }

    public void inicio () {
        System.out.println ( "Vai" );
        serverAddress = JOptionPane.showInputDialog ( "IP servidor:" );
        System.out.println ( serverAddress );
        try {
            socket = new Socket ( serverAddress, 2300 );
            frame = new ClienteFramer ();
            frame.setVisible ( true );
            new Thread () {
                @Override
                public void run () {
                    while (true) {
                        receber ();
                    }
                }
            }.start ();
        } catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    public static void enviar ( String message ) {
        PrintWriter output;
        try {
            output = new PrintWriter ( socket.getOutputStream (), true );
            message = Producao.getInstance ().correctMessage ( message );
            
            if (message == null){
                JOptionPane.showMessageDialog ( null, "Sintaxe errada. Comandos aceitos:\nfind;indice\ndelete;indice\ninsert;indice;coisas\nedit;indice;coisas");
                return;
            }

            if ( !message.equals ( "ERRO" ) ) {
                output.println ( message );
            }

        } catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    private void receber () {
        try {
            BufferedReader input = new BufferedReader ( new InputStreamReader ( socket.getInputStream () ) );
            String answer = input.readLine ();
            System.out.println ( answer );
            if ( Producao.getInstance ().messageToRead ( answer ) ) {
                System.out.println ( "OK" );
            } else {
                System.out.println ( "NO OK" );
            }
        } catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    public static Socket getSocket () {
        return socket;
    }

    public String getIpServer () {
        return serverAddress;
    }

    public String getUsername () {
        return username;
    }
    
    public void Disconnect(){
        try {
            socket.close ();
        }
        catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName() ).log ( Level.SEVERE, null, ex );
        }
    }

}
