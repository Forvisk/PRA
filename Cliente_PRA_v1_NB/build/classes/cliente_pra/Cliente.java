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

    private Socket socket;
    private String username;
    private String password;
    private String serverAddress;

    private static Cliente instancia = null;

    private ClienteFramer frame;

    private Cliente () {
    }

    public static Cliente getInstance () {
        if ( instancia == null ) {
            instancia = new Cliente ();
        }
        return instancia;
    }

    public void inicio ( String ip ) {
        System.out.println ( "Vai" );
        if ( ip == null ) {
            serverAddress = JOptionPane.showInputDialog ( "IP servidor:" );
        }
        System.out.println ( serverAddress );
        try {
            socket = new Socket ( serverAddress, 2300 );
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
                Cliente.getInstance ().inicio ( serverAddress );
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
                Cliente.getInstance ().inicio ( serverAddress );
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
        return serverAddress;
    }

    public String getUsername () {
        return username;
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
