package praservidorarquivo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Gerenciador Servidor</h1>
 * Classe destinada a gerenciar as conexões a serem realizadas. Contém algumas
 * constantes que serão usadas nos servidores.
 *
 * @author Adriano
 * @author Gustavo
 */
public class GerenciadorServidor {

    /**
     * {@link String} que será usada para serparar os diversos argumentos que o
     * cliente enviará para o servidor por uma {@link String} pela conexão TCP
     * entre eles
     */
    private static final String STRING_SPLIT = ";";

    /**
     * Instancia atual e única do {@link GerenciadorServidor}
     */
    private static GerenciadorServidor instance;

    /**
     * Retorna a atual e única instancia do {@link GerenciadorServidor}, para
     * nenhum argumento a ser utilizado
     *
     * @return a instancia do {@link GerenciadorServidor}
     */
    public static GerenciadorServidor getInstance () {
        if ( instance == null ) {
            instance = new GerenciadorServidor ( 6653 );
        }
        return instance;
    }

    /**
     * Construtor que possui um argumento para criarmos o servidor em uma
     * determinada porta.
     *
     * @param porta a porta a ser utilizada pelo servidor
     *
     * @return a atual instancia do {@link GerenciadorServidor}
     */
    public static GerenciadorServidor getInstance ( int porta ) {
        if ( instance == null ) {
            instance = new GerenciadorServidor ( porta );
        }
        return instance;
    }

    /**
     * Porta que o servidor está utilizando
     */
    private final int porta;

    /**
     * Váriavel para armazenar o {@link Socket} atual do servidor
     */
    private ServerSocket socket;

    /**
     * Uma {@link List} que contém todos os clientes conectados TODO: retirar
     * clientes que foram desconectados.
     */
    private final List<Socket> clientes;

    private boolean serverClosed = false;

    /**
     * Construtor que precisa de uma porta de rede para iniciar o servidor
     *
     * @param porta é a porta em que o servidor estará atrelado
     */
    private GerenciadorServidor ( int porta ) {
        this.porta = porta;
        clientes = new ArrayList<> ();
        JanelaServidor.getInstance ();
        IniciarListener ();
    }

