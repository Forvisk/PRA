/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FreeClientePackage;

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
 * @author Adriano Zanella jr
 * @author Gustavo Diel
 */
public class FreeCliente {

    private static final String SEPARADOR = ";";

    private static final String TURN_COMMAND = "turn";
    private static final String PYECE_COMMAND = "setPiece";
    private static final String SETGAME_COMMAND = "refreshGame";
    private static final String NEWOPONENT_COMMAND = "oponetFound";
    private static final String ENDGAME_COMMAND = "endGame";
    private static final String USERNAMESET_COMMAND = "setUsername";
    private static final String VALIDPLAY_COMMAND = "falidatePlay";

    private static final String OK_SERV = "ok";
    private static final String NOTOK_SERV = "notOk";

    private static final String P_X_1 = "X";
    private static final String P_O_2 = "O";
    private static final String P_VOID = "-";

    private static final String G_VICTORY = "V";
    private static final String G_LOSS = "L";
    private static final String G_DRAWN = "D";
    private static final String G_NOT = "N";

    private static final String GIVEUP_MESS = "giveUp";
    private static final String PLAY_MESS = "play";
    
    private static final String EXITGAME_TOSERV = "exitGame";
    private static final String GIVEUP_TOSERV = "giveUp";

    private static Socket socket;
    private static final int N_SOCKET = 62469;
    private String username;
    private String enemyUsername;
    private static String serverAddress;

    private static FreeCliente instancia;

    private boolean gameOn = false;
    private boolean goOn = true;
    private short myPiece = 0;
    private short turn = 0;

    private FreeCliente() {
        inicio();
    }

