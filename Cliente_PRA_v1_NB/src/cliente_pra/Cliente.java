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
 * <h1>Cliente</h1>
 * Esta classe executa a conexão entre cliente e servidor
 * <p>
 * 
 * @author Adriano Zanella Junior
 * @author Gustavo Diel
 */
class Cliente {

    private static final String SEPARADOR = ";";
    
    /**
     * Construtor
     */
    private Cliente () {
    }

    /**
     * Instancia da graça para quem quiser
     */
    public static Cliente getInstance () {
        if ( instancia == null ) {
            instancia = new Cliente ();
        }
        return instancia;
    }

    private static Cliente instancia;

    private Socket socket;
    private String enderecoIPV4Servidor;

    /**
     * Inicia a conexão com o servidor
     * @param ip é o ip do servidor a ser conectado, se o ip for nulo, o que ocorrerá no 
     * inicio da execução do programa, será pedido o IP do servidor, caso contrario,
     * ele usara o enderecoIPV4Servidor ja existente
     */
    public void iniciarConexao ( String ip ) {
        if ( ip == null ) {
            enderecoIPV4Servidor = JOptionPane.showInputDialog ( "IP servidor:" );
        }

        try {
            socket = new Socket ( enderecoIPV4Servidor, 2300 );
            ClienteFramer.getInstance ().setVisible ( true );
            new Thread () {
                @Override
                public void run () {
                    while ( true ) {
                        if ( !escutarPorMensagemDoServidor () ) {
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

    /**
     * Envia uma mensagem para o servidor
     * @param comando recebe o comando a ser enviado
     * @param message recebe um codigo junto do comando
     */
    public void enviarMensagemParaServidor ( String comando, String message ) {
        PrintWriter output;
        try {
            output = new PrintWriter ( socket.getOutputStream (), true );
            output.println ( comando + SEPARADOR + message );
        }
        catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    /**
     * Verifica as mensagens que o servidor envia
     * @return o estado do servidor:
     *      false caso a conexão tenha sido perdida;
     *      true caso a conexão esteja ativa
     */
    private boolean escutarPorMensagemDoServidor () {
        try {
            BufferedReader input = new BufferedReader ( new InputStreamReader ( socket.getInputStream () ) );
            String answer = input.readLine ();
            if ( answer == null ) {
                processaServidorCaido ();
                return false;
            }
            Producao.getInstance ().messageToRead ( answer );
            return true;
        }
        catch ( IOException ex ) {
            processaServidorCaido ();
            return false;
        }
    }

    /**
     * Função para  tentar reconectar com o servidor, fechando a conexão antiga 
     * e tentando reconectar
     */
    private void processaServidorCaido () {
        try {
            JOptionPane.showMessageDialog ( ClienteFramer.getInstance (), "Servidor caiu!" );
            socket.close ();
            socket = null;
            ClienteFramer.getInstance ().fecha ();
            Cliente.getInstance ().iniciarConexao ( enderecoIPV4Servidor );
        }
        catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    public Socket getSocket () {
        return socket;
    }

    public String getIpServer () {
        return enderecoIPV4Servidor;
    }

    public void disconectarCliente () {
        try {
            socket.close ();
        }
        catch ( IOException ex ) {
            Logger.getLogger ( Cliente.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

}
