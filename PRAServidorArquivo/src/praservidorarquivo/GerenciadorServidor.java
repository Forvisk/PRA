/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author usuario
 */
public class GerenciadorServidor {

    private static final String STRING_SPLIT = ";";

    private static GerenciadorServidor instance;

    public static GerenciadorServidor getInstance() {
        if (instance == null) {
            instance = new GerenciadorServidor(6653);
        }
        return instance;
    }

    public static GerenciadorServidor getInstance(int porta) {
        if (instance == null) {
            instance = new GerenciadorServidor(porta);
        }
        return instance;
    }

    private int porta;
    private ServerSocket socket;
    private List<Socket> clientes;

    private GerenciadorServidor(int porta) {
        this.porta = porta;
        clientes = new ArrayList<>();
        JanelaServidor.getInstance();
        IniciarListener();
    }

    public void IniciarListener() {
        try {
            socket = new ServerSocket(porta);
            ListenConnections();
        } catch (IOException ex) {
            Logger.getLogger(GerenciadorServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ListenConnections() {
        new Thread() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Socket cliente = socket.accept();
                        clientes.add(cliente);
                        EscutaCliente(cliente);
                        JanelaServidor.getInstance().AddMensagemLog("Cliente conectado", clientes.indexOf(cliente) + 1);
                    } catch (IOException ex) {
                        Logger.getLogger(GerenciadorServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

    private void EscutaCliente(Socket cliente) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        BufferedReader inputCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        String msg = inputCliente.readLine();
                        if (msg == null) {
                            JanelaServidor.getInstance().AddMensagemLog("Cliente caiu", clientes.indexOf(cliente));
                            break;
                        }
                        JanelaServidor.getInstance().AddMensagemLog(msg, clientes.indexOf(cliente));
                        ProcessaMensagem(msg, cliente);
                    } catch (IOException ex) {
                        JanelaServidor.getInstance().AddMensagemLog("Cliente caiu", clientes.indexOf(cliente));
                        Logger.getLogger(GerenciadorServidor.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                }
            }
        }.start();

    }

    private void EnviaParaOsClientes(String msg, Socket cliente) {
        for (Socket c : clientes) {
            if (!c.equals(cliente)) {
                try {
                    PrintWriter paraCliente = new PrintWriter(c.getOutputStream());
                    paraCliente.write(msg + "\n");
                    paraCliente.flush();
                    System.out.println("Envia msg : " + msg);
                } catch (IOException ex) {
                    Logger.getLogger(GerenciadorServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void ProcessaMensagem(String msg, Socket cliente) {
        // Vamos processar a mensagem que o cliente enviou
        // Se possuir o formato "algumacoisa;outracoisa;..." entao
        // podemos usa-la para descobrir os argumentos
        String[] args = msg.split(STRING_SPLIT);
        
        // Se o nr de argumentos for 2 ou mais, entao segue o formato "coisa;outracoisa;..."
        if (args.length > 1) {
            
            // Temos que certificar que o argumento que foi mandado é valido!
            if (PRAServidorArquivo.mapMetodos.containsKey(args[0])) {
                
                // Depois da verificação, podemos prosseguir executando a funcão
                String resposta = PRAServidorArquivo.mapMetodos.get(args[0]).method(args);
                JanelaServidor.getInstance().AddMensagemLog("pedido: " + args[0], clientes.indexOf(cliente));
                
                // Agora enviamos a resposta ao cliente
                try {
                    PrintWriter paraCliente = new PrintWriter(cliente.getOutputStream());
                    paraCliente.write(resposta + "\n");
                    paraCliente.flush();
                    JanelaServidor.getInstance().AddMensagemLog("Envia msg : " + msg, clientes.indexOf(cliente));
                } catch (IOException ex) {
                    
                    // Deu erro ao enviar a mensagem
                    Logger.getLogger(GerenciadorServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try{
                // Argumento que o cliente enviou é invalido
                // Mandamos resposta informando-o
                PrintWriter paraCliente = new PrintWriter(cliente.getOutputStream());
                paraCliente.write("argumento_invalido\n");
                paraCliente.flush();
                JanelaServidor.getInstance().AddMensagemLog("Enviou msg : " + msg, clientes.indexOf(cliente));
                } catch (IOException ex) {
                    Logger.getLogger(GerenciadorServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            
            // A mensagem do cliente não segue o formato desejado
            EnviaParaOsClientes("Vou comer kibe e coxinha. É o combo kibexinha", cliente);
        }
    }

    String FindIndex(String arg) {
        return "{\"titulo\": \"Estamos procurando a linha " + arg + "\","
                + "\"coisa\": \"legal\"}";
    }

}