    public static FreeCliente getInstance() {
        if (instancia == null) {
            instancia = new FreeCliente();
        }
        return instancia;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private void inicio() {
        serverAddress = JOptionPane.showInputDialog("IP servidor");
        try {
            socket = new Socket(serverAddress, N_SOCKET);
            //System.out.println("oi");
            connectToServer();
            InterfaceFreeCliente.getInstance().refreshGame(" : : : : : : : : ");
            InterfaceFreeCliente.getInstance().setPlayStatus("Sem jogadas");
            changeTurn(0);
            while (true) {
                receptMessage();
            }
        } catch (IOException ex) {
            //System.out.println("bays");
            Logger.getLogger(FreeCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void connectToServer() {
        try {
            String message;
            boolean isOkToContinue = false;
            PrintWriter output;
            output = new PrintWriter(socket.getOutputStream(), true);
            do {
                message = JOptionPane.showInputDialog("Insira seu nome de usuario que o identifica-rá:");
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String answer, campos[];
                answer = input.readLine();
                campos = answer.split(SEPARADOR);

                if (campos[0].equals(USERNAMESET_COMMAND)) {
                    if (campos[1].equals(OK_SERV)) {
                        isOkToContinue = true;
                    }
                }

            } while (!isOkToContinue);
            username = message;
        } catch (IOException ex) {
            Logger.getLogger(FreeCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initiateGame() {
        gameOn = true;
        InterfaceFreeCliente.getInstance().refreshGame(" : : : : : : : : ");
        InterfaceFreeCliente.getInstance().setPlayStatus("Sem jogadas");
        changeTurn(0);
    }

    public void endGame(String message) {
        gameOn = false;
        turn = 0;
        if( message.equals(G_VICTORY)){
            JOptionPane.showMessageDialog(null, "vitoria");
        }else if( message.equals(G_LOSS)){
            JOptionPane.showMessageDialog(null, "!!DERROTA!!");
        }else if( message.equals(G_DRAWN)){
            JOptionPane.showMessageDialog(null, "empate");
        }else if( message.equals(G_NOT)){
            JOptionPane.showMessageDialog(null, "jogo finalizado");
        }
        InterfaceFreeCliente.getInstance().refreshGame(" : : : : : : : : ");
        changeTurn(0);
    }

    public void receptMessage() {
        BufferedReader input;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = input.readLine();
            System.out.println(message);
            fixInputMessage(message);
        } catch (IOException ex) {
            Logger.getLogger(FreeCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fixInputMessage(String message) {
        String campos[] = message.split(SEPARADOR);

        String ordem = campos[0];
        switch (ordem) {
            case TURN_COMMAND:
        switch (campos[1]) {
            case P_X_1:
                changeTurn(1);
                break;
            case P_O_2:
                changeTurn(2);
                break;
            case P_VOID:
                changeTurn(0);
                break;
            default:
                break;
        }
                break;
            case NEWOPONENT_COMMAND:
                setOponent(campos[1]);
                break;
            case ENDGAME_COMMAND:
                endGame(campos[1]);
                break;
            case PYECE_COMMAND:
                switch (campos[1]) {
                    case P_X_1:
                        setMyPiece(1);
                        break;
                    case P_O_2:
                        setMyPiece(2);
                        break;
                    case P_VOID:
                        setMyPiece(0);
                        break;
                    default:
                        break;
                }
                break;
            case SETGAME_COMMAND:
                InterfaceFreeCliente.getInstance().refreshGame(campos[1]);
                break;
            case VALIDPLAY_COMMAND:
                String validate;
                if(campos[1].equals(OK_SERV))
                    validate = "Jogada valida";
                else
                    validate = "Jogada invalida";
                InterfaceFreeCliente.getInstance().setPlayStatus(validate);
                break;
            default:
                break;
        }
    }

    private void atualizaJogo(String message) {

    }

    private void validatePlay(String message) {
        if (message.equals(OK_SERV)) {

        }
    }

    public boolean ifMyTurn() {
        return myPiece == turn;
    }

    public void fixChoice(int linha, int coluna) {
        String message = PLAY_MESS.concat(SEPARADOR);
        switch (linha) {
            case 1:
                switch (coluna) {
                    case 1:
                        sentChoice(message.concat("1;1"));
                        break;
                    case 2:
                        sentChoice(message.concat("1;2"));
                        break;
                    case 3:
                        sentChoice(message.concat("1;3"));
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalido");
                        break;
                }
                break;
            case 2:
                switch (coluna) {
                    case 1:
                        sentChoice(message.concat("2;1"));
                        break;
                    case 2:
                        sentChoice(message.concat("2;2"));
                        break;
                    case 3:
                        sentChoice(message.concat("2;3"));
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalido");
                        break;
                }
                break;
            case 3:
                switch (coluna) {
                    case 1:
                        sentChoice(message.concat("3;1"));
                        break;
                    case 2:
                        sentChoice(message.concat("3;2"));
                        break;
                    case 3:
                        sentChoice(message.concat("3;3"));
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalido");
                        break;
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalido");
                break;
        }
    }

    private void sentChoice(String message) {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            output.println(message);
        } catch (IOException ex) {
            Logger.getLogger(FreeCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void changeTurn(int i) {
        turn = (short) i;
        InterfaceFreeCliente.getInstance().mudouOTurno(turn, myPiece);
    }

    public void giveUp() {
        try {
            PrintWriter output = new PrintWriter( socket.getOutputStream(), true);
            output.println(GIVEUP_TOSERV);
        } catch (IOException ex) {
            Logger.getLogger(FreeCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void leave() {
        goOn = false;
        try {
            PrintWriter output = new PrintWriter( socket.getOutputStream(), true);
            output.println(EXITGAME_TOSERV);
        } catch (IOException ex) {
            Logger.getLogger(FreeCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Saindo...");
        System.exit(0);
    }

    private void setMyPiece(int i) {
        myPiece = (short) i;
    }

    private void setOponent(String campo) {
        enemyUsername = campo;
        InterfaceFreeCliente.getInstance().setEnemyName( enemyUsername);
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public String getUsername() {
        return username;
    }

    public static Socket getSocket() {
        return socket;
    }

    public String getEnemyUsername() {
        return enemyUsername;
    }

    public short getTurn() {
        return turn;
    }

    public short getPlayerTurn() {
        return myPiece;
    }
}