    /**
     * Inicia o Listener do servidor. Serve para escutar e aceitar todos os
     * clientes e suas mensagens.
     */
    private void IniciarListener () {
        try {
            socket = new ServerSocket ( porta );
            ListenConnections ();
        }
        catch ( IOException ex ) {
            Logger.getLogger ( GerenciadorServidor.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    /**
     * Cria uma Thread que fica aceitando todos os clientes que tentarem se
     * conectar no servidor
     */
    private void ListenConnections () {
        new Thread ( () -> {
            while ( true ) {
                if ( serverClosed ) {
                    return;
                }
                try {
                    Socket cliente = socket.accept ();
                    clientes.add ( cliente );
                    EscutaCliente ( cliente );
                    JanelaServidor.getInstance ().AddMensagemLog ( "Cliente conectado", clientes.indexOf ( cliente ) + 1 );
                }
                catch ( IOException ex ) {
                    System.out.println ( "Socket encerrando.." );
                }
            }
        } ).start ();
    }

    /**
     * Cria uma Thread que escuta as mensagens de um cliente
     *
     * @param cliente {@link Socket} do cliente a ser escutado
     */
    private void EscutaCliente ( Socket cliente ) {
        new Thread ( () -> {
            while ( true ) {
                try {
                    BufferedReader inputCliente = new BufferedReader ( new InputStreamReader ( cliente.getInputStream () ) );
                    String msg = inputCliente.readLine ();
                    if ( msg == null ) {
                        JanelaServidor.getInstance ().AddMensagemLog ( "Cliente caiu", clientes.indexOf ( cliente ) + 1 );
                        clientes.remove ( cliente );
                        break;
                    }
                    JanelaServidor.getInstance ().AddMensagemLog ( msg, clientes.indexOf ( cliente ) + 1 );
                    ProcessaMensagem ( msg, cliente );
                }
                catch ( IOException ex ) {
                    JanelaServidor.getInstance ().AddMensagemLog ( "Cliente caiu", clientes.indexOf ( cliente ) + 1 );
                    Logger.getLogger ( GerenciadorServidor.class.getName () ).log ( Level.SEVERE, null, ex );
                    clientes.remove ( cliente );
                    break;
                }
            }
        } ).start ();
    }

    /**
     * Envia uma mensagem para todos os clientes na
     * {@link List} {@link GerenciadorServidor#clientes}. Não mandaremos a
     * mensagem para o cliente dono da mensagem
     *
     * @param msg     mensagem a ser enviada para os clientes
     * @param cliente fonte da mensagem
     */
    private void EnviaParaOsClientes ( String msg, Socket cliente ) {
        clientes.stream ().filter ( ( c ) -> ( !c.equals ( cliente ) ) ).forEach ( ( c ) -> {
            try {
                PrintWriter paraCliente = new PrintWriter ( c.getOutputStream () );
                paraCliente.write ( msg + "\n" );
                paraCliente.flush ();
                System.out.println ( "Envia msg : " + msg );
            }
            catch ( IOException ex ) {
                Logger.getLogger ( GerenciadorServidor.class.getName () ).log ( Level.SEVERE, null, ex );
            }
        } );
    }

    /**
     * Processa a mensagem do cliente. A estrutura da mensagem aceita é:
     * <p>
     * "funcao;arguemnto_1;argumento_2;..."
     * <p>
     * Sendo que o caractere ';' é o valor da constante
     * {@link GerenciadorServidor#STRING_SPLIT}
     *
     * @param msg     é a mensagem do cliente.
     * @param cliente é o {@link Socket} do cliente.
     */
    private void ProcessaMensagem ( String msg, Socket cliente ) {

        // Vamos processar a mensagem que o cliente enviou
        // Se possuir o formato "algumacoisa;outracoisa..." entao
        // podemos usa-la para descobrir os argumentos
        // Mudei para usar estruturas JSON
        String[] args = msg.split ( STRING_SPLIT, 2 );

        // Se o nr de argumentos for 2 ou mais, entao segue o formato "coisa;outracoisa;..."
        if ( args.length >= 1 ) {

            // Temos que certificar que o argumento que foi mandado é valido!
            if ( PRAServidorArquivo.mapMetodos.containsKey ( args[ 0 ] ) ) {

                // Depois da verificação, podemos prosseguir executando a funcão
                String resposta = PRAServidorArquivo.mapMetodos.get ( args[ 0 ] ).executar ( args );
                JanelaServidor.getInstance ().AddMensagemLog ( "pedido: " + args[ 0 ], clientes.indexOf ( cliente ) + 1 );

                // Agora enviamos a resposta ao cliente
                try {
                    PrintWriter paraCliente = new PrintWriter ( cliente.getOutputStream () );
                    paraCliente.write ( resposta + "\n" );
                    paraCliente.flush ();
                    JanelaServidor.getInstance ().AddMensagemLog ( "Envia msg : " + msg, clientes.indexOf ( cliente ) + 1 );
                }
                catch ( IOException ex ) {

                    // Deu erro ao enviar a mensagem
                    Logger.getLogger ( GerenciadorServidor.class.getName () ).log ( Level.SEVERE, null, ex );
                }
                return;
            }
            try {

                // Argumento que o cliente enviou é invalido
                // Mandamos resposta informando-o
                PrintWriter paraCliente = new PrintWriter ( cliente.getOutputStream () );
                paraCliente.write ( "{\"erro\":1, \"status\": \"argumento_invalido\"}\n" );
                paraCliente.flush ();
                JanelaServidor.getInstance ().AddMensagemLog ( "Enviou msg : " + msg, clientes.indexOf ( cliente ) + 1 );
            }
            catch ( IOException ex ) {
                Logger.getLogger ( GerenciadorServidor.class.getName () ).log ( Level.SEVERE, null, ex );
            }
            return;
        }
        // A mensagem do cliente não segue o formato desejado
        // NUNCA DEVE ACONTECER ~> a não ser que não seja o nosso cliente
        // entao mandaremos uma mensagem de amor <3
        EnviaParaOsClientes ( "Vou comer quibe e coxinha. É o combo quibexinha", cliente );

    }

    /**
     * Fecha a conexão com o servidor. Fecha o socket.
     */
    public void Close () {
        serverClosed = true;
        try {
            socket.close ();
        }
        catch ( IOException ex ) {
            Logger.getLogger ( GerenciadorServidor.class.getName () ).log ( Level.SEVERE, null, ex );
        }
    }

    /**
     * Retorna a lista de clientes conectados
     *
     * @return lista de sockets dos clientes conectados
     */
    public List<Socket> getClientes () {
        return clientes;
    }

}
