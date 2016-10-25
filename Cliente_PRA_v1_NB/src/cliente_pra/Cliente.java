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

    private Cliente () {
        
    }

    /**
     * Retorna a instancia do cliente
     * @return única instancia do Cliente
     */
    public static Cliente getInstance () {
        if ( instancia == null ) {
            instancia = new Cliente ();
        }
        return instancia;
    }

    private static Cliente instancia;
    /**
     * Socket para realizar a troca de informações
     */
    private Socket socket;

    /**
     * Endereço IPV4 do servidor
     */
    private String enderecoIPV4Servidor;

    private ClienteFramer frame;

    public void inicio ( String ip ) {
        System.out.println ( "Vai" );
        if ( ip == null ) {
            enderecoIPV4Servidor = JOptionPane.showInputDialog ( "IP servidor:" );
        }
        System.out.println ( enderecoIPV4Servidor );
        try {
            socket = new Socket ( enderecoIPV4Servidor, 2300 );
            frame = ClienteFramer.getInstance ();
            frame.setVisible ( true );
            new Thread () {
                @Override
                public void run () {
                    while ( true ) {
                        if ( !receber () ) {
                            break;
                        }
                    }
                }

            }.start ();
        }
        catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    public void enviar ( String comando, String message ) {
        PrintWriter output;
        try {
            output = new PrintWriter ( socket.getOutputStream (), true );

            output.println ( comando + ";" + message );

        }
        catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    private boolean receber () {
        try {
            BufferedReader input = new BufferedReader ( new InputStreamReader ( socket.getInputStream () ) );
            String answer = input.readLine ();
            if ( answer == null ) {
                JOptionPane.showMessageDialog ( null, "Servidor caiu!" );
                socket.close ();
                socket = null;
                ClienteFramer.getInstance ().fecha ();
                Cliente.getInstance ().inicio ( enderecoIPV4Servidor );
                return false;

            }
            System.out.println ( answer );
            if ( Producao.getInstance ().messageToRead ( answer ) ) {
                System.out.println ( "OK" );
            } else {
                System.out.println ( "NO OK" );
            }
            return true;
        }
        catch ( IOException ex ) {
            try {
                JOptionPane.showMessageDialog ( null, "Servidor caiu!" );
                socket.close ();
                socket = null;
                ClienteFramer.getInstance ().fecha ();
                Cliente.getInstance ().inicio ( enderecoIPV4Servidor );
            }
            catch ( IOException ex1 ) {
                Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex1 );
            }
            return false;
        }
    }

    public Socket getSocket () {
        return socket;
    }

    public String getIpServer () {
        return enderecoIPV4Servidor;
    }

    public void Disconnect () {
        try {
            socket.close ();
        }
        catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

}
